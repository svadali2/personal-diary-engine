package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntryDoesNotExistException extends Exception {
    public EntryDoesNotExistException(long id) {
        super(String.format("There is no diary entry with this id:{}", id));
    }
}
