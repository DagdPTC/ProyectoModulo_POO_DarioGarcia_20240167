package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Controllers;

import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.DTO.libroDTO;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Exceptions.ExcepcionDatosDuplicados;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Exceptions.ExcepcionLibroNoEncontrado;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Services.libroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiLibros")
public class libroController {

    //Inyectamos el service sobre el controller
    @Autowired
    private libroService service;

    @GetMapping("/consultarDatos")
    public List <libroDTO> obtenerDatos(){
        return service.obtenerLibro();
    }

    @PostMapping("/registrarDatos")
    public ResponseEntity<?> nuevoLibro(@Valid @RequestBody libroDTO json, HttpServletRequest request){
        try {
            libroDTO respuesta = service.insertarDatos(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
               "status", "Error",
               "message", "Error no controlado al registrar libro",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/editarLibro/{id}")
    public ResponseEntity<?> modificarLibro(
            @PathVariable Long id,
            @Valid @RequestBody libroDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            //Se crea el objeto tipo DTO y se invoca al metodo actualizarLibro que está en el service
            libroDTO dto = service.actualizarLibro(id, json);
            //La api retorna una respuesta la cual contendrá los datos en formato DTO
            return ResponseEntity.ok(dto);
        }catch (ExcepcionLibroNoEncontrado e){
            return ResponseEntity.notFound().build();
        }catch (ExcepcionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "Datos duplicados",
                    "Campo", e.getCampoDuplicado())
            );
        }
    }

    @DeleteMapping("/eliminarRegistro/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long id){
        try {
            if (!service.removerLibro(id)){
                //Error
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Mensaje error", "Libro no encontrado")
                        .body(Map.of(
                                "Error", "Not Found",
                                "Mensaje", "El libro no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Libro eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
               "status", "Error",
               "message", "Error al eliminar el usuario",
               "detail", e.getMessage()
            ));
        }
    }
}
