package com.jasonngor.blackjack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Gameplay extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private TextView txtCash;
    private TextView txtHand;
    private Deck hand;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        txtCash = (TextView) findViewById(R.id.txtCash);
        txtCash.setText("" + sharedPref.getInt(getString(R.string.cash), 0));
        txtHand = (TextView) findViewById(R.id.txtHand);



        // handles the game logic
        deck = new Deck();
        deck.newDeck();
        deck.shuffle();

        hand = new Deck();
        hand.addCard(deck.popCard());
        hand.addCard(deck.popCard());
        updateHand();

    }
    // updates txtHand
    public void updateHand() {
        String s = "";
        for (Card card:hand) {
            s += card.toString() + "\n";
        }
        txtHand.setText(s);
    }
    // method to be called when the hit button is pressed
    public void hit(View view) {
        if (hand.size() < 5) {
            hand.addCard(deck.popCard());
            updateHand();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.full_hand_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

}
