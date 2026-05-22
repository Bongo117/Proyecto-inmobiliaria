package com.eskere.inmobiliaria.ui.inmuebles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eskere.inmobiliaria.databinding.ItemInmuebleBinding;
import com.eskere.inmobiliaria.modelo.Inmueble;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {

    private List<Inmueble> listaInmuebles;
    private Context context;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleBinding binding = ItemInmuebleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = listaInmuebles.get(position);

        holder.binding.tvDireccionInmueble.setText(inmueble.getDireccion());
        holder.binding.tvPrecioInmueble.setText("$ " + inmueble.getValor());

        // URL completa porque la API solo devuelve la ruta relativa
        String urlImagen = "https://capacitacion.alwaysdata.net/" + inmueble.getImagen();

        // Magia de Glide
        Glide.with(context)
                .load(urlImagen)
                .into(holder.binding.ivFotoInmueble);
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemInmuebleBinding binding;

        public ViewHolder(@NonNull ItemInmuebleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}