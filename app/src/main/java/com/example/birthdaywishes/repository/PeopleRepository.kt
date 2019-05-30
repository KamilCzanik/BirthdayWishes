package com.example.birthdaywishes.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.birthdaywishes.POJO.Person
import com.example.birthdaywishes.repository.db.PersonDao
import javax.inject.Inject

class PeopleRepository @Inject constructor(private val personDao: PersonDao) {

    val people: LiveData<List<Person>> = personDao.getAllPeople()

    fun delete(person: Person) {
        DeletePersonAsyncTask(personDao).doInBackground(person)
    }

    companion object {
        private class DeletePersonAsyncTask(val personDao: PersonDao) : AsyncTask<Person, Any, Any>() {

            public override fun doInBackground(vararg params: Person): Any {
                params.forEach { personDao.delete(it) }
                return true
            }
        }
    }

}