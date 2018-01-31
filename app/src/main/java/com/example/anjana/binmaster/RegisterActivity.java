package com.example.anjana.binmaster;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText fullnameET,emailET,addressET,mobilenoET,passwordET,reenterpasswordET;
    Button nextBTN;
    String fullname,email,address,mobileno,password,reenterpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        fullnameET = (EditText)findViewById(R.id.txtFullName);
        emailET = (EditText)findViewById(R.id.txtEmail);
        addressET = (EditText)findViewById(R.id.txtAddress);
        mobilenoET = (EditText)findViewById(R.id.txtMobileNumber);
        passwordET = (EditText) findViewById(R.id.txtPassword);
        reenterpasswordET = (EditText)findViewById(R.id.txtReEnterPasword);
        nextBTN = (Button)findViewById(R.id.btnNext);




        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullname = fullnameET.getText().toString();
                email = emailET.getText().toString();
                address = addressET.getText().toString();
                mobileno = mobilenoET.getText().toString();
                password = passwordET.getText().toString();
                reenterpassword = reenterpasswordET.getText().toString();


                if(fullname.equals("")||email.equals("")||address.equals("")||mobileno.equals("")||password.equals("")||reenterpassword.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"Please Fill all the Fields!",Toast.LENGTH_LONG).show();
                }
                else if(!password.equals(reenterpassword))
                {
                    passwordET.setText("");
                    reenterpasswordET.setText("");
                    Toast.makeText(RegisterActivity.this,"Passwords are not matching!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(RegisterActivity.this,LocationPicker.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fullname",fullname);
                    bundle.putString("email",email);
                    bundle.putString("address",address);
                    bundle.putString("mobileno",mobileno);
                    bundle.putString("password",password);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

            }
        });



    }


}
