package pl.potera.bmiapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ToggleButton;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private static final String WEIGHT = "50";
    private static final String HEIGHT = "150";
    private static final String RESULT_KG = "22.22";
    private static final String RESULT_LB = "10.08";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void before(){
        onView(withId(R.id.input_weight)).perform(clearText(), typeText(WEIGHT));
        onView(withId(R.id.input_height)).perform(clearText(), typeText(HEIGHT));
    }

    @Test
    public void check_bmi_kg_results(){
        switchWeightKG(true);
        clickCalculate();
        onView(withId(R.id.bmi_result)).check(matches(withText(RESULT_KG)));
    }

    @Test
    public void check_bmi_pounds_results(){
        switchWeightKG(false);
        clickCalculate();
        onView(withId(R.id.bmi_result)).check(matches(withText(RESULT_LB)));
    }

    private void switchWeightKG(boolean state){
        ToggleButton weightButton =
                (ToggleButton) mActivityRule.getActivity().findViewById(R.id.weight_switch);
        if(weightButton.isChecked() == state){
            onView(withId(R.id.weight_switch)).perform(click());
        }
    }

    private void clickCalculate(){
        onView(withId(R.id.calculate)).perform(click());
    }

}
