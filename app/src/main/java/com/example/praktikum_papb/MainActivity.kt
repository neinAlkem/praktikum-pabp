package com.example.praktikum_papb
/*Nama : Baghas Rizaluddin | NIM  : 225150207111065 */


import com.example.praktikum_papb.api.FirebaseManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.praktikum_papb.navigation.NavigationItem
import com.example.praktikum_papb.navigation.Screen
import com.example.praktikum_papb.screen.MatkulScreen
import com.example.praktikum_papb.screen.ProfileScreen
import com.example.praktikum_papb.screen.TugasScreen
import com.example.praktikum_papb.ui.theme.PraktikumPAPBTheme
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    private val firebaseManager = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            LaunchedEffect(Unit) {
                    val matakuliahList = firebaseManager.getMatakuliah()
                    Log.d("ListScreen", "Updated MataKuliah List: $matakuliahList")
            }
            PraktikumPAPBTheme {
                MainScreen()
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    Scaffold(
        bottomBar = {BottomBar(navController)},
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Matkul.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Matkul.route){
                MatkulScreen()
            }
            composable(Screen.Tugas.route){
                TugasScreen()
            }
            composable(Screen.Profile.route){
                ProfileScreen("neinAlkem")
            }
        }
    }
}
@Composable
private fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = "Matkul",
            icon = Icons.Default.Home,
            selectedIcon = Icons.Filled.Home,
            screen = Screen.Matkul
        ),
        NavigationItem(
            title = "Tugas",
            icon = Icons.Default.Add,
            selectedIcon = Icons.Filled.Add,
            screen = Screen.Tugas
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Person,
            selectedIcon = Icons.Filled.Person,
            screen = Screen.Profile
        ),
    )

    NavigationBar(modifier = modifier) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                label = { Text(text = item.title) },
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            // Clear back stack to the start destination to prevent back navigation
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.screen.route) item.selectedIcon else item.icon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}
//    val items = listOf(
//        NavigationItem(
//            title = "Matkul",
//            icon = Icons.Default.Home,
//            selectedIcon = Icons.Filled.Home,
//            screen = Screen.Matkul
//        ),
//        NavigationItem(
//            title = "Tugas",
//            icon = Icons.Default.Add,
//            selectedIcon = Icons.Filled.Add,
//            screen = Screen.Tugas
//        ),
//        NavigationItem(
//            title = "Profile",
//            icon = Icons.Default.Person,
//            selectedIcon = Icons.Filled.Person,
//            screen = Screen.Profile
//        )
//    )
//    var selectedItemIndex by rememberSaveable {
//        mutableStateOf(0)
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Scaffold(
//            bottomBar = {
//                NavigationBar {
//                    items.forEachIndexed { index, item ->
//                        NavigationBarItem(
//                            selected = selectedItemIndex == index,
//                            onClick = {
//                                selectedItemIndex = index
//                                navController.navigate(item.screen)
//                            },
//                            label = {
//                                Text(text = item.title)
//                            },
//                            alwaysShowLabel = false,
//                            icon = { Icon(imageVector = if (index == selectedItemIndex) {item.selectedIcon}else item.icon,
//                                contentDescription = "ItemTitleLogo" )
//                            })
//                    }
//                }
//            }
//        ) {
//
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PraktikumPAPBTheme {
        MainScreen()
    }
}