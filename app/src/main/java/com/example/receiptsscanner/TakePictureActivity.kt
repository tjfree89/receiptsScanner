package com.example.receiptsscanner

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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



class TakePictureActivity : AppCompatActivity() {
    lateinit var recogResult:TextView
    lateinit var btnChoose:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        val back = findViewById<Button>(R.id.picture_back_button)

        back.setOnClickListener { v ->
            Intent(this, TakePictureActivity::class.java).also {
                finish()
            }
        }


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
    }

    private fun convertImageToText(imageUri: Uri) {
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

        recogResult.text = prices
    }

}
