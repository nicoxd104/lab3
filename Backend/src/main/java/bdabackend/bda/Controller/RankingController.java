package bdabackend.bda.Controller;

import bdabackend.bda.DTO.TareaDot;
import bdabackend.bda.Entity.MongoTareaEntity;
import bdabackend.bda.Entity.RankingEntity;
import bdabackend.bda.Service.AuditoriaService;
import bdabackend.bda.Service.MongoTareaService;
import bdabackend.bda.Service.RankingService;
import bdabackend.bda.Service.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private RankingService rankingService;
    @Autowired
    private VoluntarioService voluntarioService;
    @Autowired
    private MongoTareaService tareaService;

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("/palabra/{palabraClave}")
    public ResponseEntity<List<RankingEntity>> buscarVoluntarios(@PathVariable String palabraClave) {
        List<RankingEntity> rankingEntities = rankingService.listaFiltro(palabraClave);
        if (rankingEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rankingEntities);
    }

    @GetMapping("/{id}")
    public List<?> getRankingById(@PathVariable Long id) {
        return rankingService.buscarRankingPorId(id);
    }

    @GetMapping("/all")
    public List<?> getAllRankings() {
        return rankingService.listaRanking();
    }


    @PostMapping("/add")
    public void crearRanking(@RequestBody Map<String, String> body){
        Long idVoluntario = Long.parseLong(body.get("idVoluntario"));
        Long idEmergencia = Long.parseLong(body.get("idEmergencia"));
        List<?> emergenciaZona = rankingService.emergenciaZona(idEmergencia);
        Object[] emergencia = (Object[]) emergenciaZona.get(0);
        String text = rankingService.bytesToString((byte[]) emergencia[5]);
        assert text != null;
        double[] latLong = rankingService.wkbToLatLong(rankingService.hexStringToByteArray(text));
        double latitudEmergencia = latLong[1];
        double longitudEmergencia = latLong[0];
        List<?> voluntarioZona = rankingService.voluntarioZona(idVoluntario);
        Object[] voluntario = (Object[]) voluntarioZona.get(0);
        String text1 = rankingService.bytesToString((byte[]) voluntario[6]);
        assert text1 != null;
        double[] latLong1 = rankingService.wkbToLatLong(rankingService.hexStringToByteArray(text1));
        double latitudVoluntario = latLong1[1];
        double longitudVoluntario = latLong1[0];
        double distancia = rankingService.distanciaEntrePuntos(latitudVoluntario, longitudVoluntario, latitudEmergencia, longitudEmergencia);


        List<MongoTareaEntity> tareas = tareaService.listaTarea();// obtener las tareas desde tu base de datos o servicio
                List<TareaDot> tareasFormateadas = new ArrayList<>();


        for (MongoTareaEntity tarea : tareas) {
            // Formatear los atributos de la tarea
            String id = tarea.getId();
            String nombre = tarea.getNombre();
            String descripcion = tarea.getDescripcion();
            String tipo = tarea.getTipo();
            String zona = String.format("Lat: %s, Lon: %s", tarea.getZona().getX(), tarea.getZona().getY());
            String emergencia3 = String.valueOf(tarea.getEmergencia());
            String emergenciaStr = String.valueOf(idEmergencia);
            System.out.println(emergencia3);
            System.out.println(emergenciaStr);
            if(Objects.equals(emergencia3, emergenciaStr)){

                // Crear el DTO de la tarea formateada
                TareaDot tareaFormateada = new TareaDot(id, nombre, descripcion, tipo, zona, emergencia3);

                // Añadir a la lista de tareas formateadas
                tareasFormateadas.add(tareaFormateada);
            }
        }

// Ahora tienes una lista de cadenas formateadas que puedes imprimir o manipular
        System.out.println(tareasFormateadas);


        for (TareaDot tarea : tareasFormateadas) {


            System.out.println("hola2");

            String idTarea = tarea.getId();
            String tareaRanking = tareaService.nombre(idTarea);



            int nivelRanking = rankingService.puntajeRanking(distancia, idVoluntario);
            String nombreVoluntario = voluntarioService.nombrev(idVoluntario);
            String numeroDocumentoVoluntario = voluntarioService.numerov(idVoluntario);
            //RankingEntity ranking = new RankingEntity(nivelRanking, tareaRanking, nombreVoluntario,
            //        numeroDocumentoVoluntario);
            //Long idUsuario = 1L;
            // auditoriaService.registrarCambio(idUsuario, "Add", "añadio un ranking");
            rankingService.insertarRanking(nivelRanking, tareaRanking, nombreVoluntario,
                    numeroDocumentoVoluntario, idTarea,
                    idVoluntario);
            // Long idUsuario = metodo para obtener id de usuario ya listo, esperar a pablo
            // auditoriaService.registrarCambio(idUsuario, "Add", "añadio un ranking");
        }
    }


    @DeleteMapping("/delete/{idRanking}")
    public void eliminar(@PathVariable Long idRanking) {
        //RankingEntity rankingEntity = rankingService.buscarRankingPorId(idRanking);
        //Long idUsuario = 1L;// metodo para obtener id de usuario ya listo, esperar a
        // pablo
        // auditoriaService.registrarCambio(idUsuario, "Delete", "elimino un ranking");
        rankingService.eliminarRankingPorId(idRanking);

    }
}
