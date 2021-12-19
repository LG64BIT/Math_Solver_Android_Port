package com.example.mathsolver

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.util.DisplayMetrics
import android.view.Gravity
import androidx.core.view.marginBottom
import java.lang.Exception


class MatrixActivity : AppCompatActivity() {
    private lateinit var firstText : TextView
    private lateinit var secondText : TextView
    private lateinit var thirdText : TextView

    private lateinit var tl1: TableLayout
    private lateinit var tl2: TableLayout
    private lateinit var tl3: TableLayout
    private var widthMultiplier = 2
    private var heightMultiplier = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val matrixAdimension1 = findViewById<EditText>(R.id.matAdim1)
        val matrixAdimension2 = findViewById<EditText>(R.id.matAdim2)
        val matrixBdimension1 = findViewById<EditText>(R.id.matBdim1)
        val matrixBdimension2 = findViewById<EditText>(R.id.matBdim2)

        firstText = findViewById(R.id.firstText)
        secondText = findViewById(R.id.secondText)
        thirdText = findViewById(R.id.thirdText)

        tl1 = findViewById(R.id.matA)
        tl2 = findViewById(R.id.matB)
        tl3 = findViewById(R.id.matC)

        var matrixA :Array<EditText>? = null
        var matrixB : Array<EditText>? = null
        var matrixC :Array<EditText>

        matrixAdimension1.doAfterTextChanged {
            if(matrixAdimension1.text.isNotEmpty() && matrixAdimension2.text.isNotEmpty())
            {
                tl3.removeAllViews()
                thirdText.visibility = View.GONE
                if(matrixAdimension1.text.toString().toInt() >= 9)
                    matrixAdimension1.setText("8")
                matrixA = drawTableOnScreen(matrixAdimension1.text.toString().toInt(), matrixAdimension2.text.toString().toInt(), tl1, firstText)
            }
        }
        matrixAdimension2.doAfterTextChanged {
            if(matrixBdimension1.text.isNotEmpty() && matrixAdimension2.text.isNotEmpty() && matrixAdimension2.text.toString().toInt() != matrixBdimension1.text.toString().toInt())
                matrixBdimension1.text = matrixAdimension2.text
            if(matrixAdimension1.text.isNotEmpty() && matrixAdimension2.text.isNotEmpty())
            {
                tl3.removeAllViews()
                thirdText.visibility = View.GONE
                if(matrixAdimension2.text.toString().toInt() >= 9)
                    matrixAdimension2.setText("8")
                matrixA = drawTableOnScreen(matrixAdimension1.text.toString().toInt(), matrixAdimension2.text.toString().toInt(), tl1, firstText)
            }
        }
        matrixBdimension1.doAfterTextChanged {
            if(matrixAdimension2.text.isNotEmpty() && matrixBdimension1.text.isNotEmpty() && matrixBdimension1.text.toString().toInt() != matrixAdimension2.text.toString().toInt())
                matrixAdimension2.text = matrixBdimension1.text
            if(matrixBdimension1.text.isNotEmpty() && matrixBdimension2.text.isNotEmpty())
            {
                tl3.removeAllViews()
                thirdText.visibility = View.GONE
                if(matrixBdimension1.text.toString().toInt() >= 9)
                    matrixBdimension1.setText("8")
                matrixB = drawTableOnScreen(matrixBdimension1.text.toString().toInt(), matrixBdimension2.text.toString().toInt(), tl2, secondText)
            }
        }
        matrixBdimension2.doAfterTextChanged {
            if(matrixBdimension1.text.isNotEmpty() && matrixBdimension2.text.isNotEmpty())
            {
                tl3.removeAllViews()
                thirdText.visibility = View.GONE
                if(matrixBdimension2.text.toString().toInt() >= 9)
                    matrixBdimension2.setText("8")
                matrixB = drawTableOnScreen(matrixBdimension1.text.toString().toInt(), matrixBdimension2.text.toString().toInt(), tl2, secondText)
            }
        }
        btnCalculate.setOnClickListener{
            tl3.removeAllViews()
            try{
                if(isFilled(matrixA, matrixAdimension1.text.toString().toInt(), matrixAdimension2.text.toString().toInt()) &&
                    isFilled(matrixB, matrixBdimension1.text.toString().toInt(), matrixBdimension2.text.toString().toInt()))
                {
                    matrixC = MultiplyMatrices(matrixA, matrixB, matrixAdimension1.text.toString().toInt(), matrixBdimension2.text.toString().toInt())
                    fillTable(matrixC, matrixAdimension1.text.toString().toInt(), matrixBdimension2.text.toString().toInt(), tl3, thirdText)
                }
                else Toast.makeText(this, "Please fill the matrices entirely!", Toast.LENGTH_SHORT).show()
            }catch (e: Exception) {
                Toast.makeText(this, "Please fill the matrices correctly! (Integer only)", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun fillTable(matrix: Array<EditText>, dim1: Int, dim2: Int, tl: TableLayout?, textElement: TextView) {
        textElement.visibility = View.VISIBLE
        //drawing on screen
        val trArray = Array(dim1){ TableRow(this) }
        for (i in 0 until dim1)
        {
            for (j in 0 until dim2)
            {
                matrix[i*dim2 + j].setBackgroundColor(resources.getColor(R.color.strong_blue, resources.newTheme()))
                matrix[i*dim2 + j].setBackgroundResource(R.drawable.matrix_element_shape)
                matrix[i*dim2 + j].setRawInputType(InputType.TYPE_CLASS_NUMBER)
                matrix[i*dim2 + j].setPadding(5,5,5,5)
                matrix[i*dim2 + j].gravity = Gravity.CENTER
                matrix[i*dim2 + j].id = i*dim2 + j
                matrix[i*dim2 + j].hint = matrix[i*dim2 + j].id.toString()
                matrix[i*dim2 + j].setTextAppearance(R.style.matrixElementStyleText)
                matrix[i*dim2 + j].setHintTextColor(Color.TRANSPARENT)
                matrix[i*dim2 + j].width = (applicationContext.resources.getDimensionPixelSize(R.dimen.etTextSize)*widthMultiplier).toInt()
                matrix[i*dim2 + j].height = (applicationContext.resources.getDimensionPixelSize(R.dimen.etTextSize)*heightMultiplier).toInt()
                matrix[i*dim2 + j].isEnabled = false

                trArray[i].addView(matrix[i*dim2 + j])
            }
            tl?.addView(trArray[i])
        }
    }
    private fun MultiplyMatrices(matrixA: Array<EditText>?, matrixB: Array<EditText>?, dimA1: Int, dimB2: Int): Array<EditText> {
        val dimA2 :Int = matrixA?.size?.div(dimA1)!!
        val dimB1 :Int = matrixB?.size?.div(dimB2)!!

        val matrixC = Array<EditText>(dimA1*dimB2){ EditText(this) }

        val A = Array<Int>(dimA1*dimA2){0}
        val B = Array<Int>(dimB1*dimB2){0}
        val C = Array<Int>(dimA1*dimB2){0}

        for(i in 0 until dimA1)
            for (j in 0 until dimA2)
                A[i*dimA2 + j] = matrixA[i*dimA2 + j].text.toString().toInt()
        for(i in 0 until dimB1)
            for (j in 0 until dimB2)
                B[i*dimB2 + j] = matrixB[i*dimB2 + j].text.toString().toInt()
        //matrix multiplication
        for(i in 0 until dimA1)
            for (j in 0 until dimB2)
                for (k in 0 until dimA2)
                    C[i*dimB2 + j] += A[i*dimA2 + k] * B[k*dimB2 + j]
        for(i in 0 until dimA1)
            for (j in 0 until dimB2)
                matrixC[i*dimB2 + j].setText(C[i*dimB2 + j].toString())
        return matrixC
    }
    private fun isFilled(matrix: Array<EditText>?, dim1: Int, dim2: Int): Boolean {
        for (i in 0 until dim1)
            for (j in 0 until dim2)
                if (matrix?.get(i * dim2 + j)?.text?.isEmpty() == true)
                    return false
        return true
    }
    private fun drawTableOnScreen(dim1: Int, dim2: Int, tl: TableLayout, textElement: TextView): Array<EditText>? {
        if(dim1 <= 0 || dim2 <= 0)
            return null
        tl.removeAllViews()
        textElement.visibility = View.VISIBLE
        //drawing on screen
        val etArray = Array(dim1*dim2){ EditText(this) }
        val trArray = Array(dim1){ TableRow(this) }
        for (i in 0 until dim1 step 1)
        {
            for (j in 0 until dim2 step 1)
            {
                etArray[i*dim2 + j].setBackgroundColor(resources.getColor(R.color.strong_blue, resources.newTheme()))
                etArray[i*dim2 + j].setBackgroundResource(R.drawable.matrix_element_shape)
                etArray[i*dim2 + j].setRawInputType(InputType.TYPE_CLASS_NUMBER)
                etArray[i*dim2 + j].setPadding(5,5,5,5)
                etArray[i*dim2 + j].gravity = Gravity.CENTER
                etArray[i*dim2 + j].id = i*dim2 + j
                etArray[i*dim2 + j].hint = etArray[i*dim2 + j].id.toString()
                etArray[i*dim2 + j].setTextAppearance(R.style.matrixElementStyleText)
                etArray[i*dim2 + j].setHintTextColor(Color.TRANSPARENT)
                etArray[i*dim2 + j].width = (applicationContext.resources.getDimensionPixelSize(R.dimen.etTextSize)*widthMultiplier).toInt()
                etArray[i*dim2 + j].height = (applicationContext.resources.getDimensionPixelSize(R.dimen.etTextSize)*heightMultiplier).toInt()

                trArray[i].addView(etArray[i*dim2 + j])
            }
            tl.addView(trArray[i])
        }
        return etArray
    }
}