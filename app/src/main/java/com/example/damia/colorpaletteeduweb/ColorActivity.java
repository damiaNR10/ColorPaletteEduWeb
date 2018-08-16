package com.example.damia.colorpaletteeduweb;

import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String LOG_TAG = ColorActivity.class.getSimpleName();
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String COLOR_IN_HEX_KEY = "color_in_hex";
    public static final String OLD_COLOR_KEY = "old_color";
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
    @BindView(R.id.redLabel)
    TextView redLabel;
    @BindView(R.id.greenLabel)
    TextView greenLabel;
    @BindView(R.id.blueLabel)
    TextView blueLabel;
    @BindView(R.id.colorScrollView)
    ScrollView colorScrollView;

    private int red;
    private int green;
    private int blue;

    private ActionBar actionBar;
    private Random random = new Random(); //do generowania liczb pseudolosowych
    private String oldColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.add_new_color);

        redSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);

        Intent intent = getIntent(); //zwraca intent użyty do uruchomienia tego activity
        oldColor = intent.getStringExtra(OLD_COLOR_KEY);
        if (oldColor != null) {
            int color = Color.parseColor(oldColor);
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);

            updateBackgroundColor();
            updateSeekBars();

            generateButton.setVisibility(View.GONE);
            actionBar.setTitle(R.string.edit_color);
        }
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
        red = random.nextInt(256); //wygenerowanie losowej wartości z przedziału 0-255
        green = random.nextInt(256);
        blue = random.nextInt(256);

        updateSeekBars();
        updateBackgroundColor();
    }

    private void updateSeekBars() {
        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);
    }

    private void updateBackgroundColor() {
        int color = Color.rgb(red, green, blue); //"tworzenie" koloru
        int textColor = PaletteActivity.getTextColorFromColor(color);

        redLabel.setTextColor(textColor);
        greenLabel.setTextColor(textColor);
        blueLabel.setTextColor(textColor);

        colorScrollView.setBackgroundColor(color); //ustawienie koloru tła
    }

    @OnClick(R.id.saveButton)
    public void save() {
        Intent data = new Intent(); //nowy Intent
        data.putExtra(COLOR_IN_HEX_KEY, String.format("#%02X%02X%02X", red, green, blue)); //"wkładamy" potrzebne dane o podanym formacie
        if (oldColor != null) {
            data.putExtra(OLD_COLOR_KEY, oldColor);
        }
        setResult(RESULT_OK, data); //ustawiamy wynik activity na wartość data
        finish(); //zakończenie activity

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.redSeekBar:
                red = i;
                break;
            case R.id.greenSeekBar:
                green = i;
                break;
            case R.id.blueSeekBar:
                blue = i;
                break;
        }
        updateBackgroundColor();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RED, red);
        outState.putInt(GREEN, green);
        outState.putInt(BLUE, blue);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        red = savedInstanceState.getInt(RED);
        green = savedInstanceState.getInt(GREEN);
        blue = savedInstanceState.getInt(BLUE);
    }
}
