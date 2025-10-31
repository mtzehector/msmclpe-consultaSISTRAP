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
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sistrap.model.ClabeNominaAnterior;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaClabeNominaAnteriorResponse;
import mx.gob.imss.dpes.sistrap.exception.PrestamoException;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.loyo
 */
@Provider
public class GetCuentaClabeService extends ServiceDefinition<ClabeNominaAnterior, ConsultaClabeNominaAnteriorResponse> {

    @Inject
    @RestClient
    private SistrapClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SistrapClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<ConsultaClabeNominaAnteriorResponse> execute(Message<ClabeNominaAnterior> request) throws BusinessException {

        try {
            log.log(Level.INFO, ">>>>Envio a sistrap= {0}", request.getPayload());

            Response load = client.consultaClabeNominaAnteriorResponse(request.getPayload());
            log.log(Level.INFO, ">>>>Respuesta sistrap= {0}", load);
            if (load.getStatus() == 200) {
                ConsultaClabeNominaAnteriorResponse clabe = load.readEntity(ConsultaClabeNominaAnteriorResponse.class);
                log.log(Level.INFO, ">>>>Respuesta sistrap.  clabe= {0}", clabe);
                log.log(Level.INFO, ">>>>Respuesta sistrap.  clabe.getCodigoError= {0}", clabe.getCodigoError());
                //int codigoError = Integer.parseInt(clabe.getCodigoError());
                switch (clabe.getCodigoError().trim()) {
                    case "200":
                        
//                        try {
//
//                            createBitacoraService.createInterfaz(
//                                    new Message<>(
//                                            ServiceLogger.creaObjetoBI(
//                                                    request.getPayload(),
//                                                    clabe,
//                                                    endPoint+"sistrap/ConsultaCLABENominaAnterior",
//                                                    true,null
//                                            )
//                                    )
//                            );
//
//                        } catch (BusinessException ex) {
//                            Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        
                        return new Message<>(clabe);

                    case "402":
                        String message = "SISTRAP ConsultaCLABENominaAnterior sin resultado para: CLABE=" + request.getPayload().getClabe() + " NSS=" + request.getPayload().getIdNss() + " PeriodoNomina=" + request.getPayload().getFecPeriodoNomina();
                        //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload(), message, 1L, true)));
                        throw new VariableMessageException(message);

                    default:
                        String messageOther = "SISTRAP ConsultaCLABENominaAnterior ERROR= " + clabe.getMensajeError() + " para: CLABE=" + request.getPayload().getClabe() + " NSS=" + request.getPayload().getIdNss() + " PeriodoNomina=" + request.getPayload().getFecPeriodoNomina();
                        //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload(), messageOther, 1L, true)));
                        throw new VariableMessageException(messageOther);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload(), e.getMessage(), 1L, true)));

        }
        return response(null, ServiceStatusEnum.EXCEPCION, new PrestamoException(PrestamoException.NOFOUND), null);
    }

}
