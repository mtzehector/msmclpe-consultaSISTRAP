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
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaTitularGrupoResponse;
import mx.gob.imss.dpes.sistrap.model.ConsultaTitularGrupo;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.loyo
 */
@Provider
public class ReadTitularGrupoService extends ServiceDefinition<ConsultaTitularGrupo, ConsultaTitularGrupo> {

    @Inject
    @RestClient
    private SistrapClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<ConsultaTitularGrupo> execute(Message<ConsultaTitularGrupo> request) throws BusinessException {
        log.log(Level.INFO, "Consulta a sistrap {0}", request.getPayload().getRequest());
        Response load = null;
        try {
            load = client.consultaTitularGrupo(request.getPayload().getRequest());

        } catch (Exception e) {
            e.printStackTrace();
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRequest(), e.getMessage(), 1L, true)));
            throw new UnknowException();
        }
        return response(load, request);
    }

    @Override
    protected Message<ConsultaTitularGrupo> onOk(Response response, Message<ConsultaTitularGrupo> request) {
        ConsultaTitularGrupoResponse consultaTitularGrupoResponse = response.readEntity(ConsultaTitularGrupoResponse.class);
        log.log(Level.INFO, "Respuesta sistrap {0}", consultaTitularGrupoResponse);
        request.getPayload().setResponse(consultaTitularGrupoResponse);

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getRequest(),
//                                    consultaTitularGrupoResponse,
//                                    endPoint+"sistrap/ConsultaTitularGrupo",
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
