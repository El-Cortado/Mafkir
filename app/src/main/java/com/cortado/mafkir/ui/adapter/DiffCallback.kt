package com.cortado.mafkir.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cortado.mafkir.persistence.MafkirContact

class DiffCallback(
    private val oldList: List<MafkirContact>,
    private val newList: List<MafkirContact>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].contact == newList[newItemPosition].contact
                && oldList[oldItemPosition].interactionIntervalMillis == newList[newItemPosition].interactionIntervalMillis
    }
}