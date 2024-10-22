package com.example.praktikum_papb.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Tugas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "matkul")
    var matkul: String,

    @ColumnInfo(name = "detail")
    var detail: String,

    @ColumnInfo(name = "selesai")
    var selesai: Boolean
) : Parcelable
