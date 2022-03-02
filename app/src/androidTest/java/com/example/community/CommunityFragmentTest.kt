package com.example.community

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.community.presentation.MainActivity

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommunityFragmentTest {

    @Rule
    @JvmField
    public var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testRecyclerViewItemClick(){

        onView(withId(R.id.communityRv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5,
            click()))
    }

    @Test
    fun RecyclerViewScrollTest(){
        var recyclerView:RecyclerView = activityRule.activity.findViewById(R.id.communityRv)
        var recyclerViewItems = recyclerView.adapter?.itemCount

        if(recyclerView!=null){
            Espresso.onView(withId(R.id.communityRv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(recyclerViewItems!!.minus(1)))
        }
    }


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
}