/*
 * Copyright 2017-2024 Koki Fukuda
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

import android.content.Context;
import android.content.Intent;

public final class LicenseViewer {

    /**
     * Opens the list of licenses.
     *
     * @param context     Android Context
     * @param windowTitle a string shown as a title of the activity
     */
    public static void open(Context context, String windowTitle) {
        open(context, windowTitle, false);
    }

    /**
     * Opens the list of licenses.
     *
     * @param context             Android Context
     * @param windowTitle         a string shown as a title of the activity
     * @param showSearchInterface shows a search icon or not
     */
    public static void open(Context context, String windowTitle, boolean showSearchInterface) {
        Intent intent = new Intent(context, LicenseListActivity.class);
        intent.putExtra(LicenseListActivity.EXTRA_TITLE, windowTitle);
        intent.putExtra(LicenseListActivity.EXTRA_ENABLE_SEARCH, showSearchInterface);
        context.startActivity(intent);
    }
}
