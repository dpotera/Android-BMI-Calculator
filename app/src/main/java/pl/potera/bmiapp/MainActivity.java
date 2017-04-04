package pl.potera.bmiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.potera.bmiapp.bmi.BmiCalculator;
import pl.potera.bmiapp.bmi.BmiResult;
import pl.potera.bmiapp.utils.PoundsConverter;

public class MainActivity extends AppCompatActivity implements BmiMainActivity {

    @BindView(R.id.input_height)
    EditText inputHeight;
    @BindView(R.id.input_weight)
    EditText inputWeight;
    @BindView(R.id.bmi_result)
    TextView bmiResult;
    @BindView(R.id.switch1)
    ToggleButton weightToggleButton;

    @BindColor(R.color.bmi_green)
    int green;
    @BindColor(R.color.bmi_red)
    int red;
    @BindColor(R.color.bmi_yellow)
    int yellow;

    @BindString(R.string.invalid_input_msg)
    String INVALID_INPUT_MSG;
    @BindString(R.string.saved_state)
    String SAVED_STATE;
    @BindString(R.string.save_failed)
    String SAVE_FAILED;

    private BmiCalculator bmiCalculator;
    private BmiResult bmi;

    private double weight;
    private double height;
    private boolean poundsUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bmiCalculator = new BmiCalculator();
        bmi = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean(PREFERENCE_SAVE, false)){
            inputWeight.setText(Float.toString(preferences.getFloat(PREFERENCE_WEIGHT, 0f)));
            inputHeight.setText(Float.toString(preferences.getFloat(PREFERENCE_HEIGHT, 0f)));
            if(preferences.getBoolean(PREFERENCE_POUNDS_CHECKED, false)){
                weightToggleButton.setChecked(true);
            }
            calculateButtonOnClick();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                menuActionSave();
                break;
            case R.id.action_share:
                menuActionShare();
                break;
            case R.id.action_about:
                openAboutActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void menuActionShare(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, bmi.getResultMessage());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void menuActionSave(){
        if(isValidResult()){
            saveState();
            showShortToast(SAVED_STATE);
        } else {
            showShortToast(SAVE_FAILED);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.action_save).setEnabled(isValidResult());
        menu.findItem(R.id.action_share).setEnabled(isValidResult());
        return true;
    }

    @OnClick(R.id.calculate)
    public void calculateButtonOnClick(){
        if(inputChanged()){
            weight = getDoubleValue(inputWeight);
            height = getDoubleValue(inputHeight);
            if(calculateBmi(convertWeightUnits(weight), height)){
                showShortToast(bmi.getStatus().toString());
            } else {
                showShortToast(INVALID_INPUT_MSG);
            }
        }
    }

    private double convertWeightUnits(double weight){
        poundsUnits = weightToggleButton.isChecked();
        return weightToggleButton.isChecked() ? PoundsConverter.toKg(weight) : weight;
    }

    private void saveState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFERENCE_SAVE, bmi != null);
        if(bmi != null){
            editor.putFloat(PREFERENCE_WEIGHT, (float) weight);
            editor.putFloat(PREFERENCE_HEIGHT, (float) height);
            editor.putBoolean(PREFERENCE_POUNDS_CHECKED, poundsUnits);
        }
        editor.apply();
    }

    private boolean calculateBmi(double weight, double height){
        try{
            bmi = bmiCalculator.countBmi(weight, height);
            bmi.setColor(getBmiResultColor());
            showBmiResults();
        } catch (IllegalArgumentException e){
            bmi = null;
            bmiResult.setText("");
            return false;
        } finally {
            closeKeyboard();
        }
        return true;
    }

    private int getBmiResultColor(){
        switch (bmi.getStatus()){
            case NORMAL: return green;
            case OVERWEIGHT: return yellow;
            case UNDERWEIGHT:
            case OBESITY: return red;
            default: return 256;
        }
    }

    private void showBmiResults(){
        bmiResult.setText(bmi.toString());
        bmiResult.setTextColor(bmi.getColor());
    }

    private double getDoubleValue(EditText editText){
        return isEmpty(editText) ? 0 : Double.valueOf(editText.getText().toString());
    }

    private boolean isEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }

    private boolean isValidResult(){
        return bmi != null;
    }

    private void closeKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if(currentFocus != null){
            inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean inputChanged(){
        return getDoubleValue(inputWeight) != weight || getDoubleValue(inputHeight) != height ||
                weightToggleButton.isChecked() != poundsUnits;
    }

    private void showShortToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }
}
