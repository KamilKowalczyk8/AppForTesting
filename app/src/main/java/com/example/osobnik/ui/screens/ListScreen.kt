package com.example.osobnik.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.osobnik.PersonViewModel
import com.example.osobnik.data.Person

@Composable
fun ListScreen(viewModel: PersonViewModel) {
    val people by viewModel.allPeople.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (people.isEmpty()) {
            item {
                Text("Brak danych do wyświetlenia.")
            }
        }
        items(people) { person ->
            PersonListItem(person)
            HorizontalDivider()
        }
    }
}

@Composable
fun PersonListItem(person: Person) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Text("Imię: ${person.firstName} ${person.lastName}", style = MaterialTheme.typography.titleMedium)
        Text("Email: ${person.email}")
        Text("Tel: ${person.phone}")
        Text("Adres: ${person.address}")
    }
}