package com.example.mathsolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class QuadraticActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadratic)

        var a:Float? = null
        var b:Float? = null
        var c:Float? = null

        val firstInput = findViewById<EditText>(R.id.qa)
        val secondInput = findViewById<EditText>(R.id.qb)
        val thirdInput = findViewById<EditText>(R.id.qc)
        val output = findViewById<TextView>(R.id.qSolutionPlaceholder)
        firstInput.doAfterTextChanged {
            a = firstInput.text.toString().toFloatOrNull()
            output.text = RecalulateEquation(a, b, c)
        }
        secondInput.doAfterTextChanged {
            b = secondInput.text.toString().toFloatOrNull()
            output.text = RecalulateEquation(a, b, c)
        }
        thirdInput.doAfterTextChanged {
            c = thirdInput.text.toString().toFloatOrNull()
            output.text = RecalulateEquation(a, b, c)
        }
    }

    private fun RecalulateEquation(a: Float?, b: Float?, c: Float?):String {

        if((a==null || a==0f) && b!=null && c!=null)
            return "x = " + ((-c)/b).toString()
        if(a!=null && b!=null && c!=null && b.pow(2) - 4 * a * c < 0)
        {
            val real = -b / (2 * a)
            val imag = sqrt(-b.pow(2) + 4 * a * c) / (2 * a)
            return "x1 = $real + ${abs(imag)}" + "i\n x2 = $real - ${abs(imag)}" +"i"
        }
        if (a!=null && b!=null && c!=null && b.pow(2) - 4 * a * c == 0f)
        {
            return "x1 = x2 = " + (-b / (2 * a)).toString()
        }
        if(a!=null && b!=null && c!=null)
        {
            val x1 = (-b + sqrt(b.pow(2) - 4 * a * c)) / (2 * a)
            val x2 = (-b - sqrt(b.pow(2) - 4 * a * c)) / (2 * a)
            return "x1 = $x1\n x2 = $x2"
        }
        else
            return "";
    }
    private fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
}