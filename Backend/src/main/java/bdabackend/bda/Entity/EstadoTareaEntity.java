package bdabackend.bda.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_tarea")
public class EstadoTareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private Boolean estado;
/*
    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private MongoTareaEntity tarea;
*/
    // Constructores
    public EstadoTareaEntity() {
    }

    public EstadoTareaEntity(Boolean estado
            //, MongoTareaEntity tarea
    ) {
        this.estado = estado;
        //this.tarea = tarea;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Boolean getEstado() {
        return estado;
    }
/*
    public MongoTareaEntity getTarea() {
        return tarea;
    }
*/
    public void setId(Long id) {
        this.id = id;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
/*
    public void setTarea(MongoTareaEntity tarea) {
        this.tarea = tarea;
    }

 */
}
