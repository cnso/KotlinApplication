package org.jash.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import org.jash.roomdemo.viewmodel.Main2ViewModel;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Main2ViewModel main2ViewModel = new ViewModelProvider(this).get(Main2ViewModel.class);
    }
}