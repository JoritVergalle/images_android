package com.example.jorit.images_app;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppHasFourPresetTagsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textTagName), withText("Relationships"),
                        childAtPosition(
                                allOf(withId(R.id.tagItem),
                                        childAtPosition(
                                                withId(R.id.tags_recycler_view),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Relationships")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textTagName), withText("Living"),
                        childAtPosition(
                                allOf(withId(R.id.tagItem),
                                        childAtPosition(
                                                withId(R.id.tags_recycler_view),
                                                1)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Living")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textTagName), withText("Spare time"),
                        childAtPosition(
                                allOf(withId(R.id.tagItem),
                                        childAtPosition(
                                                withId(R.id.tags_recycler_view),
                                                2)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Spare time")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textTagName), withText("Health"),
                        childAtPosition(
                                allOf(withId(R.id.tagItem),
                                        childAtPosition(
                                                withId(R.id.tags_recycler_view),
                                                3)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Health")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textTagName), withText("Health"),
                        childAtPosition(
                                allOf(withId(R.id.tagItem),
                                        childAtPosition(
                                                withId(R.id.tags_recycler_view),
                                                3)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Health")));
    }

    private static Matcher<View> childAtPosition(
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
