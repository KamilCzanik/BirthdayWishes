package com.example.birthdaywishes.repository

import android.os.AsyncTask
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.db.PersonDao
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personDao: PersonDao) {

    fun add(person: Person) = AddPersonAsyncTask(personDao).execute(person)

    fun update(person: Person) = UpdatePersonAsyncTask(personDao).execute(person)

    class AddPersonAsyncTask(private val personDao: PersonDao) : AsyncTask<Person,Any,Any>() {
        public override fun doInBackground(vararg params: Person) = personDao.insert(params[0])
    }

    class UpdatePersonAsyncTask(private val personDao: PersonDao) : AsyncTask<Person,Any,Any>() {
        public override fun doInBackground(vararg params: Person) = personDao.update(params[0])
    }
}