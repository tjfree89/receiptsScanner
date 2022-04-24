package com.example.receiptsscanner

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

var intentActivityResultLauncher:ActivityResultLauncher<Intent>?=null

lateinit var inputImage: InputImage
lateinit var textRecognizer:TextRecognizer

private val STORAGE_PERMISSION_CODE=135


class TakePictureActivity : AppCompatActivity() {
    lateinit var recogResult:TextView
    lateinit var sendResult:String
    lateinit var btnChoose:Button
    lateinit var back:Button
    lateinit var confirm:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        back=findViewById(R.id.picture_back_button)
        confirm=findViewById(R.id.picture_confirm_button)
        recogResult=findViewById(R.id.recogResult)
        btnChoose=findViewById(R.id.btnChoose)

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

        back.setOnClickListener { v ->
            Intent(this, MainActivity::class.java).also{
                finish()
            }
        }

        confirm.setOnClickListener {
            val intent = Intent(this@TakePictureActivity, PurchaseHistoryActivity::class.java).apply {
                putExtra("result", recogResult.text.toString())
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
                    splitPrice(it.text)
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

//        Log.d("Default", rec)
//        Log.d("matches", matches.toString())
//        Log.d("Prices", prices)
        recogResult.text = prices
        sendResult = recogResult.text.toString()
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
