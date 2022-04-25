package com.example.receiptsscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class RecipeActivity : AppCompatActivity() {
    lateinit var recView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeVal = intent.getStringExtra("recipe")
        val recBack = findViewById<Button>(R.id.recBack)
        recView=findViewById(R.id.recipeView)

        recBack.setOnClickListener { v ->
            Intent(this, TakePictureActivity::class.java).also {
                //startActivity(it)
                finish() // instead of starting a new activity pop current off stack
            }
        }
        exampleFoodGrab(recipeVal.toString())

    }
    private fun exampleFoodGrab(rec: String) {
        val chiliesReg = Regex("""\b(CHILIES)\b""")
        val peppersReg = Regex("""\b(PEPPERS)\b""")
        val chickenReg = Regex("""\b(CHICKEN)\b""")
        var chiliesMatch = chiliesReg.findAll(rec)
        val chilies = chiliesMatch.map { it.groupValues[0] }.joinToString()
        var peppersMatch = peppersReg.findAll(rec)
        val peppers = peppersMatch.map { it.groupValues[0] }.joinToString()
        var chickenMatch = chickenReg.findAll(rec)
        val chicken = chickenMatch.map { it.groupValues[0] }.joinToString()
        recView.movementMethod = ScrollingMovementMethod()

        val pic = findViewById<ImageView>(R.id.recipePictureFrame)
        if (chilies != "") {

            recView.text = "Chili Cheese Dip III\n Cook: 5mins\n Total: 10mins\n" +
                    "Ingredients:\n" +
                    "* 2(8 ounce) packages cream cheese, softened\n" +
                    "* 1(15 ounce) can chili without beans\n" +
                    "* 16 ounces shredded Cheddar cheese\n" +
                    "* 1(13.5 ounce) package nacho-flavor tortilla chips\n\n" +
                    "Directions:\n" +
                    "Step 1\n" +
                    "Spread cream cheese on the bottom of a microwave-safe dish. Spread a layer of chili over the cream cheese. Finish with a layer of shredded cheddar cheese. Microwave for 5 minutes or until the cheese melts. Serve with spicy nacho tortilla chips. "
            pic.setImageResource(R.drawable.chili_cheese)
        } else if (peppers != "") {
            recView.text = "Grandma's Cucumber and Onion Salad\n Cook: 15mins\n" +
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
            pic.setImageResource(R.drawable.cuc_onion)
        } else if (chicken != "") {
            recView.text = "Chicken and Summer Squash\n Cook: 15mins\n" +
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
            pic.setImageResource(R.drawable.chkn_squash)
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when {
            item.itemId == R.id.Logout -> {
                finish()
            }
            item.itemId == R.id.purchaseHistory || item.itemId == R.id.purchaseHistoryText -> {

                Intent(this, RecipeActivity::class.java).also{
                    startActivity(it)
                }
            }
            item.itemId == R.id.takePicture ->{
                Intent(this, TakePictureActivity::class.java).also{
                    startActivity(it)
                }
            }

        }
        return true
    }
}