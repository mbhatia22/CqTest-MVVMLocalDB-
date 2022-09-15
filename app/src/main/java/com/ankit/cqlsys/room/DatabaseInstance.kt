package com.ankit.cqlsys.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ankit.cqlsys.model.RegisterModel

@Database(entities = [RegisterModel::class], version = 1)

abstract class DatabaseInstance : RoomDatabase() {

    abstract fun dao(): UserDao

    companion object {
        private var instance: DatabaseInstance? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseInstance {
            if (instance == null)
                instance = createRoomInstance(ctx)

            return instance ?: createRoomInstance(ctx)

        }

        private fun createRoomInstance(ctx: Context) = Room.databaseBuilder(
            ctx.applicationContext, DatabaseInstance::class.java,
            "note_database"
        ).build()

    }


}