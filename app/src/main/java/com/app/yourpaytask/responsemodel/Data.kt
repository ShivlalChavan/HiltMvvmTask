package com.app.yourpaytask.responsemodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class Data(
    @PrimaryKey(autoGenerate = true)
    val email: String,
    val gender: String,
    var id: Int?=null,
    val name: String,
    val status: String
): Serializable