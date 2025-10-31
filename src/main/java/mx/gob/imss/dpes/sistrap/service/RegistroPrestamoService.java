/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoRQ;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoResponse;
import mx.gob.imss.dpes.sistrap.exception.PrestamoException;
import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author juan.garfias
 */
@Provider
public class RegistroPrestamoService extends
        ServiceDefinition<
        RegistroPrestamoRQ, RegistroPrestamoRQ> {

    @Inject
    @RestClient
    private SipreClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<RegistroPrestamoRQ> execute(Message<RegistroPrestamoRQ> request) throws BusinessException {
        RegistroPrestamoResponse registroPrestamoResponse = null;
        try {
            Response load = client.registroPrestamo(request.getPayload().getRegistroPrestamoRequest());
            if (load != null && load.getStatus() == 200 &&
                    (registroPrestamoResponse = load.readEntity(RegistroPrestamoResponse.class)).
                            getCodigoError().equals("200")) {
                request.getPayload().setRegistroPrestamoResponse(registroPrestamoResponse);
                return request;
            }
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRegistroPrestamoRequest(), load, 1L)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR RegistroPrestamoService.execute() - request = [" + request + "]," +
                    " registroPrestamoResponse = [" + registroPrestamoResponse + "]", e);
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRegistroPrestamoRequest(), e.getMessage(), 1L, true)));
        }
        throw new PrestamoException(PrestamoException.ERROR_SISTRAP);
    }

    /*
    @Override
    protected Message<RegistroPrestamoRQ> onOk(Response response, Message<RegistroPrestamoRQ> request) {
        log.log(Level.INFO, "Mapeo Prestamos en Recuperacion {0}", response);

        RegistroPrestamoResponse res = response.readEntity(RegistroPrestamoResponse.class);
        request.getPayload().setRegistroPrestamoResponse(res);
//
//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getRegistroPrestamoRequest(),
//                                    res,
//                                    endPoint+"sipre/RegistroPrestamo",
//                                    true,null
//                            )
//                    )
//            );
//
//        } catch (BusinessException ex) {
//            Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return request;
    }
*/
}