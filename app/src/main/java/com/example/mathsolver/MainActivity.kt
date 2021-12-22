package com.example.mathsolver

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.core.view.MotionEventCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    //personal access token: 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
    companion object{
        var isLoggedIn = false
        var emailTxt = String()
        var auth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val madeBy = findViewById<TextView>(R.id.madeBy)
        val ferit = findViewById<TextView>(R.id.ferit)

        val register = findViewById<TextView>(R.id.tvRegister)
        val login = findViewById<TextView>(R.id.tvLogin)
        val logout = findViewById<TextView>(R.id.tvLogout)
        val greetText = findViewById<TextView>(R.id.greetPlaceholder)

        madeBy.setOnClickListener{
            madeBy.movementMethod = LinkMovementMethod.getInstance()
        }
        ferit.setOnClickListener{
            ferit.movementMethod = LinkMovementMethod.getInstance()
        }
        register.setOnClickListener{
            val intent = Intent(this, LoginRegisterActivity::class.java)
            intent.putExtra("login", false)
            startActivity(intent)
        }
        login.setOnClickListener {
            val intent = Intent(this, LoginRegisterActivity::class.java)
            intent.putExtra("login", true)
            startActivity(intent)
        }
        logout.setOnClickListener{
            auth.signOut()
            Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show()
            isLoggedIn = false
            register.visibility = View.VISIBLE
            login.visibility = View.VISIBLE
            logout.visibility = View.GONE
            greetText.visibility = View.GONE
        }
    }

    override fun onResume() {
        if(isLoggedIn)
        {
            auth.currentUser?.reload()
            findViewById<TextView>(R.id.tvRegister).visibility = View.GONE
            findViewById<TextView>(R.id.tvLogin).visibility = View.GONE
            findViewById<TextView>(R.id.tvLogout).visibility = View.VISIBLE
            val greetText = findViewById<TextView>(R.id.greetPlaceholder)
            if(auth.currentUser?.isEmailVerified != true)
                greetText.text = "Hi, "  + auth.currentUser!!.displayName + " (unverified)"
            else
                greetText.text = "Hi, "  + auth.currentUser!!.displayName
            greetText.visibility = View.VISIBLE
        }
        super.onResume()
    }

    override fun onDestroy() {
        auth.signOut()
        super.onDestroy()
    }

    fun quadraticActivity(view: android.view.View) {
        val intent = Intent(this, QuadraticActivity::class.java)
        startActivity(intent)
    }

    fun matrixActivity(view: android.view.View) {
        val intent = Intent(this, MatrixActivity::class.java)
        startActivity(intent)
    }
}