package com.example.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.start.setOnClickListener((view -> binding.countdown.start()));
        binding.stop.setOnClickListener((view -> binding.countdown.stop()));
        binding.resume.setOnClickListener((view -> binding.countdown.resume()));
        binding.reset.setOnClickListener((view -> binding.countdown.reset()));

        binding.progress.setDateStart("19 07 2022 8:59:55");
        binding.progress.setDateEnd("19 07 2022 9:00:00");
        binding.progress.startProgress();

        binding.countdown.setDateStart("18 07 2022 8:00:00");
        binding.countdown.setDateEnd("19 07 2022 9:00:00");
        binding.countdown.start();

    }
}