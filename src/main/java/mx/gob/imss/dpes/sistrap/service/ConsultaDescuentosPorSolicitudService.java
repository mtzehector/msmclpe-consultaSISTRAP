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
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentosPorSolicitudResponse;
import mx.gob.imss.dpes.sistrap.model.ConsultaDescuentos;
import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author juan.garfias
 */
@Provider
public class ConsultaDescuentosPorSolicitudService extends ServiceDefinition<ConsultaDescuentos, ConsultaDescuentos> {

    @Inject
    @RestClient
    private SipreClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<ConsultaDescuentos> execute(Message<ConsultaDescuentos> message) throws BusinessException {
        log.log(Level.INFO, "Sipre ConsultaPrestamo {0}", message.getPayload().getRequest());
        Response response = null;
        try {
            response = client.consultaDescuentosPorSolicitud(message.getPayload().getRequest());
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(message.getPayload().getRequest(), response, 1L)));

        } catch (Exception e) {
            e.printStackTrace();
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(message.getPayload().getRequest(), e.getMessage(), 1L, true)));
            throw new UnknowException();
        }
        return response(response, message);
    }

    @Override
    protected Message<ConsultaDescuentos> onOk(Response response, Message<ConsultaDescuentos> message) {

        ConsultaDescuentosPorSolicitudResponse res = response.readEntity(ConsultaDescuentosPorSolicitudResponse.class);

        message.getPayload().setResponse(res);
        log.log(Level.INFO, "Sipre ConsultaPrestamo {0}", message.getPayload().getResponse());
//
//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    message.getPayload().getRequest(),
//                                    res,
//                                    endPoint+"sipre/ConsultaDescuentosPorSolicitud",
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
