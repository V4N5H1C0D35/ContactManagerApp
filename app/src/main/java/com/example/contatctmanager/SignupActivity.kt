package com.example.contatctmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`activity_signup`)

        val signUpButton = findViewById<Button>(R.id.btnSignUp)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etMail = findViewById<TextInputEditText>(R.id.etMail)
        val etId = findViewById<TextInputEditText>(R.id.etId)
        val tv1 = findViewById<TextView>(R.id.tv1)

        tv1.setOnClickListener {
            val openSignInActivity = Intent(this, SigninActivity::class.java)
            startActivity(openSignInActivity)
        }

        signUpButton.setOnClickListener {

            val name = etName.text.toString()
            val mail = etMail.text.toString()
            val uid = etId.text.toString()
            val Contacts = ""

            val user = User(name, mail, uid, Contacts)
            database = FirebaseDatabase.getInstance().getReference("Users")

            database.child(uid).setValue(user).addOnSuccessListener {

                etName.text?.clear()
                etMail.text?.clear()
                etId.text?.clear()

                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }


        }
    }
}