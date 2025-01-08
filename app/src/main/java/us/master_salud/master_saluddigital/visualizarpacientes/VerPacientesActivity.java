package us.master_salud.master_saluddigital.visualizarpacientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class VerPacientesActivity extends AppCompatActivity {

    private ListView listViewPacientes;
    private DatabaseReference puntoAcceso;
    private List<String> listaIdentificadores;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pacientes);

        // Obtener el grupo sanguíneo pasado desde la actividad anterior
        String grupoSanguineo = getIntent().getStringExtra("grupoSanguineo");

        // Inicializar vistas
        listViewPacientes = findViewById(R.id.listViewPacientes);
        listaIdentificadores = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaIdentificadores);
        listViewPacientes.setAdapter(adapter);

        // Acceder a Firebase
        puntoAcceso = FirebaseDatabase.getInstance().getReference().child(grupoSanguineo);

        // Listener para obtener los identificadores de pacientes del grupo sanguíneo
        puntoAcceso.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaIdentificadores.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String nuhsa = data.getKey(); // Obtener el identificador del paciente
                    listaIdentificadores.add(nuhsa);
                }
                adapter.notifyDataSetChanged(); // Notificar al adaptador para refrescar la lista
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VerPacientesActivity.this,
                        "Error al cargar datos: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
