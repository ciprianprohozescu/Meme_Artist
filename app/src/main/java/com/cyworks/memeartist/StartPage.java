package com.cyworks.memeartist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        TextView startText = (TextView) findViewById(R.id.startText);

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(10);
        switch (randomInt) {
            case 0:
                startText.setText("Be creative! :)");
                break;
            case 1:
                startText.setText("If you are unsure how to use a meme, ALWAYS look it up on the Internet.");
                break;
            case 2:
                startText.setText("\"Not all that glitters is gold\" -William Shakespeare \n\"Not all that is gold glitters\" -J.R.R.Tolkien");
                break;
            case 3:
                startText.setText("\"Art should terrify\" -a video game character \nMemes should terrify too.");
                break;
            case 4:
                startText.setText("A meme a day keeps the lols close... Rhymes are overrated.");
                break;
            case 5:
                startText.setText("Always share your memes with the world. Unless they suck, of course.");
                break;
            case 6:
                startText.setText("How do memes grow? They grow out of your life. \n-adapted from Robert Penn Warren");
                break;
            case 7:
                startText.setText("\"The poet is a liar who always speaks the truth.\" -Jean Cocteau");
                break;
            case 8:
                startText.setText("\"You can't blame gravity for falling in love\" -Albert Einstein");
                break;
            case 9:
                startText.setText("\"Words are but pictures of our thoughts.\" -John Dryden \nSo are memes.");
                break;
        }

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void gotoSelector(View view) {
        Intent goSelector = new Intent(this, Selector.class);
        startActivity(goSelector);
    }
}
