package com.example.foodlovers.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foodlovers.R
import com.example.foodlovers.databinding.ActivityMealBinding
import com.example.foodlovers.fragments.HomeFragment
import com.example.foodlovers.pojo.Meal
import com.example.foodlovers.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealID: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String

    private lateinit var mealMvvm: MealViewModel
    private lateinit var mealYoutubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        getIntentMealInformation()
        setInformationInView()

        onLoginCase()
        mealMvvm.getMealDetail(mealID)
        observeMealDetail()

        onYoutubeClickListener()

    }

    private fun onYoutubeClickListener() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYoutubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetail() {
        mealMvvm.observeMealDetailLiveData().observe(this) { value ->
            onResponseCase()
            binding.tvCategory.text = "Category: ${value!!.strCategory}"
            binding.tvLocation.text = "Location: ${value!!.strArea}"
            binding.tvFoodDesc.text = value.strInstructions

            mealYoutubeLink = value.strYoutube
        }
    }

    private fun setInformationInView() {
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealThumb)
    }

    private fun getIntentMealInformation() {
        val intent = intent
        mealID = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun onLoginCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btmFavorite.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.imgCategory.visibility = View.INVISIBLE
        binding.imgLocation.visibility = View.INVISIBLE
        binding.tvLocation.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvFoodDesc.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btmFavorite.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.imgCategory.visibility = View.VISIBLE
        binding.imgLocation.visibility = View.VISIBLE
        binding.tvLocation.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvFoodDesc.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }


}