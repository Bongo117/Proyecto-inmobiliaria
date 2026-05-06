package com.eskere.inmobiliaria.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// 1. Importar la clase de ViewBinding autogenerada
import com.eskere.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}