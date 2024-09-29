import android.util.Log
import com.example.praktikum_papb.MataKuliah
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await

class FirebaseManager {
    private val database = FirebaseDatabase.getInstance("https://praktikum-papb-f39e6-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val matakuliahRef = database.getReference("jadwal_mata_kuliah")

    suspend fun getMatakuliah(): List<MataKuliah> {
        return try {
            val snapshot = matakuliahRef.get().await()
            Log.d("FirebaseManager", "Fetched snapshot: $snapshot")
            snapshot.children.mapNotNull { it.getValue<MataKuliah>() }
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error fetching data", e)
            emptyList()
        }
    }
}
