package com.chronoscoper.library.licenseviewer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SearchableListAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mLicensesSource;

    SearchableListAdapter(Context context, String[] licenses) {
        mContext = context;
        mLicensesSource = licenses;

        mQueryHandledLicenses = new ArrayList<>();
        Collections.addAll(mQueryHandledLicenses, licenses);
    }

    private List<String> mQueryHandledLicenses;

    @Override
    public int getCount() {
        return mQueryHandledLicenses.size();
    }

    @Override
    public Object getItem(int position) {
        return mQueryHandledLicenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(mContext)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(mQueryHandledLicenses.get(position));

        return tv;
    }

    void setQuery(String query) {
        if (TextUtils.isEmpty(query)) {
            mQueryHandledLicenses.clear();
            Collections.addAll(mQueryHandledLicenses, mLicensesSource);
        } else {
            mQueryHandledLicenses.clear();

            for (String s : mLicensesSource) {
                if (s != null && s.contains(query)) {
                    if (s.contains(query)) {
                        mQueryHandledLicenses.add(s);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    String getLicenseName(int position) {
        return mQueryHandledLicenses.get(position);
    }
}
