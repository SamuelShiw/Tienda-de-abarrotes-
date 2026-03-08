package com.tienda.abarrotes.ui.admin.productos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.ui.adapters.ProductoAdapter;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

import java.util.List;

public class ProductosFragment extends Fragment {

    private RecyclerView rvProductos;
    private TextView tvEmptyProductos;
    private Button btnNuevoProducto;

    private ProductoViewModel productoViewModel;
    private ProductoAdapter adapter;

    private final ActivityResultLauncher<Intent> launcherProducto =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> cargarProductos()
            );

    public ProductosFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productoViewModel = new ProductoViewModel(requireContext());

        initViews(view);
        setupRecyclerView();
        configurarEventos();
        cargarProductos();
    }

    private void initViews(View view) {
        rvProductos = view.findViewById(R.id.rvProductos);
        tvEmptyProductos = view.findViewById(R.id.tvEmptyProductos);
        btnNuevoProducto = view.findViewById(R.id.btnNuevoProducto);
    }

    private void setupRecyclerView() {
        adapter = new ProductoAdapter(new ProductoAdapter.OnProductoActionListener() {
            @Override
            public void onDetalle(Producto producto) {
                Intent intent = new Intent(requireContext(), DetalleProductoActivity.class);
                intent.putExtra("id_producto", producto.getIdProducto());
                launcherProducto.launch(intent);
            }

            @Override
            public void onEditar(Producto producto) {
                Intent intent = new Intent(requireContext(), EditarProductoActivity.class);
                intent.putExtra("id_producto", producto.getIdProducto());
                launcherProducto.launch(intent);
            }

            @Override
            public void onDesactivar(Producto producto) {
                mostrarDialogoDesactivar(producto);
            }
        });

        rvProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvProductos.setAdapter(adapter);
    }

    private void configurarEventos() {
        btnNuevoProducto.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RegistrarProductoActivity.class);
            launcherProducto.launch(intent);
        });
    }

    private void cargarProductos() {
        List<Producto> lista = productoViewModel.listarProductos();
        adapter.setLista(lista);

        if (lista == null || lista.isEmpty()) {
            tvEmptyProductos.setVisibility(View.VISIBLE);
            rvProductos.setVisibility(View.GONE);
        } else {
            tvEmptyProductos.setVisibility(View.GONE);
            rvProductos.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarDialogoDesactivar(Producto producto) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Desactivar producto")
                .setMessage("¿Deseas desactivar el producto " + producto.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    boolean ok = productoViewModel.desactivarProducto(producto.getIdProducto());
                    Toast.makeText(requireContext(),
                            ok ? "Producto desactivado" : "No se pudo desactivar",
                            Toast.LENGTH_SHORT).show();
                    cargarProductos();
                })
                .setNegativeButton("No", null)
                .show();
    }
}