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
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

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

            double aceleracion = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

            // fuerza requerida 6 para que no sea tan sensible
            if (aceleracion > 6) {
                long tiempoActual = System.currentTimeMillis();

                // Si pasaron más de 3 segundos desde la última sacudida, el usuario se detuvo.
                // Reseteamos el contador para que tenga que hacer 3 juntas.
                if (tiempoActual - ultimoTiempoShake > 3000) {
                    contadorSacudidas = 0;
                }

                // Ponemos un freno demedio segundo entre cada golpe4 para que el sensor no cuente el mismo ida y vuelta del brazo como 5 sacudidas.
                if ((tiempoActual - ultimoTiempoShake) > 500) {
                    ultimoTiempoShake = tiempoActual;
                    contadorSacudidas++; // Sumamos 1 sacudida válida

                    //Si el contador llego a 3 hacemos la llamada
                    if (contadorSacudidas >= 3) {
                        hacerLlamada();
                        contadorSacudidas = 0; // Reseteamos por si corta y quiere volver a agitar
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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