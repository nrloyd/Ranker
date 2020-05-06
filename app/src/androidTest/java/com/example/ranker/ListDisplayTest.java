package com.example.ranker;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
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
import static androidx.test.espresso.action.ViewActions.typeText;
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
    public void rankAndDisplayGroup() {

        //click on "AFC North"
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        //check that group title says "AFC North"
        onView(withId(R.id.group_title)).check(matches(withText("AFC North")));

        //check that left option says "Ravens" and right option says "Bengals"; select left
        onView(withId(R.id.left_option_name)).check(matches(withText("Ravens")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Bengals")));
        onView(withId(R.id.left_option)).perform(click());

        //check that left option says "Browns" and right option says "Steelers"; select right
        onView(withId(R.id.left_option_name)).check(matches(withText("Browns")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Steelers")));
        onView(withId(R.id.right_option)).perform(click());

        //check that left option says "Ravens" and right option says "Steelers"; select right
        onView(withId(R.id.left_option_name)).check(matches(withText("Ravens")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Steelers")));
        onView(withId(R.id.right_option)).perform(click());

        //check that left option says "Ravens" and right option says "Browns"; select left
        onView(withId(R.id.left_option_name)).check(matches(withText("Ravens")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Browns")));
        onView(withId(R.id.left_option)).perform(click());

        //check that left option says "Bengals" and right option says "Browns"; select "Tie"
        onView(withId(R.id.left_option_name)).check(matches(withText("Bengals")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Browns")));
        onView(withId(R.id.tie_option)).perform(click());

        //check that each element of the table matches the corresponding string in the ranked group
        String[] ranks = new String[]{"1","2","3","3"};
        String[] items = new String[]{"Steelers","Ravens","Bengals","Browns"};

        for(int index = 0; index < items.length; index++) {
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(ranks[index])))));
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(items[index])))));

        }
    }

    @Test
    public void clickOnOneItemGroup() {

        //click on "One item"
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));

        //check the lone element of the table matches the corresponding string in the ranked group
        onView(withId(R.id.ranked_list)).perform(scrollToPosition(0))
                .check(matches(atPosition(0, hasDescendant(withText("1")))));
        onView(withId(R.id.ranked_list)).perform(scrollToPosition(0))
                .check(matches(atPosition(0, hasDescendant(withText("item")))));

    }

    @Test
    public void clickOnSortedGroup() {

        //click on "Famous Ducks"
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));

        //rank items
        onView(withId(R.id.left_option)).perform(click());
        onView(withId(R.id.left_option)).perform(click());
        onView(withId(R.id.right_option)).perform(click());

        //check that each element of the table matches the corresponding string in the ranked group
        String[] ranks = new String[]{"1","2","3"};
        String[] items = new String[]{"Donald","Daisy","Scrooge"};

        for(int index = 0; index < items.length; index++) {
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(ranks[index])))));
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(items[index])))));

        }

        Espresso.pressBack();

        //click on "Famous Ducks" again
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));

        //check that each element of the table matches the corresponding string in the ranked group again
        for(int index = 0; index < items.length; index++) {
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(ranks[index])))));
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(items[index])))));

        }

        //click on "Back to List"
        onView(withId(R.id.back_button)).perform(click());

        //click on "Famous Ducks" again
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));

        //check that each element of the table matches the corresponding string in the ranked group again
        for(int index = 0; index < items.length; index++) {
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(ranks[index])))));
            onView(withId(R.id.ranked_list)).perform(scrollToPosition(index))
                    .check(matches(atPosition(index, hasDescendant(withText(items[index])))));

        }
    }

    @Test
    public void resetListButton() {
        //click on "Famous Ducks"
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));

        //rank items
        onView(withId(R.id.left_option)).perform(click());
        onView(withId(R.id.left_option)).perform(click());
        onView(withId(R.id.right_option)).perform(click());

        //click on "Re-rank list" button
        onView(withId(R.id.reset_button)).perform(click());

        //check that the options say "Donald" and "Scrooge"; select "Donald"
        onView(withId(R.id.left_option_name)).check(matches(withText("Donald")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Scrooge")));
        onView(withId(R.id.left_option)).perform(click());

        Espresso.pressBack();

        //click on "Famous Ducks" again
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));

        //check that the options say "Donald" and "Daisy"; select "Daisy"
        onView(withId(R.id.left_option_name)).check(matches(withText("Donald")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Daisy")));
        onView(withId(R.id.right_option)).perform(click());
    }

    @Test
    public void createNewList() {
        //click the new list button
        onView(withId(R.id.fab)).perform(click());

        //type a list name and contents
        onView(withId(R.id.edit_list_name)).perform(typeText("Test list title"));
        onView(withId(R.id.edit_list_content)).perform(typeText("Option 1\nOption 2\nOption 3"));

        //click "create"
        onView(withId(R.id.create_list_button)).perform(click());

        //check the last option on the list of lists
        onView(withId(R.id.list_groups)).perform(RecyclerViewActions.actionOnItemAtPosition(6,click()));

        //check that the options say "Option 1" and "Option 2"
        onView(withId(R.id.left_option_name)).check(matches(withText("Option 1")));
        onView(withId(R.id.right_option_name)).check(matches(withText("Option 2")));
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