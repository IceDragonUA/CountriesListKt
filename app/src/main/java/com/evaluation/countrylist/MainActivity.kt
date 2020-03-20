package com.evaluation.countrylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.evaluation.dagger.data.DataComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataComponent.Injector.init()
        setContentView(R.layout.activity_main)
    }
}