package com.cortado.mafkir.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cortado.mafkir.R
import com.cortado.mafkir.databinding.MafkircontactItemsBinding
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.ui.ListFragmentDirections
import com.cortado.mafkir.ui.extension.toTransitionGroup
import kotlinx.android.synthetic.main.mafkircontact_items.view.*

class MafkirContactListAdapter : RecyclerView.Adapter<MafkirContactListAdapter.ViewHolder>() {

    private val mafkirContacts = mutableListOf<MafkirContact>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            parent.context,
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

    fun swap(newMafkirContacts: List<MafkirContact>) {
        val diffCallback = DiffCallback(mafkirContacts, newMafkirContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        mafkirContacts.clear()
        mafkirContacts.addAll(newMafkirContacts)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val context: Context, private val binding: MafkircontactItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MafkirContact) {
            binding.mafkirContact = item
            binding.intervalDisplayText =
                "Every ${item.interval.interval} ${item.interval.type.text}"
            if (intervalPassed(item)) {
                itemView.cardView.setCardBackgroundColor(context.resources.getColor(R.color.colorError))
            }
            binding.clickListener = View.OnClickListener {
                val navDirection = ListFragmentDirections.actionListFragmentToEditFragment(
                    item.contact,
                    item.phoneNumber,
                    true,
                    item
                )
                val extras = FragmentNavigatorExtras(binding.root.toTransitionGroup())
                it.findNavController().navigate(navDirection, extras)
            }
        }

        private fun intervalPassed(contact: MafkirContact) =
            contact.interval.interval * contact.interval.type.millisInUnit <
                    System.currentTimeMillis() - contact.lastInteractionMillis
    }
}