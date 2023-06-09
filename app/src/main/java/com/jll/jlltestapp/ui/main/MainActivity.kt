package com.jll.jlltestapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jll.jlltestapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var navigationController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //navigation
//        navigationController = Navigation.findNavController(this, R.id.fragment)
//        NavigationUI.setupActionBarWithNavController(this, navigationController)

    }

    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navigationController, null)
        return false
    }

}