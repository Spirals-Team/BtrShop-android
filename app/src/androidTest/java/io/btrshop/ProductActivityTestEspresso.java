package io.btrshop;

import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.btrshop.R;
import io.btrshop.products.ProductsActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by charlie on 2/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class ProductActivityTestEspresso {

    @Rule
    public ActivityTestRule<ProductsActivity> mActivityProduct =
            new ActivityTestRule<>(ProductsActivity.class);

    @Before
    public void unlockScreen() {
        final ProductsActivity activity = mActivityProduct.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }


    @Test
    public void startProductActivity() {
        // Test l'activity
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));

        // Test si la page s'est ouverte
        onView(withId(R.id.fab_scan_article)).check(matches(isDisplayed()));
        // Test du click sur du scan
        onView(withId(R.id.fab_scan_article)).perform(click());
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }

}
