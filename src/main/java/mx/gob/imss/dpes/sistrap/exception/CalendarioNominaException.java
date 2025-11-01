/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;


/**
 *
 * @author antonio
 */
public class CalendarioNominaException extends BusinessException{
  private final static String KEY = "msg058";
  
  public CalendarioNominaException() {
    super(KEY);
  }
  
}
