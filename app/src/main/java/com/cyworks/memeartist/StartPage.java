package com.cyworks.memeartist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
    }

    public void gotoSelector(View view) {
        Intent goSelector = new Intent(this, Selector.class);
        startActivity(goSelector);
        finish();
    }
}
