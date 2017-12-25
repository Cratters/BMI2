package com.demo.android.bmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{

    TextView result;
    TextView suggest;
    Button back;
    public static final String TAG ="LifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Report.onCreate");
        setContentView(R.layout.activity_report);
        findViews();
        calcBMI();
    }

    void findViews(){
        result = (TextView) findViewById(R.id.result);
        suggest = (TextView) findViewById(R.id.suggest);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    void calcBMI(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String height = bundle.getString("KEY_HEIGHT");
        String weight = bundle.getString("KEY_WEIGHT");
        double h = Double.parseDouble(height) / 100;
        double w = Double.parseDouble(weight);
        double BMI = w / (h * h);
        Log.d(TAG,"BMI=" + BMI);
        System.out.println("BMI = " + BMI);

        DecimalFormat df = new DecimalFormat("0.00");

        result.setText("您的BMI值：" + df.format(BMI));

        if (BMI > 25)
            suggest.setText(R.string.advice_heavy);
        else if (BMI < 20)
            suggest.setText(R.string.advice_light);
        else
            suggest.setText(R.string.advice_average);
        //openOptionsDialog();

    }

    @Override
    public void onClick(View v) {
        finish();
//        Intent intent = new Intent();
//        intent.setClass(ReportActivity.this,Bmi.class);
//        startActivity(intent);
    }
}
