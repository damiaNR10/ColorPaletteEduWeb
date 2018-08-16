package com.example.damia.colorpaletteeduweb;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder> {

    public static final String COLORS_KEY = "colors";
    private final LayoutInflater layoutInflater;
    private final SharedPreferences sharedPreferences;
    private List<String> colors = new ArrayList<>();
    private ColorClickedListener colorClickedListener;

    public ColorAdapter(LayoutInflater layoutInflater, SharedPreferences sharedPreferences) {

        this.layoutInflater = layoutInflater;
        this.sharedPreferences = sharedPreferences;

        String colorsJson = sharedPreferences.getString(COLORS_KEY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(colorsJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                colors.add(i, jsonArray.getString(i));
            }
            notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//tworzy widok i opakowuje go w ViewHolder
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);//utowrzenie widokow pojedynczych elementow
        return new ColorViewHolder(view, this);//przypisanie odpowiednich wartosci do kazdego wiersza
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder colorViewHolder, int i) {//łączy widok z danymi, czyli dla poszczególnego wiersza wyświetla okreslone dane
        String colorInHex = colors.get(i);
        colorViewHolder.setColor(colorInHex);
    }

    @Override
    public int getItemCount() { //zlicza liczbę elemntów
        return colors.size(); //liczba elementów do wyświetlenia do długość listy elementów
    }

    public int add(String color) {
        colors.add(color);
        int position = colors.size() - 1;
        notifyItemInserted(position);

        storeInPreferences();
        return position;
    }

    private void storeInPreferences() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < colors.size(); i++) {
                jsonArray.put(i, colors.get(i));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(COLORS_KEY, jsonArray.toString());
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void remove(int position) {
        colors.remove(position);
        storeInPreferences();
        notifyDataSetChanged();

    }

    public void setColorClickedListener(ColorClickedListener colorClickedListener) {
        this.colorClickedListener = colorClickedListener;
    }

    public void clicked(int position) {
        if(colorClickedListener != null){
            colorClickedListener.onColorClicked(colors.get(position));
        }
    }

    public void replace(String oldColor, String colorInHex) {
        int indexOf = colors.indexOf(oldColor);
        colors.set(indexOf, colorInHex);
        notifyItemChanged(indexOf);
        storeInPreferences();
    }

    public void clear() {
        colors.clear();
        notifyDataSetChanged();
        sharedPreferences.edit().clear().apply();
    }

    public interface ColorClickedListener {
        void onColorClicked(String colorInHex);
    }
}

class ColorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private String color;
    private TextView textView;
    private final ColorAdapter colorAdapter;

    public ColorViewHolder(View itemView, ColorAdapter colorAdapter) {
        super(itemView);
        textView = (TextView) itemView;
        this.colorAdapter = colorAdapter;
        textView.setOnClickListener(this);

    }

    public void setColor(String color) {
        this.color = color;
        textView.setText(color);
        int backgroundColor = Color.parseColor(color);
        textView.setBackgroundColor(backgroundColor);
        textView.setTextColor(PaletteActivity.getTextColorFromColor(backgroundColor));
    }

    public String getColor() {
        return color;
    }

    @Override
    public void onClick(View view) {
        colorAdapter.clicked(getAdapterPosition());
    }
}