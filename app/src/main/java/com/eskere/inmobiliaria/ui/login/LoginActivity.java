package com.eskere.inmobiliaria.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eskere.inmobiliaria.MainActivity;
import com.eskere.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        viewModel.getTokenMutable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String tokenJWT) {
                guardarToken(tokenJWT);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

        viewModel.getErrorMutable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String usuario = binding.etUsuario.getText().toString();
            String clave = binding.etClave.getText().toString();

            viewModel.autenticar(usuario, clave);
        });
    }

    private void guardarToken(String token) {
        SharedPreferences sp = getSharedPreferences("token.xml", 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("token", "Bearer " + token);
        editor.apply();
    }
}