package Backend_Voluntarios.Backend.Controller;

import java.util.List;
import java.util.Map;

import Backend_Voluntarios.Backend.Entity.EmergenciaEntity;
import Backend_Voluntarios.Backend.Service.AuditoriaService;
import Backend_Voluntarios.Backend.Service.EmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import Backend_Voluntarios.Backend.Service.InstitucionService;
import Backend_Voluntarios.Backend.Entity.InstitucionEntity;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/institucion")
@CrossOrigin(origins = "*")

public class InstitucionController {
    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private EmergenciaService emergenciaService;

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("/{id}")
    public InstitucionEntity getInstitucionById(@PathVariable Long id) {
        return institucionService.getInstitucionById(id);
    }

    @GetMapping("/all")
    public List<InstitucionEntity> getAllInstituciones() {
        return institucionService.getAllInstituciones();
    }

    @GetMapping("/palabra/{PalabraClave}")
    public ResponseEntity<List<InstitucionEntity>> Buscar_rankings(@PathVariable String PalabraClave) {
        List<InstitucionEntity> rankings_encontrados = institucionService.listaFiltro(PalabraClave);
        if (rankings_encontrados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rankings_encontrados);
    }

    @PostMapping("/add")
    public InstitucionEntity addInstitucion(@RequestBody Map<String, String> body) {
        String nombreInstitucion = body.get("nombreInstitucion");

        InstitucionEntity institucion = new InstitucionEntity(nombreInstitucion);
        Long idUsuario = 1L;
        //metodo para obtener id de usuario ya listo, esperar a
        // pablo
        //auditoriaService.registrarCambio(idUsuario, "Add", "añadio una institucion");
        institucionService.addInstitucion(institucion);


        return institucion;
    }

    // @DeleteMapping("/delete/{id}")
    // public void Eliminar(@PathVariable Long id) {
    // InstitucionEntity institucionEliminada =
    // institucionService.getInstitucionById(id);
    // institucionService.deleteInstitucion(institucionEliminada);

    // // Long idUsuario = //metodo para obtener id de usuario ya listo, esperar a
    // // pablo
    // // auditoriaService.registrarCambio(idUsuario, "Delete", "eliminio una
    // // institucion");
    // }

}
