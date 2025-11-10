package com.example.osobnik

import com.example.osobnik.ui.screens.HomeScreen
import com.example.osobnik.ui.screens.ListScreen
import com.example.osobnik.ui.screens.DeleteScreen
import com.example.osobnik.ui.screens.AddScreen
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.osobnik.ui.theme.OsobnikTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OsobnikTheme {
                AppScreen()
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Start", Icons.Default.Home)
    object Add : Screen("add", "Dodaj", Icons.Default.Add)
    object List : Screen("list", "Lista", Icons.Default.List)
    object Delete : Screen("delete", "Usuń", Icons.Default.Delete)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Add,
    Screen.List,
    Screen.Delete
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: PersonViewModel = viewModel(
        factory = PersonViewModelFactory(context.applicationContext as Application)
    )

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = bottomNavItems.find { it.route == currentRoute }?.label ?: ""

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (currentRoute != Screen.Home.route) {
                IconButton(onClick = { navController.navigateUp() }) { // Back button
                    Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz")
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: PersonViewModel,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Add.route) {
            AddScreen(viewModel = viewModel)
        }
        composable(Screen.List.route) {
            ListScreen(viewModel = viewModel)
        }
        composable(Screen.Delete.route) {
            DeleteScreen(viewModel = viewModel)
        }
    }
}