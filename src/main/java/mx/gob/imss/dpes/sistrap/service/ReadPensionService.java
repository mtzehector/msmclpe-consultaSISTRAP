package mx.gob.imss.dpes.sistrap.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.Message;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.sistrap.model.ConsultaPensiones;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionesResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequest;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pension;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import mx.gob.imss.dpes.support.util.ExceptionUtils;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class ReadPensionService extends ServiceDefinition<ConsultaPensiones, ConsultaPensiones> {

    @Inject
    @RestClient
    private SistrapClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SistrapClient/mp-rest/url")
    String endPoint;
    
    @Override
    public Message<ConsultaPensiones> execute(Message<ConsultaPensiones> request) throws BusinessException {

        CurpRequest curpRequest = new CurpRequest();
        curpRequest.setCurp(request.getPayload().getRequest().getCurp());
        log.log(Level.INFO, ">>>ReadPensionService Consulta a sistrap curp={0}", curpRequest.toString());
        Response load = null;
        try {
            load = client.consultaPensionGrupoFamiliar(curpRequest);

        } catch (RuntimeException e) {
            e.printStackTrace();
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(curpRequest, e.getMessage(), 1L, true)));
            log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: RuntimeException message=\n{0}", e.getMessage());
            ExceptionUtils.throwServiceException("SISTRAP");
        }
        ConsultaPensionesResponse consultaPensionesResponse = load.readEntity(ConsultaPensionesResponse.class);

        if (consultaPensionesResponse.getPensiones() != null) {
            for (Pension p : consultaPensionesResponse.getPensiones()) {
                p.setIdTipoPension(
                        p.getDesTipoPension().substring(0, 1).toUpperCase()
                        + p.getDesTipoPension().substring(1).toLowerCase()
                );
            }
        }
        String codigoErrorStr = consultaPensionesResponse.getCodigoError();
        String msgErrorStr = consultaPensionesResponse.getMensajeError();
        int codigoError = 0;
        try {
            codigoError = Integer.parseInt(codigoErrorStr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnknowException();
        }
        if (codigoError != 200) {
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    curpRequest,
//                                    consultaPensionesResponse,
//                                    endPoint+"sistrap/ConsultaPensionGrupoFamiliar",
//                                    false,null
//                            )
//                    )
//            );
            throw new VariableMessageException(msgErrorStr);
        }
        request.getPayload().setResponse(consultaPensionesResponse);
        return new Message<>(request.getPayload());
    }

    @Override
    protected Message<ConsultaPensiones> onOk(Response response, Message<ConsultaPensiones> request) {

        ConsultaPensionesResponse consultaPensionesResponse = response.readEntity(ConsultaPensionesResponse.class);

        CurpRequest curpRequest = new CurpRequest();
        curpRequest.setCurp(request.getPayload().getRequest().getCurp());
        log.log(Level.INFO, ">>>ReadPensionService Consulta a sistrap curp={0}", curpRequest.toString());

//        try {
//            
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    curpRequest,
//                                    consultaPensionesResponse,
//                                    endPoint+"sistrap/ConsultaPensionGrupoFamiliar",
//                                    true,null
//                                    
//                            )
//                    )
//            );
//            
//        } catch (BusinessException ex) {
//            Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//        }

        log.log(Level.INFO, ">>> Respuesta sistrap {0}", consultaPensionesResponse);
        request.getPayload().setResponse(consultaPensionesResponse);
        return request;

    }
}
