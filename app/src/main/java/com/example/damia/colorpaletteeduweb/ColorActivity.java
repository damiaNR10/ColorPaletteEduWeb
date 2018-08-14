package com.example.damia.colorpaletteeduweb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorActivity extends AppCompatActivity {

    public static final String LOG_TAG = ColorActivity.class.getSimpleName();
    @BindView(R.id.redSeekBar)
    SeekBar redSeekBar;
    @BindView(R.id.greenSeekBar)
    SeekBar greenSeekBar;
    @BindView(R.id.blueSeekBar)
    SeekBar blueSeekBar;
    @BindView(R.id.generateButton)
    Button generateButton;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.colorLinearLayout)
    LinearLayout colorLinearLayout;


    private int red;
    private int green;
    private int blue;

    private ActionBar actionBar;
    private Random random = new Random(); //do generowania liczb pseudolosowych


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Log.d("sprawdzam", "sprawdzam");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.generateButton)
    public void generate() {
        Log.d("sprawdzam", "sprawdzam1");
        red = random.nextInt(256); //wygenerowanie losowej wartości z przedziału 0-255
        green = random.nextInt(256);
        blue = random.nextInt(256);
        Log.d("sprawdzam", "sprawdzam2");

        int color = Color.rgb(red, green, blue); //"tworzenie" koloru
        colorLinearLayout.setBackgroundColor(color); //ustawienie koloru tła
        Log.d("sprawdzam", "sprawdzam3");
    }

    @OnClick(R.id.saveButton)
    public void save() {

    }


}
