package mx.gob.imss.dpes.sistrap.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class ReinstalacionPrestamoException extends BusinessException {

    public final static String ERROR_DESCONOCIDO_EN_EL_SERVICIO = "msg364";
    public final static String MENSAJE_DE_SOLICITUD_INCORRECTO = "msg365";
    public final static String ERROR_SERVICIO_SIPRE_CONSULTA_ESTATUS_PRESTAMO = "msg366";
    public final static String ERROR_SERVICIO_SIPRE_CONSULTA_DATOS_PERSONALES = "msg367";
    public final static String ERROR_AL_EJECUTAR_CONSULTA = "msg368";
    public final static String ERROR_DE_LECTURA_DE_BD = "msg375";
    public final static String ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_ESTATUS_PRESTAMO = "msg376";
    public final static String ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_DATOS_PERSONALES = "msg377";

    public ReinstalacionPrestamoException(String messageKey) {
        super(messageKey);
    }
}
