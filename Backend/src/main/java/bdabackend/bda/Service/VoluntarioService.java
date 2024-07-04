package bdabackend.bda.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import bdabackend.bda.Entity.VoluntarioEntity;
import bdabackend.bda.Repository.VoluntarioRepository;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Service
public class VoluntarioService {
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    public void insertarVoluntario(VoluntarioEntity voluntario) {
        voluntarioRepository.insertarVoluntario(voluntario.getNombre(), voluntario.getCorreo(),
                voluntario.getNumeroDocumento(), voluntario.getZonaVivienda(), voluntario.getContrasena(),
                voluntario.getEquipamiento());
    }

    public List<VoluntarioEntity> listaFiltro(String palabraClave) {
        return voluntarioRepository.findAll(palabraClave);
    }

    public void eliminarVoluntarioPorId(Long id) {
        voluntarioRepository.eliminarVoluntarioPorId(id);
    }

    public VoluntarioEntity buscarVoluntarioPorId(Long id) {
        return voluntarioRepository.buscarVoluntarioPorId(id);
    }

    public VoluntarioEntity buscarPorCorreo(String correo) {

        VoluntarioEntity voluntario = new VoluntarioEntity();
        List<String> voluntarioData = voluntarioRepository.buscarPorCorreo(correo);
        // Se hace split "," para obtener los datos de la lista al primer elemento
        String[] parts = voluntarioData.get(0).split(",");
        if (parts.length > 0) {
            voluntario.setNombre(parts[0]);
            voluntario.setCorreo(parts[1]);
            voluntario.setEquipamiento(parts[2]);
            voluntario.setNumeroDocumento(parts[3]);
            voluntario.setContrasena(parts[4]);
        }
        return voluntario;
    }

    public List<?> listaVoluntario() {
        return voluntarioRepository.listaVoluntario();
    }

    public List<?> listaVoluntarioId(Long id) {
        return voluntarioRepository.listaVoluntarioId(id);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void crearVoluntario(String nombreVoluntario, String correoVoluntario, String numeroDocumentoVoluntario,
            Double latitud, Double longitud, String contrasenaVoluntario, String equipamientoVoluntario) {

        // Convierte las coordenadas a un formato adecuado para PostgreSQL, como WKT
        DecimalFormat df = new DecimalFormat("#.######", new DecimalFormatSymbols(Locale.US));
        String zonaViviendaWKT = String.format("POINT(%s %s)", df.format(longitud), df.format(latitud));

        // Ejecuta la consulta SQL parametrizada para insertar el nuevo voluntario
        jdbcTemplate.update("INSERT INTO voluntario (nombre, correo, "
                + "numero_documento, zona_vivienda, contrasena, "
                + "equipamiento) VALUES (?, ?, ?, ST_GeomFromText(?), ?, ?)", nombreVoluntario,
                correoVoluntario, numeroDocumentoVoluntario, zonaViviendaWKT, contrasenaVoluntario,
                equipamientoVoluntario);
    }

    public String nombrev(Long id) {
        return voluntarioRepository.nombre(id);
    }

    public String numerov(Long id) {
        return voluntarioRepository.numeroDocumento(id);
    }

    public String eqipamientov(Long id) {
        return voluntarioRepository.equipamiento(id);
    }
}
