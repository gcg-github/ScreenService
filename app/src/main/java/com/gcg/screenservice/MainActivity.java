package com.gcg.screenservice;

import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();

        startService(new Intent(this, MyService.class));
    }
}
