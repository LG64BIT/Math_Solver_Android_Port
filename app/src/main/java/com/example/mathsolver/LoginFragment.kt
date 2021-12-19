package com.example.mathsolver

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mathsolver.MainActivity.Companion.emailTxt
import com.example.mathsolver.MainActivity.Companion.isLoggedIn
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val btn = view.findViewById<Button>(R.id.registerBtn)
        btn.setOnClickListener{
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frameLayout, RegisterFragment())?.commit()
        }

        val email = view.findViewById<EditText>(R.id.emailLogin)
        val password = view.findViewById<EditText>(R.id.passwordLogin)
        val btnLogin = view.findViewById<Button>(R.id.finishLoginBtn)
        btnLogin.setOnClickListener{
            val emailTxt = email.text.toString()
            val passwordTxt = password.text.toString()
            if(TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(emailTxt))
                Toast.makeText(view.context, "Empty credentials!", Toast.LENGTH_SHORT).show()
            else
                LoginUser(emailTxt, passwordTxt)
        }
        return view
    }

    private fun LoginUser(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
            if(task.isSuccessful)
            {
                Toast.makeText(view?.context, "Login successful", Toast.LENGTH_SHORT).show()
                isLoggedIn = true
                emailTxt = email
                activity?.finish()
            }
            else
                Toast.makeText(view?.context, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}