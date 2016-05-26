package com.cyworks.memeartist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Selector extends AppCompatActivity {

    DrawerLayout memeDrawer;
    Button memeDrawerButton;
    ImageView memeImage;
    private final static int SELECT_PHOTO = 12345;
    public static Bitmap memeBitmap;
    boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        selected = false;

        memeDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        memeDrawerButton = (Button) findViewById(R.id.memeDrawerButton);

        String[] memes = {"Use an image from your gallery", "Scumbag Steve", "Insanity wolf", "Philosoraptor", "Good advice duck", "Bad advice duck", "Grumpy cat", "I don't always"};
        ListAdapter myAdapter = new MemeAdapter(this, memes);
        final ListView memeList = (ListView) findViewById(R.id.memeList);
        memeList.setAdapter(myAdapter);
        memeList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView currentMemeText = (TextView) findViewById(R.id.currentMemeText);
                        memeImage = (ImageView) findViewById(R.id.memeImage);
                        switch (position) {
                            case 0:
                                useOwnImage();
                                currentMemeText.setText("It's your pic, go crazy with it");
                                selected = true;
                                break;
                            case 1:
                                memeImage.setImageResource(R.drawable.scumbag_steve);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("When you evoke that one douchebag who ruined your day");
                                selected = true;
                                break;
                            case 2:
                                memeImage.setImageResource(R.drawable.insanity_wolf);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("You must have done something REALLY crazy...");
                                selected = true;
                                break;
                            case 3:
                                memeImage.setImageResource(R.drawable.philosoraptor);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("A revolutionary shower thought");
                                selected = true;
                                break;
                            case 4:
                                memeImage.setImageResource(R.drawable.good_advice_duck);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("Give some live-saving advice with this duck.");
                                selected = true;
                                break;
                            case 5:
                                memeImage.setImageResource(R.drawable.bad_advice_duck);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("Give some horrible advice with this duck.");
                                selected = true;
                                break;
                            case 6:
                                memeImage.setImageResource(R.drawable.grumpy_cat);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("A hateful message to humanity.");
                                selected = true;
                                break;
                            case 7:
                                memeImage.setImageResource(R.drawable.i_dont_always);
                                memeImage.buildDrawingCache();
                                memeBitmap = memeImage.getDrawingCache();
                                currentMemeText.setText("I don't always [stuff], but when I do...[other stuff]");
                                selected = true;
                                break;
                            default:
                                break;
                        }
                        memeDrawer.closeDrawer(Gravity.LEFT);
                    }
                }
        );
    }

    public void openDrawer(View view) {
        memeDrawer.openDrawer(Gravity.LEFT);
    }

    public void useOwnImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            memeBitmap = BitmapFactory.decodeFile(imagePath, options);
            memeImage.setImageBitmap(memeBitmap);
            cursor.close();
        }
    }

    public void gotoCreator(View view) {
        if (selected) {
            Intent intent = new Intent(this, Creator.class);
            selected = false;
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "You have to select an image", Toast.LENGTH_SHORT).show();
        }
    }
}
