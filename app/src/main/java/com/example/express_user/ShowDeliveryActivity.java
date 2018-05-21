package com.example.express_user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ShowDeliveryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String phoneNum;
    private RecyclerView recyclerView;
    private showDeliveryAdapter adapter;
    private List<Delivery> deliveryList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_delivery);

        toolbar = findViewById(R.id.toolbar_showdelivery);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        nodata = findViewById(R.id.tv_nodata);

        initlist();

        recyclerView = findViewById(R.id.recyclerView_show_delivery);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowDeliveryActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new showDeliveryAdapter(deliveryList);
        recyclerView.setAdapter(adapter);
        //设置动画
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(1000);

        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,-200,0);
        animationSet.addAnimation(translateAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setInterpolator(new BounceInterpolator());

        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animationSet,0.2f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        recyclerView.setLayoutAnimation(layoutAnimationController);
    }

    private void initlist() {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest("http://192.168.1.105/delivery_data.json", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ShowDeliveryActivity.this, "获取数据失败,请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    Delivery delivery = null;
                    JSONArray jsonArray = new JSONArray(responseData);
                    for(int i = 0; i < jsonArray.length(); i++){
                        delivery = new Delivery();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String phone = jsonObject.getString("delivery_phonenum");
                        if (phone.equals(phoneNum)){
                            String num = jsonObject.getString("delivery_num");
                            String location = jsonObject.getString("delivery_location");
                            String random = jsonObject.getString("random_num");

                            delivery.setdeliveryNum(num);
                            delivery.setPhoneNum(phone);
                            delivery.setLocation(location);
                            delivery.setRandomCode(random);
                            deliveryList.add(delivery);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (deliveryList.size() == 0){
                                recyclerView.setVisibility(View.GONE);
                                nodata.setVisibility(View.VISIBLE);
                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                nodata.setVisibility(View.GONE);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(ShowDeliveryActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
