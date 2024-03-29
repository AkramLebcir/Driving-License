package com.akramlebcir.mac.drivinglicense.Activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.akramlebcir.mac.drivinglicense.R;
import com.akramlebcir.mac.drivinglicense.Controller.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

public class LoginActivityTest {

    private String email = "akram";
    private String password = "akramakram";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void logintestui(){
        Espresso.onView(withId(R.id.email)).perform(typeText(email));
        Espresso.onView(withId(R.id.password)).perform(typeText(password));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.btn_login)).perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        Espresso.onView(withId(R.id.toolbar_id)).perform(click());

        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Search"), childAtPosition(childAtPosition(
                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),0),
                                0), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatImageView = onView(
                Matchers.allOf(withId(R.id.icon_star), childAtPosition(childAtPosition(
                        withId(R.id.recycler_view), 1), 3), isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                Matchers.allOf(withId(R.id.icon_profile), childAtPosition(childAtPosition(
                        withId(R.id.recycler_view), 1), 2), isDisplayed()));
        appCompatImageView2.perform(click());

        Espresso.onView(withId(R.id.recycler_view)).perform(swipeUp());

        Espresso.onView(withId(R.id.recycler_view)).perform(scrollTo());

        Espresso.onView(withId(R.id.recycler_view)).perform(longClick());

        Espresso.onView(withId(R.id.floatingActionButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}