package com.cortado.mafkir.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.cortado.mafkir.Constants.Companion.CONTACT_PICKER_RESULT
import com.cortado.mafkir.R
import com.cortado.mafkir.notifications.InteractionsService
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mafkirPermissionsValidator: MafkirPermissionsValidator

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mafkirPermissionsValidator.validate(this)

        startService(Intent(this, InteractionsService::class.java))

        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.container)

        fab.setOnClickListener {
            doLaunchContactPicker(it)
        }
    }

    private fun onFloatingClicked(contact:String) {
        val navDirection = ListFragmentDirections.actionListFragmentToAddFragment(contact)
        navController.navigate(navDirection)
        fab.hide()
    }

    fun showFloatingButton() {
        fab.show()
        fab.visibility = View.VISIBLE
    }

    private fun doLaunchContactPicker(view: View?) {
        val contactPickerIntent = Intent(
            Intent.ACTION_PICK,
            ContactsContract.Contacts.CONTENT_URI
        )
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CONTACT_PICKER_RESULT -> {
                    data?.data?.apply {
                        val projection = arrayOf(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                        )
                        val cursor =
                            applicationContext.contentResolver.query(
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
