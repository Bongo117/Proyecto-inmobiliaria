package com.eskere.inmobiliaria.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> tokenMutable;

    private MutableLiveData<String> errorMutable;

    public LiveData<String> getTokenMutable() {
        if (tokenMutable == null) {
            tokenMutable = new MutableLiveData<>();
        }
        return tokenMutable;
    }

    public LiveData<String> getErrorMutable() {
        if (errorMutable == null) {
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public void autenticar(String usuario, String clave) {

        if (usuario.isEmpty() || clave.isEmpty()) {
            getErrorMutable();
            errorMutable.setValue("Por favor, complete todos los campos");
            return;
        }

        //  Simulacion de la API Hasta que conectemos Retrofit
        /*
           el endpoint va a ser:
           POST /api/Propietarios/login
           Usuario de prueba: luisprofessor@gmail.com
           Clave de prueba: DEEKQW
        */
        if (usuario.equals("luisprofessor@gmail.com") && clave.equals("DEEKQW")) {
            // Éxito: Mandamos el Token simulado a la vista
            getTokenMutable();
            tokenMutable.setValue("TOKEN_JWT_SIMULADO_12345");
        } else {
            // Error: Mandamos el mensaje de error a la vista
            getErrorMutable();
            errorMutable.setValue("Credenciales incorrectas");
        }
    }
}