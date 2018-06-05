package com.techapp.james.mvvm

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.mvvm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bind: ActivityMainBinding? = null
    var viewModel: ViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModel()
        bind!!.model = viewModel
        button.setOnClickListener{
            viewModel!!.name="James"
        }
    }
}
