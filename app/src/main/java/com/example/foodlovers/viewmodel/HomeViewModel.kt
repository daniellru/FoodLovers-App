package com.example.foodlovers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodlovers.pojo.Category
import com.example.foodlovers.pojo.CategoryList
import com.example.foodlovers.pojo.MealsByCategory
import com.example.foodlovers.pojo.MealByCategoryList
import com.example.foodlovers.pojo.Meal
import com.example.foodlovers.pojo.MealList
import com.example.foodlovers.retrofit.MealAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal(){

        MealAPI.getInstance().getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeFrag",t.message.toString())
            }
        })
    }

    fun getPopularItem(){
        MealAPI.getInstance().getPopularItem("Seafood").enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.e("HomeFragment",t.message.toString())
            }

        })
    }

    fun getCategories(){
        MealAPI.getInstance().getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null){
                   categoriesLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }

        })
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observePopularMealLiveData(): LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }


}