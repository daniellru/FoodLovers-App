package com.example.foodlovers.retrofit

import com.example.foodlovers.pojo.CategoryList
import com.example.foodlovers.pojo.MealByCategoryList
import com.example.foodlovers.pojo.MealList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id: String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName: String): Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    companion object{
        private val retrofitService: MealAPI by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(MealAPI::class.java)
        }

        fun getInstance(): MealAPI{
            return retrofitService
        }
    }




}