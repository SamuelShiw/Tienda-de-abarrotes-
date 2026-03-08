package com.tienda.abarrotes.ui.admin.inventario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.ui.adapters.ProductoAdapter;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;

import java.util.List;

public class InventarioFragment extends Fragment {

    private RecyclerView rvInventarioAdmin;
    private Button btnIngresoMercaderia;
    private Button btnVerMovimientos;

    private InventarioViewModel inventarioViewModel;
    private ProductoAdapter adapter;

    private final ActivityResultLauncher<Intent> launcherInventario =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> cargarInventario()
            );

    public InventarioFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inventarioViewModel = new InventarioViewModel(requireContext());

        initViews(view);
        setupRecycler();
        configurarEventos();
        cargarInventario();
    }

    private void initViews(View view) {
        rvInventarioAdmin = view.findViewById(R.id.rvInventarioAdmin);
        btnIngresoMercaderia = view.findViewById(R.id.btnIngresoMercaderia);
        btnVerMovimientos = view.findViewById(R.id.btnVerMovimientos);
    }

    private void setupRecycler() {
        adapter = new ProductoAdapter(new ProductoAdapter.OnProductoActionListener() {
            @Override
            public void onDetalle(Producto producto) {
                Intent intent = new Intent(requireContext(), IngresoMercaderiaActivity.class);
                intent.putExtra("id_producto", producto.getIdProducto());
                launcherInventario.launch(intent);
            }

            @Override
            public void onEditar(Producto producto) {
                Intent intent = new Intent(requireContext(), IngresoMercaderiaActivity.class);
                intent.putExtra("id_producto", producto.getIdProducto());
                launcherInventario.launch(intent);
            }

            @Override
            public void onDesactivar(Producto producto) {
                Intent intent = new Intent(requireContext(), IngresoMercaderiaActivity.class);
                intent.putExtra("id_producto", producto.getIdProducto());
                launcherInventario.launch(intent);
            }
        });

        rvInventarioAdmin.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvInventarioAdmin.setAdapter(adapter);
    }

    private void configurarEventos() {
        btnIngresoMercaderia.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), IngresoMercaderiaActivity.class);
            launcherInventario.launch(intent);
        });

        btnVerMovimientos.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MovimientosStockActivity.class);
            launcherInventario.launch(intent);
        });
    }

    private void cargarInventario() {
        List<Producto> lista = inventarioViewModel.listarInventario();
        adapter.setLista(lista);
    }
}