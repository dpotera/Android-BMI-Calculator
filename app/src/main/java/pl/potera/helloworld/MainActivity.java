package pl.potera.helloworld;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CountBmi countBmi = new CountBmiKG();

        final EditText inputHeight = (EditText) findViewById(R.id.input_height);
        final EditText inputWeight = (EditText) findViewById(R.id.input_weight);
        final TextView bmiResult = (TextView) findViewById(R.id.bmi_result);

        Button calculateButton = (Button) findViewById(R.id.calculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    float bmi = countBmi.countBmi(getFloatValue(inputWeight), getFloatValue(inputHeight));
                    bmiResult.setText(String.format(getString(R.string.bmi_result_title), bmi));
                } catch (IllegalArgumentException e){
                    bmiResult.setText(e.getMessage());
                }
                closeKeyboard();
            }
        });
    }

    private float getFloatValue(EditText editText){
        return Float.valueOf(editText.getText().toString());
    }

    private void closeKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
