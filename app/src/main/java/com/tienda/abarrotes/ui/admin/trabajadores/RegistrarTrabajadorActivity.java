package com.tienda.abarrotes.ui.admin.trabajadores;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

public class RegistrarTrabajadorActivity extends AppCompatActivity {

    private EditText etNombres;
    private EditText etApellidos;
    private EditText etDni;
    private EditText etTelefono;
    private EditText etUsuario;
    private EditText etContrasena;
    private Spinner spRol;
    private Button btnGuardarTrabajador;
    private Button btnCancelarTrabajador;

    private TrabajadorViewModel trabajadorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_trabajador);

        trabajadorViewModel = new TrabajadorViewModel(this);

        initViews();
        setupRolSpinner();
        configurarEventos();
    }

    private void initViews() {
        etNombres = findViewById(R.id.etNombresTrabajador);
        etApellidos = findViewById(R.id.etApellidosTrabajador);
        etDni = findViewById(R.id.etDniTrabajador);
        etTelefono = findViewById(R.id.etTelefonoTrabajador);
        etUsuario = findViewById(R.id.etUsuarioTrabajador);
        etContrasena = findViewById(R.id.etContrasenaTrabajador);
        spRol = findViewById(R.id.spRolTrabajador);
        btnGuardarTrabajador = findViewById(R.id.btnGuardarTrabajador);
        btnCancelarTrabajador = findViewById(R.id.btnCancelarTrabajador);
    }

    private void setupRolSpinner() {
        String[] roles = {
                AppConstants.ROL_ADMINISTRADOR,
                AppConstants.ROL_REPONEDOR,
                AppConstants.ROL_CAJERO
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                roles
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRol.setAdapter(adapter);
    }

    private void configurarEventos() {
        btnGuardarTrabajador.setOnClickListener(v -> guardarTrabajador());
        btnCancelarTrabajador.setOnClickListener(v -> finish());
    }

    private void guardarTrabajador() {
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String dni = etDni.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String rol = spRol.getSelectedItem().toString();

        String errorValidacion = trabajadorViewModel.validarDatos(
                nombres, apellidos, dni, telefono, usuario, contrasena, rol
        );

        if (errorValidacion != null) {
            mostrarMensaje(errorValidacion);
            return;
        }

        String errorRegistro = trabajadorViewModel.registrarTrabajador(
                "", nombres, apellidos, dni, telefono, usuario, contrasena, rol
        );

        if (errorRegistro != null) {
            mostrarMensaje(errorRegistro);
            return;
        }

        mostrarMensaje("Trabajador registrado correctamente");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}