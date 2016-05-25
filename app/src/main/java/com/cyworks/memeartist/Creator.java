package com.cyworks.memeartist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class Creator extends Selector {

    ImageView memeTemplate;
    TextView topText, bottomText;
    EditText topInput, bottomInput, topSize, bottomSize;
    Bitmap topBitmap, bottomBitmap, finalMeme, memeTemplateBitmap;
    float topFloat, bottomFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);
        memeTemplate = (ImageView) findViewById(R.id.memeTemplate);
        topText = (TextView) findViewById(R.id.topText);
        topText.setDrawingCacheEnabled(true);
        bottomText = (TextView) findViewById(R.id.bottomText);
        bottomText.setDrawingCacheEnabled(true);
        topInput = (EditText) findViewById(R.id.topInput);
        bottomInput = (EditText) findViewById(R.id.bottomInput);
        topSize = (EditText) findViewById(R.id.topSize);
        bottomSize = (EditText) findViewById(R.id.bottomSize);
        memeTemplate.setImageBitmap(memeBitmap);

        topInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                topText.setText(topInput.getText().toString());
            }
        });

        bottomInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                bottomText.setText(bottomInput.getText().toString());
            }
        });

        topSize.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!topSize.getText().toString().equals("")) {
                    topFloat = Float.parseFloat(topSize.getText().toString());
                    topText.setTextSize(topFloat);
                }
            }
        });

        bottomSize.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!bottomSize.getText().toString().equals("")) {
                    bottomFloat = Float.parseFloat(bottomSize.getText().toString());
                    bottomText.setTextSize(bottomFloat);
                }
            }
        });

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/meme.ttf");
        topText.setTypeface(custom_font);
        bottomText.setTypeface(custom_font);
        topText.setShadowLayer(10, 0, 0, Color.BLACK);
        bottomText.setShadowLayer(10, 0, 0, Color.BLACK);
    }

    public void saveMeme(View view) {
        if (topText.getText().toString().equals("") || bottomText.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "You should write some text there though...", Toast.LENGTH_SHORT).show();
        }
        else {
            topBitmap = Bitmap.createBitmap(topText.getDrawingCache());
            bottomBitmap = Bitmap.createBitmap(bottomText.getDrawingCache());
            memeTemplate.setDrawingCacheEnabled(true);
            memeTemplateBitmap = Bitmap.createBitmap(memeTemplate.getDrawingCache());
            finalMeme = combineImages(memeTemplateBitmap, topBitmap, bottomBitmap);
            MediaStore.Images.Media.insertImage(getContentResolver(), finalMeme, "meme", "dank");
            Toast.makeText(getApplicationContext(), "Meme saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), StartPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public Bitmap combineImages(Bitmap background, Bitmap foreground1, Bitmap foreground2) {

        int width = 0, height = 0;
        Bitmap cs;

        width = background.getWidth();
        height = background.getHeight();

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height, true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground1, (width - topBitmap.getWidth()) / 2, 0 - topText.getTextSize() / 10, null);
        comboImage.drawBitmap(foreground2, (width - bottomBitmap.getWidth()) / 2, height - 60 - bottomText.getTextSize(), null);
        return cs;
    }
}
