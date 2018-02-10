package com.example.anjana.binmaster.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjana.binmaster.AboutUs;
import com.example.anjana.binmaster.Complains;
import com.example.anjana.binmaster.LoginActivity;
import com.example.anjana.binmaster.MySingleton;
import com.example.anjana.binmaster.Points;
import com.example.anjana.binmaster.Profile;
import com.example.anjana.binmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Intent i;
    String userName;
    String uId;
    String url="http://192.168.8.102:8000/api/getUserName";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();
        uId=prefs.getString("uId",null);

        ActionBar actionBar = getSupportActionBar();


        actionBar.hide();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                     userName=""+jsonObject.get("fullname");



                    View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
                    ImageView imgvw = (ImageView)hView.findViewById(R.id.imageView);
                    TextView tv = (TextView)hView.findViewById(R.id.textView);
                    imgvw .setImageResource(0);
                    tv.setText(userName);



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


        MySingleton.getInstance(HomePage.this).addToRequestQueue(stringRequest);








        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView= (NavigationView) findViewById(R.id.shitstuff);



        FM= getSupportFragmentManager();
        FT= FM.beginTransaction();
        FT.replace(R.id.containerView, new TabFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId()== R.id.nav_item_points) {
                    Intent i=new Intent(HomePage.this, Points.class);
                    startActivity(i);

                }
                if (item.getItemId()== R.id.nev_item_complains) {
                    Intent i=new Intent(HomePage.this,Complains.class);
                    startActivity(i);


                }
                if (item.getItemId()== R.id.nev_item_aboutUs) {
                    Intent i=new Intent(HomePage.this, AboutUs.class);
                    startActivity(i);

                }


                if (item.getItemId()==R.id.nav_item_profile)
                {
                    Intent i=new Intent(HomePage.this, Profile.class);
                    startActivity(i);

                }
                if (item.getItemId()==R.id.logout){
                    editor.putBoolean("isLoggedIn",false);
                    editor.commit();
                    Intent intent=new Intent(HomePage.this, LoginActivity.class);
                    startActivity(intent);

                }
                return false;
            }
        });


        android.support.v7.widget.Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
}
