package larentina.exceptions;

import lombok.Data;

@Data
public class ForbiddenCellException extends RuntimeException{
    private final String message;

    public ForbiddenCellException(String message) {
        this.message = message;
    }
}
