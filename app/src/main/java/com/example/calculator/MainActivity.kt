package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var result: TextView

    companion object {
        const val FIRST_ARG_STR_KEY = "KEY of firstArgStr"
        const val FIRST_ARG_KEY = "KEY of firstArgument"
        const val FIRST_ARG_HAS_DOT_KEY = "KEY of firstHasDot"

        const val SECOND_ARG_STR_KEY = "KEY of secondArgStr"
        const val SECOND_ARG_KEY = "KEY of secondArgument"
        const val SECOND_ARG_HAS_DOT_KEY = "KEY of secondHasDot"

        const val OPERATION_KEY = "KEY of operation"

        const val OUTPUT_STRING_KEY = "KEY of outputString"
    }

    object calculator : CalculatorBrains()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)

        val digitButtons = arrayOf(
            findViewById<Button>(R.id.zero),
            findViewById(R.id.one),
            findViewById(R.id.two),
            findViewById(R.id.three),
            findViewById(R.id.four),
            findViewById(R.id.five),
            findViewById(R.id.six),
            findViewById(R.id.seven),
            findViewById(R.id.eight),
            findViewById(R.id.nine)
        )

        val digitIds = arrayOf(
            R.id.zero,
            R.id.one,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine
        )

        val actionIds = arrayOf(
            R.id.clear,
            R.id.delete,
            R.id.dot,
            R.id.add,
            R.id.subtract,
            R.id.multiply,
            R.id.divide,
            R.id.equal
        )

        val actionButtons = arrayOf(
            findViewById<Button>(R.id.clear),
            findViewById(R.id.delete),
            findViewById(R.id.dot),
            findViewById(R.id.add),
            findViewById(R.id.subtract),
            findViewById(R.id.multiply),
            findViewById(R.id.divide),
            findViewById(R.id.equal)
        )

        for (i in digitIds.indices) {
            digitButtons[i].setOnClickListener {
                calculator.digitPressed(digitIds[i])
                result.text = calculator.outputString
            }
        }

        for (i in actionIds.indices) {
            actionButtons[i].setOnClickListener {
                calculator.actionPressed(actionIds[i])
                result.text = calculator.outputString
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("saving", "start")

        outState.putString(FIRST_ARG_STR_KEY, calculator.firstArgStr)
        outState.putDouble(FIRST_ARG_KEY, calculator.firstArgument)
        outState.putBoolean(FIRST_ARG_HAS_DOT_KEY, calculator.firstArgHasDot)

        outState.putString(SECOND_ARG_STR_KEY, calculator.secondArgStr)
        outState.putDouble(SECOND_ARG_KEY, calculator.secondArgument)
        outState.putBoolean(SECOND_ARG_HAS_DOT_KEY, calculator.secondArgHasDot)

        outState.putString(OPERATION_KEY, calculator.operation)

        outState.putString(OUTPUT_STRING_KEY, calculator.outputString)

        Log.i("saving", "successfully")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("restore", "start")
        super.onRestoreInstanceState(savedInstanceState)

        calculator.firstArgStr = savedInstanceState.getString(FIRST_ARG_STR_KEY)!!
        calculator.firstArgument = savedInstanceState.getDouble(FIRST_ARG_KEY)
        calculator.firstArgHasDot = savedInstanceState.getBoolean(FIRST_ARG_HAS_DOT_KEY)

        calculator.secondArgStr = savedInstanceState.getString(SECOND_ARG_STR_KEY)!!
        calculator.secondArgument = savedInstanceState.getDouble(SECOND_ARG_KEY)
        calculator.secondArgHasDot = savedInstanceState.getBoolean(SECOND_ARG_HAS_DOT_KEY)

        calculator.operation = savedInstanceState.getString(OPERATION_KEY)!!

        calculator.outputString = savedInstanceState.getString(OUTPUT_STRING_KEY)!!

        if (calculator.outputString != "") {
            result.text = calculator.outputString
        }
    }
}