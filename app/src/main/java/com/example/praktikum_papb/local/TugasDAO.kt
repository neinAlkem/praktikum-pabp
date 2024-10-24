package com.example.praktikum_papb.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TugasDAO {

    @Query("SELECT * FROM Tugas ORDER BY id DESC")
    fun getAllTugas(): LiveData<List<Tugas>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTugas(tugas: Tugas)

    @Query("UPDATE Tugas SET selesai = :isCompleted WHERE id = :tugasId")
    fun updateTugasCompletion(tugasId: Int, isCompleted: Boolean)

    @Query("DELETE FROM Tugas WHERE id = :tugasId")
    fun deleteTugas(tugasId: Int)
}
