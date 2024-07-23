package com.example.foodlovers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodlovers.databinding.PopularItemBinding
import com.example.foodlovers.pojo.MealsByCategory

class MostPopularMealAdapter(): RecyclerView.Adapter<MostPopularMealAdapter.MealViewHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemCLickListener: ((MealsByCategory) -> Unit)

    fun setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealsList = mealList
        notifyDataSetChanged()
    }

    inner class MealViewHolder(val itemBinding: PopularItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemBinding = PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MealViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.itemBinding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemCLickListener.invoke(mealsList[position])
        }
    }
}