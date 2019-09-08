package com.babenkovladimir.intents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.babenkovladimir.R;
import java.io.File;

public class IntentActivity extends AppCompatActivity {

  @BindView(R.id.btAlyarmClock)
  Button btAlyarmClock;

  @BindView(R.id.btActionView)
  Button btActionView;

  @BindView(R.id.btTakePicture)
  Button btTakeVideo;

  @BindView(R.id.btOpenMap)
  Button btOpneMap;



  private static final int TAKE_PICTURE = 1;
  private Uri imageUri;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intent);

    ButterKnife.bind(this);

    btActionView.setOnClickListener(view -> {
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setData(Uri.parse("http://www.rutube.com"));
          startActivity(intent);
        }
    );

    btAlyarmClock.setOnClickListener(view -> {
          Intent alyarmIntent = new Intent();
          alyarmIntent.setAction(AlarmClock.ACTION_SET_ALARM);
          alyarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "My SkillUp message");
          startActivity(alyarmIntent);

        }
    );

    btOpneMap.setOnClickListener(view-> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      intent.setData(Uri.parse("geo:50.45025, 30.523889?z=17"));
      intent.setData(Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway%2C+CA"));
startActivity(intent);
    });

    btTakeVideo.setOnClickListener(view-> {
      takePhoto(view);
    });


  }

  public void takePhoto(View view) {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   // File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
//    intent.putExtra(MediaStore.EXTRA_OUTPUT,
//        Uri.fromFile(photo));
   // Uri imageUri = Uri.fromFile(photo);
    startActivityForResult(intent, TAKE_PICTURE);
  }

//  @Override
//  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    switch (requestCode) {
//      case TAKE_PICTURE:
//        if (resultCode == Activity.RESULT_OK) {
//          Uri selectedImage = imageUri;
//          getContentResolver().notifyChange(selectedImage, null);
//          ImageView imageView = (ImageView) findViewById(R.id.ImageView);
//          ContentResolver cr = getContentResolver();
//          Bitmap bitmap;
//          try {
//            bitmap = android.provider.MediaStore.Images.Media
//                .getBitmap(cr, selectedImage);
//
//            imageView.setImageBitmap(bitmap);
//            Toast.makeText(this, selectedImage.toString(),
//                Toast.LENGTH_LONG).show();
//          } catch (Exception e) {
//            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
//                .show();
//            Log.e("Camera", e.toString());
//          }
//        }
//    }
}
