package com.example.praktikum_papb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum_papb.local.Tugas
import com.example.praktikum_papb.local.TugasRepository
import kotlinx.coroutines.launch

class TugasViewModel(private val tugasRepository: TugasRepository) : ViewModel() {

    val listTugas: LiveData<List<Tugas>> = tugasRepository.getAllTugas()

    fun addTugas(matkul: String, detail: String) {
        val newTugas = Tugas(matkul = matkul, detail = detail, selesai = false)
        viewModelScope.launch {
            tugasRepository.insert(newTugas)
        }
    }

    // New function to update the completion status of a task
    fun updateTugasCompletion(tugasId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            tugasRepository.updateTugasCompletion(tugasId, isCompleted) // You'll need to implement this in your repository
        }
    }
}
