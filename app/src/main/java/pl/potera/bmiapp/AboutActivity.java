package pl.potera.bmiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindString;
import pl.potera.bmiapp.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
