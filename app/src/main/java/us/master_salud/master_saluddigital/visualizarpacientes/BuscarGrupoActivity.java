package us.master_salud.master_saluddigital.visualizarpacientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BuscarGrupoActivity extends AppCompatActivity {

    private Spinner spinnerGrupoSanguineo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_grupo);

        // Inicializar el Spinner
        spinnerGrupoSanguineo = findViewById(R.id.spinnerGrupoSanguineo);

        // Lista de grupos sanguíneos con una opción predeterminada
        String[] gruposSanguineos = {
                "Selecciona un grupo sanguíneo", // Opción predeterminada
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, gruposSanguineos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Configurar el adaptador al Spinner
        spinnerGrupoSanguineo.setAdapter(adapter);
    }

    // Método para manejar la acción del botón "Buscar"
    public void buscarPorGrupo(View view) {
        // Obtener el grupo sanguíneo seleccionado del Spinner
        String grupoSeleccionado = spinnerGrupoSanguineo.getSelectedItem().toString();

        // Validar si se ha seleccionado un grupo válido
        if (!grupoSeleccionado.equals("Selecciona un grupo sanguíneo")) {
            // Inicia la actividad de ver pacientes pasando el grupo sanguíneo seleccionado
            Intent intent = new Intent(BuscarGrupoActivity.this, VerPacientesActivity.class);
            intent.putExtra("grupoSanguineo", grupoSeleccionado);
            startActivity(intent);
        } else {
            // Mostrar un mensaje si no se ha seleccionado un grupo válido
            Toast.makeText(this, "Por favor, selecciona un grupo sanguíneo", Toast.LENGTH_SHORT).show();
        }
    }
}
