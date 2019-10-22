package com.example.rebookapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    ProgressBar progressBar;
    RecyclerView rvResponse;
    public URL bookUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb_loading);
        rvResponse = findViewById(R.id.rvResponse);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResponse.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");


        try {

            if (query==null || query.isEmpty()) {
                bookUrl = ApiUtil.buildUrl("cooking");
            }
            else {
                bookUrl = new URL(query);
            }

            new AsyncTaskQuery().execute(bookUrl);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.book_menu);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        ArrayList<String> recentList = SpUtil.getQueryList(getApplicationContext());
        int menuNum = recentList.size();
        MenuItem menuItemRecent;
        for (int i=0; i<menuNum; i++) {
            menuItemRecent = menu.add(Menu.NONE, i, Menu.NONE, recentList.get(i));

        }




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.av_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                int position = item.getItemId() + 1;
                String query = SpUtil.QUERY + String.valueOf(position);
                String preference = SpUtil.getPrefString(getApplicationContext(), query);
                String[] prefParams = preference.split("\\,");
                String[] queryParams = new String[4];
                for (int i = 0; i<prefParams.length; i++) {
                    queryParams[i] = prefParams[i];
                }
                bookUrl = ApiUtil.buildUrl(
                        (queryParams[0] == null)?"" : queryParams[0]);

                        new AsyncTaskQuery().execute(bookUrl);
                        return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        URL url = ApiUtil.buildUrl(s);
        new AsyncTaskQuery().execute(url);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public  class AsyncTaskQuery extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
             result = ApiUtil.getJson(searchUrl);

            } catch (IOException e) {
                Log.e("Error", e.getMessage());

            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView tvError = (TextView) findViewById(R.id.textView_error);
            progressBar.setVisibility(View.INVISIBLE);
            if (s != null) {
                rvResponse.setVisibility(View.VISIBLE);

                tvError.setVisibility(View.INVISIBLE);
                //tvError.setText(s);


//            BookAdapter bookAdapter = new BookAdapter(books);
//            rvResponse.setAdapter(bookAdapter);


            }
            else {
                rvResponse.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }

            ArrayList<Book> books = ApiUtil.getBooksFromJson(s);
            BookAdapter bookAdapter = new BookAdapter(books);
            rvResponse.setAdapter(bookAdapter);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }
    }


}
