package com.example.praktikum_papb.screen

import com.example.praktikum_papb.api.FirebaseManager
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikum_papb.ui.theme.PraktikumPAPBTheme

@Composable
fun MatkulScreen() {
    val firebaseManager = FirebaseManager()
    ListScreen(firebaseManager)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(firebaseManager: FirebaseManager) {
    var pertemuanByHari by remember { mutableStateOf<Map<String, List<PertemuanWithNama>>>(emptyMap()) }

    // LaunchedEffect to load data from Firebase
    LaunchedEffect(Unit) {
        val matakuliahList = firebaseManager.getMatakuliah()

        val pertemuanMap = matakuliahList.flatMap { matakuliah ->
            matakuliah.pertemuan.map { pertemuan ->
                PertemuanWithNama(
                    namaMataKuliah = matakuliah.nama,
                    hari = pertemuan.hari,
                    jam_mulai = pertemuan.jam_mulai,
                    jam_selesai = pertemuan.jam_selesai,
                    ruangan = pertemuan.ruangan,
                    praktikum = pertemuan.praktikum
                )
            }
        }.groupBy { it.hari }

        pertemuanByHari = pertemuanMap.toSortedMap(compareBy { getDayOrder(it) })

        Log.d("ListScreen", "Updated and sorted pertemuan by day: $pertemuanByHari")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pertemuanByHari.forEach { (hari, pertemuanList) ->
                item {
                    Text(text = hari, style = MaterialTheme.typography.titleMedium)
                }
                items(pertemuanList) { pertemuan ->
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Nama Mata Kuliah: ${pertemuan.namaMataKuliah}")
                            Text("Jam: ${pertemuan.jam_mulai} - ${pertemuan.jam_selesai}")
                            Text("Ruangan: ${pertemuan.ruangan}")
                            Text("Praktikum: ${if (pertemuan.praktikum) "Ada" else "Tidak Ada"}")
                        }
                    }
                }
            }
        }
    }
}

// Utility function to get the order of days
private fun getDayOrder(hari: String): Int {
    return when (hari) {
        "Senin" -> 1
        "Selasa" -> 2
        "Rabu" -> 3
        "Kamis" -> 4
        "Jumat" -> 5
        "Sabtu" -> 6
        "Minggu" -> 7
        else -> 8
    }
}

data class PertemuanWithNama(
    val namaMataKuliah: String,
    val hari: String,
    val jam_mulai: String,
    val jam_selesai: String,
    val ruangan: String,
    val praktikum: Boolean
)
