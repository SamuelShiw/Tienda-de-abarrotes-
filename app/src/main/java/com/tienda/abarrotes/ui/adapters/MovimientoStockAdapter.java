package com.tienda.abarrotes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.MovimientoStock;

import java.util.ArrayList;
import java.util.List;

public class MovimientoStockAdapter extends RecyclerView.Adapter<MovimientoStockAdapter.MovimientoViewHolder> {

    private final List<MovimientoStock> lista = new ArrayList<>();

    public void setLista(List<MovimientoStock> movimientos) {
        lista.clear();
        if (movimientos != null) {
            lista.addAll(movimientos);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movimiento_stock, parent, false);
        return new MovimientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoViewHolder holder, int position) {
        MovimientoStock movimiento = lista.get(position);

        holder.tvTipo.setText(movimiento.getTipoMovimiento());
        holder.tvProducto.setText("ID producto: " + movimiento.getIdProducto());
        holder.tvCantidad.setText("Cantidad: " + movimiento.getCantidad());
        holder.tvOrigenDestino.setText("Origen: " + movimiento.getOrigen() + " → Destino: " + movimiento.getDestino());
        holder.tvFechaHora.setText("Fecha: " + movimiento.getFecha() + " | Hora: " + movimiento.getHora());
        holder.tvResponsable.setText("Responsable ID: " + movimiento.getIdTrabajador());
        holder.tvObservacion.setText(
                movimiento.getObservacion() == null || movimiento.getObservacion().trim().isEmpty()
                        ? "Observación: Sin detalle"
                        : "Observación: " + movimiento.getObservacion()
        );
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MovimientoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvProducto, tvCantidad, tvOrigenDestino, tvFechaHora, tvResponsable, tvObservacion;

        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tvItemTipoMovimiento);
            tvProducto = itemView.findViewById(R.id.tvItemProductoMovimiento);
            tvCantidad = itemView.findViewById(R.id.tvItemCantidadMovimiento);
            tvOrigenDestino = itemView.findViewById(R.id.tvItemOrigenDestinoMovimiento);
            tvFechaHora = itemView.findViewById(R.id.tvItemFechaHoraMovimiento);
            tvResponsable = itemView.findViewById(R.id.tvItemResponsableMovimiento);
            tvObservacion = itemView.findViewById(R.id.tvItemObservacionMovimiento);
        }
    }
}