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
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    //personal access token: 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
    companion object{
        var isLoggedIn = false
        var emailTxt = String()
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
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show()
            register.visibility = View.VISIBLE
            login.visibility = View.VISIBLE
            logout.visibility = View.GONE
            greetText.visibility = View.GONE
        }
    }

    override fun onResume() {
        if(isLoggedIn)
        {
            findViewById<TextView>(R.id.tvRegister).visibility = View.GONE
            findViewById<TextView>(R.id.tvLogin).visibility = View.GONE
            findViewById<TextView>(R.id.tvLogout).visibility = View.VISIBLE
            val greetText = findViewById<TextView>(R.id.greetPlaceholder)
            greetText.visibility = View.VISIBLE
            greetText.text = "Hi, "  + ParseEmail(emailTxt)

        }
        super.onResume()
    }

    private fun ParseEmail(email: String): String {
        var text = email.substring(0, email.indexOf('@'))
        text = text.replace('.', ' ')
        text = text.replace('-', ' ')
        return text
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