package com.example.foodlovers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodlovers.pojo.Meal
import com.example.foodlovers.pojo.MealList
import com.example.foodlovers.retrofit.MealAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel: ViewModel() {

    private var mealLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String){
        MealAPI.getInstance().getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealLiveData.value = response.body()!!.meals[0]
                }else{
                    Log.i("tá caindo aqui", "e afora?")
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("Meal Detail Error", t.message.toString())
            }

        })
    }

    fun observeMealDetailLiveData(): LiveData<Meal>{
        return mealLiveData
    }
}