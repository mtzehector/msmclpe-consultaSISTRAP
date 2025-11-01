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
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaIncidenciaTitularGrupoResponse;
import mx.gob.imss.dpes.sistrap.model.ConsultaIncidenciaTitularGrupo;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.loyo
 */
@Provider
public class ReadIncidenciaTitularGrupoService extends ServiceDefinition<ConsultaIncidenciaTitularGrupo, ConsultaIncidenciaTitularGrupo> {

    @Inject
    @RestClient
    private SistrapClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SistrapClient/mp-rest/url")
    String endPoint;    
    
    @Override
    public Message<ConsultaIncidenciaTitularGrupo> execute(Message<ConsultaIncidenciaTitularGrupo> request) throws BusinessException {
        log.log(Level.INFO, "Consulta a sistrap {0}", request.getPayload().getRequest());
        Response load = null;
        try {
            load = client.consultaIncidenciaTitularGrupo(request.getPayload().getRequest());
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRequest(), load, 1L)));

        } catch (Exception e) {
            e.printStackTrace();
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRequest(), e.getMessage(), 1L, true)));
            throw new UnknowException();
        }
        return response(load, request);
    }

    @Override
    protected Message<ConsultaIncidenciaTitularGrupo> onOk(Response response, Message<ConsultaIncidenciaTitularGrupo> request) {
        ConsultaIncidenciaTitularGrupoResponse consultaIncidenciaTitularGrupoResponse = response.readEntity(ConsultaIncidenciaTitularGrupoResponse.class);
        log.log(Level.INFO, "Respuesta sistrap {0}", consultaIncidenciaTitularGrupoResponse);
        request.getPayload().setResponse(consultaIncidenciaTitularGrupoResponse);

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getRequest(),
//                                    consultaIncidenciaTitularGrupoResponse,
//                                    endPoint+"sistrap/ConsultaIncidenciaTitularGrupo",
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
