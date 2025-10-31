package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class TokenLoginException extends BusinessException {
    private final static String KEY = "msg0383";

    public TokenLoginException() {
        super(KEY);
    }

}
