package com.example.birthdaywishes.db

import android.os.AsyncTask
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.db.db.PersonDao
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personDao: PersonDao) {

    fun add(person: Person,afterInsert: (Long) -> Any) = AddPersonAsyncTask(personDao,afterInsert).execute(person)

    fun update(person: Person) = UpdatePersonAsyncTask(personDao).execute(person)

    class AddPersonAsyncTask(private val personDao: PersonDao,private val afterInsert: (Long) -> Any) : AsyncTask<Person,Any,Any>() {

        private var lastId: Long = -1

        public override fun doInBackground(vararg params: Person) {
            lastId = personDao.insert(params[0])
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            afterInsert(lastId)
        }
    }

    class UpdatePersonAsyncTask(private val personDao: PersonDao) : AsyncTask<Person,Any,Any>() {
        public override fun doInBackground(vararg params: Person) = personDao.update(params[0])
    }
}