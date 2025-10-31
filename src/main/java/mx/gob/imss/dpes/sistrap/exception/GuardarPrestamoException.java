/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

/**
 *
 * @author eduardo.loyo
 */
public class GuardarPrestamoException extends BusinessException {

    public final static String KEY = "msg025";

    public GuardarPrestamoException() {
        super(KEY);
    }

    public GuardarPrestamoException(String caso) {
        super(caso);
    }
}
