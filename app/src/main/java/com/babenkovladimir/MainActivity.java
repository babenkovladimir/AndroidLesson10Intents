package com.babenkovladimir;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.babenkovladimir.async.HandlerActivity;
import com.babenkovladimir.intents.IntentActivity;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.btNavigateIntentsActivity)
  Button btIntents;

  @BindView(R.id.btNavigateAsyncActivity)
  Button btAsync;

  @BindView(R.id.btHandlerActivity)
  Button btHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    btIntents.setOnClickListener(view -> startActivity(new Intent(this, IntentActivity.class)));

    btHandler.setOnClickListener(view -> startActivity(new Intent(this, HandlerActivity.class)));
  }
}
