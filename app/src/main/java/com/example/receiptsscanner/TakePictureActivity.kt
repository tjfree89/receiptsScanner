package com.example.receiptsscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import android.widget.ArrayAdapter
import android.widget.ListView

var intentActivityResultLauncher:ActivityResultLauncher<Intent>?=null

lateinit var inputImage: InputImage
lateinit var textRecognizer:TextRecognizer
private val STORAGE_PERMISSION_CODE=135
private var counter = 0


class TakePictureActivity : AppCompatActivity() {
    lateinit var recogResult:TextView
    lateinit var btnChoose:Button
    lateinit var confirm:Button
    lateinit var getFoods:Button
    lateinit var getRecipe:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        var arrayAdapter: ArrayAdapter<*>
        var receipts = arrayOf<String>()

        var ReceiptListView = findViewById<ListView>(R.id.ReceiptList)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, receipts)
        ReceiptListView.adapter = arrayAdapter

        val back = findViewById<Button>(R.id.picture_back_button)

        back.setOnClickListener { v ->
            Intent(this, TakePictureActivity::class.java).also {
                finish()
            }
        }

        recogResult=findViewById(R.id.recogResult)
        btnChoose=findViewById(R.id.btnChoose)
        confirm=findViewById(R.id.confirm)
        getFoods=findViewById(R.id.get_foods)
        getRecipe=findViewById(R.id.recipe)

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        intentActivityResultLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                //here we handle result
                val data=it.data
                val imageUri=data?.data

                if (imageUri != null) {
                    convertImageToText(imageUri)
                }
            }
        )
        btnChoose.setOnClickListener {
            val chooseIntent = Intent()
            chooseIntent.type="image/*"
            chooseIntent.action=Intent.ACTION_GET_CONTENT
            intentActivityResultLauncher?.launch(chooseIntent)
        }

        confirm.setOnClickListener {
            splitPrice(recogResult.text.toString())
            receipts += recogResult.text.toString()
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, receipts)
            ReceiptListView.adapter = arrayAdapter
        }

        getFoods.setOnClickListener {
            grabFoods(recogResult.text.toString())
            receipts += recogResult.text.toString()
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, receipts)
            ReceiptListView.adapter = arrayAdapter
        }

        getRecipe.setOnClickListener {
            exampleFoodGrab(recogResult.text.toString())
            val intent = Intent(this@TakePictureActivity, PurchaseHistoryActivity::class.java).apply {
                putExtra("item", recogResult.text.toString())
            }
            startActivity(intent)
        }
    }

    private fun convertImageToText(imageUri: Uri) {
        print("Converted")
        try{
            //prepare the input image
            inputImage = InputImage.fromFilePath(applicationContext, imageUri)

            //get text from input image
            val result: Task<Text> = textRecognizer.process(inputImage)
                .addOnSuccessListener {
                    recogResult.text = it.text
                    recogResult.movementMethod = ScrollingMovementMethod()
                }.addOnFailureListener {
                    recogResult.text = "Error : ${it.message}"
                }
        }catch (e:Exception){

        }
    }
    private fun splitPrice(rec: String) {
        val reg = Regex("""[0-9]*\.[0-9]{2} *F""")
        val matches = reg.findAll(rec)
        val prices = matches.map{it.groupValues[0]}.joinToString()

        recogResult.text = prices
    }

    private fun grabFoods(rec: String) {
        val reg = Regex("""\b[A-Z][A-Z]+|\b[A-Z]\b""")
        var matches = reg.findAll(rec)
        val foods = matches.map{it.groupValues[0]}.joinToString()

        recogResult.text = foods
    }

    private fun exampleFoodGrab(rec: String) {
        val reg = Regex("""\b(BLACKBERRIES)\b""")
        var matches = reg.findAll(rec)
        val item = matches.map{it.groupValues[0]}.joinToString()

        recogResult.text = item
    }

//    override fun onResume() {
//        super.onResume()
//        checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
//    }

//    private fun checkPermission(permission: String, requestCode:Int){
//        if(ContextCompat.checkSelfPermission(this@TakePictureActivity, permission)== PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(this@TakePictureActivity, arrayOf(permission), requestCode)
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if(requestCode == STORAGE_PERMISSION_CODE){
//            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this@TakePictureActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this@TakePictureActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }

}
