package com.example.mathsolver

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val btn = view.findViewById<Button>(R.id.loginBtn)
        btn.setOnClickListener{
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frameLayout, LoginFragment())?.commit()
        }
        val email = view.findViewById<EditText>(R.id.etEmail)
        val password = view.findViewById<EditText>(R.id.etPassword)
        val rePassword = view.findViewById<EditText>(R.id.etRePassword)
        val btnRegister = view.findViewById<Button>(R.id.finishRegisterBtn)

        btnRegister.setOnClickListener{
            val emailTxt = email.text.toString()
            val passwordTxt = password.text.toString()
            val passwordReText = rePassword.text.toString()
            if(TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(passwordReText) || TextUtils.isEmpty(emailTxt))
                Toast.makeText(view.context, "Empty credentials!", Toast.LENGTH_SHORT).show()
            else if(passwordReText != passwordTxt)
                Toast.makeText(view.context, "Password and repeated password not equal!", Toast.LENGTH_SHORT).show()
            else if(passwordTxt.length < 6)
                Toast.makeText(view.context, "Password too short!", Toast.LENGTH_SHORT).show()
            else
                RegisterUser(emailTxt, passwordTxt)
        }
        return view
    }

    private fun RegisterUser(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task->
            if (task.isSuccessful)
            {
                Toast.makeText(view?.context, "Registering user successful! Login to continue...", Toast.LENGTH_SHORT).show()
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.frameLayout, LoginFragment())?.commit()
            }
            else
                Toast.makeText(view?.context, "Registration failed!", Toast.LENGTH_SHORT).show()
        }
    }
}