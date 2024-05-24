package com.example.roomops.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Upsert
    suspend fun upsertPost(post: Posts)

    @Delete
    suspend fun deletePost(post: Posts)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<Posts>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostItem(id: Int): Posts
}