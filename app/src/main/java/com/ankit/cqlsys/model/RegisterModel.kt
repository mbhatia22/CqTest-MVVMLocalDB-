package com.ankit.cqlsys.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "register")
data class RegisterModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "phone") val phone: String? = null,
    @ColumnInfo(name = "gender") val gender: String? = null,
    @ColumnInfo(name = "country") val country: String? = null,
    @ColumnInfo(name = "password") val password: String? = null,
    @ColumnInfo(name = "created") val created: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated") val updated: Long = System.currentTimeMillis(),
) : Serializable