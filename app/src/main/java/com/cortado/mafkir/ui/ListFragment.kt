package com.cortado.mafkir.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cortado.mafkir.Constants
import com.cortado.mafkir.R
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.ui.adapter.MafkirContactListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class ListFragment : DaggerFragment(),
    MafkirContactListAdapter.Interaction {

    private lateinit var mafkirContactListAdapter: MafkirContactListAdapter

    private lateinit var mafkirContactViewModel: MafkirContactViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var allMafkirContacts: List<MafkirContact>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allMafkirContacts = mutableListOf()
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab()
        setupViewModel()
        initRecyclerView()
        observerLiveData()
    }

    private fun observerLiveData() {
        mafkirContactViewModel.getAll()
            .observe(viewLifecycleOwner, Observer { lisOfMafkirContacts ->
                lisOfMafkirContacts?.let {
                    allMafkirContacts = it
                    mafkirContactListAdapter.swap(it)
                }
            })
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            mafkirContactListAdapter = MafkirContactListAdapter(
                allMafkirContacts,
                this@ListFragment
            )
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = mafkirContactListAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(recyclerView)
        }
    }

    private fun setupViewModel() {
        mafkirContactViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(MafkirContactViewModel::class.java)
    }

    private fun setupFab() {
        fab.setOnClickListener {
            launchContactPicker()
        }
    }

    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mafkirContactViewModel.delete(allMafkirContacts[position].contact)
                Toast.makeText(activity, "Contact tracking Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(position: Int, item: MafkirContact) {
        val navDirection = ListFragmentDirections.actionListFragmentToEditFragment(item)
        findNavController().navigate(navDirection)
    }

    private fun launchContactPicker() {
        val contactPickerIntent = Intent(
            Intent.ACTION_PICK,
            ContactsContract.Contacts.CONTENT_URI
        )
        startActivityForResult(contactPickerIntent, Constants.CONTACT_PICKER_RESULT)
    }

    private fun onFloatingClicked(contact: String) {
        val navDirection = ListFragmentDirections.actionListFragmentToAddFragment(contact)
        findNavController().navigate(navDirection)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.CONTACT_PICKER_RESULT -> {
                    data?.data?.apply {
                        val projection = arrayOf(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                        )
                        val cursor =
                            activity?.applicationContext?.contentResolver?.query(
                                this, projection,
                                null, null, null
                            )
                        cursor?.apply {
                            cursor.moveToFirst()
                            val nameColumnIndex =
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                            val name = cursor.getString(nameColumnIndex)
                            onFloatingClicked(name)
                        }
                        cursor?.close()
                    }
                }
            }
        } else {
            Log.w("Mafkir", "Warning: activity result not ok")
        }
    }
}




