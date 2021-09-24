package com.my.targetDemoTests.tests


import android.os.Environment
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import com.my.targetDemoApp.activities.InterstitialsActivity
import com.my.targetDemoTests.screens.InterstitialScreen

import com.schibsted.spain.barista.rule.BaristaRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test


import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

import androidx.test.uiautomator.UiDevice
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed



import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.DriverAtoms.webClick
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed



import org.json.JSONObject

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import com.my.targetDemoApp.R
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.internal.performAction
import com.schibsted.spain.barista.internal.performActionOnView
import kotlinx.coroutines.withContext
import java.util.regex.Pattern.matches
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import java.io.File


class AdsTest : TestBase() {

    private val interstitialScreen = InterstitialScreen()

    @get:Rule
    var baristaRule = BaristaRule.create(InterstitialsActivity::class.java)


    @Before
    override fun setUp() {
        super.setUp()
        baristaRule.launchActivity()
    }

    /**
     * Проверить, в остановленном видео, нажимается кнопка Play
     */
    @Test
    fun test_resumeVideo(){
        interstitialScreen.showVideoStyle2()
        device.wait(interstitialScreen.adView)
        onView(withContentDescription("play")).perform(click())
    }

    /**
     * Открывает promo рекламу, нажимает Кнопку закрыть через UiAutomator
     */
    @Test()
    fun  test_webview(){
        interstitialScreen.showPromoStatic()
        device.wait(interstitialScreen.adView)
        val localDevice = UiDevice.getInstance(getInstrumentation())
        localDevice.findObject(UiSelector().resourceId("close_button")).click()
        assertDisplayed(R.id.btn_load)
    }

    /**
     * Открывает Image promo рекламу, нажимает кнопку AdChoice, выбирает причину, реклама закрывается
     * не понял как adChoices достать из проекта для нажатия на кнопку (!) и получить options
     * с вариантами ответов. Получилось сделать только через поиск по родительским элементам на экране
     */
    @Test
    fun test_hideAdByReason(){
        interstitialScreen.showImage()
        device.wait(interstitialScreen.adView)
        val adChoice = onView(
            Matchers.allOf(
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.my.target.ga")),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        adChoice.perform(click())

        onView(withText("Не интересует")).perform(click())
        assertDisplayed(R.id.btn_load)

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}