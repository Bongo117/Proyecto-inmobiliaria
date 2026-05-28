package com.eskere.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.eskere.inmobiliaria.R;
import com.eskere.inmobiliaria.databinding.FragmentDetalleContratoBinding;

public class ContratoDetalleFragment extends Fragment {

    private FragmentDetalleContratoBinding binding;
    private ContratoDetalleViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);

        viewModel.getContratoMutable().observe(getViewLifecycleOwner(), contrato -> {
            binding.tvCodigoContrato.setText("Código: " + contrato.getIdContrato());
            binding.tvFechaInicioContrato.setText("Fecha inicio: " + contrato.getFechaInicio());
            binding.tvFechaFinContrato.setText("Fecha fin: " + contrato.getFechaFin());
            binding.tvMontoContrato.setText("Monto: $" + contrato.getMonto());

            binding.tvInquilinoContrato.setText("Inquilino: " + contrato.getNombreInquilinoCompleto());
            binding.tvDireccionContrato.setText("Dirección: " + contrato.getDireccionAMostrar());

            binding.btnPagos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idContrato", contrato.getIdContrato());
                    Navigation.findNavController(v).navigate(R.id.action_nav_detalle_contrato_to_nav_pagos, bundle);
                }
            });
        });

        viewModel.recuperarDatos(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}