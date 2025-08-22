package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Services;

import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.DTO.libroDTO;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Entities.libroEntity;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Repositories.libroRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class libroService {

    //Inyectamos el repository sobre el service
    @Autowired
    libroRepository repo;

    public List<libroDTO> obtenerLibro() {
        List<libroEntity> lista = repo.findAll();
        return lista.stream()
                .map(this::convertirADTO)
                .collect((Collectors.toList()));
    }

    private libroDTO convertirADTO(libroEntity libroEntity) {
    }

    public libroDTO insertarDatos(@Valid libroDTO json) {
    }
}
