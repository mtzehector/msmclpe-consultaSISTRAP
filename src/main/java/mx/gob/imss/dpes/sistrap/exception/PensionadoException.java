package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class PensionadoException extends BusinessException{

  public final static String ERROR_EN_SERVICIO_SISTRAP_CONSULTA_PENSIONADO = "msg362";
  public final static String ERROR_EN_RESPUESTA_SERVICIO_SISTRAP_CONSULTA_PENSIONADO = "msg363";
  public final static String ERROR_DESCONOCIDO_EN_EL_SERVICIO = "msg364";

  public PensionadoException(String caso){
      super(caso);
  }

}
