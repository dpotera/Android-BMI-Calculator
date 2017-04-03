package pl.potera.helloworld;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.potera.helloworld.bmi.BmiCalculator;
import pl.potera.helloworld.bmi.BmiResult;
import pl.potera.helloworld.utils.PoundsConverter;

import static pl.potera.helloworld.bmi.BmiStatus.*;

public class MainActivity extends AppCompatActivity {

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

    private BmiCalculator bmiCalculator;
    private BmiResult bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bmiCalculator = new BmiCalculator();
        bmi = null;
    }

    @OnClick(R.id.calculate)
    public void calculateButtonOnClick(){
        if(calculateBmi(convertWeightUnits(getDoubleValue(inputWeight)), getDoubleValue(inputHeight))){
            showShortToast(bmi.getStatus().toString());
        } else {
            showShortToast("Please enter valid weight and height!");
        }
    }

    private double convertWeightUnits(double weight){
        return weightToggleButton.isChecked() ? PoundsConverter.toKg(weight) : weight;
    }

    private boolean calculateBmi(double weight, double height){
        try{
            bmi = bmiCalculator.countBmi(weight, height);
            bmi.setColor(getBmiResultColor());
            showBmiResults();
        } catch (IllegalArgumentException e){
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

    private void closeKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void showShortToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }
}
