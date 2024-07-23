package com.example.foodlovers.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodlovers.activites.MealActivity
import com.example.foodlovers.adapter.CategoryAdapters
import com.example.foodlovers.adapter.MostPopularMealAdapter
import com.example.foodlovers.databinding.FragmentHomeBinding
import com.example.foodlovers.pojo.MealsByCategory
import com.example.foodlovers.pojo.Meal
import com.example.foodlovers.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularMealAdapter
    private lateinit var categoryItemsAdapter: CategoryAdapters

    companion object{
        const val MEAL_ID = "com.example.foodlovers.fragments.idmeal"
        const val MEAL_NAME = "com.example.foodlovers.fragments.namemeal"
        const val MEAL_THUMB = "com.example.foodlovers.fragments.thumbmeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularMealAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        randomMealObserver()
        onRandomMealClick()

        homeMvvm.getPopularItem()
        observePopularItemLiveData()
        onPopularItemClick()

        setUpCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesLiveData()


    }

    private fun setUpCategoriesRecyclerView() {
        categoryItemsAdapter = CategoryAdapters()
        binding.recyclerViewCategory.apply {
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL, false)
            adapter = categoryItemsAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner){ categories ->
            categoryItemsAdapter.setCategoryList(categories)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemCLickListener = {meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularMealLiveData().observe(viewLifecycleOwner) { mealList ->
            popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun randomMealObserver() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }
}