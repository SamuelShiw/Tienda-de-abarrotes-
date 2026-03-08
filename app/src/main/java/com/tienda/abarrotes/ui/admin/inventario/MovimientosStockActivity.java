package com.tienda.abarrotes.ui.admin.inventario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.adapters.MovimientoStockAdapter;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;

public class MovimientosStockActivity extends AppCompatActivity {

    private RecyclerView rvMovimientosStock;
    private MovimientoStockAdapter adapter;
    private InventarioViewModel inventarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos_stock);

        inventarioViewModel = new InventarioViewModel(this);

        initViews();
        setupRecycler();
        cargarMovimientos();
    }

    private void initViews() {
        rvMovimientosStock = findViewById(R.id.rvMovimientosStock);
    }

    private void setupRecycler() {
        adapter = new MovimientoStockAdapter();
        rvMovimientosStock.setLayoutManager(new LinearLayoutManager(this));
        rvMovimientosStock.setAdapter(adapter);
    }

    private void cargarMovimientos() {
        adapter.setLista(inventarioViewModel.listarMovimientos());
    }
}