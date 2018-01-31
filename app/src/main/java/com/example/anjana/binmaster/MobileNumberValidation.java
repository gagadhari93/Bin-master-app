package com.example.anjana.binmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MobileNumberValidation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_validation);
    }

    public void goNext(View v){
        Intent i=new Intent(MobileNumberValidation.this,RegSuccessPage.class);

        startActivity(i);

    }
}
