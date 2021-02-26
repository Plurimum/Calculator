package com.example.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class ComputeResultTest {
    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickFivePlusFiveEqual() {
        onView(withId(R.id.five)).perform(click())
        onView(withId(R.id.add)).perform(click())
        onView(withId(R.id.five)).perform(click())
        onView(withId(R.id.equal)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("10")))
        onView(withId(R.id.clear)).perform(click())
    }

    @Test
    fun clickAnyDigitsOrOperationsToNaN() {
        onView(withId(R.id.zero)).perform(click())
        onView(withId(R.id.divide)).perform(click())
        onView(withId(R.id.zero)).perform(click())
        onView(withId(R.id.equal)).perform(click())
        checkOperationOrDigitOnNaN(R.id.add)
        checkOperationOrDigitOnNaN(R.id.divide)
        checkOperationOrDigitOnNaN(R.id.multiply)
        checkOperationOrDigitOnNaN(R.id.equal)
        checkOperationOrDigitOnNaN(R.id.zero)
        checkOperationOrDigitOnNaN(R.id.one)
        onView(withId(R.id.clear)).perform(click())
    }

    private fun checkOperationOrDigitOnNaN(id: Int) {
        onView(withId(id)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("NaN")))
    }
}