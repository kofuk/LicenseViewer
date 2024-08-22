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
package com.chronoscoper.library.licenseviewer

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.BufferedReader
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class LicenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lv_activity_license)

        var licenseName: String?
        if ((intent.getStringExtra(EXTRA_LICENSE_NAME).also { licenseName = it }) == null) {
            finish()
            return
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        checkNotNull(supportActionBar)
        supportActionBar!!.title = licenseName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        var inputStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null
        val builder = StringBuilder()
        try {
            inputStream = assets.open("license/$licenseName.txt")
            inputStreamReader = InputStreamReader(inputStream)
            bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            while ((bufferedReader.readLine().also { line = it }) != null) {
                builder.append(line).append("\n")
            }
        } catch (e: IOException) {
            finish()
            return
        } finally {
            closeCloseablesQuietly(inputStream!!, inputStreamReader!!, bufferedReader!!)
        }

        (findViewById<View>(R.id.license) as TextView).text = builder.toString()
    }

    private fun closeCloseablesQuietly(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            try {
                closeable?.close()
            } catch (ignore: IOException) {
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_LICENSE_NAME: String =
            "com.chronoscoper.library.licenseviewer.extra.LICENSE_NAME"
    }
}
