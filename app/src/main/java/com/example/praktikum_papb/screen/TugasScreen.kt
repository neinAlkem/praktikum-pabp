import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum_papb.local.Tugas
import com.example.praktikum_papb.local.TugasRepository
import com.example.praktikum_papb.viewModel.TugasViewModel
import kotlinx.coroutines.launch

@Composable
fun TugasScreen(tugasRepository: TugasRepository) {
    val tugasViewModel: TugasViewModel = viewModel(factory = TugasViewModelFactory(tugasRepository))
    var matkul by remember { mutableStateOf(TextFieldValue("")) }
    var detail by remember { mutableStateOf(TextFieldValue("")) }
    val tugasList by tugasViewModel.listTugas.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var doneClicked by remember { mutableStateOf(false) } // State to check if "Done" is clicked

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.weight(1f)) {
//            Text(
//                text = "Tambah Tugas",
//                fontSize = 26.sp
//            )

          Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = matkul,
                onValueChange = { matkul = it },
                label = { Text("Nama Mata Kuliah") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = detail,
                onValueChange = { detail = it },
                label = { Text("Detail Tugas") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (matkul.text.isNotEmpty() && detail.text.isNotEmpty()) {
                        tugasViewModel.addTugas(matkul.text, detail.text)
                        scope.launch {
                            snackbarHostState.showSnackbar("Tugas Ditambahkan")
                        }
                        matkul = TextFieldValue("")  // Reset text fields
                        detail = TextFieldValue("")
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Tugas Gagal Ditambahkan")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Tambah Tugas")
            }
            SnackbarHost(hostState = snackbarHostState)
        }
        // Display the list of added tasks
        if (tugasList.isNotEmpty()) {
            LazyColumn {
                itemsIndexed(tugasList) { index, tugas ->
                    TugasItem(tugas = tugas, onComplete = { completed ->
                        tugasViewModel.updateTugasCompletion(tugas.id, completed)
                    })
                }
            }
        } else {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No items yet",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TugasItem(tugas: Tugas, onComplete: (Boolean) -> Unit) {
    var isCompleted by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isCompleted) Color.Gray else Color.Transparent)
            .border(width = 1.dp, color = if (isCompleted) Color.Transparent else Color.DarkGray, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = tugas.detail,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = tugas.matkul,
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }

        Button(
            onClick = {
                isCompleted = !isCompleted
                onComplete(isCompleted)
            }
        ) {
            Text(text = if (isCompleted) "✓" else "Selesai")
        }
    }
}
