package com.example.calculator

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DigitPressedTest {

    lateinit var brains: CalculatorBrains

    @Before
    fun init() {
        brains = CalculatorBrains()
    }

    private fun getInitializedArgAndPressDigit(
        argument: Int,
        stringValue: String,
        button: Int
    ): String {
        if (argument == 1) {
            brains.firstArgStr = stringValue
        } else {
            brains.operation = "+"
            brains.secondArgStr = stringValue
        }
        brains.digitPressed(button)
        return if (argument == 1) brains.firstArgStr else brains.secondArgStr
    }

    private fun errorMessage(addingValue: String, partOfNumber: String, argument: Int): String {
        return String.format(
            "Wrong adding %s to %s part of %s",
            addingValue,
            partOfNumber,
            (if (argument == 1) "first argument" else "second argument")
        )
    }

    private fun argumentWithoutDotAssertion(argument: Int) {
        assertEquals(
            errorMessage("non-zero digit", "integer", argument),
            "2281",
            getInitializedArgAndPressDigit(argument, "228", R.id.one)
        )
        assertEquals(
            errorMessage("\"0\"", "integer", argument),
            "0",
            getInitializedArgAndPressDigit(argument, "0", R.id.zero)
        )
    }

    private fun argumentWithDotAssertion(argument: Int) {
        assertEquals(
            errorMessage("non-zero digit", "fractional", argument),
            "228.01",
            getInitializedArgAndPressDigit(argument, "228.0", R.id.one)
        )
        assertEquals(
            errorMessage("non-zero digit", "empty after dot", argument),
            "228.1",
            getInitializedArgAndPressDigit(argument, "228.", R.id.one)
        )
        assertEquals(
            errorMessage("\"0\"", "fractional", argument),
            "228.00",
            getInitializedArgAndPressDigit(argument, "228.0", R.id.zero)
        )
    }

    @Test
    fun testToFirstArgWithoutDot() {
        brains.firstArgHasDot = false
        argumentWithoutDotAssertion(1)
    }

    @Test
    fun testToFirstArgWithDot() {
        brains.firstArgHasDot = true
        argumentWithDotAssertion(1)
    }

    @Test
    fun testToSecondArgWithoutDot() {
        brains.secondArgHasDot = false
        argumentWithoutDotAssertion(2)
    }

    @Test
    fun testToSecondArgWithDot() {
        brains.secondArgHasDot = true
        argumentWithDotAssertion(2)
    }
}