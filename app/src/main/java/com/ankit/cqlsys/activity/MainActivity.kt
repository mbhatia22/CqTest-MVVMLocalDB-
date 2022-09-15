package com.ankit.cqlsys.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ankit.cqlsys.adapter.AllUsersAdapter
import com.ankit.cqlsys.databinding.ActivityMainBinding
import com.ankit.cqlsys.model.RegisterModel
import com.ankit.cqlsys.vm.UserViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { AllUsersAdapter() }
    private val userViewModel by lazy { UserViewModel(application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
        userViewModel.getAllUser()?.observe(this) {
            adapter.setData(it as ArrayList<RegisterModel>)
        }
    }

    private fun initUI() {
        binding.recyclerAllUser.adapter = adapter
    }

}