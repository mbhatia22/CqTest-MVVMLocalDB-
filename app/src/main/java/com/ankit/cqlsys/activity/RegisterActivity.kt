package com.ankit.cqlsys.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.ankit.cqlsys.R
import com.ankit.cqlsys.databinding.ActivityRegisterBinding
import com.ankit.cqlsys.model.RegisterModel
import com.ankit.cqlsys.utils.getTrimText
import com.ankit.cqlsys.utils.isValidEmail
import com.ankit.cqlsys.utils.isValidPassword
import com.ankit.cqlsys.utils.toast
import com.ankit.cqlsys.vm.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private var mGender = "Male"
    private var mSelectedCountry = ""
    private val userViewModel by lazy { UserViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
        listeners()
    }

    private fun initUI() {
        binding.rbMale.isChecked = true

    }

    private fun listeners() {

        binding.btnRegister.setOnClickListener {
            checkValidation()
        }

        binding.rbMale.setOnClickListener {
            mGender = "Male"
        }
        binding.rbFemale.setOnClickListener {
            mGender = "Female"
        }
        binding.rbOther.setOnClickListener {
            mGender = "Other"
        }

        binding.spinnerCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    mSelectedCountry = resources.getStringArray(R.array.country)[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
    }

    private fun checkValidation() {
        if (binding.edtName.getTrimText() == "") {
            toast("Please fill name")
        } else if (binding.edtEmail.getTrimText() == "") {
            toast("Please enter email")
        } else if (!binding.edtEmail.getTrimText().isValidEmail()) {
            toast("Please enter valid email")
        } else if (binding.edtPhone.getTrimText() == "") {
            toast("Please fill mobile number")
        } else if (binding.edtPhone.getTrimText().length < 10) {
            toast("Please fill valid number")
        } else if (mGender == "") {
            toast("Please select gender")
        } else if (mSelectedCountry == "" || mSelectedCountry == "Select Country") {
            toast("Please select country")
        } else if (binding.edtPassword.getTrimText() == "") {
            toast("Please enter password")
        } else if (binding.edtPassword.getTrimText().length < 8) {
            toast("Please enter password at least 8 digit")
        } else if (!binding.edtPassword.getTrimText().isValidPassword()) {
            toast("Please enter password having at least one uppercase, one lowercase, one special characters and one digit")
        } else if (binding.edtConPassword.getTrimText() == "") {
            toast("Please fill confirm password")
        } else if (binding.edtPassword.getTrimText() != binding.edtConPassword.getTrimText()) {
            toast("Confirm password not match")
        } else {
            createNewUser(binding.edtName.getTrimText(),
                binding.edtEmail.getTrimText(),
                binding.edtPhone.getTrimText(),
                binding.edtPassword.getTrimText(),
                mGender,
                mSelectedCountry)
        }

    }

    private fun createNewUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        mGender: String,
        mSelectedCountry: String,
    ) {
        val registerModel = RegisterModel(name = name,
            email = email,
            phone = phone,
            gender = mGender,
            country = mSelectedCountry,
            password = password)
        CoroutineScope(Dispatchers.Main).launch {
            userViewModel.getExistingUser(email)?.observe(this@RegisterActivity) {
                if (it == null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val id = userViewModel.newRegister(registerModel)
                        withContext(Dispatchers.Main) {
                            if ((id ?: 0) > 0) {
                                toast("Register successfully. Please login")
                                finish()
                            } else {
                                toast("Something went wrong. please try again")
                            }
                        }
                    }
                } else {
                    if (!isFinishing)
                        toast("Email already exist")
                }
            }
        }
    }
}