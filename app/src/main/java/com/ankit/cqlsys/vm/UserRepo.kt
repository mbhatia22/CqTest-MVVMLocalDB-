package com.ankit.cqlsys.vm

import android.content.Context
import androidx.lifecycle.LiveData
import com.ankit.cqlsys.model.RegisterModel
import com.ankit.cqlsys.room.DatabaseInstance
import com.ankit.cqlsys.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepo(context: Context) {

    private var mUserDao: UserDao? = null
    private var registerUser: LiveData<List<RegisterModel>>? = null

    init {
        mUserDao = DatabaseInstance.getInstance(context).dao()
        registerUser = mUserDao?.getAllUser()
    }

    suspend fun getExisting(email: String): LiveData<RegisterModel?>? {
        return withContext(Dispatchers.IO) {
            mUserDao?.getExistingUser(email)
        }
    }

    suspend fun getLoginExist(email: String): LiveData<RegisterModel?>? {
        return withContext(Dispatchers.IO) {
            mUserDao?.getExistingUser(email)
        }
    }

    fun getAllWords(): LiveData<List<RegisterModel>>? {
        return registerUser
    }

    fun newRegister(item: RegisterModel): Long? {
        return mUserDao?.insert(item)
    }

}