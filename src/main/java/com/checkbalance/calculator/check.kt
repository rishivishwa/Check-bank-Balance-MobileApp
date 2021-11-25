package com.checkbalance.calculator

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.text.MessageFormat.format
import android.icu.text.SimpleDateFormat
import android.icu.text.Transliterator
import android.icu.util.UniversalTimeScale.toLong
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.jar.Manifest
import kotlin.concurrent.timer
import android.database.Cursor as Cursor
import android.provider.ContactsContract
import android.text.format.DateFormat
import android.text.format.DateFormat.format
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import java.lang.String.format
import java.text.MessageFormat.format
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import kotlin.collections.HashMap
import androidx.core.database.getDoubleOrNull as getDoubleOrNull1


class check : AppCompatActivity() {

    var button: Button? = null
    var button1: Button? = null
    var numberEt: TextView? = null
    var textView: TextView? = null
    var textView2: TextView? = null
    var editTextDate: EditText? = null
    var number: String? = ""
    var selectedBankName: String? = ""
    var cursor: Cursor?=null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        button = findViewById(R.id.dialButton)
        button1 = findViewById(R.id.smsbutton)

        numberEt = findViewById(R.id.numberEt)

        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        editTextDate = findViewById(R.id.editTextDate)
//        numberEt2 = findViewById(R.id.numberEt2)

        selectedBankName = intent?.getStringExtra("BANK_NAME")
//        Log.d("#ASD", selectedBankName.toString())
        Toast.makeText(this, selectedBankName, Toast.LENGTH_LONG).show()
        numberEt?.setText("Your bank name is :" + selectedBankName)

        var map = HashMap<String, String>()
        map.put("State Bank of India", "09223766666")
        map.put("Punjab National Bank", "18001802223")
        map.put("Bank of Broda", "8468001111")
        map.put("Allahabad bank", "09223150150")
        map.put("HDFC Bank", "18002703333")

        button?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "no content", Toast.LENGTH_LONG).show()
                requestPermissions()
            } else {
                val it = Intent(Intent.ACTION_CALL)
                val n = map.get(selectedBankName)
                it.data = Uri.parse("tel:$n")
                startActivity(it)
            }
        }


        button1?.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_SMS),
                    11
                )

            } else {
                requestPermissions()
//                cursor = contentResolver.query(Uri.parse("content://sms/inbox"),null,null,null,null)
                cursor = contentResolver.query(Telephony.Sms.CONTENT_URI,
                   null, null, null, null)
                var count = cursor?.count
                if (cursor != null) {
                    if(selectedBankName == "Punjab National Bank") {
                    while (cursor?.moveToNext() == true) {
                            val smsBody = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!).toString()
                            if (smsBody.contains("Dear Customer,Your a/c")) {
                                var smsDate: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.DATE)!!)
                                val simpleDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
                                val dateString =LocalDate.parse(smsDate, simpleDateFormat)

                                var smsAddress: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)!!)
                                var smsBody: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!)
                                textView?.setText("Address :" + smsAddress)
                                textView2?.setText("Your Balance" + smsBody)
                                editTextDate?.setText(" Date and time is " + dateString)
                                break
                            }
                        }
                    }
                    if(selectedBankName == "State Bank of India"){
                        while (cursor?.moveToNext() == true) {
                                val smsBody = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!).toString()
                                if (smsBody.contains("SBI")) {
                                    var smsDate: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.DATE)!!)
                                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                                    val dateString = simpleDateFormat.format(smsDate?.toLong())

                                    var smsAddress: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)!!)
                                    var smsBody: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!)
                                    textView?.setText("Address :" + smsAddress)
                                    textView2?.setText("Your Balance" + smsBody)
                                    editTextDate?.setText(" Date and time is " + dateString)
                                    break
                                }
                            }
                        }
                    if(selectedBankName == "Bank of Broda") {
                        while (cursor?.moveToNext() == true) {
                                val smsBody = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!).toString()
                                if (smsBody.contains("Bank of Baroda")) {
                                    var smsDate: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.DATE)!!)
                                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                                    val dateString = simpleDateFormat.format(smsDate?.toLong())

                                    var smsAddress: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)!!)
                                    var smsBody: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!)
                                    textView?.setText("Address :" + smsAddress)
                                    textView2?.setText("Your Balance" + smsBody)
                                    editTextDate?.setText(" Date and time is " + dateString)
                                    break
                                }
                            }
                        }

                    if(selectedBankName == "HDFC Bank"){
                        while (cursor?.moveToNext() == true) {
                                val smsBody = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!).toString()
                                if (smsBody.contains("hdfcbank")) {
                                    var smsDate: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.DATE)!!)
                                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                                    val dateString = simpleDateFormat.format(smsDate?.toLong())

                                    var smsAddress: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)!!)
                                    var smsBody: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!)
                                    textView?.setText("Address :" + smsAddress)
                                    textView2?.setText("Your Balance" + smsBody)
                                    editTextDate?.setText(" Date and time is " + dateString)
                                    break
                                }
                            }
                        }
                    if(selectedBankName == "Allahabad bank"){
                        while (cursor?.moveToNext() == true) {
                                val smsBody = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!).toString()
                                if (smsBody.contains("Allahabad bank")) {
                                    var smsDate: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.DATE)!!)
                                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                                    val dateString = simpleDateFormat.format(smsDate?.toLong())

                                    var smsAddress: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)!!)
                                    var smsBody: String? = cursor?.getString(cursor?.getColumnIndexOrThrow(Telephony.Sms.BODY)!!)
                                    textView?.setText("Address :" + smsAddress)
                                    textView2?.setText("Your Balance" + smsBody)
                                    editTextDate?.setText(" Date and time is " + dateString)
                                    break
                                }
                            }
                        }
                }

            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.d("rishikesh","granted");
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(
                android.Manifest.permission.CALL_PHONE
//                android.Manifest.permission.READ_SMS
            ),
            1
        )


    }

}









