package com.example.contatctmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    companion object{
        const val KEY1 = "com.example.contatct_manager.SigninActivity.mail"
        const val KEY2 = "com.example.contatct_manager.SigninActivity.name"
        const val KEY3 = "com.example.contatct_manager.SigninActivity.id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val signInButton = findViewById<Button>(R.id.btnSignIn)
        val uid = findViewById<TextInputEditText>(R.id.etId)

        signInButton.setOnClickListener {

            val uniqueIdString = uid.text.toString()

            if(uniqueIdString.isNotEmpty())
            {
                readData(uniqueIdString)
            }
            else
            {
                Toast.makeText(this, "Please enter the UserName", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(uid: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(uid).get().addOnSuccessListener{

            if(it.exists())
            {
                val email = it.child("email").value
                val name = it.child("name").value
                val username = it.child("uid").value

                val intentAddCont = Intent(this, AddContact::class.java)
                intentAddCont.putExtra(KEY2, email.toString())
                intentAddCont.putExtra(KEY1, name.toString())
                intentAddCont.putExtra(KEY3, username.toString())
                startActivity(intentAddCont)
            }
            else
            {
                Toast.makeText(this, "User does not exist, please Sign Up first", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }
}