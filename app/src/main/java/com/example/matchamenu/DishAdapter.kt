package com.example.tercerparcial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.matchamenu.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.dishes.view.*

class DishAdapter(val mCtx: Context,val layoutResId:Int,val  dishList: List<Dish>)
    :ArrayAdapter<Dish>(mCtx,layoutResId,dishList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewPrice= view.findViewById<TextView>(R.id.textViewPrice)


        val dish = dishList[position]

        textViewName.text = dish.name
        textViewPrice.text = dish.price

        return view

    }
}