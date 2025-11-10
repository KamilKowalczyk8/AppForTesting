package com.example.osobnik

import android.app.Application
import androidx.lifecycle.*
import com.example.osobnik.data.AppDatabase
import com.example.osobnik.data.Person
import com.example.osobnik.data.PersonDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PersonViewModel(private val dao: PersonDao) : ViewModel() {

    val allPeople: Flow<List<Person>> = dao.getAllPeople()

    fun addPerson(
        firstName: String, lastName: String, dob: String,
        phone: String, email: String, address: String
    ) {
        val person = Person(
            firstName = firstName, lastName = lastName, dob = dob,
            phone = phone, email = email, address = address
        )
        viewModelScope.launch {
            dao.insertPerson(person)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            dao.deletePerson(person)
        }
    }
}
class PersonViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val dao = AppDatabase.getDatabase(application).personDao()
            return PersonViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}