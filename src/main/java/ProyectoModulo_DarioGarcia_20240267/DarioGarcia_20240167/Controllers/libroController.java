package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Controllers;

import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.DTO.libroDTO;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Services.libroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        }catch (){

        }
    }
}
