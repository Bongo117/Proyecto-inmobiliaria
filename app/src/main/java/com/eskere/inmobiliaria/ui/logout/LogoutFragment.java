package com.eskere.inmobiliaria.ui.logout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.eskere.inmobiliaria.ui.login.LoginActivity;

public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = new View(requireContext());

        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    SharedPreferences sp = requireContext().getSharedPreferences("token.xml", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
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
