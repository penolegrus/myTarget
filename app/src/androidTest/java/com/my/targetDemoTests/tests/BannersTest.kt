package com.my.targetDemoTests.tests

import androidx.test.espresso.web.sugar.Web.onWebView
import com.facebook.testing.screenshot.Screenshot
import com.my.targetDemoApp.R
import com.my.targetDemoApp.activities.BannersActivity
import com.my.targetDemoTests.screens.BannersScreen
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.rule.BaristaRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BannersTest : TestBase() {

    private val bannersScreen = BannersScreen()

    @get:Rule
    var baristaRule = BaristaRule.create(BannersActivity::class.java)

    @Before
    override fun setUp() {
        super.setUp()
        baristaRule.launchActivity()
    }

    /**
     * Проверяет, что количесто абзацев после нажатия кнопки Show во вкладке Banners равно 10
     */
    @Test
    fun test_checkParagraphCount(){
        bannersScreen.show320x50()
        device.wait(bannersScreen.webview)
        val paragraphCount =
            baristaRule.activityTestRule.activity.findViewById<androidx.recyclerview.widget.RecyclerView>(
                R.id.rv_fish).adapter!!.itemCount
        assert(paragraphCount==10)
    }

    /**
     * Тоже самое, что и предыдущий тест, только другой метод проверки
     */
    @Test
    fun test_checkParagraphCount2(){
        bannersScreen.show320x50()
        device.wait(bannersScreen.webview)
        assertRecyclerViewItemCount(R.id.rv_fish,10)
    }

    @Test
    fun test_BannersMenuSnapshot() {
        Screenshot.snapActivity(baristaRule.activityTestRule.activity)
                .record()
    }

    @Test
    fun test_Show320x50() {
        bannersScreen.show320x50()
        device.wait(bannersScreen.webview)
        onWebView().forceJavascriptEnabled()
    }

    @Test
    fun test_Show300x250() {
        bannersScreen.show300x250()
        device.wait(bannersScreen.webview)
        onWebView().forceJavascriptEnabled()
    }

    @Test
    fun test_Show728x90() {
        bannersScreen.show728x90()
        device.wait(bannersScreen.webview)
        onWebView().forceJavascriptEnabled()
    }

    @Test
    fun test_ShowAdaptive() {
        bannersScreen.showAdaptive()
        device.wait(bannersScreen.webview)
    }
}