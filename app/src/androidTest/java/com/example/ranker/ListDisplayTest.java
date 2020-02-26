package com.example.ranker;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ListDisplayTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void displayGroupList() {

        //click on "NSYNC members"
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        List<RankGroup> groups = DataManager.getInstance().getGroups();
        RankGroup nsync = groups.get(1);

        //check that group title says "NSYNC members"
        onView(withId(R.id.group_title)).check(matches(withText(nsync.getTitle())));

        //check that each element of the table matches the corresponding string in the group
        for(int index = 0; index < nsync.getStrings().length; index++) {

            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(nsync.getStrings()[index])))));

        }
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {

        checkNotNull(itemMatcher);

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) return false;
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

}