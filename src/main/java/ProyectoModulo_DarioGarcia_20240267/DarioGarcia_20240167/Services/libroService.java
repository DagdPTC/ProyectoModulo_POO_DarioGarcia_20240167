package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Services;

import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.DTO.libroDTO;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Entities.libroEntity;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Exceptions.ExcepcionLibroNoEncontrado;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Exceptions.ExcepcionLibroNoRegistrado;
import ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Repositories.libroRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public libroDTO insertarDatos(libroDTO data) {
        if (data == null || data.getTitulo() == null
                || data.getIsbn() == null ||
                data.getGenero() == null){
            throw new IllegalArgumentException("Los campos no pueden ser nulos");
        }
        try{
            libroEntity entity = ConvertirAEntity(data);
            libroEntity libroGuardado = repo.save(entity);
            return convertirADTO(libroGuardado);
        }catch (Exception e){
            log.error("Error al registrar el libro: " + e.getMessage());
            throw new ExcepcionLibroNoRegistrado("Error al registrar el libro");
        }
    }

    private libroEntity ConvertirAEntity(libroDTO data) {
        libroEntity entity = new libroEntity();
        entity.setTitulo(data.getTitulo());
        entity.setIsbn(data.getIsbn());
        entity.setAño_publicacion(data.getAño_publicacion());
        entity.setGenero(data.getGenero());
        return entity;
    }

    private libroDTO convertirADTO(libroEntity libroEntity) {
        //Creando el objeto a retornar
        libroDTO dto = new libroDTO();
        //Transferir los datos del entity al dto
        dto.setId(libroEntity.getId());
        dto.setTitulo(libroEntity.getTitulo());
        dto.setIsbn(libroEntity.getIsbn());
        dto.setAño_publicacion(libroEntity.getAño_publicacion());
        dto.setGenero(libroEntity.getGenero());
        //Se retorna el objeto dto
        return dto;
    }

    public libroDTO actualizarLibro(Long id, libroDTO json){
        //Verificar la existencia del usuario
        libroEntity existente = repo.findById(id).orElseThrow(() ->
        new ExcepcionLibroNoEncontrado("Usuario no encontrado"));

        //Convertir datos DTO a Entity
        existente.setTitulo(json.getTitulo());
        existente.setIsbn(json.getIsbn());
        existente.setAño_publicacion(json.getAño_publicacion());
        existente.setGenero(json.getGenero());
        //Guardar los cambios
        libroEntity libroActualizado = repo.save(existente);
        //Convertir los datos a DTO y retornalos
        return convertirADTO(libroActualizado);
    }

    public boolean removerLibro(Long id){
        try{
            //Validar la existencia del libro
            libroEntity existente = repo.findById(id).orElse(null);
            //Eliminar el libro en caso exista, si no existe retornar false
            if (existente != null){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontró libro con id: " + id + "para eliminar", 1);
        }
    }
}
