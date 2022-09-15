package com.ankit.cqlsys.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ankit.cqlsys.model.RegisterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(val applications: Application) : AndroidViewModel(applications) {

    private var mUserRepo: UserRepo? = null
    private var registerUser: LiveData<List<RegisterModel>>? = null

    init {
        mUserRepo = UserRepo(applications.baseContext)
        registerUser = mUserRepo?.getAllWords()
    }

    fun getAllUser() = registerUser

    suspend fun getExistingUser(email: String): LiveData<RegisterModel?>? {
        return withContext(Dispatchers.IO) {
            Log.i("getExistingUser: ", "log")
            mUserRepo?.getExisting(email)

        }

    }

    suspend fun getLoginExist(email: String): LiveData<RegisterModel?>? {
        return withContext(Dispatchers.IO) {
            Log.i("getExistingUser: ", "log")
            mUserRepo?.getLoginExist(email)
        }

    }

    fun newRegister(item: RegisterModel): Long? {
        return mUserRepo?.newRegister(item)
    }
}