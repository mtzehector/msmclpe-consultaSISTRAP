package mx.gob.imss.dpes.sistrap.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadRequestAux;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionesResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequest;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pension;
import mx.gob.imss.dpes.sistrap.entity.PerfilPersona;
import mx.gob.imss.dpes.sistrap.exception.CapacidadCreditoException;
import mx.gob.imss.dpes.sistrap.model.ConsultaCapacidadCredito;
import mx.gob.imss.dpes.sistrap.repository.PerfilPersonaRepository;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PerfilPersonaService extends ServiceDefinition<ConsultaCapacidadCredito, ConsultaCapacidadCredito> {

    @Autowired
    private PerfilPersonaRepository perfilPersonaRepository;

    @Inject
    @RestClient
    private SistrapClient client;

    private boolean validaNSS(String nss, PerfilPersona perfilPersona) throws BusinessException {

        if(
            !(
                nss != null && perfilPersona != null &&
                perfilPersona.getNss() != null &&
                perfilPersona.getCveCurp() != null
            )
        )
            throw new CapacidadCreditoException(CapacidadCreditoException.MENSAJE_DE_SOLICITUD_INCORRECTO);

        Response response = null;

        try {
            CurpRequest curpRequest = new CurpRequest();
            curpRequest.setCurp(perfilPersona.getCveCurp());
            response = client.consultaPensionGrupoFamiliar(curpRequest);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PerfilPersonaService.validaNSS(" + nss +
                ", " + perfilPersona.getNss() +
                ", " + perfilPersona.getCveCurp() +
                ") - ", e.getMessage());
            throw new CapacidadCreditoException(
                CapacidadCreditoException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR);
        }

        try {
            ConsultaPensionesResponse consultaPensionesResponse = response.readEntity(ConsultaPensionesResponse.class);

            if (!(consultaPensionesResponse != null && consultaPensionesResponse.getCodigoError() != null))
                throw new CapacidadCreditoException(
                    CapacidadCreditoException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR);

            if (consultaPensionesResponse.getPensiones() != null) {
                for (Pension p : consultaPensionesResponse.getPensiones()) {
                    if (nss.equals(p.getIdNss()))
                        return Boolean.TRUE;
                }
            }

        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PerfilPersonaService.validaNSS(" + nss +
                ", " + perfilPersona.getNss() +
                ", " + perfilPersona.getCveCurp() +
                ") - ", e.getMessage());

            throw new CapacidadCreditoException(
                    CapacidadCreditoException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR);
        }

        return Boolean.FALSE;
    }

    @Override
    public Message<ConsultaCapacidadCredito> execute(Message<ConsultaCapacidadCredito> request)
        throws BusinessException {

        try {
            ConsultaCapacidadRequestAux consultaCapacidadRequest = null;
            String nss = null;
            Long sesion = null;

            if (
                !(
                    request != null &&
                    request.getPayload() != null &&
                    (consultaCapacidadRequest = request.getPayload().getRequest()) != null &&
                    consultaCapacidadRequest.getToken() != null &&
                    (nss = consultaCapacidadRequest.getNss()) != null &&
                    (sesion = consultaCapacidadRequest.getSesion()) != null &&
                    sesion > 0L
                )
            )
                throw new CapacidadCreditoException(CapacidadCreditoException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            PerfilPersona perfilPersona = perfilPersonaRepository.
                    buscarPerfilPersonaPorToken(consultaCapacidadRequest.getToken());

            boolean esPerfilValido = perfilPersona != null && perfilPersona.getCvePerfil() == 1;
            boolean sesionCoincide = perfilPersona != null && sesion.equals(perfilPersona.getNumSesion());
            boolean nssCoincide = perfilPersona != null && nss.equals(perfilPersona.getNss());

            if(
                !(esPerfilValido && sesionCoincide && (nssCoincide || this.validaNSS(nss, perfilPersona)))
            )
                throw new CapacidadCreditoException(CapacidadCreditoException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            return request;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
            "ERROR PerfilPersonaService.execute(" +
            request + ")", e);
        }

        throw new CapacidadCreditoException(CapacidadCreditoException.MENSAJE_DE_SOLICITUD_INCORRECTO);
    }
}
