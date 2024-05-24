package com.example.roomops.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Posts::class],
    version = 1
)
abstract class PostsDatabase: RoomDatabase() {

    abstract fun postsDao(): PostsDao

}