package com.example.express_user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ShowDeliveryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_delivery);

        toolbar = findViewById(R.id.toolbar_showdelivery);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (toolbar != null){
            toolbar.setTitle("手机号:" + phoneNum);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_delivery_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.quit:
                Intent intent = new Intent(ShowDeliveryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
