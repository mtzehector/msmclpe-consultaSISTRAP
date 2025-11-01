/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.service;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.bitacora.model.BitacoraInterfaz;
import mx.gob.imss.dpes.interfaces.sistrap.model.AltModEntFinancierasRQ;
import mx.gob.imss.dpes.interfaces.sistrap.model.AltModEntFinancierasResponse;
import mx.gob.imss.dpes.sistrap.exception.CapacidadCreditoException;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author juan.garfias
 */
@Provider
public class AltModEntFinancierasService extends
        ServiceDefinition<
        AltModEntFinancierasRQ, AltModEntFinancierasRQ> {

    @Inject
    @RestClient
    private SistrapClient client;

    @Inject
    CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SistrapClient/mp-rest/url")
    String endPoint;

    @Override
    public Message<AltModEntFinancierasRQ> execute(Message<AltModEntFinancierasRQ> request) throws BusinessException {
        try {
            log.log(Level.INFO, "INSER-UPDATE de Entidad Financiera {0}", request.getPayload().getAltModEntFinancierasRequest());

            Response load = client.altaModificacionEntidadesFinancieras(request.getPayload().getAltModEntFinancierasRequest());

            return response(load, request);
        } catch (Exception e) {
            log.log(Level.WARNING, "AltModEntFinancierasService.execute - request = [" + request+ "]", e);
        }

        throw new CapacidadCreditoException(
                CapacidadCreditoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO);
    }

    @Override
    protected Message<AltModEntFinancierasRQ> onOk(Response response, Message<AltModEntFinancierasRQ> request) {
        try {
            log.log(Level.INFO, "Mapeo respuesta alta-modificacion Entidad Financiera {0}", response);

            AltModEntFinancierasResponse res = response.readEntity(AltModEntFinancierasResponse.class);

            request.getPayload().setAltModEntFinancierasResponse(res);

            BitacoraInterfaz bitacoraInterfaz = ServiceLogger.creaObjetoBI(
                request.getPayload().getAltModEntFinancierasRequest(),
                res,
                endPoint+"sistrap/AltModEntFinancieras",
                true,
                null
            );
            bitacoraInterfaz.setAltaRegistro(new Date());

            createBitacoraService.execute(new Message<>(bitacoraInterfaz));
        } catch (BusinessException ex) {
            //Logger.getLogger(AltModEntFinancierasService.class.getName()).log(Level.SEVERE, null, ex);
            log.log(Level.WARNING, "AltModEntFinancierasService.onOk - request = [" + request+ "]", ex);
        }

        return request;
    }

}
