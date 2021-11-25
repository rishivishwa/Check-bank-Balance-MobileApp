package com.checkbalance.calculator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.makeText as makeText

class CheckBalanceScreen : AppCompatActivity() {
    var bankId = 0
    var name = arrayOf("State Bank of India", "Punjab National Bank", "Bank of Broda", "Allahabad bank", "HDFC Bank")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_balance_screen)
        val spinner = findViewById<Spinner>(R.id.spinner)

        val arrayAdapter = ArrayAdapter(
            this@CheckBalanceScreen,
            android.R.layout.simple_spinner_dropdown_item,
            name
        )
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bankId = position
                Toast.makeText(
                    this@CheckBalanceScreen,
                    "select item" + name[position],
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    fun hello( view: View)
    {
        val intent = Intent(this,check::class.java)
        intent.putExtra("BANK_NAME",name[bankId])
        startActivity(intent)
//        intent.putExtra(check.)

    }



}


