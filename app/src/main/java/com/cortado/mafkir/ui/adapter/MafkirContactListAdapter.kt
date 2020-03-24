package com.cortado.mafkir.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cortado.mafkir.R
import com.cortado.mafkir.model.TimeConverter
import com.cortado.mafkir.persistence.MafkirContact
import kotlinx.android.synthetic.main.mafkircontact_items.view.*
import javax.inject.Inject

class MafkirContactListAdapter(mafkirContactList:List<MafkirContact>, private val interaction: Interaction? = null
) : RecyclerView.Adapter<MafkirContactListAdapter.ViewHolder>() {
    private val mafkirContacts = mutableListOf<MafkirContact>()

    init {
        mafkirContacts.addAll(mafkirContactList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MafkirContactListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.mafkircontact_items, parent, false)
        return ViewHolder(
            view,
            interaction
        )
    }

    override fun getItemCount() = mafkirContacts.size

    override fun onBindViewHolder(holder: MafkirContactListAdapter.ViewHolder, position: Int) {
        holder.bind(item = mafkirContacts[position])
    }

    fun swap(newMafkirContacts: List<MafkirContact>) {
        val diffCallback = DiffCallback(mafkirContacts, newMafkirContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        mafkirContacts.clear()
        mafkirContacts.addAll(newMafkirContacts)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @Inject
        lateinit var timeConverter: TimeConverter

        fun bind(item: MafkirContact) {
            itemView.txtContact.text = item.contact
            itemView.txtInterval.text = timeConverter.millisToDays(item.interactionInterval).toString()
            itemView.txtLastInteraction.text = timeConverter.millisToDate(item.lastInteraction)

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition,item)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: MafkirContact)
    }
}