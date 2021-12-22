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
import com.example.mathsolver.MainActivity.Companion.auth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val btn = view.findViewById<Button>(R.id.loginBtn)
        btn.setOnClickListener{
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frameLayout, LoginFragment())?.commit()
        }
        val username = view.findViewById<EditText>(R.id.etUsername)
        val email = view.findViewById<EditText>(R.id.etEmail)
        val password = view.findViewById<EditText>(R.id.etPassword)
        val rePassword = view.findViewById<EditText>(R.id.etRePassword)
        val btnRegister = view.findViewById<Button>(R.id.finishRegisterBtn)
        btnRegister.setOnClickListener{
            val usernameTxt =username.text.toString()
            val emailTxt = email.text.toString()
            val passwordTxt = password.text.toString()
            val passwordReText = rePassword.text.toString()
            if(TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(passwordReText) || TextUtils.isEmpty(emailTxt))
                Toast.makeText(view.context, "Empty credentials!", Toast.LENGTH_SHORT).show()
            else if(passwordReText != passwordTxt)
                Toast.makeText(view.context, "Password and repeated password are not equal!", Toast.LENGTH_LONG).show()
            else if(passwordTxt.length < 6)
                Toast.makeText(view.context, "Password too short!", Toast.LENGTH_SHORT).show()
            else
                registerUser(usernameTxt, emailTxt, passwordTxt)
        }
        return view
    }

    private fun registerUser(username : String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task->
            if (task.isSuccessful)
            {
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build();
                auth.currentUser?.updateProfile(profileUpdates)
                auth.currentUser?.sendEmailVerification()
                Toast.makeText(view?.context, "Verification email has been sent.", Toast.LENGTH_SHORT).show()

                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.frameLayout, LoginFragment())?.commit()
            }
            else
                Toast.makeText(view?.context, "Error!" + (task.exception?.message ?: "Something went wrong!"), Toast.LENGTH_LONG).show()
        }
    }
}