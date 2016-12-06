package com.example.tatyana.buildplot;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tatyana.buildplot.graph.PlotActivity;

public class MenuActivity extends AppCompatActivity {

    Button plotRunButton, infoButton, languageButton;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Init buttons
        plotRunButton  = (Button) findViewById(R.id.btn_plot);
        infoButton     = (Button) findViewById(R.id.btn_info);
        languageButton = (Button) findViewById(R.id.btn_language);

        // Create intent for PlotActivity
        final Intent intent = new Intent(MenuActivity.this, PlotActivity.class);

        // Set text for button
        languageButton.setText(R.string.language_rus);
        plotRunButton.setText(R.string.plot_rus);
        infoButton.setText(R.string.info_rus);
        intent.putExtra(Constants.LANGUAGE, languageButton.getText());

        // Start activity with graphic
        plotRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // Show dialog info
        dialog = new AlertDialog.Builder(MenuActivity.this);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageButton.getText().equals(Constants.EN)) {
                    dialog.setTitle(R.string.info_en);
                    dialog.setMessage(R.string.about_en);
                } else {
                    dialog.setTitle(R.string.info_rus);
                    dialog.setMessage(R.string.about_rus);
                }
                dialog.show();
            }
        });

        //Change language
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageButton.getText().equals(Constants.RUS)) {
                    languageButton.setText(R.string.language_en);
                    plotRunButton.setText(R.string.plot_en);
                    infoButton.setText(R.string.info_en);
                } else {
                    languageButton.setText(R.string.language_rus);
                    plotRunButton.setText(R.string.plot_rus);
                    infoButton.setText(R.string.info_rus);
                }
                intent.putExtra(Constants.LANGUAGE, languageButton.getText());
            }
        });
    }
}