package com.tienda.abarrotes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.DetalleVenta;
import com.tienda.abarrotes.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    public interface OnCarritoActionListener {
        void onQuitar(int position);
    }

    private final List<DetalleVenta> carrito = new ArrayList<>();
    private final List<Producto> productos = new ArrayList<>();
    private final OnCarritoActionListener listener;

    public CarritoAdapter(OnCarritoActionListener listener) {
        this.listener = listener;
    }

    public void setData(List<DetalleVenta> carritoData, List<Producto> productosData) {
        carrito.clear();
        productos.clear();

        if (carritoData != null) carrito.addAll(carritoData);
        if (productosData != null) productos.addAll(productosData);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        DetalleVenta detalle = carrito.get(position);
        Producto producto = productos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvCantidad.setText("Cantidad: " + detalle.getCantidad());
        holder.tvPrecioUnitario.setText(String.format(Locale.getDefault(), "P. Unit: S/ %.2f", detalle.getPrecioUnitario()));
        holder.tvImporte.setText(String.format(Locale.getDefault(), "Importe: S/ %.2f", detalle.getImporte()));

        holder.btnQuitar.setOnClickListener(v -> listener.onQuitar(position));
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    static class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCantidad, tvPrecioUnitario, tvImporte;
        ImageButton btnQuitar;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvItemNombreCarrito);
            tvCantidad = itemView.findViewById(R.id.tvItemCantidadCarrito);
            tvPrecioUnitario = itemView.findViewById(R.id.tvItemPrecioUnitarioCarrito);
            tvImporte = itemView.findViewById(R.id.tvItemImporteCarrito);
            btnQuitar = itemView.findViewById(R.id.btnQuitarCarrito);
        }
    }
}