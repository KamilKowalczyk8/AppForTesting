package com.example.osobnik.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.osobnik.PersonViewModel

@Composable
fun AddScreen(viewModel: PersonViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Imię") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Nazwisko") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Data urodzenia (DD.MM.RRRR)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Numer telefonu") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("E-mail") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Adres") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (firstName.isNotBlank() && lastName.isNotBlank()) {
                    viewModel.addPerson(firstName, lastName, dob, phone, email, address)
                    Toast.makeText(context, "Zapisano", Toast.LENGTH_SHORT).show()
                    firstName = ""; lastName = ""; dob = ""; phone = ""; email = ""; address = ""
                } else {
                    Toast.makeText(context, "Imię i nazwisko są wymagane", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zapisz")
        }
    }
}