package com.evaluation.countrylist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.evaluation.dagger.data.DataComponent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.init();

        setContentView(R.layout.activity_main);
    }
}
