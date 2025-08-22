package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Controllers;

import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.DTO.libroDTO;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Services.libroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/editarLibro")
}
