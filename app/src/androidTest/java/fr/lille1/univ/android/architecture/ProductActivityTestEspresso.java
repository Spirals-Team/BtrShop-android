package fr.lille1.univ.android.architecture;

import android.content.Intent;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.lille1.univ.android.architecture.btrshop.R;
import fr.lille1.univ.android.architecture.btrshop.products.ProductsActivity;

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
    public void grantPhonePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.CALL_PHONE");
        }
    }

    @Test
    public void startProductActivity() {
        mActivityProduct.launchActivity(new Intent());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        // Test si la page s'est ouverte
        onView(withId(R.id.fab_scan_article)).check(matches(isDisplayed()));
        // Test du click sur du scan
        onView(withId(R.id.fab_scan_article)).perform(click());
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }

}
