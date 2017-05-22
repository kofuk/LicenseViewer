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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.IOException;

public class LicenseListActivity extends Activity {
    static final String EXTRA_TITLE = "com.chronoscoper.library.licenseviewer.extra.TITLE";
    static final String EXTRA_ENABLE_SEARCH =
            "com.chronoscoper.library.licenseviewer.extra.ENABLE_SEARCH";

    private SearchableListAdapter mAdapter;

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
        String[] mLicenses;
        try {
            mLicenses = getAssets().list("license");
        } catch (IOException e) {
            finish();
            return;
        }

        for (int i = 0; i < mLicenses.length; i++) {
            mLicenses[i] = mLicenses[i].replaceAll(".txt", "");
        }

        final ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new SearchableListAdapter(this, mLicenses);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LicenseListActivity.this, LicenseActivity.class);
                intent.putExtra(LicenseActivity.EXTRA_LICENSE_NAME,
                        mAdapter.getLicenseName(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!getIntent().getBooleanExtra(EXTRA_ENABLE_SEARCH, false)) return false;

        getMenuInflater().inflate(R.menu.lv_license_list_option, menu);
        MenuItem item = menu.findItem(R.id.lv_menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getString(android.R.string.search_go) + "…");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.setQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.setQuery(newText);
                return true;
            }
        });
        return true;
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
