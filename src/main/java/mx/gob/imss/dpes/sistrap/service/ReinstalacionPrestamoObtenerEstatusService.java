package mx.gob.imss.dpes.sistrap.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstatusRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstatusResponse;
import mx.gob.imss.dpes.sistrap.exception.ReinstalacionPrestamoException;
import mx.gob.imss.dpes.sistrap.restclient.ReinstalacionPrestamoClient;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ReinstalacionPrestamoObtenerEstatusService {

    @Inject
    @RestClient
    private ReinstalacionPrestamoClient reinstalacionClient;
    private Logger log = Logger.getLogger(this.getClass().getName());

    public ConsultaEstatusResponse obtenerEstatusByIdPrestamo(ConsultaEstatusRequest request) throws BusinessException {
        try {
            Response response = reinstalacionClient.obtenerEstatus(request);

            return this.procesaRespuestaConsultaEstatus(response);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReinstalacionPrestamoObtenerEstatusService.execute()", e);
        }
        throw new ReinstalacionPrestamoException(
            ReinstalacionPrestamoException.ERROR_SERVICIO_SIPRE_CONSULTA_ESTATUS_PRESTAMO);
    }

    private ConsultaEstatusResponse procesaRespuestaConsultaEstatus(Response response) throws BusinessException {
        try {
            ConsultaEstatusResponse consultaEstatusResponse = response.readEntity(ConsultaEstatusResponse.class);

            if (!(consultaEstatusResponse != null && consultaEstatusResponse.getCodigoError() != null))
                throw new ReinstalacionPrestamoException(
                    ReinstalacionPrestamoException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_ESTATUS_PRESTAMO);

            if (Integer.parseInt(consultaEstatusResponse.getCodigoError()) !=
                    Response.Status.OK.getStatusCode())
                throw new VariableMessageException(consultaEstatusResponse.getMensajeError());

            return consultaEstatusResponse;
        } catch (VariableMessageException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR ReinstalacionPrestamoObtenerEstatusService.procesaRespuestaConsultaEstatus ", e);
        }
        throw new ReinstalacionPrestamoException(
            ReinstalacionPrestamoException.ERROR_SERVICIO_SIPRE_CONSULTA_ESTATUS_PRESTAMO);
    }

}
