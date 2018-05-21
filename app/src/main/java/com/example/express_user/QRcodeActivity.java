package com.example.express_user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QRcodeActivity extends AppCompatActivity {
    public static final String DELIVERY_NUM = "delivery_num";
    public static final String DELIVERY_PHONENUM = "delivery_phonenum";
    public static final String DELIVERY_LOCATION = "delivery_location";
    public static final String DELIVERY_RANDOMCODE = "delivery_randomcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
    }
}
