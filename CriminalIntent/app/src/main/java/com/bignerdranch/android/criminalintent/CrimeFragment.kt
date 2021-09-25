package com.bignerdranch.android.criminalintent

import android.Manifest
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val DATE_FORMAT = "EEE, MMM, dd"

class CrimeFragment : Fragment() {

    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var timeButton: Button
    private lateinit var reportButton: Button
    private lateinit var suspectButton: Button
    private lateinit var callButton: Button
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    private val crimeDetailViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().requestPermissions(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
            ), 100
        )
        crime = Crime()
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeDetailViewModel.loadCrime(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            when {
                result.resultCode != Activity.RESULT_OK -> return@registerForActivityResult
                result.data != null -> {
                    val contactURI: Uri? = result.data?.data
                    val queryFields = arrayOf(
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    )
                    val cursor = contactURI?.let { uri ->
                        requireActivity().contentResolver.query(
                            uri,
                            queryFields,
                            null, null, null
                        )
                    }
                    cursor?.use {
                        if (it.count == 0) {
                            return@registerForActivityResult
                        }
                        it.moveToFirst()
                        val suspect = it.getString(0)
                        crime.suspect = suspect
                        crimeDetailViewModel.saveCrime(crime)
                        suspectButton.text = crime.suspect
                        val contactId = it.getString(
                            it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
                        )
                        val phoneCursor = activity?.contentResolver?.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null, null
                        )
                        phoneCursor?.use { cursor ->
                            cursor.moveToFirst()
                            val number = cursor.getString(
                                cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            callButton.isEnabled = true
                            callButton.text = number
                        }
                    }
                }
            }
        }
        return inflater.inflate(R.layout.fragment_crime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleField = view.findViewById(R.id.crime_title)
        solvedCheckBox = view.findViewById(R.id.crime_solved)
        dateButton = view.findViewById(R.id.crime_date)
        timeButton = view.findViewById(R.id.time_button)
        reportButton = view.findViewById(R.id.crime_report)
        suspectButton = view.findViewById(R.id.crime_suspect)
        callButton = view.findViewById(R.id.button_call)
        callButton.isEnabled = false
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        timeButton.text = formatter.format(Calendar.getInstance().time)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    crime = it
                    updateUI()
                }
            }
        )

        parentFragmentManager.setFragmentResultListener(
            "datePicker",
            viewLifecycleOwner
        ) { _, bundle ->
            crime.date = bundle.getSerializable("picked") as Date
            updateUI()
        }
    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        titleField.addTextChangedListener(titleWatcher)
        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(crime.date).apply {
                show(this@CrimeFragment.parentFragmentManager, DIALOG_DATE)
            }
        }
        timeButton.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { _, i, i2 ->
                val result = if (i2 == 0) {
                    "$i:$i2$i2"
                } else "$i:$i2"
                timeButton.text = result
            }
            val minutes = (crime.date.time / 1000 / 60).toInt()
            val seconds = (crime.date.time / 1000 % 60).toInt()
            TimePickerDialog(
                requireContext(),
                listener,
                minutes, seconds, true
            ).show()
        }
        reportButton.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getCrimeReport())
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
            }.also {
                val chooserIntent = Intent.createChooser(it, getString(R.string.send_report))
                startActivity(chooserIntent)
            }
        }

        suspectButton.apply {
            val pickContactIntent = Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
            )
            pickContactIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            setOnClickListener {
                startForResult.launch(pickContactIntent)
            }
            val packageManager = requireActivity().packageManager
            val resolvedActivity = packageManager.resolveActivity(
                pickContactIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            if (resolvedActivity == null) {
                isEnabled = false
            }
        }

        callButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${callButton.text}")
            )
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    private fun updateUI() {
        titleField.setText(crime.title)
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateButton.text = formatter.format(crime.date)
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
        if (crime.suspect.isNotEmpty()) {
            suspectButton.text = crime.suspect
        }
    }

    private fun getCrimeReport(): String {
        val solvedString = if (crime.isSolved) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }
        val dateString = android.text.format.DateFormat.format(
            DATE_FORMAT,
            crime.date
        ).toString()
        val suspect = if (crime.suspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, crime.suspect)
        }
        return getString(
            R.string.crime_report,
            crime.title,
            dateString,
            solvedString,
            suspect
        )
    }

    companion object {
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
}