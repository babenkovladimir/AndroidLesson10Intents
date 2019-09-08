package com.babenkovladimir.async;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.babenkovladimir.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerActivity extends AppCompatActivity {

    public static final int MESSAGE_HACK_IN_PROGRESS = 0;
    public static final int MESSAGE_HACK_END = 1;

    @BindView(R.id.etNeedToHack)
    EditText etNeedToHack;

    @BindView(R.id.btStartHack)
    Button btStartHack;

    @BindView(R.id.tvHackResult)
    TextView tvHackResult;

    @BindView(R.id.tvHackTime)
    TextView tvHackTime;

    @BindView(R.id.tvHackEndLabel)
    TextView tvHackEndLabel;

    @BindView(R.id.rgTask1)
    RadioGroup rg;

    @BindView(R.id.pb)
    ProgressBar pb;

    Handler h;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        ButterKnife.bind(this);

        h = new Handler() {

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                //	1-й способ

                tvHackResult.setText((String) msg.obj);
                tvHackTime.setText("" + msg.arg1 + " мс.");

                //	2-й способ

//                Bundle b = msg.getData();
//                tvHackResult.setText(b.getString("text"));
//                tvHackTime.setText("" + b.getLong("time") + " мс.");

                if (msg.what == MESSAGE_HACK_END) {
                    tvHackEndLabel.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    public void onClick(View view) {
        final String needToHackString = etNeedToHack.getText().toString();
        pb.setVisibility(View.VISIBLE);

        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbHandler:

                Runnable runnable = () -> {
                    int indexHacking = 0;
                    StringBuilder sbHackResult = new StringBuilder();
                    long timeStart = System.currentTimeMillis();

                    while (indexHacking < needToHackString.length()) {

                        for (char c = '\u0000'; c < 'z'; c++) {

                            // проходимся по всей UNICODE-таблице
                            // и сравниваем каждый символ в ней
                            // с выбранным из строки

                            if (c == needToHackString.charAt(indexHacking)) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Log.e("PassHack", " >> Подобрано : " + sbHackResult.append(c) + " за " + (System.currentTimeMillis() - timeStart) + " мс.");


                                // Создаём сообщение для отправки его в главный поток

                                Message msg = new Message();

                                // 1-й способ
                                msg.obj = sbHackResult.toString();
                                msg.arg1 = (int) (System.currentTimeMillis() - timeStart);

                                // 2-й способ

//                                Bundle data = new Bundle();
//                                data.putString("text", sbHackResult.toString());
//                                data.putLong("time", (System.currentTimeMillis() - timeStart));
//                                msg.setData(data);


                                msg.what = MESSAGE_HACK_IN_PROGRESS;
                                // Отсылка сообщения в хендлн
                                h.sendMessage(msg);

                            }
                        }
                        indexHacking++;
                    }

                    Message msg = new Message();

                    // 1 способ первый способ
                    msg.obj = sbHackResult.toString();
                    msg.arg1 = (int) (System.currentTimeMillis() - timeStart);
                    msg.what = MESSAGE_HACK_END;

                    // Второй способ

//                    Bundle data = new Bundle();
//                    data.putString("text", sbHackResult.toString());
//                    data.putLong("time", System.currentTimeMillis() - timeStart);
//                    msg.setData(data);
//                    msg.what = MESSAGE_HACK_END;

                    h.sendMessage(msg);

                };

                Thread thread = new Thread(runnable);
                thread.start();
                break;
        }
    }
}
