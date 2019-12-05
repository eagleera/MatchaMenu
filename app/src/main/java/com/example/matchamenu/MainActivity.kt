package com.example.matchamenu

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {
    lateinit var restaurantList: MutableList<Restaurant>
    private val PREF_NAME = "favs"
    internal var qrScanIntegrator: IntentIntegrator? = null
    private val FAV_STRING = "favs"


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener { view ->
            performAction()
        }
        qrScanIntegrator = IntentIntegrator(this)
    }

    override fun onResume() {
        super.onResume()
        restaurantList = mutableListOf()
        val arr = getArrayList(FAV_STRING)
        if (arr != null) {
            arr!!.toMutableList()
        }
        val adapter = RestaurantAdapter(applicationContext, R.layout.restaurants, restaurantList)
        listView.adapter = adapter
        val db = FirebaseFirestore.getInstance()
        val restaRef = db.collection("restaurant")
        if (arr != null) {
            arr.forEach { resId ->
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

    private fun performAction() {
        qrScanIntegrator?.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, "Not Found", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    Log.d("Chris", result.contents)
                    startActivity(
                        Intent(this, com.example.matchamenu.Menu::class.java)
                            .putExtra(com.example.matchamenu.Menu.RESTAURANT_ID, result.contents)
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.

                    Log.d("Chris", e.toString())
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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