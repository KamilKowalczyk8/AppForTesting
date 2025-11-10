package com.example.osobnik.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.osobnik.PersonViewModel
import com.example.osobnik.data.Person

@Composable
fun DeleteScreen(viewModel: PersonViewModel) {
    val people by viewModel.allPeople.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (people.isEmpty()) {
            item {
                Text("Brak danych do usunięcia.")
            }
        }
        items(people) { person ->
            PersonDeletableItem(person, onClick = { viewModel.deletePerson(person) })
            HorizontalDivider()
        }
    }
}

@Composable
fun PersonDeletableItem(person: Person, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("${person.firstName} ${person.lastName}", style = MaterialTheme.typography.titleMedium)
            Text(person.email)
        }
        IconButton(onClick = onClick) {
            Icon(Icons.Default.Delete, contentDescription = "Usuń", tint = MaterialTheme.colorScheme.error)
        }
    }
}