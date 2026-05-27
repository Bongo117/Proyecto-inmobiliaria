package com.eskere.inmobiliaria.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eskere.inmobiliaria.databinding.FragmentDetalleInquilinoBinding;

public class DetalleInquilinoFragment extends Fragment {

    private FragmentDetalleInquilinoBinding binding;
    private DetalleInquilinoViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        vm.getInquilinoMutable().observe(getViewLifecycleOwner(), inquilino -> {
            binding.tvCodigoInquilino.setText("Código: " + inquilino.getIdInquilino());
            binding.tvNombreInquilino.setText("Nombre: " + inquilino.getNombre());
            binding.tvApellidoInquilino.setText("Apellido: " + inquilino.getApellido());
            binding.tvDniInquilino.setText("DNI: " + inquilino.getDni());
            binding.tvEmailInquilino.setText("E-mail: " + inquilino.getEmail());
            binding.tvTelefonoInquilino.setText("Teléfono: " + inquilino.getTelefono());
            binding.tvGarante.setText("Garante: " + inquilino.getNombreGarante());
            binding.tvTelefonoGarante.setText("Teléfono Garante: " + inquilino.getTelefonoGarante());
        });

        vm.getErrorMutable().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });


        vm.cargarInquilinoDesdeBundle(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}