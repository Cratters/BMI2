package com.demo.android.bmi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class Bmi extends AppCompatActivity {

    Button button;
    EditText fieldHeight;
    EditText fieldWeight;
    TextView tipText1, tipText2, tipText3;
    ImageView imageView;
    LinearLayout description;
    int clickCount = 1;

    public static final String TAG = "LifeCycle";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"onSave " + clickCount);
        super.onSaveInstanceState(outState);
        if (outState == null)
            outState = new Bundle();
        outState.putInt("clickCount", clickCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG,"onRestore " + clickCount);
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            clickCount = savedInstanceState.getInt("clickCount");

        if(clickCount > 1)
            tipText2.setVisibility(View.VISIBLE);
        if(clickCount > 2)
            tipText3.setVisibility(View.VISIBLE);
        if(clickCount > 3)
            description.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Bmi.onCreate");
        setContentView(R.layout.activity_bmi);
        findViews();
        setListeners();
        playViewAnimation();
    }

    void findViews() {
        button = (Button) findViewById(R.id.submit);
        fieldHeight = (EditText) findViewById(R.id.height);
        fieldWeight = (EditText) findViewById(R.id.weight);
        tipText1 = (TextView) findViewById(R.id.tipText1);
        tipText2 = (TextView) findViewById(R.id.tipText2);
        tipText3 = (TextView) findViewById(R.id.tipText3);
        imageView = (ImageView) findViewById(R.id.imageView);
        description = (LinearLayout) findViewById(R.id.description);
    }

    void setListeners() {
        button.setOnClickListener(listener);
        description.setOnClickListener(descListener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Bmi.this, ReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT", fieldHeight.getText().toString());
            bundle.putString("KEY_WEIGHT", fieldWeight.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener descListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (clickCount == 1) {
                tipText2.setVisibility(View.VISIBLE);
                clickCount++;
            } else if (clickCount == 2) {
                tipText3.setVisibility(View.VISIBLE);
                clickCount++;
            } else {
                clickCount++;
                description.setVisibility(View.GONE);
            }
        }
    };


    void openOptionsDialog() {
        Toast.makeText(Bmi.this, "顯示Toast訊息", Toast.LENGTH_LONG).show();

        final ProgressDialog progressDialog =
                ProgressDialog.show(Bmi.this, "處理中...", "請等一會，處理完畢會自動結束...");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
//                    result.setTextColor(Color.BLUE);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        };
        thread.start();
    }


    void playViewAnimation() {
        AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
        animation.start();
    }
}
