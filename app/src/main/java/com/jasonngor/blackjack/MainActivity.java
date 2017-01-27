package com.jasonngor.blackjack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private EditText editBet;
    private Button btnBet;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private int startingCash = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        editBet = (EditText) findViewById(R.id.editBet);
        btnBet = (Button) findViewById(R.id.btnBet);

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt(getString(R.string.cash), startingCash);
        editor.commit();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int bet = ++progress;
                editBet.setText(Integer.toString(bet));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editBet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int bet = Integer.parseInt(s.toString());
                    bet--;
                    if (bet >= 0 && bet <= 19) {
                        seekBar.setProgress(bet);
                        editBet.setSelection(editBet.getText().length());
                    }
                    else if (bet < 0) {
                        seekBar.setProgress(0);
                        editBet.setText("" + 0);
                        editBet.setSelection(editBet.getText().length());
                    }

                    else if (bet > 19) {
                        seekBar.setProgress(20);
                        editBet.setText("" + 20);
                        editBet.setSelection(editBet.getText().length());
                    }

                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    public void startGame(View view) {
        int currentBet = Integer.parseInt(editBet.getText().toString());
        int cash = sharedPref.getInt(getString(R.string.cash), 0);
        editor.putInt(getString(R.string.current_bet), currentBet);
        editor.putInt(getString(R.string.cash), cash - currentBet);
        editor.commit();
        Intent intent = new Intent(this, Gameplay.class);
        this.startActivity(intent);
    }

}
