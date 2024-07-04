package bdabackend.bda.Service;


import bdabackend.bda.Entity.MongoTareaEntity;
import bdabackend.bda.Repository.MongoTareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoTareaService {
    private static final Logger logger = LoggerFactory.getLogger(MongoTareaService.class);

    @Autowired
    private MongoTareaRepository mongoTareaRepository;

    // Guardar una nueva tarea o actualizar una existente
    public MongoTareaEntity insertarTarea(String nombreTarea, String descripcionTarea, String tipoTarea, Point zona, String emergencia) {
        MongoTareaEntity tarea = new MongoTareaEntity(nombreTarea, descripcionTarea, tipoTarea, zona);
        logger.info("Guardando tarea: {}", tarea);
        return mongoTareaRepository.save(tarea);
    }

    public MongoTareaEntity buscarTareaPorId(String id) {
        logger.info("Buscando tarea con id: {}", id);
        Optional<MongoTareaEntity> tarea = mongoTareaRepository.findById(id);
        return tarea.orElse(null);
    }

    public List<MongoTareaEntity> listaTarea() {
        logger.info("Listando todas las tareas");
        return mongoTareaRepository.findAll();
    }

    public void eliminarTareaPorId(String id) {
        logger.info("Eliminando tarea con id: {}", id);
        mongoTareaRepository.deleteById(id);
    }
}
