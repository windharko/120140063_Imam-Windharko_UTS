package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editName = findViewById(R.id.name)
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        btnRegister = findViewById(R.id.register)
        btnLogin = findViewById(R.id.login)
        btnRegister.setOnClickListener {
            if (editName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()) {
                registering()
            } else {
                Toast.makeText(this, "Silahkan isi Semua Data", Toast.LENGTH_SHORT).show()
            }
        }
        btnLogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun registering(){
        val name = editName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = name
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnSuccessListener {
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                }
            }
            .addOnFailureListener{ task ->
                Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT ).show()
            }
    }

}