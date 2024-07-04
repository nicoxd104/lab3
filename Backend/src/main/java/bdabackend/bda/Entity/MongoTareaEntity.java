package bdabackend.bda.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "Tarea")

public class MongoTareaEntity {
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Point zona;

    @DBRef
    private Set<RankingEntity> ranking = new HashSet<>();

    @DBRef
    private Set<TareaHabilidadEntity> tareaHabilidad = new HashSet<>();

    @DBRef
    private Set<EstadoTareaEntity> estadoTarea = new HashSet<>();

    @DBRef
    private EmergenciaEntity emergencia;

    // Constructor
    public MongoTareaEntity() {
    }

    public MongoTareaEntity(String nombre, String descripcion, String tipo, Point zona) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Point getZona() {
        return zona;
    }

    public void setZona(Point zona) {
        this.zona = zona;
    }
}
