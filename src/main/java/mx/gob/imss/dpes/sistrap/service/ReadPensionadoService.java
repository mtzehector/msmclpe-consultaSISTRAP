package mx.gob.imss.dpes.sistrap.service;

import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.common.exception.BadRequestException;
import mx.gob.imss.dpes.common.exception.BusinessException;
//import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.sistrap.exception.PensionadoException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.sistrap.model.ConsultaPensionado;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionadoResponse;
//import mx.gob.imss.dpes.support.util.ServiceLogger;
import mx.gob.imss.dpes.sistrap.restclient.SistrapClient;
//import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class ReadPensionadoService extends ServiceDefinition<ConsultaPensionado, ConsultaPensionado> {

    @Inject
    @RestClient
    private SistrapClient client;

    //@Inject
    //CreateBitacoraService createBitacoraService;

    //@Inject
    //@ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    //String endPoint;
    
    @Override
    public Message<ConsultaPensionado> execute(Message<ConsultaPensionado> request) throws BusinessException {
        //log.log(Level.INFO, "Consulta a sistrap {0}", request.getPayload().getRequest());
        try {
            Response response = client.consultaPensionado(request.getPayload().getRequest());
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRequest(), response, 1L)));

            if (response.getStatus() != Response.Status.OK.getStatusCode())
                throw new PensionadoException(
                    PensionadoException.ERROR_EN_RESPUESTA_SERVICIO_SISTRAP_CONSULTA_PENSIONADO);

            request.getPayload().setResponse(this.procesaConsultaPensionado(response));
            return request;
        } catch (VariableMessageException e) {
            throw e;
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.log(Level.SEVERE, "ERROR ReadPensionadoService.execute = {0}", e);
            //createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload().getRequest(), e.getMessage(), 1L, true)));
            return response(null, ServiceStatusEnum.EXCEPCION,
                new PensionadoException(PensionadoException.ERROR_EN_SERVICIO_SISTRAP_CONSULTA_PENSIONADO), null);
        }
    }

/*
    @Override
    protected Message<ConsultaPensionado> onOk(Response response, Message<ConsultaPensionado> request) {
        ConsultaPensionadoResponse consultaPensionadoResponse = response.readEntity(ConsultaPensionadoResponse.class);

//        try {
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getRequest(),
//                                    consultaPensionadoResponse,
//                                    endPoint+"sistrap/ConsultaPensionado",
//                                    true,null
//                            )
//                    )
//            );
//
//        } catch (BusinessException ex) {
//            Logger.getLogger(ReadPensionService.class.getName()).log(Level.SEVERE, null, ex);
//        }

        consultaPensionadoResponse.getPensionado().setDescEntidadFederativa(
                consultaPensionadoResponse.getPensionado().getDescDelegacion().substring(0, 1).toUpperCase()
                + consultaPensionadoResponse.getPensionado().getDescDelegacion().substring(1).toLowerCase()
        );

        //log.log(Level.INFO, "Respuesta sistrap {0}", consultaPensionadoResponse);
        consultaPensionadoResponse.getPensionado().setFecNacimiento(toFormatedDate(
                consultaPensionadoResponse.getPensionado().getFecNacimiento()));
        request.getPayload().setResponse(consultaPensionadoResponse);
        return request;
    }
*/
    private ConsultaPensionadoResponse procesaConsultaPensionado(Response response) throws BusinessException {
        try {
            ConsultaPensionadoResponse consultaPensionadoResponse =
                response.readEntity(ConsultaPensionadoResponse.class);

            if (!(consultaPensionadoResponse != null && consultaPensionadoResponse.getCodigoError() != null))
                throw new PensionadoException(
                    PensionadoException.ERROR_EN_RESPUESTA_SERVICIO_SISTRAP_CONSULTA_PENSIONADO);

            if (Integer.parseInt(consultaPensionadoResponse.getCodigoError()) !=
                    Response.Status.OK.getStatusCode())
                throw new VariableMessageException(consultaPensionadoResponse.getMensajeError());

            consultaPensionadoResponse.getPensionado().setDescEntidadFederativa(
                consultaPensionadoResponse.getPensionado().getDescDelegacion().substring(0, 1).toUpperCase()
                + consultaPensionadoResponse.getPensionado().getDescDelegacion().substring(1).
                    toLowerCase()
            );

            consultaPensionadoResponse.getPensionado().setFecNacimiento(
                toFormatedDate(consultaPensionadoResponse.getPensionado().getFecNacimiento()));

            return consultaPensionadoResponse;
        } catch (VariableMessageException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReadPensionadoService.procesaConsultaPensionado = {0}", e);
        }

        throw new PensionadoException(PensionadoException.ERROR_EN_SERVICIO_SISTRAP_CONSULTA_PENSIONADO);
    }
}
