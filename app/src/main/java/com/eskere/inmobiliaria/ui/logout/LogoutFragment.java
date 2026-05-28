package com.eskere.inmobiliaria.ui.logout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.eskere.inmobiliaria.ui.login.LoginActivity;

public class LogoutFragment extends Fragment {

    private LogoutViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = new View(requireContext());

        vm = new ViewModelProvider(this).get(LogoutViewModel.class);


        vm.getLogoutExitosoMutable().observe(getViewLifecycleOwner(), exitoso -> {
            if (exitoso) {

                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    vm.cerrarSesion();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Navigation.findNavController(requireActivity(), com.eskere.inmobiliaria.R.id.nav_host_fragment_content_main).popBackStack();
                })
                .setOnCancelListener(dialog -> {
                    Navigation.findNavController(requireActivity(), com.eskere.inmobiliaria.R.id.nav_host_fragment_content_main).popBackStack();
                })
                .show();

        return root;
    }
}