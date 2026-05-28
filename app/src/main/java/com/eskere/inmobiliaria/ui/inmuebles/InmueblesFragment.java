package com.eskere.inmobiliaria.ui.inmuebles;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.eskere.inmobiliaria.R;
import com.eskere.inmobiliaria.databinding.FragmentInmueblesBinding;

public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmueblesViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);

        binding.rvInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getInmueblesMutable().observe(getViewLifecycleOwner(), inmuebles -> {
            InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, requireContext());
            binding.rvInmuebles.setAdapter(adapter);
        });

        viewModel.getErrorMutable().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });

        binding.svBuscador.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.buscar(newText);
                return true;
            }
        });

        viewModel.obtenerInmuebles();
        binding.fabAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_inmuebles_to_nav_inmuebles_agregar);
            }
        });
        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}