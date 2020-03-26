package com.cortado.mafkir.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cortado.mafkir.persistence.MafkirContact

class DiffCallback(
    private val oldList: List<MafkirContact>,
    private val newList: List<MafkirContact>
) : DiffUtil.Callback() {

    // Method #1
    override fun getOldListSize() = oldList.size

    // Method #2
    override fun getNewListSize() = newList.size

    // Method #3
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    // Method #4
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].contact == newList[newItemPosition].contact
                && oldList[oldItemPosition].interactionInterval == newList[newItemPosition].interactionInterval
    }
}