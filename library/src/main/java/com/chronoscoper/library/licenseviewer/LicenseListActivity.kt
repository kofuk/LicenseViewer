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

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class LicenseListActivity : AppCompatActivity() {
    private var mAdapter: SearchableListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setContentView(R.layout.lv_activity_license_list)

        var title: String?
        if (supportActionBar != null) {
            if ((intent.getStringExtra(EXTRA_TITLE).also { title = it }) != null) {
                supportActionBar!!.title = title
            }
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        val mLicenses: Array<String>?
        try {
            mLicenses = assets.list("license")
        } catch (e: IOException) {
            finish()
            return
        }

        for (i in mLicenses!!.indices) {
            mLicenses[i] = mLicenses[i].replace(".txt".toRegex(), "")
        }

        val listView = findViewById<ListView>(R.id.list)
        mAdapter = SearchableListAdapter(this, mLicenses)
        listView.adapter = mAdapter
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            openLicense(view, position)
        }
        val slide = AnimationUtils.loadAnimation(this, R.anim.lv_slide_up)
        listView.clearAnimation()
        listView.animation = slide
        listView.visibility = View.VISIBLE
        slide.start()
    }

    private fun openLicense(view: View, position: Int) {
        val intent = Intent(this@LicenseListActivity, LicenseActivity::class.java)
        intent.putExtra(
            LicenseActivity.EXTRA_LICENSE_NAME,
            mAdapter!!.getLicenseName(position)
        )
        view.transitionName = "title"
        val options = ActivityOptions
            .makeSceneTransitionAnimation(this@LicenseListActivity, view, "title")
        startActivity(intent, options.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!intent.getBooleanExtra(EXTRA_ENABLE_SEARCH, false)) return false

        menuInflater.inflate(R.menu.lv_license_list_option, menu)
        val item = menu.findItem(R.id.lv_menu_search)

        val searchView = item.actionView as SearchView?
        searchView!!.isIconifiedByDefault = true
        searchView.isSubmitButtonEnabled = false
        searchView.queryHint = getString(android.R.string.search_go) + "…"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter!!.setQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter!!.setQuery(newText)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun finish() {
        val listView = findViewById<ListView>(R.id.list)
        val slide = AnimationUtils.loadAnimation(this, R.anim.lv_slide_down)
        listView.clearAnimation()
        listView.animation = slide
        slide.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                super@LicenseListActivity.finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        slide.start()
        listView.visibility = View.INVISIBLE
    }

    companion object {
        const val EXTRA_TITLE: String = "com.chronoscoper.library.licenseviewer.extra.TITLE"
        const val EXTRA_ENABLE_SEARCH: String =
            "com.chronoscoper.library.licenseviewer.extra.ENABLE_SEARCH"
    }
}
