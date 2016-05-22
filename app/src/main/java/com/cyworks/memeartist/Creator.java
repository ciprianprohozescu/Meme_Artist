package com.cyworks.memeartist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class Creator extends Selector {

    ImageView memeTemplate;
    TextView topText, bottomText;
    EditText topInput, bottomInput;
    Bitmap topBitmap, bottomBitmap, finalMeme, memeTemplateBitmap;

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
    }

    public void saveMeme(View view) {
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

    public Bitmap combineImages(Bitmap background, Bitmap foreground1, Bitmap foreground2) {

        int width = 0, height = 0;
        Bitmap cs;

        width = background.getWidth();
        height = background.getHeight();

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height, true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground1, (width - topBitmap.getWidth()) / 2, 0, null);
        comboImage.drawBitmap(foreground2, (width - bottomBitmap.getWidth()) / 2, height - 100, null);
        return cs;
    }
}
