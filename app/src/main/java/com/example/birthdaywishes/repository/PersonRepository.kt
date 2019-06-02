package com.example.birthdaywishes.repository

import android.os.AsyncTask
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.db.PersonDao
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personDao: PersonDao) {

    fun add(person: Person) = AddPersonAsyncTask(personDao).doInBackground(person)

    fun update(person: Person) = UpdatePersonAsyncTask(personDao).doInBackground(person)

    private class AddPersonAsyncTask(private val personDao: PersonDao) : AsyncTask<Person,Any,Any>() {
        public override fun doInBackground(vararg params: Person) = params.forEach { personDao.insert(it) }
    }

    private class UpdatePersonAsyncTask(private val personDao: PersonDao) : AsyncTask<Person,Any,Any>() {
        public override fun doInBackground(vararg params: Person) = params.forEach { personDao.update(it) }
    }
}