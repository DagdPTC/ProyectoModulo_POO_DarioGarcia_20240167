package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class libroDTO {

    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String isbn;

    @NotBlank
    @Positive(message = "El año de publicación debe ser positivo")
    private int año_publicacion;

    @NotBlank
    private String genero;


}
