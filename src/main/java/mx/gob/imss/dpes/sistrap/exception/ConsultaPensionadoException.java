package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;


public class ConsultaPensionadoException extends BusinessException{
  public final static String KEY = "msg058";
 
  public ConsultaPensionadoException() {
    super(KEY);
  }
  public ConsultaPensionadoException(String caso){
      super(caso);
  }
  
}
