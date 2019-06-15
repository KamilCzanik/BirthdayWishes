package com.example.birthdaywishes.db

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.db.db.WishesDao
import javax.inject.Inject

class WishesRepository @Inject constructor(private val wishesDao: WishesDao) {

    val wishes: LiveData<List<Wishes>> = wishesDao.getAllWishes()

    fun delete(wishes: Wishes): Any = DeleteWishesAsyncTask(wishesDao).execute(wishes)


    fun add(wishes: Wishes): Any = InsertWishesAsyncTask(wishesDao).execute(wishes)

    private class InsertWishesAsyncTask(private val wishesDao: WishesDao) : AsyncTask<Wishes, Any, Any>() {

        public override fun doInBackground(vararg wishesArgs: Wishes) = wishesArgs.forEach { wishesDao.insert(it) }
    }

    private class DeleteWishesAsyncTask(private val wishesDao: WishesDao) : AsyncTask<Wishes, Any, Any>() {

        public override fun doInBackground(vararg params: Wishes) = params.forEach { wishesDao.delete(it) }
    }
}