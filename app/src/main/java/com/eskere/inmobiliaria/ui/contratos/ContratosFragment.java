package com.eskere.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eskere.inmobiliaria.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;
    private ContratosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

        binding.rvContratos.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getContratosMutable().observe(getViewLifecycleOwner(), contratos -> {
            ContratoAdapter adapter = new ContratoAdapter(contratos, requireContext());
            binding.rvContratos.setAdapter(adapter);
        });

        viewModel.getErrorMutable().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });

        viewModel.obtenerContratos();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
