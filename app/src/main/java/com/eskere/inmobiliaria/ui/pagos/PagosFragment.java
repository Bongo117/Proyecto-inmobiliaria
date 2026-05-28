package com.eskere.inmobiliaria.ui.pagos;

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

import com.eskere.inmobiliaria.databinding.FragmentPagosBinding;

import java.util.ArrayList;

public class PagosFragment extends Fragment {

    private FragmentPagosBinding binding;
    private PagosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentPagosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        binding.rvPagos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPagos.setAdapter(new PagosAdapter(new ArrayList<>(), requireContext()));

        viewModel.getPagosMutable().observe(getViewLifecycleOwner(), pagos -> {
            PagosAdapter adapter = new PagosAdapter(pagos, requireContext());
            binding.rvPagos.setAdapter(adapter);
        });

        viewModel.getErrorMutable().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
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
