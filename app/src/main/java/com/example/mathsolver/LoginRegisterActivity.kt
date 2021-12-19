package com.example.mathsolver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        val setLogin = intent.getBooleanExtra("login", false)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if(setLogin)
            fragmentTransaction.replace(R.id.frameLayout, LoginFragment()).commit()
        else
            fragmentTransaction.replace(R.id.frameLayout, RegisterFragment()).commit()
    }
}