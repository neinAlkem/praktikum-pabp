package com.example.praktikum_papb.local

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TugasRepository(application: Application) {
    private val mTugasDAO: TugasDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = TugasDB.getDatabase(application)
        mTugasDAO = db.tugasDAO()
    }
    fun getAllTugas(): LiveData<List<Tugas>> = mTugasDAO.getAllTugas()
    fun insert(tugas: Tugas){
        executorService.execute { mTugasDAO.insertTugas(tugas) }
    }
    fun updateTugasCompletion(tugasId: Int, isCompleted: Boolean) {
        executorService.execute {
            mTugasDAO.updateTugasCompletion(tugasId, isCompleted)
        }
    }
    fun deleteTugas(tugasId: Int) {
        executorService.execute {
            mTugasDAO.deleteTugas(tugasId)
        }
    }





}