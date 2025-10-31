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
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionadoResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionesResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pension;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pensionado;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.sistrap.exception.ConsultaPensionadoException;
import mx.gob.imss.dpes.sistrap.model.ConsultaTitularYPensionado;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author juan.garfias
 */
@Provider
public class ConsultaPensionadoSistrapService extends ServiceDefinition<ConsultaTitularYPensionado, ConsultaTitularYPensionado> {

    @Inject
    SistrapClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SistrapClient/mp-rest/url")
    String endPoint;

    @Override
    public Message<ConsultaTitularYPensionado> execute(Message<ConsultaTitularYPensionado> message) throws BusinessException {
        log.log(Level.INFO, ">>> consultaPensionadoPorCurp.execute getRequest consultaPensionGrupoFamiliar {0}:", message.getPayload().getRequest());
        Response responseCPGF = client.consultaPensionGrupoFamiliar(message.getPayload().getRequest());

        if (responseCPGF.getStatus() == 200) {
            ConsultaPensionesResponse consultaPensionesResponse = responseCPGF.readEntity(ConsultaPensionesResponse.class);
            log.log(Level.INFO, ">>> consultaPensionesResponse {0}:", consultaPensionesResponse);

//            try {
//
//                createBitacoraService.createInterfaz(
//                        new Message<>(
//                                ServiceLogger.creaObjetoBI(
//                                        message.getPayload().getRequest(),
//                                        consultaPensionesResponse,
//                                        endPoint+"sistrap/ConsultaPensionGrupoFamiliar",
//                                        true,null
//                                )
//                        )
//                );
//
//            } catch (BusinessException ex) {
//                Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//            }
            if (consultaPensionesResponse.getCodigoError().equals("200")) {
                //log.log(Level.INFO, ">>> consultaPensionesResponse GET 0 {0}:", consultaPensionesResponse.getPensiones().get(0));
                Boolean flagPension = false;
                String grupoFamiliar = null;
                for (Pension c : consultaPensionesResponse.getPensiones()) {
                    if (message.getPayload().getNss().equals(c.getIdNss())) {
                        flagPension = Boolean.TRUE;
                        grupoFamiliar = c.getIdGrupoFamiliar();
                    }
                }
                Response responseCP = null;
                if (flagPension) {
                    PensionadoRequest p = new PensionadoRequest();
                    p.setIdGrupoFamiliar(grupoFamiliar);
                    p.setIdNss(message.getPayload().getNss());
                    responseCP = client.consultaPensionado(p);
                    log.log(Level.INFO, ">>> responseCP {0}:", responseCP.getStatus());
                } else {
                    Pensionado p = new Pensionado();
                    p.setCodigoError("406");
                    p.setMensajeError("No coinciden los nss del servicio vs el capturado");
                    log.log(Level.INFO, ">>> ERROR {0}:", message.getPayload());
                    return message;
                }

                if (responseCP.getStatus() == 200) {
                    ConsultaPensionadoResponse consultaPensionadoResponse = responseCP.readEntity(ConsultaPensionadoResponse.class);

//                    try {
//
//                        createBitacoraService.createInterfaz(
//                                new Message<>(
//                                        ServiceLogger.creaObjetoBI(
//                                                new PensionadoRequest(
//                                                        consultaPensionesResponse.getPensiones().get(0).getIdNss(),
//                                                        consultaPensionesResponse.getPensiones().get(0).getIdGrupoFamiliar()),
//                                                consultaPensionadoResponse,
//                                                endPoint+"sistrap/ConsultaPensionado",
//                                                true,null
//                                        )
//                                )
//                        );
//
//                    } catch (BusinessException ex) {
//                        Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    consultaPensionadoResponse.setCodigoError(consultaPensionesResponse.getCodigoError());
                    consultaPensionadoResponse.setMensajeError(consultaPensionesResponse.getMensajeError());
                    consultaPensionadoResponse.getPensionado().setCodigoError(consultaPensionesResponse.getCodigoError());
                    consultaPensionadoResponse.getPensionado().setMensajeError(consultaPensionesResponse.getMensajeError());

                    log.log(Level.INFO, ">>> consultaPensionadoResponse {0}:", consultaPensionadoResponse);
                    message.getPayload().setResponse(consultaPensionadoResponse);
                    return message;
                }

            } else {
                ConsultaPensionadoResponse cpr = new ConsultaPensionadoResponse();
                cpr.setCodigoError(consultaPensionesResponse.getCodigoError());
                cpr.setMensajeError(consultaPensionesResponse.getMensajeError());
                message.getPayload().setResponse(cpr);
                Pensionado p = new Pensionado();
                p.setCodigoError(cpr.getCodigoError());
                p.setMensajeError(cpr.getMensajeError());
                message.getPayload().getResponse().setPensionado(p);
                log.log(Level.INFO, ">>> ERROR {0}:", message.getPayload());

                return message;
            }
        }
        throw new ConsultaPensionadoException();
    }

}
