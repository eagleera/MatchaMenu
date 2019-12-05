package com.example.matchamenu

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_menu.*
import android.widget.Toast
import com.example.tercerparcial.Dish
import com.example.tercerparcial.DishAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_main.*

class Menu : AppCompatActivity() {

    lateinit var dishList: MutableList<Dish>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    companion object {
        const val RESTAURANT_ID = "RESTAURANT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btnFav.setOnClickListener {
            btnFav.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#EEDCD4"))
            Toast.makeText(this,"Suh",Toast.LENGTH_LONG).show()
        }
        val resId = intent.getStringExtra(RESTAURANT_ID)
        Toast.makeText(this, resId.toString(), Toast.LENGTH_LONG).show()

        dishList = mutableListOf()

        val db = FirebaseFirestore.getInstance()
        val restaRef = db.collection("restaurant")


        restaRef.document("$resId")
            .get()
            .addOnCompleteListener { resta ->
                if (resta != null) {
                    Log.d("HOLA", "DocumentSnapshot data: ${resta.result}")
                } else {
                    Log.d("HOLA", "No such document")
                }
            }

    }
}




