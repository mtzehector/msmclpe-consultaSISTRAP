package mx.gob.imss.dpes.sistrap.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularResponse;
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
public class ReinstalacionPrestamoDatosTitularService  {

    @Inject
    @RestClient
    private ReinstalacionPrestamoClient reinstalacionClient;
    private Logger log = Logger.getLogger(this.getClass().getName());

    public ConsultaDatosTitularResponse obtenerDatosTitular(ConsultaDatosTitularRequest request)
        throws BusinessException {

        try {
            Response response = reinstalacionClient.obtenerDatosTitular(request);

            if (response != null && response.getStatus() == 200){
                return response.readEntity(ConsultaDatosTitularResponse.class);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReinstalacionPrestamoDatosTitularService.obtenerDatosTitular()", e);
        }

        throw new ReinstalacionPrestamoException(
            ReinstalacionPrestamoException.ERROR_SERVICIO_SIPRE_CONSULTA_DATOS_PERSONALES);
    }

    private ConsultaDatosTitularResponse procesaRespuestaConsultaDatosTitular(Response response)
        throws BusinessException {

        try {
            ConsultaDatosTitularResponse consultaDatosTitularResponse =
                response.readEntity(ConsultaDatosTitularResponse.class);

            if (!(consultaDatosTitularResponse != null && consultaDatosTitularResponse.getCodigoError() != null))
                throw new ReinstalacionPrestamoException(
                    ReinstalacionPrestamoException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_DATOS_PERSONALES);

            if (Integer.parseInt(consultaDatosTitularResponse.getCodigoError()) !=
                    Response.Status.OK.getStatusCode())
                throw new VariableMessageException(consultaDatosTitularResponse.getMensajeError());

            return consultaDatosTitularResponse;
        } catch (VariableMessageException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR ReinstalacionPrestamoDatosTitularService.procesaRespuestaConsultaDatosTitular ", e);
        }
        throw new ReinstalacionPrestamoException(
            ReinstalacionPrestamoException.ERROR_SERVICIO_SIPRE_CONSULTA_DATOS_PERSONALES);
    }

}
