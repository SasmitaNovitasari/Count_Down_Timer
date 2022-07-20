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

        binding.start.setOnClickListener((view -> {
            binding.countdown.start();
        }));
        binding.stop.setOnClickListener((view -> {
            binding.countdown.stop();
        }));
        binding.resume.setOnClickListener((view -> {
            binding.countdown.resume();
        }));
        binding.reset.setOnClickListener((view -> {
            binding.countdown.reset();
        }));

    }
}