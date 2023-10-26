package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart(){
        super.onStart()
        Thread.sleep(3000)
        installSplashScreen()
        if(firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        btnRegister = findViewById(R.id.register)
        btnLogin = findViewById(R.id.login)

        btnLogin.setOnClickListener{
            if(editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                loginProccess()
            }else{
                Toast.makeText(this, "Silahkan isi Email dan password", Toast.LENGTH_SHORT).show()
            }
        }
        btnRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun loginProccess(){
        val email = editEmail.text.toString()
        val password= editPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                startActivity(Intent(this,MainActivity::class.java))
            }.addOnFailureListener{ task ->
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT ).show()
            }
    }
}