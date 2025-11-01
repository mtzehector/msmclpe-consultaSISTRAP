package mx.gob.imss.dpes.sistrap.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.Message;

import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.sistrap.exception.PensionException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.sistrap.model.ConsultaPensiones;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionesResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequest;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class ReadPensionRegistroPensionadoService extends ServiceDefinition<ConsultaPensiones, ConsultaPensiones> {

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
        try {
            CurpRequest curpRequest = new CurpRequest();
            curpRequest.setCurp(request.getPayload().getRequest().getCurp());
            //log.log(Level.INFO, ">>>ReadPensionService Consulta a sistrap curp={0}", curpRequest.toString());

            Response responseConsultaPensionGrupoFamiliar = client.consultaPensionGrupoFamiliar(curpRequest);

            if (responseConsultaPensionGrupoFamiliar.getStatus() != Response.Status.OK.getStatusCode())
                throw new PensionException(
                    PensionException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR);

            request.getPayload().setResponse(
                this.procesaRespuestaConsultaPensionGrupoFamiliar(responseConsultaPensionGrupoFamiliar));

            return new Message<>(request.getPayload());
        } catch (VariableMessageException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReadPensionRegistroPensionadoService.execute", e);
            return response(null, ServiceStatusEnum.EXCEPCION,
                new PensionException(PensionException.ERROR_EN_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR), null);
        }
    }

    @Override
    protected Message<ConsultaPensiones> onOk(Response response, Message<ConsultaPensiones> request) {
        ConsultaPensionesResponse consultaPensionesResponse = response.readEntity(ConsultaPensionesResponse.class);

        CurpRequest curpRequest = new CurpRequest();
        curpRequest.setCurp(request.getPayload().getRequest().getCurp());
        //log.log(Level.INFO, ">>>ReadPensionService Consulta a sistrap curp={0}", curpRequest.toString());

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    curpRequest,
//                                    consultaPensionesResponse,
//                                    endPoint+"sistrap/ConsultaPensionGrupoFamiliar",
//                                    true,
//                                    request.getPayload().getSesion()
//                            )
//                    )
//            );
//
//        } catch (BusinessException ex) {
//            Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//        }

        request.getPayload().setResponse(consultaPensionesResponse);
        return request;
    }

    private ConsultaPensionesResponse procesaRespuestaConsultaPensionGrupoFamiliar(Response response)
        throws BusinessException {

        try {
            ConsultaPensionesResponse consultaPensionesResponse =
                response.readEntity(ConsultaPensionesResponse.class);

            if (!(consultaPensionesResponse != null && consultaPensionesResponse.getCodigoError() != null))
                throw new PensionException(
                    PensionException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR);

            if (Integer.parseInt(consultaPensionesResponse.getCodigoError()) !=
                    Response.Status.OK.getStatusCode())
                throw new VariableMessageException(consultaPensionesResponse.getMensajeError());

            return consultaPensionesResponse;
        } catch (VariableMessageException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR ReadPensionRegistroPensionadoService.procesaRespuestaConsultaPensiones ", e);
        }

        throw new PensionException(PensionException.ERROR_EN_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR);
    }
}
