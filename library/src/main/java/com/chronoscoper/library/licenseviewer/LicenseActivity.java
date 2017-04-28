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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LicenseActivity extends Activity {
    public static final String EXTRA_LICENSE_NAME =
            "com.chronoscoper.library.licenseviewer.extra.LICENSE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_activity_license);

        String licenseName;
        if ((licenseName = getIntent().getStringExtra(EXTRA_LICENSE_NAME)) == null) {
            finish();
            return;
        }

        if (getActionBar() != null) {
            getActionBar().setTitle(licenseName);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = getAssets().open("license/" + licenseName + ".txt");
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            finish();
            return;
        } finally {
            closeCloseablesQuietly(inputStream, inputStreamReader, bufferedReader);
        }

        ((TextView) findViewById(R.id.license)).setText(builder.toString());
    }

    private void closeCloseablesQuietly(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void open(Context context, String name) {
        Intent intent = new Intent(context, LicenseActivity.class);
        intent.putExtra(EXTRA_LICENSE_NAME, name);
        context.startActivity(intent);
    }
}
