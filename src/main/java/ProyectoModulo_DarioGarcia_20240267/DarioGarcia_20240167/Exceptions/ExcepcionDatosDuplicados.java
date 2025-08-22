package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167.Exceptions;

import lombok.Getter;

public class ExcepcionDatosDuplicados extends RuntimeException {
    @Getter
    private String campoDuplicado;

    public ExcepcionDatosDuplicados(String message, String campoDuplicado) {
        super(message);
        this.campoDuplicado = campoDuplicado;
    }

    public ExcepcionDatosDuplicados(String campoDuplicado){this.campoDuplicado;}
}
