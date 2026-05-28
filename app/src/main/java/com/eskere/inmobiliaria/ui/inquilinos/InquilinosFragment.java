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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eskere.inmobiliaria.databinding.FragmentInquilinosBinding;

public class InquilinosFragment extends Fragment {

    private FragmentInquilinosBinding binding;
    private InquilinosViewModel vm;
    private InquilinoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);

        binding.rvInquilinos.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.getInmueblesAlquiladosMutable().observe(getViewLifecycleOwner(), inmuebles -> {
            adapter = new InquilinoAdapter(inmuebles, getContext());
            binding.rvInquilinos.setAdapter(adapter);
        });

        vm.getErrorMutable().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });

        binding.svBuscador.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                vm.buscar(newText);
                return true;
            }
        });

        vm.cargarInmueblesAlquilados();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}