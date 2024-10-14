import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum_papb.api.GithubUser
import com.example.praktikum_papb.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {
    var user: GithubUser? by mutableStateOf(null)
        private set

    fun fetchGitHubProfile(username: String) {
        viewModelScope.launch {
            try {
                val fetchedUser = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getUser(username)
                }
                user = fetchedUser
            } catch (e: Exception) {
                // Handle error, log the exception, or show UI error state
                Log.e("GithubProfile", "Error Fetching Github User", e)
            }
        }
    }
}
