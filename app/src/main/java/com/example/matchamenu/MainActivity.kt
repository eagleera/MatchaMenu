package com.example.matchamenu

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {
    lateinit var restaurantList: MutableList<Restaurant>
    private val PREF_NAME = "favs"
    private val FAV_STRING = "favs"


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, 0)
        val arrayList = ArrayList<String>()
        arrayList.add("RFpbvqc6AVR7Qsmfn52E")
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(arrayList)
        editor.putString(FAV_STRING, json)
        editor.commit()
        editor.apply()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        restaurantList = mutableListOf()
        val arr = getArrayList(FAV_STRING)
        arr!!.toMutableList()
        val adapter = RestaurantAdapter(applicationContext, R.layout.restaurants, restaurantList)
        listView.adapter = adapter
        val db = FirebaseFirestore.getInstance()
        val restaRef = db.collection("restaurant")
        arr.forEach {resId ->
            Log.d("HOLA", resId.toString())
            restaRef.document("$resId")
                .get()
                .addOnCompleteListener { resta ->
                    if (resta != null) {
                        val restaTemp = Restaurant(
                            "$resId",
                            (resta.result?.data?.get("name")?.toString() ?: "")
                        )
                        restaurantList.add(restaTemp)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.d("HOLA", "No such document")
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the Menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
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
