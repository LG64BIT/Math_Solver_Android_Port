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
import com.example.mathsolver.MainActivity.Companion.emailTxt
import com.example.mathsolver.MainActivity.Companion.isLoggedIn

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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
            //else if(auth.currentUser.)
            //    FirebaseDatabase.getInstance().getReference().child("users")
            //        .addListenerForSingleValueEvent(object : ValueEventListener() {
            //            fun onDataChange(dataSnapshot: DataSnapshot) {
            //                for (snapshot in dataSnapshot.getChildren()) {
            //                    val user: User = snapshot.getValue(User::class.java)
            //                    System.out.println(user.email)
            //                }
            //            }
//
            //            fun onCancelled(databaseError: DatabaseError?) {}
            //        })
            //else if(auth.currentUser?.isEmailVerified != true)
            //    Toast.makeText(view.context, "Please verify you email first!", Toast.LENGTH_LONG).show()
            else
                loginUser(emailTxt, passwordTxt)
        }
        return view
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
            if(task.isSuccessful)
            {
                Toast.makeText(view?.context, "Login successful", Toast.LENGTH_SHORT).show()
                isLoggedIn = true
                emailTxt = email
                activity?.finish()
            }
            else
                Toast.makeText(view?.context, "Error! " + (task.exception?.message ?: "Something went wrong!"), Toast.LENGTH_LONG).show()
        }
    }
}