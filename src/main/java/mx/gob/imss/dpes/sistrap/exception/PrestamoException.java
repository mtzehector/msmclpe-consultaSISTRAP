package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;


public class PrestamoException extends BusinessException{
  public final static String KEY = "msg058";
  public final static String SAVESISTRA = "msg059";
  public final static String NOFOUND = "msg058";
  public final static String ERROR_SISTRAP = "msg060";
 
  public PrestamoException() {
    super(KEY);
  }
  public PrestamoException(String caso){
      super(caso);
  }
  
 
  
}
