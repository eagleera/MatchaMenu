package com.example.matchamenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RestaurantAdapter(val mCtx: Context, val layoutResId: Int, val restaurantList: List<Restaurant>)
    : ArrayAdapter<Restaurant>(mCtx, layoutResId, restaurantList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val textViewName = view.findViewById<TextView>(R.id.textViewRestaurant)
        val restaurant = restaurantList[position]
        textViewName.text = restaurant.name
        return view;
    }
}