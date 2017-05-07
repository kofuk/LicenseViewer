/*
 * Copyright 2017 KoFuk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chronoscoper.library.licenseviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

class LicenseListActivity extends Activity {
    static final String EXTRA_TITLE = "com.chronoscoper.library.licenseviewer.extra.TITLE";

    private String[] mLicenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Intent intent = new Intent(LicenseListActivity.this, LicenseActivity.class);
                intent.putExtra(LicenseActivity.EXTRA_LICENSE_NAME, mLicenses[position]);
                startActivity(intent);
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
}
