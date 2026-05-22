package com.eskere.inmobiliaria.ui.cambioclave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.eskere.inmobiliaria.R;
import com.eskere.inmobiliaria.request.ApiClient;

public class CambioClaveFragment extends Fragment {

    private CambioClaveViewModel cambioClaveViewModel;
    private EditText etClaveActual;
    private EditText etClaveNueva;
    private Button btnGuardarClave;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cambioClaveViewModel = new ViewModelProvider(this).get(CambioClaveViewModel.class);

        View root = inflater.inflate(R.layout.fragment_cambio_clave, container, false);

        etClaveActual = root.findViewById(R.id.etClaveActual);
        etClaveNueva = root.findViewById(R.id.etClaveNueva);
        btnGuardarClave = root.findViewById(R.id.btnGuardarClave);

        cambioClaveViewModel.getMensajeExito().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(root).popBackStack();
        });

        cambioClaveViewModel.getMensajeError().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
        });

        btnGuardarClave.setOnClickListener(v -> {
            String actual = etClaveActual.getText().toString().trim();
            String nueva = etClaveNueva.getText().toString().trim();
            String token = ApiClient.usarToken(requireContext());
            cambioClaveViewModel.cambiarClave(token, actual, nueva);
        });

        return root;
    }
}
