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
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamosEnRecuperacionRQ;
import mx.gob.imss.dpes.interfaces.sipre.model.PrestamosEnRecuperacion;
import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author juan.garfias
 */
@Provider
public class PrestamosEnRecuperacionService extends
        ServiceDefinition<
        ConsultaPrestamosEnRecuperacionRQ, ConsultaPrestamosEnRecuperacionRQ> {

    @Inject
    @RestClient
    private SipreClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;
    
    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;

    @Override
    public Message<ConsultaPrestamosEnRecuperacionRQ> execute(Message<ConsultaPrestamosEnRecuperacionRQ> request) throws BusinessException {
        log.log(Level.INFO, "Consulta Prestamos en Recuperacion {0}", request.getPayload().getPrestamosEnRecuperacionRq());
        Response load = null;
        try {
            load = client.consultaPrestamosEnRecuperacion(request.getPayload().getPrestamosEnRecuperacionRq());
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getPrestamosEnRecuperacionRq(), load, 1L)));

        } catch (Exception e) {
            e.printStackTrace();
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getPrestamosEnRecuperacionRq(), e.getMessage(), 1L, true)));
            throw new UnknowException();
        }
        return response(load, request);

    }

    @Override
    protected Message<ConsultaPrestamosEnRecuperacionRQ> onOk(Response response, Message<ConsultaPrestamosEnRecuperacionRQ> request) {
        log.log(Level.INFO, "Mapeo Prestamos en Recuperacion {0}", response);

        PrestamosEnRecuperacion res = response.readEntity(PrestamosEnRecuperacion.class);
        request.getPayload().setPrestamosEnRecuperacionRs(res);

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getPrestamosEnRecuperacionRq(),
//                                    res,
//                                    endPoint+"sipre/ConsultaPrestamosEnRecuperacion",
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

}
