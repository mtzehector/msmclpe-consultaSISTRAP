package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class PensionException extends BusinessException {

  public final static String ERROR_DESCONOCIDO_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR = "msg369";
  public final static String ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR = "msg370";
  public final static String ERROR_EN_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR = "msg371";

  public PensionException(String caso){
      super(caso);
  }

}
