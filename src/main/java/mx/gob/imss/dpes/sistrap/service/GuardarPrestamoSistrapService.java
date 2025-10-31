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
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoResponse;
import mx.gob.imss.dpes.sistrap.exception.GuardarPrestamoException;
import mx.gob.imss.dpes.sistrap.model.CrearRegistroPrestamo;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author eduardo.loyo
 */
@Provider
public class GuardarPrestamoSistrapService extends ServiceDefinition<CrearRegistroPrestamo, CrearRegistroPrestamo> {

    @Inject
    @RestClient
    private SipreClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;
        
    @Override
    public Message<CrearRegistroPrestamo> execute(Message<CrearRegistroPrestamo> message) throws BusinessException {
        log.log(Level.INFO, ">>> GuardarPrestamoSistrapService.execute Sipre RegistroPrestamo message.getPayload().getRequest()={0}", message.getPayload().getRequest());

        try {
            Response response = client.registroPrestamo(message.getPayload().getRequest());
            log.log(Level.INFO, ">>> GuardarPrestamoSistrapService.execute Sipre Response: {0}", response);
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(message.getPayload().getRequest(), response, 1L)));
            return response(response, message);
        } catch (Exception e) {
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(message.getPayload().getRequest(), e.getMessage(), 1L, true)));
            log.log(Level.SEVERE, ">>> GuardarPrestamoSistrapService.execute Sipre Exception:{0}", e.getMessage());
            message.getHeader().setStatus(ServiceStatusEnum.ALTERNO);
            message.getHeader().setException(new GuardarPrestamoException());
            return message;
        }

    }

    @Override
    protected Message<CrearRegistroPrestamo> onOk(Response response, Message<CrearRegistroPrestamo> message) {

        RegistroPrestamoResponse res = response.readEntity(RegistroPrestamoResponse.class);
        message.getPayload().setResponse(res);

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    message.getPayload().getRequest(),
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

        return message;
    }
}
