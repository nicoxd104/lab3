package bdabackend.bda.Service;


import bdabackend.bda.Entity.MongoTareaEntity;
import bdabackend.bda.Repository.MongoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoTareaService {
    @Autowired
    private MongoTareaRepository mongoTareaRepository;

    public void insertarTarea(String nombre, String descripcion, String tipo, Point zona, String idEmergencia) {
        MongoTareaEntity tarea = new MongoTareaEntity(nombre, descripcion, tipo, zona);
        //tarea.setEmergencia(idEmergencia);
        mongoTareaRepository.save(tarea);
    }

    public void insertarTareaSinEmergencia(String nombre, String descripcion, String tipo, Point zona) {
        MongoTareaEntity tarea = new MongoTareaEntity(nombre, descripcion, tipo, zona);
        mongoTareaRepository.save(tarea);
    }

    public void eliminarTareaPorId(String id) {
        mongoTareaRepository.deleteById(id);
    }

    public MongoTareaEntity buscarTareaPorId(String id) {
        return mongoTareaRepository.findById(id).orElse(null);
    }

    public List<MongoTareaEntity> listaTarea() {
        return mongoTareaRepository.findAll();
    }

    public List<MongoTareaEntity> getRankingTarea(String nombreTarea) {
        // Implementar si es necesario
        return null;
    }

/*
    public List<MongoTareaEntity> tablaTareas(String id) {
        return mongoTareaRepository.findByEmergenciaId(id);
    }

    public List<MongoTareaEntity> listaFiltro(String palabraClave) {
        return mongoTareaRepository.findByNombreContaining(palabraClave);
    }
 */

}
