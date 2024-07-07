package com.example.contatctmanager

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddContact : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etMail = findViewById<TextInputEditText>(R.id.etMail)
        val etNum = findViewById<TextInputEditText>(R.id.etNum)
        val addBtn = findViewById<Button>(R.id.btnAdd)

        addBtn.setOnClickListener {
            val name = etName.text.toString()
            val mail = etMail.text.toString()
            val num = etNum.text.toString()

            val con = contact(name, mail, num)

            dialog = Dialog(this)
            dialog.setContentView(R.layout.alertbox_added)
            dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg_alert))

            val btnOk = dialog.findViewById<Button>(R.id.btnAdded)

            btnOk.setOnClickListener {
                dialog.dismiss()
            }

            val pathName = intent.getStringExtra(SigninActivity.KEY3).toString()

            database = FirebaseDatabase.getInstance().getReference("Users/"+pathName+"/contacts")
            database.child(num).setValue(con).addOnSuccessListener {
                etName.text?.clear()
                etMail.text?.clear()
                etNum.text?.clear()

//                addBtn.setOnClickListener {
                    dialog.show()
//                }

            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }



        }
    }
}