package com.chronoscoper.library.licenseviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

public class LicenseListActivity extends Activity {
    private static final String EXTRA_TITLE = "com.chronoscoper.library.licenseviewer.extra.TITLE";

    private String[] mLicenses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_activity_license_list);

        String title;
        if (getActionBar() != null) {
            if ((title = getIntent().getStringExtra(EXTRA_TITLE)) != null) {
                getActionBar().setTitle(title);
            }
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            mLicenses = getAssets().list("license");
        } catch (IOException e) {
            finish();
            return;
        }

        for (int i = 0; i < mLicenses.length; i++) {
            mLicenses[i] = mLicenses[i].replaceAll(".txt", "");
        }

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLicenses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LicenseActivity.open(LicenseListActivity.this, mLicenses[position]);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void open(Context context, String title) {
        Intent intent = new Intent(context, LicenseListActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }
}
