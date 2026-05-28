package com.eskere.inmobiliaria.ui.login;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.eskere.inmobiliaria.MainActivity;
import com.eskere.inmobiliaria.databinding.ActivityLoginBinding;
import com.eskere.inmobiliaria.ui.login.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;
    private long ultimoTiempoShake = 0;
    private int contadorSacudidas = 0;

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getLlamarInmobiliariaMutable().observe(this, hacerLlamada -> {
            if (hacerLlamada != null && hacerLlamada) {
                hacerLlamada();
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        viewModel.getTokenMutable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String tokenJWT) {
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

        binding.tvOlvidePassword.setOnClickListener(v -> {
            viewModel.resetearPassword();
        });
        
        viewModel.getMensajeResetMutable().observe(this, mensaje -> {
            Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show();
        });

    }

    // --- SENSOR ---

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null && acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            viewModel.procesarMovimientoSensor(x, y, z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Queda vacío pero es obligatorio implementarlo
    }

    // --- Llamada al profe jeje ---

    private void hacerLlamada() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:2664553747"));
        startActivity(intent);
    }
}