package us.master_salud.master_saluddigital.visualizarpacientes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Random;
public class MainActivity extends AppCompatActivity {

    // Atributos de la clase:
    EditText ETnuhsa; // EditText para el texto de búsqueda de paciente por NUHSA
    FirebaseDatabase database; // FirebaseDatabase
    DatabaseReference puntoAcceso; // Referencia de base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa el EditText para la búsqueda de pacientes y lo oculta inicialmente
        ETnuhsa = findViewById(R.id.editText);
        ETnuhsa.setVisibility(View.GONE); // Ocultar el EditText al inicio

        // Configura el botón para buscar por grupo sanguíneo
        findViewById(R.id.btnBuscarPorGrupo).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarGrupoActivity.class);
            startActivity(intent);
        });

        // Configura el botón para buscar un paciente
        findViewById(R.id.btnBuscarPaciente).setOnClickListener(v -> {
            // Mostrar el EditText cuando se pulsa el botón de "Buscar Paciente"
            if (ETnuhsa.getVisibility() == View.GONE) {
                ETnuhsa.setVisibility(View.VISIBLE); // Mostrar el EditText
            } else {
                // Si el EditText ya está visible, se procede con la funcionalidad de búsqueda
                String nuhsa = ETnuhsa.getText().toString();
                if (!nuhsa.isEmpty()) {
                    // Lanza la actividad BuscaActivity pasando el NUHSA como parámetro
                    Intent intent = new Intent(MainActivity.this, BuscaActivity.class);
                    intent.putExtra(Constantes.nuhsa, nuhsa);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Por favor, ingrese un NUHSA válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ocultar el EditText cuando se regrese a MainActivity
        ETnuhsa.setVisibility(View.GONE);
    }

    // OnClick asignado desde el Layout para insertar un paciente aleatorio
    public void insertaPaciente(View view) {
        Paciente paciente = generaPacienteAleatorio();
        database = FirebaseDatabase.getInstance();
        puntoAcceso = database.getReference();
        puntoAcceso.child(Constantes.pacientes).child(paciente.getNuhsa()).setValue(paciente);
        puntoAcceso.child(paciente.getGrupoSanguineo()).child(paciente.getNuhsa()).setValue(paciente);
        Toast.makeText(this, paciente.toString(), Toast.LENGTH_LONG).show();
    }

    // Método para generar un paciente aleatorio
    private Paciente generaPacienteAleatorio() {
        Random random = new Random();
        String nuhsa = Constantes.nuhsa + random.nextInt(9999);
        String grupo = Constantes.grupo[random.nextInt(8)];
        return new Paciente(
                Constantes.nombre[random.nextInt(8)],
                Constantes.apellido[random.nextInt(8)] + " " + Constantes.apellido[random.nextInt(8)],
                grupo,
                nuhsa
        );
    }

    // Método para abrir la pantalla de ver pacientes
    public void VerPacientes(View view) {
        Intent intent = new Intent(MainActivity.this, VerPacientesActivity.class);
        startActivity(intent);
    }
}
