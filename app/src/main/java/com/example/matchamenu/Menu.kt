package com.example.matchamenu

import android.content.SharedPreferences
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class Menu : AppCompatActivity() {

    lateinit var dishList: MutableList<Dish>

    private val PREF_NAME = "favs"
    private val FAV_STRING = "favs"
    private var favList = ArrayList<String>()
    private var alreadyFav = false;

    companion object {
        const val RESTAURANT_ID = "RESTAURANT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val resId = intent.getStringExtra(RESTAURANT_ID)
        val sharedPref = getSharedPreferences(PREF_NAME,0)
        val arr = getArrayList(FAV_STRING)
        if (arr != null) {
            arr.forEach { fav ->
                Log.d("HOLA", fav)
                Log.d("HOLA", resId.toString())
                if (fav.equals(resId.toString())){
                    btnFav.setImageResource(R.drawable.fav_fill)
                    Log.d("HOLA", "si esta repetido")
                    alreadyFav = true
                    btnFav.isEnabled = false
                }
            }
        }
        btnFav.setOnClickListener {
            if(alreadyFav == true){
                btnFav.isEnabled = false
            }
            if(alreadyFav == false){
                Log.d("HOLA", "si entraa")
                val editor :SharedPreferences.Editor = sharedPref.edit()
                val gson = Gson()
                favList.add(resId)
                val json = gson.toJson(favList)
                editor.putString(FAV_STRING,json)
                editor.commit()
                editor.apply()
                btnFav.setImageResource(R.drawable.fav_fill)
                btnFav.isEnabled = false
                Toast.makeText(this,"¡Añadido a favoritos!",Toast.LENGTH_LONG).show()
            }
        }

        dishList = mutableListOf()

        val adapter = DishAdapter(applicationContext,R.layout.dishes,dishList)
        idMenu.adapter = adapter

        val db = FirebaseFirestore.getInstance()
        val restaRef = db.collection("restaurant")


        restaRef.document("$resId")
            .get()
            .addOnCompleteListener { resta ->
                if (resta != null) {
                    Log.d("HOLA", "DocumentSnapshot data: ${resta.result}")
                    var rResult = resta.result?.data?.get("menu").toString()

                    val obj = JSONArray(rResult)
                    for (i in 0 until obj.length()) {
                        val item = obj.getJSONObject(i)
                        val dishTemp = Dish(
                            item.getString("name"),
                            item.getString("description"),
                            item.getDouble("price")
                            )
                        dishList.add(dishTemp)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d("HOLA", "No such document")
                }
            }

    }
    fun getArrayList(key: String?): ArrayList<String?>? {
        val prefs = getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }
}




