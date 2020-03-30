package com.cortado.mafkir.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.cortado.mafkir.databinding.MafkircontactItemsBinding
import com.cortado.mafkir.model.TimeConverter
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.ui.ListFragmentDirections
import com.cortado.mafkir.ui.extension.toTransitionGroup
import kotlinx.android.synthetic.main.mafkircontact_items.view.*

class MafkirContactListAdapter() : RecyclerView.Adapter<MafkirContactListAdapter.ViewHolder>() {

    private val mafkirContacts = mutableListOf<MafkirContact>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            MafkircontactItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mafkirContacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mafkirContacts[position])
    }

    fun submitList(newMafkirContacts: List<MafkirContact>) {
        mafkirContacts.clear()
        mafkirContacts.addAll(newMafkirContacts)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: MafkircontactItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val timeConverter = TimeConverter()

        fun bind(item: MafkirContact) {
            binding.mafkirContact = item
            binding.timeConverter = timeConverter
            if (System.currentTimeMillis() - item.lastInteractionMillis > item.interactionIntervalMillis) {
                itemView.cardView.setCardBackgroundColor(Color.RED)
            }
            binding.clickListener = View.OnClickListener {
                val navDirection = ListFragmentDirections.actionListFragmentToEditFragment(item)
                val extras =  FragmentNavigatorExtras(binding.root.toTransitionGroup())
                it.findNavController().navigate(navDirection, extras)
            }
        }
    }
}