package com.example.galleryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_row.view.*

class MyAdapter(val data:List<Image>,val clickInterface:ItemClickListener): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
  inner class MyViewHolder(val view:View):RecyclerView.ViewHolder(view){
       init {
           view.setOnClickListener {
                   if(adapterPosition!=RecyclerView.NO_POSITION){
                       clickInterface.onclick(data.get(adapterPosition).uri,adapterPosition)
                   }
           }
       }


   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val imageView=LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false)
        return MyViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val uri=data.get(position).uri
        Glide.with(holder.view).load(uri).into(holder.view.image)
    }

    override fun getItemCount()=data.size

}