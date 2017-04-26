package com.chronoscoper.android.licenseviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chronoscoper.library.licenseviewer.LicenseViewer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LicenseViewer.open(this, "Licenses");
    }
}
