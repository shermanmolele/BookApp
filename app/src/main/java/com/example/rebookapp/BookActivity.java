package com.example.rebookapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rebookapp.databinding.ActivityBookBinding;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Book book = getIntent().getParcelableExtra("Book");
        ActivityBookBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_book);
        binding.setBook(book);
    }
}
