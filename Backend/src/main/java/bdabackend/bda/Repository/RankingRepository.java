package bdabackend.bda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import bdabackend.bda.Entity.RankingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntity, Long> {
        // Crear
        // Insertar un nuevo registro en la tabla ranking con los valores de idTarea y
        // idVoluntario
        @Transactional
        @Modifying
        @Query(value = "INSERT INTO ranking (nivel, tarea_ranking, nombre_voluntario, numero_docuemto_voluntario, id_tarea, id_voluntario) "
                        +
                        "VALUES (:nivel, :tareaRanking, :nombre_voluntario, :numeroDocumentoVoluntario, :idTarea, :idVoluntario)", nativeQuery = true)
        public void insertarRanking(@Param("nivel") int nivel, @Param("tareaRanking") String tareaRanking,
                        @Param("nombre_voluntario") String nombre_voluntario,
                        @Param("numeroDocumentoVoluntario") String numeroDocumentoVoluntario,
                        @Param("idTarea") Long idTarea,
                        @Param("idVoluntario") Long idVoluntario);

        // Crear
        // Insertar un nuevo registro en la tabla ranking sin los valores de idTarea y
        // idVoluntario
        @Transactional
        @Modifying
        @Query(value = "INSERT INTO ranking (nivel, tarea_ranking, nombre_voluntario, numero_docuemto_voluntario) "
                        +
                        "VALUES (:nivel, :tareaRanking, :nombre_voluntario, :numeroDocumentoVoluntario)", nativeQuery = true)
        public void insertarRankingSinTareaVoluntario(@Param("nivel") String nivel,
                        @Param("tareaRanking") String tareaRanking,
                        @Param("nombre_voluntario") String nombre_voluntario,
                        @Param("numeroDocumentoVoluntario") String numeroDocumentoVoluntario);

        // Leer
        @Query(value = "SELECT * FROM ranking WHERE ranking.id = ?1", nativeQuery = true)
        public List<?> buscarRankingPorId(@Param("v") Long id);

        @Query("SELECT palabra FROM RankingEntity palabra WHERE"
                        + " CONCAT(palabra.nivel, palabra.numeroDocumentoVoluntario, " +
                        "palabra.nombreVoluntario, palabra.tareaRanking)"
                        + " LIKE %?1%")
        public List<RankingEntity> findAll(@Param("palabra") String palabraClave);

        @Query(value = "SELECT * FROM ranking", nativeQuery = true)
        public List<?> listaRanking();

        // Delete
        @Transactional
        @Modifying
        @Query("DELETE FROM RankingEntity v WHERE v.id = :id")
        public void eliminarRankingPorId(@Param("id") Long id);

        @Query(value = "SELECT * FROM emergencia WHERE emergencia.id =?1", nativeQuery = true)
        public List<?> sacarZonaEmergencia(@Param("v") Long id);

        @Query(value = "SELECT * FROM voluntario WHERE voluntario.id =?1", nativeQuery = true)
        public List<?> sacarZonaVoluntario(@Param("v") Long id);

        @Query("SELECT v.habilidadRequerida FROM TareaHabilidadEntity v WHERE v.habilidadRequerida LIKE CONCAT('%', :equipo, '%')")
        public List<String> matchEquipo(@Param("equipo") String equipo);

        @Query("SELECT COUNT(v) FROM VoluntarioHabilidadEntity v WHERE v.voluntario.id=:id")
        public int matchHabilidad(@Param("id") Long id);
}
