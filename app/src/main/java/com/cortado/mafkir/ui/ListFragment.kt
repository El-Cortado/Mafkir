package com.cortado.mafkir.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cortado.mafkir.Constants
import com.cortado.mafkir.R
import com.cortado.mafkir.databinding.FragmentListBinding
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.ui.actionbar.ActionBarController
import com.cortado.mafkir.ui.adapter.MafkirContactListAdapter
import com.cortado.mafkir.ui.exceptions.NoPhoneNumberException
import com.cortado.mafkir.ui.extension.toTransitionGroup
import com.cortado.mafkir.ui.extension.waitForTransition
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class ListFragment : DaggerFragment() {

    private lateinit var binding: FragmentListBinding

    private var mafkirContactListAdapter = MafkirContactListAdapter()

    private var allMafkirContacts: List<MafkirContact> = mutableListOf()

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var mafkirContactViewModel: MafkirContactViewModel

    @Inject
    lateinit var actionBarController: ActionBarController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allMafkirContacts = mutableListOf()
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupFab()
        setupViewModel()
        initRecyclerView()
        observerLiveData()
        waitForTransition(binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.no_options, menu)
        actionBarController.hideBack(this)
        actionBarController.setHeadline(this, "Mafkir")
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
        binding.recyclerView.let {
            it.adapter = mafkirContactListAdapter
            val swipe = ItemTouchHelper(setupSwipeToDelete())
            swipe.attachToRecyclerView(it)
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

    private fun setupSwipeToDelete(): ItemTouchHelper.SimpleCallback {
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
                Toast.makeText(activity, "Reminder deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchContactPicker() {
        val contactPickerIntent = Intent(
            Intent.ACTION_PICK,
            ContactsContract.Contacts.CONTENT_URI
        )
        startActivityForResult(contactPickerIntent, Constants.CONTACT_PICKER_RESULT)
    }

    private fun onContactPicked(contact: String, number: String) {
        if (allMafkirContacts.any { mafkirContact -> mafkirContact.contact == contact }) {
            Toast.makeText(
                context!!,
                "$contact is already here!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val extras = FragmentNavigatorExtras(binding.fab.toTransitionGroup())
            val navDirection =
                ListFragmentDirections.actionListFragmentToEditFragment(
                    contact,
                    number,
                    false,
                    null
                )
            findNavController().navigate(navDirection, extras)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    Constants.CONTACT_PICKER_RESULT -> {
                        onContactPicked(
                            getContactDisplayName(data)!!,
                            getContactPhoneNumber(data)!!
                        )
                    }
                }
            } else {
                Log.w("Mafkir", "Warning: activity result not ok")
            }
        } catch (e: NoPhoneNumberException) {
            Toast.makeText(
                context,
                "The contact chosen does not have a phone number!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getContactDisplayName(data: Intent?): String? {
        var contactName: String? = null

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

        val cursor = activity?.applicationContext?.contentResolver?.query(
            data?.data!!, projection,
            null, null, null
        )

        cursor?.also {
            it.moveToFirst()
            val nameColumnIndex =
                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            contactName = it.getString(nameColumnIndex)
        }?.close()

        return contactName
    }

    private fun getContactPhoneNumber(data: Intent?): String? {
        var hasPhone: String? = null
        var id: String? = null

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

        val cursor = activity?.applicationContext?.contentResolver?.query(
            data?.data!!, projection,
            null, null, null
        )

        cursor?.also {
            it.moveToFirst()
            val hasPhoneNumberColumnIndex =
                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
            val idColumnIndex =
                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
            hasPhone = it.getString(hasPhoneNumberColumnIndex)
            id = it.getString(idColumnIndex)
        }?.close()

        if (hasPhone!!.endsWith("0")) {
            throw NoPhoneNumberException()
        } else {
            var number: String? = null
            val phones = activity?.applicationContext?.contentResolver?.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone._ID + " = " + id,
                null,
                null
            )

            if (phones!!.count > 0) {
                while (phones.moveToNext()) {
                    number =
                        phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                }
            }

            phones.close()

            if (number == null) {
                throw NoPhoneNumberException()
            }

            return number
        }
    }
}




