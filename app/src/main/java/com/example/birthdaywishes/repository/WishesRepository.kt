package com.example.birthdaywishes.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.repository.db.WishesDao
import javax.inject.Inject

class WishesRepository @Inject constructor(private val wishesDao: WishesDao) {

    val wishes: LiveData<List<Wishes>> = wishesDao.getAllWishes()

    fun delete(wishes: Wishes) {
        DeleteWishesAsyncTask(wishesDao).execute(wishes)
    }

    fun add(wishes: Wishes) {
        InsertWishesAsyncTask(wishesDao).execute(wishes)
    }

    private class InsertWishesAsyncTask(private val wishesDao: WishesDao) : AsyncTask<Wishes, Any, Any>() {

        public override fun doInBackground(vararg wishesArgs: Wishes) { wishesArgs.forEach { wishesDao.insert(it) } }
    }

    private class DeleteWishesAsyncTask(private val wishesDao: WishesDao) : AsyncTask<Wishes, Any, Any>() {

        public override fun doInBackground(vararg params: Wishes) {
            params.forEach { wishesDao.delete(it) }
        }

    }
}