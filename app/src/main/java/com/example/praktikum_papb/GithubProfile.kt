package com.example.praktikum_papb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch


class GithubProfile : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent {
            val username = "neinAlkem"
            GithubProfileScreen(username)
        }
    }
    @Composable
    fun GithubProfileScreen(username: String) {
        var user by remember { mutableStateOf<GithubUser?>(null) }

        LaunchedEffect(username) {
            fetchGitHubProfile(username) { fetchedUser ->
                user = fetchedUser
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            user?.let {
                Image(painter = rememberAsyncImagePainter(it.avatarUrl), contentDescription = "Profile Picture",
                 modifier = Modifier
                     .size(150.dp)
                     .clip(CircleShape)
                    )
                Text(
                    text = it.login,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
                )
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 15.dp)
                )
                Text(
                    text = "Followers: ${it.followersCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Following: ${it.followingCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    private fun fetchGitHubProfile(username: String, onResult: (GithubUser) -> Unit) {
        lifecycleScope.launch {
            try{
                val user  = RetrofitInstance.api.getUser(username)
                onResult(user)
            }catch (
                e: Exception
            ){
                Log.e("GithubProfile", "Error Fetching Github User")
            }
        }
    }
}
