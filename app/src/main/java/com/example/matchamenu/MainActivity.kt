package com.example.matchamenu

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import sun.jvm.hotspot.utilities.IntArray


class MainActivity : AppCompatActivity() {
    lateinit var restaurantList: MutableList<Restaurant>
    private val PREF_NAME = "favs"


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, 0)
        val favs: MutableSet<String>? = sharedPref.getStringSet(PREF_NAME, HashSet())
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
}
