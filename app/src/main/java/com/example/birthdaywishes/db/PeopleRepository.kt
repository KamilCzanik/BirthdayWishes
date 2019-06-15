package com.example.birthdaywishes.db

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.db.db.PersonDao
import javax.inject.Inject

class PeopleRepository @Inject constructor(private val personDao: PersonDao) {

    val people: LiveData<List<Person>> = personDao.getAllPeople()

    fun delete(person: Person): Any = DeletePersonAsyncTask(personDao).execute(person)

    companion object {
        private class DeletePersonAsyncTask(val personDao: PersonDao) : AsyncTask<Person, Any, Any>() {

            public override fun doInBackground(vararg params: Person) = params.forEach { personDao.delete(it) }
        }
    }

}