package com.ankit.cqlsys.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ankit.cqlsys.model.RegisterModel


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: RegisterModel): Long

    @Query("Select * from register order by created DESC")
    fun getAllUser(): LiveData<List<RegisterModel>>

    @Query("Select * from register where email=:email")
    fun getExistingUser(email: String): LiveData<RegisterModel?>
}