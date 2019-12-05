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


class MainActivity : AppCompatActivity() {
    lateinit var restaurantList: MutableList<Restaurant>
    private val PREF_NAME = "favs"
    internal var qrScanIntegrator: IntentIntegrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, 0)
        val favs: MutableSet<String>? = sharedPref.getStringSet(PREF_NAME, HashSet())
        fab.setOnClickListener { view ->
            performAction()
        }
        qrScanIntegrator = IntentIntegrator(this)
        restaurantList = mutableListOf()
        val db = FirebaseFirestore.getInstance()
        val restaRef = db.collection("restaurant")

//        restaRef.whereIn(restaRef.id, )
//            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                        }
//                    } else {
//                        Log.w(TAG, "Error getting documents.", task.getException());
//                    }
//                }
//        ref = FirebaseDatabase.getInstance().getReference("restaurants")
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if(p0!!.exists()){
//                    for (r in p0.children){
//                        val resta = r.getValue(Restaurant::class.java)
//                        restaurantList.add(resta!!)
//                    }
//                }
//                val adapter = RestaurantAdapter(applicationContext, R.layout.restaurants, restaurantList)
//                listView.adapter = adapter
//            }
//        })
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
                    Log.d("Chris",result.contents)
                    startActivity(Intent(this, com.example.matchamenu.Menu::class.java)
                        .putExtra(com.example.matchamenu.Menu.RESTAURANT_ID, result.contents))
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

    }
