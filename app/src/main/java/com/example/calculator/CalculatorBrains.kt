package com.example.calculator

import android.util.Log
import java.lang.Math.abs

open class CalculatorBrains {

    private companion object {
        const val DOTLENGTH: Int = 1
        const val DIGITLENGTH: Int = 1
        const val OPERATIONLENGTH: Int = 3
    }

    var firstArgument: Double = 0.0
    var firstArgStr: String = ""
    var firstArgHasDot: Boolean = false

    var secondArgument: Double = 0.0
    var secondArgStr: String = ""
    var secondArgHasDot: Boolean = false

    var operation: String = ""

    var outputString: String = ""

    fun concatDigit(number: String, buttonId: Int): String {
        var tmpNumber = number
        tmpNumber += when (buttonId) {
            R.id.zero -> {
                if (tmpNumber == "") {
                    "0"
                } else {
                    if (tmpNumber != "0") {
                        "0"
                    } else {
                        ""
                    }
                }
            }
            R.id.one -> "1"
            R.id.two -> "2"
            R.id.three -> "3"
            R.id.four -> "4"
            R.id.five -> "5"
            R.id.six -> "6"
            R.id.seven -> "7"
            R.id.eight -> "8"
            R.id.nine -> "9"
            else -> ""
        }
        return tmpNumber
    }

    fun naNOrInfinity(): Boolean {
        return (firstArgStr == "NaN" || firstArgStr == "Infinity")
    }

    fun digitPressed(buttonId: Int) {
        if (naNOrInfinity()) {
            return
        }
        var isVeryLong = false
        if (operation == "") {
            if (firstArgStr != "" && firstArgStr.last() != '.') {
                if (abs(firstArgStr.toDouble().toLong()) < 100_000_000) {
                    firstArgStr = concatDigit(firstArgStr, buttonId)
                } else {
                    isVeryLong = true
                }
            } else {
                firstArgStr = concatDigit(firstArgStr, buttonId)
            }
        } else {
            if (secondArgStr != "" && secondArgStr.last() != '.') {
                if (abs(secondArgStr.toDouble().toLong()) < 100_000_000) {
                    secondArgStr = concatDigit(secondArgStr, buttonId)
                } else {
                    isVeryLong = true
                }
            } else {
                secondArgStr = concatDigit(secondArgStr, buttonId)
            }
        }
        if (!isVeryLong) {
            outputString = concatDigit(outputString, buttonId)
        }
        Log.i("digit", "successfully pressed!")
    }

    fun compute() {
        if (naNOrInfinity()) {
            return
        }
        if (firstArgStr != "" && secondArgStr != "" && operation != "") {
            Log.i("Compute", "started!")
            firstArgument = firstArgStr.toDouble()
            secondArgument = secondArgStr.toDouble()
            var tmpResult: Double
            tmpResult = when (operation) {
                "+" -> firstArgument + secondArgument
                "-" -> firstArgument - secondArgument
                "*" -> firstArgument * secondArgument
                "/" -> firstArgument / secondArgument
                else -> 0.0
            }
            operation = ""
            firstArgument = 0.0
            secondArgument = 0.0
            firstArgHasDot = tmpResult - tmpResult.toLong() != 0.0
            secondArgHasDot = false
            var tmpResultStr = removeLastZeroes(tmpResult)
            firstArgStr = tmpResultStr
            secondArgStr = ""
            outputString = tmpResultStr
            Log.i("Compute", "successfully!")
        }
    }

    fun removeLastZeroes(number: Double): String {
        var tmpNumberStr = number.toString()
        while (tmpNumberStr.last() == '0') {
            tmpNumberStr = tmpNumberStr.dropLast(DIGITLENGTH)
        }
        if (tmpNumberStr.last() == '.') {
            tmpNumberStr = tmpNumberStr.dropLast(DOTLENGTH)
        }
        return tmpNumberStr
    }

    fun actionPressed(buttonId: Int) {
        if (naNOrInfinity() && buttonId != R.id.clear) {
            return
        }
        when (buttonId) {
            R.id.divide -> {
                if (operation == "") {
                    operation = "/"
                    outputString += " / "
                }
            }
            R.id.multiply -> {
                if (operation == "") {
                    operation = "*"
                    outputString += " * "
                }
            }
            R.id.add -> {
                if (operation == "") {
                    operation = "+"
                    outputString += " + "
                }
            }
            R.id.subtract -> {
                if (operation == "") {
                    operation = "-"
                    outputString += " - "
                }
            }
            R.id.clear -> {
                firstArgument = 0.0
                secondArgument = 0.0
                firstArgHasDot = false
                secondArgHasDot = false
                operation = ""
                firstArgStr = ""
                secondArgStr = ""
                outputString = ""
            }
            R.id.delete -> {
                if (outputString != "") {
                    if (outputString.last().isDigit()) {
                        if (operation == "") {
                            firstArgStr = firstArgStr.dropLast(DIGITLENGTH)
                            outputString = outputString.dropLast(DIGITLENGTH)
                        } else {
                            secondArgStr = secondArgStr.dropLast(DIGITLENGTH)
                            outputString = outputString.dropLast(DIGITLENGTH)
                        }
                    } else {
                        if (outputString.last() == ',') {
                            if (operation == "") {
                                firstArgHasDot = false
                                firstArgStr = firstArgStr.dropLast(DOTLENGTH)
                                outputString = outputString.dropLast(DOTLENGTH)
                            } else {
                                secondArgHasDot = false
                                secondArgStr = secondArgStr.dropLast(DOTLENGTH)
                                outputString = outputString.dropLast(DOTLENGTH)
                            }
                        } else {
                            operation = ""
                            outputString = outputString.dropLast(OPERATIONLENGTH)
                        }
                    }
                }
            }
            R.id.dot -> {
                if (firstArgStr != "" && !firstArgHasDot && operation == "") {
                    firstArgHasDot = true
                    firstArgStr += '.'
                    outputString += '.'
                }
                if (secondArgStr != "" && !secondArgHasDot && operation != "") {
                    secondArgHasDot = true
                    secondArgStr += '.'
                    outputString += '.'
                }
            }
            R.id.equal -> {
                Log.i("Equal", "pressed!")
                compute()
            }
        }
    }
}