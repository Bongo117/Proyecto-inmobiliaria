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
            binding.tvCodigoInquilino.setText(inquilino.getCodigoFormateado());
            binding.tvNombreInquilino.setText(inquilino.getNombreFormateado());
            binding.tvApellidoInquilino.setText(inquilino.getApellidoFormateado());
            binding.tvDniInquilino.setText(inquilino.getDniFormateado());
            binding.tvEmailInquilino.setText(inquilino.getEmailFormateado());
            binding.tvTelefonoInquilino.setText(inquilino.getTelefonoFormateado());
            binding.tvGarante.setText(inquilino.getGaranteFormateado());
            binding.tvTelefonoGarante.setText(inquilino.getTelefonoGaranteFormateado());
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