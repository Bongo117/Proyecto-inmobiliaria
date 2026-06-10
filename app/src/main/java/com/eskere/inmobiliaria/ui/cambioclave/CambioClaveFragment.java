package com.eskere.inmobiliaria.ui.cambioclave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.eskere.inmobiliaria.databinding.FragmentCambioClaveBinding;

public class CambioClaveFragment extends Fragment {

    private CambioClaveViewModel cambioClaveViewModel;
    private FragmentCambioClaveBinding b;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

     
        cambioClaveViewModel = new ViewModelProvider(this).get(CambioClaveViewModel.class);


        b = FragmentCambioClaveBinding.inflate(inflater, container, false);


        cambioClaveViewModel.getMensajeExito().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();

            Navigation.findNavController(b.getRoot()).popBackStack();
        });


        cambioClaveViewModel.getMensajeError().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
        });


        b.btnGuardarClave.setOnClickListener(v -> {
            String actual = b.etClaveActual.getText().toString().trim();
            String nueva = b.etClaveNueva.getText().toString().trim();


            cambioClaveViewModel.cambiarClave(actual, nueva);
        });


        return b.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}