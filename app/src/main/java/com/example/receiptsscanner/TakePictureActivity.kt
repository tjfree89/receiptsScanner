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
        var item = ""
        val chiliesReg = Regex("""\b(CHILIES)\b""")
        val peppersReg = Regex("""\b(PEPPERS)\b""")
        val chickenReg = Regex("""\b(CHICKEN)\b""")
        var chiliesMatch = chiliesReg.findAll(rec)
        val chilies = chiliesMatch.map { it.groupValues[0] }.joinToString()
        var peppersMatch = peppersReg.findAll(rec)
        val peppers = peppersMatch.map { it.groupValues[0] }.joinToString()
        var chickenMatch = chickenReg.findAll(rec)
        val chicken = chickenMatch.map { it.groupValues[0] }.joinToString()

        if (chilies != "") {
            recogResult.text = "Chili Cheese Dip III\n Cook: 5mins\n Total: 10mins\n" +
                    "Ingredients:\n" +
                    "* 2(8 ounce) packages cream cheese, softened\n" +
                    "* 1(15 ounce) can chili without beans\n" +
                    "* 16 ounces shredded Cheddar cheese\n" +
                    "* 1(13.5 ounce) package nacho-flavor tortilla chips\n\n" +
                    "Directions:\n" +
                    "Step 1\n" +
                    "Spread cream cheese on the bottom of a microwave-safe dish. Spread a layer of chili over the cream cheese. Finish with a layer of shredded cheddar cheese. Microwave for 5 minutes or until the cheese melts. Serve with spicy nacho tortilla chips. "
        } else if (peppers != "") {
            recogResult.text = "Grandma's Cucumber and Onion Salad\n Cook: 15mins\n" +
                    " Total: 30mins\n Ingredients:\n" +
                    "* 7 cups sliced cucumbers\n" +
                    "* Salt as needed\n" +
                    "* 1 cup sliced onions\n " +
                    "* 1 cup sliced green bell pepper (Optional) \n" +
                    "Dressing:\n" +
                    "* 2 cups white sugar\n" +
                    "* 1 cup white vinegar\n" +
                    "* 1 tablespoon celery seed (Optional)\n" +
                    "* 2 teaspoons salt, or to taste\n" +
                    "Directions:\n" +
                    "Step 1\n" +
                    "Place cucumber slices in a colander and liberally sprinkle salt over cucumbers; set aside until water starts to release from cucumbers, about 15 minutes. Drain and rinse cucumbers.\n" +
                    "Step 2\n" +
                    "Mix cucumbers, onions, and green bell pepper together in a bowl.\n" +
                    "Step 3\n" +
                    "Whisk sugar, vinegar, celery seed, and 2 teaspoons salt together in a bowl until dressing is smooth; pour over cucumber mixture and stir until evenly coated.\n"
        } else if (chicken != "") {
            recogResult.text = "Chicken and Summer Squash\n Cook: 15mins\n" +
                    " Total: 30mins\n Ingredients:\n" +
                    "* 4 breast half, bone and skin removed (blank)s skinless, boneless chicken breast halves " +
                    "* ½ teaspoon salt\n" +
                    "* ¼ teaspoon ground black pepper\n" +
                    "* 1 tablespoon butter\n" +
                    "* 1 tablespoon vegetable oil\n" +
                    "* ¾ pound yellow squash, sliced\n" +
                    "* ¾ pound zucchinis, sliced\n" +
                    "* 1 medium tomato - peeled, seeded and chopped\n" +
                    "Directions\n" +
                    "Step 1\n" +
                    "In a large nonstick skillet, melt butter in the oil over medium high heat. Season chicken with half of the salt and half of the pepper, and add it to skillet. Cook until lightly browned, about 2 minutes per side. Transfer to large plate or platter, and cover to keep warm.\n" +
                    "Step 2\n" +
                    "Pour off fat from skillet, and add squash, zucchini, and tomato. Season with remaining salt and pepper. Cook and stir over medium-high heat until squash is slightly softened, about 3 minutes. Reduce heat, and return chicken to skillet. Cover partially. Cook until squash is soft, and chicken is white throughout but still juicy, about 5 minutes longer.\n" +
                    "Step 3\n" +
                    "Transfer chicken to platter, and cover with foil to keep warm. Raise heat to high. Cook vegetable mixture, stirring often, until almost all of the liquid has evaporated, about 2 minutes. Arrange vegetables around chicken, and serve.\n"
        }


    }

}
