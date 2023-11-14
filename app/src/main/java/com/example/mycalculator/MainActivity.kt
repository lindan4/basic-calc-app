package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Button
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var inputField: TextView? = null

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)
    }

    fun onOperator(view: View) {
        inputField?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                inputField?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onPerformCalculation(view: View) {
        // Check that final entry is a number
        if (lastNumeric) {
            var input = inputField?.text?.toString()
            var prefix = ""
            var res: Double;
            try {
                if (input != null) {
                    if (input.startsWith("-")) {
                        prefix = "-"
                        input = input.substring(1)
                    }
                    if (input.contains("+")) {
                        res = if (prefix.isNotEmpty()) {
                            var arrSplit = input.split("+")
                            var leftOperand = arrSplit[0].toDouble()
                            var rightOperand = arrSplit[1].toDouble()

                            leftOperand - rightOperand
                        } else {
                            var arrSplit = input.split("+")
                            var leftOperand = arrSplit[0].toDouble()
                            var rightOperand = arrSplit[1].toDouble()

                            leftOperand + rightOperand
                        }


                    } else if (input.contains("-")) {
                        res = if (prefix.isNotEmpty()) {
                            var arrSplit = input.split("-")
                            var leftOperand = arrSplit[0].toDouble()
                            var rightOperand = arrSplit[1].toDouble()

                            leftOperand + rightOperand
                        } else {
                            var arrSplit = input.split("-")
                            var leftOperand = arrSplit[0].toDouble()
                            var rightOperand = arrSplit[1].toDouble()

                            leftOperand - rightOperand
                        }
                    } else if (input.contains("*")) {
                        var arrSplit = input.split("*")

                        var leftOperand = arrSplit[0].toDouble()
                        var rightOperand = arrSplit[1].toDouble()

                        res = leftOperand * rightOperand

                        if (prefix.isNotEmpty()) {
                            res *= -1.0
                        }
                    } else {
                        var arrSplit = input.split("/")

                        var leftOperand = arrSplit[0].toDouble()
                        var rightOperand = arrSplit[1].toDouble()

                        res = leftOperand / rightOperand
                        if (prefix.isNotEmpty()) {
                            res *= -1.0
                        }
                    }

                    inputField?.text  = res.toString()
                }
            }
            catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }

    }


    private fun isOperatorAdded( value: String ) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains(("/")) || value.contains(("*")) || value.contains(("+")) || value.contains(("-"))
        }
    }

    fun onDigit(view: View) {
        if (inputField?.text?.toString().equals("0")) {
            inputField?.text = (view as Button).text
        }
        else {
            inputField?.append((view as Button).text)
        }
        lastNumeric = true

    }

    fun onClr(view: View) {
        inputField?.text = "0"
    }

    fun onDecimal(view: View) {

        if (!lastDot && lastNumeric) {
            inputField?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }
}