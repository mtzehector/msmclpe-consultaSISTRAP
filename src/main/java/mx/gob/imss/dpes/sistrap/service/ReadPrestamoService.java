package mx.gob.imss.dpes.sistrap.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamoResponse;
import mx.gob.imss.dpes.sistrap.model.ConsultaPrestamo;

import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class ReadPrestamoService extends ServiceDefinition<ConsultaPrestamo, ConsultaPrestamo> {

    @Inject
    @RestClient
    private SipreClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<ConsultaPrestamo> execute(Message<ConsultaPrestamo> message) throws BusinessException {
        //log.log(Level.INFO, "Sipre ConsultaPrestamo {0}", message.getPayload().getRequest());
        Response response = null;
        try {
            response = client.consultaPrestamo(message.getPayload().getRequest());
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(message.getPayload().getRequest(), response, 1L)));

        } catch (Exception e) {
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(message.getPayload().getRequest(), e.getMessage(), 1L, true)));
            throw new UnknowException();
        }
        return response(response, message);
    }

    @Override
    protected Message<ConsultaPrestamo> onOk(Response response, Message<ConsultaPrestamo> message) {

        ConsultaPrestamoResponse res = response.readEntity(ConsultaPrestamoResponse.class);
        message.getPayload().setResponse(res);

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    message.getPayload().getRequest(),
//                                    res,
//                                    endPoint+"sipre/ConsultaPrestamo",
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
