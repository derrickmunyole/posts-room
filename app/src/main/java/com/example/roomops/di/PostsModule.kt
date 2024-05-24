package com.example.roomops.di

import android.app.Application
import androidx.room.Room
import com.example.roomops.db.PostsDao
import com.example.roomops.db.PostsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PostsModule {

    @Provides
    @Singleton
    fun providePostsDatabase(app: Application): PostsDatabase {
        return Room.databaseBuilder(app, PostsDatabase::class.java, "posts.db").build()
    }

    @Provides
    @Singleton
    fun providePostsDao(db: PostsDatabase): PostsDao = db.postsDao()

}