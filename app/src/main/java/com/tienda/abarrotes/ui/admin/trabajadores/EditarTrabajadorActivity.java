package com.tienda.abarrotes.ui.admin.trabajadores;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

public class EditarTrabajadorActivity extends AppCompatActivity {

    private EditText etNombresEditarTrabajador;
    private EditText etApellidosEditarTrabajador;
    private EditText etDniEditarTrabajador;
    private EditText etTelefonoEditarTrabajador;
    private EditText etUsuarioEditarTrabajador;
    private EditText etContrasenaEditarTrabajador;
    private Spinner spRolEditarTrabajador;
    private Spinner spEstadoEditarTrabajador;
    private Button btnActualizarTrabajador;
    private Button btnCancelarEditarTrabajador;

    private TrabajadorViewModel trabajadorViewModel;
    private Trabajador trabajadorActual;
    private int idTrabajador = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_trabajador);

        trabajadorViewModel = new TrabajadorViewModel(this);

        initViews();
        setupSpinners();
        recibirDatos();
        configurarEventos();
    }

    private void initViews() {
        etNombresEditarTrabajador = findViewById(R.id.etNombresEditarTrabajador);
        etApellidosEditarTrabajador = findViewById(R.id.etApellidosEditarTrabajador);
        etDniEditarTrabajador = findViewById(R.id.etDniEditarTrabajador);
        etTelefonoEditarTrabajador = findViewById(R.id.etTelefonoEditarTrabajador);
        etUsuarioEditarTrabajador = findViewById(R.id.etUsuarioEditarTrabajador);
        etContrasenaEditarTrabajador = findViewById(R.id.etContrasenaEditarTrabajador);
        spRolEditarTrabajador = findViewById(R.id.spRolEditarTrabajador);
        spEstadoEditarTrabajador = findViewById(R.id.spEstadoEditarTrabajador);
        btnActualizarTrabajador = findViewById(R.id.btnActualizarTrabajador);
        btnCancelarEditarTrabajador = findViewById(R.id.btnCancelarEditarTrabajador);
    }

    private void setupSpinners() {
        String[] roles = {
                AppConstants.ROL_ADMINISTRADOR,
                AppConstants.ROL_REPONEDOR,
                AppConstants.ROL_CAJERO
        };

        ArrayAdapter<String> adapterRoles = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                roles
        );
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRolEditarTrabajador.setAdapter(adapterRoles);

        String[] estados = {
                AppConstants.ESTADO_ACTIVO,
                AppConstants.ESTADO_INACTIVO
        };

        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                estados
        );
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstadoEditarTrabajador.setAdapter(adapterEstados);
    }

    private void recibirDatos() {
        idTrabajador = getIntent().getIntExtra("id_trabajador", -1);

        if (idTrabajador == -1) {
            mostrarMensaje("No se recibió el trabajador");
            finish();
            return;
        }

        trabajadorActual = trabajadorViewModel.obtenerTrabajadorPorId(idTrabajador);

        if (trabajadorActual == null) {
            mostrarMensaje("Trabajador no encontrado");
            finish();
            return;
        }

        etNombresEditarTrabajador.setText(trabajadorActual.getNombres());
        etApellidosEditarTrabajador.setText(trabajadorActual.getApellidos());
        etDniEditarTrabajador.setText(trabajadorActual.getDni());
        etTelefonoEditarTrabajador.setText(trabajadorActual.getTelefono());
        etUsuarioEditarTrabajador.setText(trabajadorActual.getUsuario());
        etContrasenaEditarTrabajador.setText("");

        setSpinnerSelection(spRolEditarTrabajador, trabajadorActual.getRol());
        setSpinnerSelection(spEstadoEditarTrabajador, trabajadorActual.getEstado());
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void configurarEventos() {
        btnActualizarTrabajador.setOnClickListener(v -> actualizarTrabajador());
        btnCancelarEditarTrabajador.setOnClickListener(v -> finish());
    }

    private void actualizarTrabajador() {
        String nombres = etNombresEditarTrabajador.getText().toString().trim();
        String apellidos = etApellidosEditarTrabajador.getText().toString().trim();
        String dni = etDniEditarTrabajador.getText().toString().trim();
        String telefono = etTelefonoEditarTrabajador.getText().toString().trim();
        String usuario = etUsuarioEditarTrabajador.getText().toString().trim();
        String contrasena = etContrasenaEditarTrabajador.getText().toString().trim();
        String rol = spRolEditarTrabajador.getSelectedItem().toString();
        String estado = spEstadoEditarTrabajador.getSelectedItem().toString();

        String error = trabajadorViewModel.validarDatos(
                nombres, apellidos, dni, telefono, usuario,
                contrasena.isEmpty() ? "******" : contrasena,
                rol
        );

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        String errorActualizacion = trabajadorViewModel.actualizarTrabajador(
                idTrabajador, trabajadorActual.getFotoUri(), nombres, apellidos,
                dni, telefono, usuario, contrasena, rol, estado
        );

        if (errorActualizacion != null) {
            mostrarMensaje(errorActualizacion);
            return;
        }

        mostrarMensaje("Trabajador actualizado");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}