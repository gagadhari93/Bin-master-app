package com.example.anjana.binmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.anjana.binmaster.R.id.btnSubmit;
import static com.example.anjana.binmaster.R.id.textLocationView;

public class LocationPicker  extends AppCompatActivity {

    private TextView get_place;
    int PLACE_PICKER_REQUEST=1;
    Button mapButton,btnSubmit;
    String Lplace,fullname,email,address,mobileno,password;
    String url = "http://192.168.8.102:8000/api/register";
    List<String> areas;
    List<Integer> areadIds;
    ArrayAdapter<String> adapter;
    Spinner spinner;
    String area;
    int areaId;

    String urlSpinner="http://192.168.8.102:8000/api/spinner";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();

        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        mapButton=(Button)findViewById(R.id.btnMap);
        get_place=(TextView)findViewById(R.id.textLocationView);
        spinner =(Spinner)findViewById(R.id.spinner);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSpinner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                areas = new ArrayList<String>();
                areadIds = new ArrayList<Integer>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        areas.add(jsonObject.getString("areaName"));
                        areadIds.add(jsonObject.getInt("id"));
                    }
                    adapter = new ArrayAdapter<String>(LocationPicker.this,android.R.layout.simple_expandable_list_item_1,areas);
                    spinner.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(LocationPicker.this).addToRequestQueue(stringRequest);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView v = (TextView)view;
                area = v.getText().toString();
                areaId=areadIds.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });










        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder =new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(getApplicationContext());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);


                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected  void onActivityResult(int requstCode,int resultCode,Intent data){
        if(requstCode==PLACE_PICKER_REQUEST)
        {
            if (resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data,this);
                String location=String.format("Place:%s\n%s",place.getAddress(),place.getLatLng());
                get_place.setText(location);

                Lplace = place.getLatLng().latitude+","+place.getLatLng().longitude;



            }

        }




    }




    public void OnClick(){

    }

    public  void goNext(View v){
        Intent i=new Intent(LocationPicker.this,MobileNumberValidation.class);
        startActivity(i);
    }


    public void sendData()
    {
        Bundle bundle = getIntent().getExtras();
        fullname = bundle.getString("fullname");
        email = bundle.getString("email");
        address = bundle.getString("address");
        mobileno = bundle.getString("mobileno");
        password = bundle.getString("password");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("error")){
                        Toast.makeText(LocationPicker.this,jsonObject.getString("errorName"),Toast.LENGTH_LONG).show();
                    }
                    else if(Lplace.equals("")){
                        Toast.makeText(LocationPicker.this,"Please Select your Locations!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        editor.putInt("areaId",areaId);
                        editor.commit();
                        Intent i =new Intent(LocationPicker.this,LoginActivity.class);


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
                Toast.makeText(LocationPicker.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("fullname",fullname);
                params.put("email",email);
                params.put("address",address);
                params.put("mobileno",mobileno);
                params.put("password",password);
                params.put("Lplace",Lplace);
                params.put("areaId",""+areaId);
                return params;
            }
        };



        MySingleton.getInstance(LocationPicker.this).addToRequestQueue(stringRequest);

    }

}

