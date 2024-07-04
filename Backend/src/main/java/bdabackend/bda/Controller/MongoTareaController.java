package bdabackend.bda.Controller;

import bdabackend.bda.Entity.MongoTareaEntity;
import bdabackend.bda.Service.MongoTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mongoTarea")
public class MongoTareaController {
    @Autowired
    private MongoTareaService mongoTareaService;

    @GetMapping("/{id}")
    public ResponseEntity<MongoTareaEntity> getTareaById(@PathVariable String id) {
        MongoTareaEntity tarea = mongoTareaService.buscarTareaPorId(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/all")
    public List<MongoTareaEntity> getAllTareas() {
        return mongoTareaService.listaTarea();
    }

/*
    @GetMapping("/palabra/{palabraClave}")
    public ResponseEntity<List<MongoTareaEntity>> buscarTareas(@PathVariable String palabraClave) {
        List<MongoTareaEntity> tareaEntities = mongoTareaService.listaFiltro(palabraClave);
        if (tareaEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tareaEntities);
    }
 */

    @PostMapping("/add")
    public void addTarea(@RequestBody Map<String, String> body) {
        String nombreTarea = body.get("nombreTarea");
        String descripcionTarea = body.get("descripcionTarea");
        String tipoTarea = body.get("tipoTarea");
        String emergencia = body.get("emergencia");
        Double latitud = Double.parseDouble(body.get("latitud"));
        Double longitud = Double.parseDouble(body.get("longitud"));
        Point zona = new Point(longitud, latitud);

        mongoTareaService.insertarTarea(nombreTarea, descripcionTarea, tipoTarea, zona, emergencia);
    }

    @DeleteMapping("/delete/{id}")
    public void eliminar(@PathVariable String id) {
        mongoTareaService.eliminarTareaPorId(id);
    }

}
