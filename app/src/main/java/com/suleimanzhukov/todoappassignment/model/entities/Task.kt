package com.suleimanzhukov.todoappassignment.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Parcelize
//@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    var priority: Int,
    var isSelected: Boolean = false
)