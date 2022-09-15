package com.ankit.cqlsys.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ankit.cqlsys.databinding.ActivityLoginBinding
import com.ankit.cqlsys.utils.getTrimText
import com.ankit.cqlsys.utils.isValidEmail
import com.ankit.cqlsys.utils.isValidPassword
import com.ankit.cqlsys.utils.toast
import com.ankit.cqlsys.vm.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val userViewModel by lazy { UserViewModel(applications = application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            checkValidations()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            binding.edtEmail.setText("")
            binding.edtPassword.setText("")
        }

    }

    private fun checkValidations() {
        if (binding.edtEmail.getTrimText() == "") {
            toast("Please enter email")
        } else if (!binding.edtEmail.getTrimText().isValidEmail()) {
            toast("Please enter valid email")
        } else if (binding.edtPassword.getTrimText() == "") {
            toast("Please enter password")
        } else if (binding.edtPassword.getTrimText().length < 8) {
            toast("Please enter password at least 8 digit")
        } else if (!binding.edtPassword.getTrimText().isValidPassword()) {
            toast("Please enter password at least one uppercase, one lowercase, one special characters or one digit")
        } else {
            login(binding.edtEmail.getTrimText(), binding.edtPassword.getTrimText())
        }

    }

    private fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            userViewModel.getLoginExist(email)?.observe(this@LoginActivity) {
                Log.i("login: ", "$it")
                if (it != null) {
                    if (password == it.password) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        toast("Login Successfully")
                        finish()
                    } else {
                        toast("Enter valid credentials")
                    }
                } else {
                    toast("Enter valid credentials")
                }
            }
        }
    }
}