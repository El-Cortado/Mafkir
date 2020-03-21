package com.example.mafkir.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mafkir_contacts")
data class MafkirContact(@PrimaryKey(autoGenerate = true)
                        var id: Int,
                        var contact: String,
                        var interactionInterval: Long,
                        var lastInteraction:Long) {
}