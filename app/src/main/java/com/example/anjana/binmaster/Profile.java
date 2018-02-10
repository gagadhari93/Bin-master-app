package com.example.anjana.binmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String uId;
    String url="http://192.168.8.102:8000/api/viewProfile";
    String fullName,email,mobileNo,addresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();
        uId=prefs.getString("uId",null);

        final TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
        final  TextView tvEmail= (TextView) findViewById(R.id.tvEmail);
        final TextView tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);
        final TextView tvAddress = (TextView) findViewById(R.id.tvAddress);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);


                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                    fullName=""+jsonObject.get("fullname");
                    email=""+jsonObject.get("email");
                    mobileNo=""+jsonObject.get("mobileno");
                    addresse=""+jsonObject.get("address");

                    tvFullName.setText(fullName);
                    tvEmail.setText(email);
                    tvMobileNo.setText(mobileNo);
                    tvAddress.setText(addresse);






                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                params.put("uId",uId);
                return params;
            }
        };


        MySingleton.getInstance(Profile.this).addToRequestQueue(stringRequest);

    }
}
