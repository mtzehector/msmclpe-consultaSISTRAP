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
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentoEmitidoResponse;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentosEmitidos;
import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class ConsultaPrestamosConDescuentoService extends ServiceDefinition<ConsultaDescuentosEmitidos, ConsultaDescuentosEmitidos> {

    @Inject
    @RestClient
    private SipreClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<ConsultaDescuentosEmitidos> execute(Message<ConsultaDescuentosEmitidos> request) throws BusinessException {
        log.log(Level.INFO, ">>>consultaSISTRAP|ConsultaPrestamosConDescuentoService|execute {0}",
                request.getPayload().getDescuentosRequest());

        Response response = null;

        try {
            response = client.consultaPrestamosEnCursoPorMovimiento(
                    request.getPayload().getDescuentosRequest()
            );
        } catch (Exception e) {
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getDescuentosRequest(), e.getMessage(), 1L, true)));
            throw new UnknowException();
        }
        return response(response, request);
    }

    @Override
    protected Message<ConsultaDescuentosEmitidos> onOk(Response response,
            Message<ConsultaDescuentosEmitidos> request) {

        ConsultaDescuentoEmitidoResponse res = response.readEntity(ConsultaDescuentoEmitidoResponse.class);

        request.getPayload().setDescuentosResponse(
                res
        );

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getDescuentosRequest(),
//                                    res,
//                                    endPoint+"sipre/ConsultaPrestamosEnCursoPorMovimiento",
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
