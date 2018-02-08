package com.example.anjana.binmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjana.binmaster.HomePage.HomePage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String url="http://192.168.8.102:8000/api/login";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();

        if(prefs.getBoolean("isLoggedIn",false)){
            i=new Intent(LoginActivity.this,HomePage.class);
            Toast.makeText(LoginActivity.this,"Logged In!",Toast.LENGTH_SHORT).show();
            startActivity(i);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }



    public void login(View v){
        i=new Intent(LoginActivity.this,HomePage.class);

        EditText passwordET = (EditText) findViewById(R.id.txtPassword);
        EditText emailET = (EditText) findViewById(R.id.txtEmail);
        final String email=emailET.getText().toString();
        final String password=passwordET.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("error")){
                        Toast.makeText(LoginActivity.this,"Error!!!!!!!",Toast.LENGTH_LONG).show();
                        editor.putBoolean("isLoggedIn",false);

                        editor.commit();
                    }

                    else{

                        editor.putBoolean("isLoggedIn",true);
                        String uId=""+jsonObject.getJSONObject("uId").getInt("id");
                        Toast.makeText(LoginActivity.this,uId,Toast.LENGTH_LONG).show();
                       // uId="1";
                        editor.putString("uId",uId);

                        editor.commit();
                        startActivity(i);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };



        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);

    }


    public  void register(View v){
        Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


}
