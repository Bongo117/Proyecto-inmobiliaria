package com.eskere.inmobiliaria.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eskere.inmobiliaria.databinding.ItemPagoBinding;
import com.eskere.inmobiliaria.modelo.Pago;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {

    private List<Pago> listaPagos;
    private Context context;

    public PagosAdapter(List<Pago> listaPagos, Context context) {
        this.listaPagos = listaPagos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPagoBinding binding = ItemPagoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pago = listaPagos.get(position);

        // la vista solo dibuja lo que le da el modelo
        holder.binding.tvCodigoPago.setText(pago.getCodigoPagoFormateado());
        holder.binding.tvNroPago.setText(pago.getNroPagoFormateado());
        holder.binding.tvIdContratoPago.setText(pago.getContratoFormateado());
        holder.binding.tvImportePago.setText(pago.getImporteFormateado());
        holder.binding.tvFechaPago.setText(pago.getFechaFormateada());
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPagoBinding binding;

        public ViewHolder(@NonNull ItemPagoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
