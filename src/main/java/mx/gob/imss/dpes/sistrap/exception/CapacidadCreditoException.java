package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class CapacidadCreditoException extends BusinessException {
  public final static String ERROR_DESCONOCIDO_EN_EL_SERVICIO = "msg364";
  public final static String ERROR_DESCONOCIDO_SERVICIO_CONSULTA_CAPACIDAD_CREDITO = "msg372";
  public final static String ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_CAPACIDAD = "msg373";
  public final static String ERROR_EN_SERVICIO_CONSULTA_CAPACIDAD_CREDITO = "msg374";
  public final static String ERROR_EN_RECUPERACION_TOKEN = "msg381";
  public final static String MENSAJE_DE_SOLICITUD_INCORRECTO = "msg382";
  public final static String ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR = "msg0385";

  public CapacidadCreditoException(String caso){
      super(caso);
  }

}
