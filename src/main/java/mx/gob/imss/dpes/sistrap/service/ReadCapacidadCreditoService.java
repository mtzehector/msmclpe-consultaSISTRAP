package mx.gob.imss.dpes.sistrap.service;

import java.util.Date;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.common.enums.TipoServicioEnum;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.bitacora.model.BitacoraInterfaz;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadRequestAux;
import mx.gob.imss.dpes.sistrap.exception.CapacidadCreditoException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadResponse;
import mx.gob.imss.dpes.sistrap.model.ConsultaCapacidadCredito;
import mx.gob.imss.dpes.sistrap.restclient.SipreClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class ReadCapacidadCreditoService extends ServiceDefinition<ConsultaCapacidadCredito, ConsultaCapacidadCredito> {

    @Inject
    @RestClient
    private SipreClient client;

    @Inject
    private CreateBitacoraService createBitacoraService;

    @Inject
    @ConfigProperty(name = "mx.gob.imss.dpes.sistrap.restclient.SipreClient/mp-rest/url")
    private String url;

    private static final String SERVICIO_CAPACIDAD_CREDITO = "/sipre/ConsultaCapacidad";

    private String obtieneSolicitudCapacidadCredito(ConsultaCapacidadRequestAux consultaCapacidadRequest) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"grupoFamiliar\": \"").
            append(consultaCapacidadRequest.getGrupoFamiliar()).
            append("\", \"nss\": \"").
            append(consultaCapacidadRequest.getNss()).
            append("\"}");

        return sb.toString();
    }

    private String obtieneRespuestaCapacidadCredito(ConsultaCapacidadResponse consultaCapacidadResponse) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"capacidad\": ");
        if(consultaCapacidadResponse.getCapacidad() == null)
            sb.append("null");
        else {
            sb.append("{\"nss\": \"").
                append(consultaCapacidadResponse.getCapacidad().getNss()).
                append("\", \"impCapacidadFija\": ").
                append(consultaCapacidadResponse.getCapacidad().getImpCapacidadFija()).
                append(", \"impCapacidadVariable\": ").
                append(consultaCapacidadResponse.getCapacidad().getImpCapacidadVariable()).
                append(", \"impCapacidadTotal\": ").
                append(consultaCapacidadResponse.getCapacidad().getImpCapacidadTotal()).
                append("}");
            ;
        }

        sb.append(", \"codigoError\": ").
            append(consultaCapacidadResponse.getCodigoError()).
            append(", \"mensajeError\": \"").
            append(consultaCapacidadResponse.getMensajeError()).
            append("\"").
            append("}");

        return sb.toString();
    }

    @Override
    public Message<ConsultaCapacidadCredito> execute(Message<ConsultaCapacidadCredito> consultaCapacidadCreditoMessage)
        throws BusinessException {

        boolean exito = false;
        Long tiempoInicial = 0L;
        Long tiempoEjecucion = 0L;
        BusinessException businessException = null;
        ConsultaCapacidadRequestAux consultaCapacidadRequest = null;
        ConsultaCapacidadResponse consultaCapacidadResponse = null;

        try {
            consultaCapacidadRequest = consultaCapacidadCreditoMessage.getPayload().getRequest();
            ConsultaCapacidadRequest ccr = new ConsultaCapacidadRequest();

            ccr.setGrupoFamiliar(consultaCapacidadRequest.getGrupoFamiliar());
            ccr.setNss(consultaCapacidadRequest.getNss());

            tiempoInicial = new Date().getTime();
            Response respuestaConsultaCapacidad = client.consultaCapacidad(ccr);
            tiempoEjecucion = new Date().getTime() - tiempoInicial;

            consultaCapacidadResponse =
                respuestaConsultaCapacidad.readEntity(ConsultaCapacidadResponse.class);

            if (!(consultaCapacidadResponse != null && consultaCapacidadResponse.getCodigoError() != null))
                throw new CapacidadCreditoException(
                        CapacidadCreditoException.ERROR_EN_RESPUESTA_SERVICIO_CONSULTA_CAPACIDAD);

            exito = true;

            if (Integer.parseInt(consultaCapacidadResponse.getCodigoError()) !=
                    Response.Status.OK.getStatusCode())
                throw new VariableMessageException(consultaCapacidadResponse.getMensajeError());

            consultaCapacidadCreditoMessage.getPayload().setResponse(consultaCapacidadResponse);
        } catch (BusinessException e) {
            log.log(Level.SEVERE, "ERROR ReadCapacidadCreditoService.execute - consultaCapacidadCreditoMessage = [" +
                    consultaCapacidadCreditoMessage + "]", e);
            tiempoEjecucion = new Date().getTime() - tiempoInicial;
            businessException = e;
        } catch (Exception e) {
            tiempoEjecucion = new Date().getTime() - tiempoInicial;
            log.log(Level.SEVERE, "ERROR ReadCapacidadCreditoService.execute - consultaCapacidadCreditoMessage = [" +
                    consultaCapacidadCreditoMessage + "]", e);
            businessException = new CapacidadCreditoException(CapacidadCreditoException.
                ERROR_EN_SERVICIO_CONSULTA_CAPACIDAD_CREDITO);
        } finally {
            BitacoraInterfaz bitacoraInterfaz = new BitacoraInterfaz();
            bitacoraInterfaz.setIdTipoServicio(TipoServicioEnum.SISTRAP.getId());
            bitacoraInterfaz.setExito(exito ? 1 : 0);
            bitacoraInterfaz.setSesion(consultaCapacidadRequest.getSesion() != null ?
                consultaCapacidadRequest.getSesion() : 0L);
            bitacoraInterfaz.setEndpoint(url + SERVICIO_CAPACIDAD_CREDITO);
            bitacoraInterfaz.setDescRequest(obtieneSolicitudCapacidadCredito(consultaCapacidadRequest));
            bitacoraInterfaz.setReponseEndpoint(exito ?
                obtieneRespuestaCapacidadCredito(consultaCapacidadResponse) : "");
            bitacoraInterfaz.setNumTiempoResp(tiempoEjecucion);
            bitacoraInterfaz.setAltaRegistro(tiempoInicial != null ? new Date(tiempoInicial) : new Date());
            bitacoraInterfaz.setToken(consultaCapacidadRequest.getToken());


            try {
                createBitacoraService.execute(new Message<>(bitacoraInterfaz));
            } catch (BusinessException bei) {
                //Este escenario nunca debería pasar
                log.log(Level.WARNING,
                    "ERROR ReadCapacidadCreditoService.execute error al guardar la bitacoraInterfaz = [" +
                        bitacoraInterfaz + "]", bei);
            }
        }

        if (businessException != null)
            throw businessException;

        return consultaCapacidadCreditoMessage;
    }

/*
    @Override
    protected Message<ConsultaCapacidadCredito> onOk(Response response, Message<ConsultaCapacidadCredito> request) {
        ConsultaCapacidadResponse readEntity
                = response.readEntity(ConsultaCapacidadResponse.class);

//        try {
//            log.log(Level.INFO, "Consulta capacidad createBitacoraService.createInterfaz  request.getPayload().getRequest() : {0}", request.getPayload().getRequest());
//
//            createBitacoraService.createInterfaz(
//                    new Message<>(
//                            ServiceLogger.creaObjetoBI(
//                                    request.getPayload().getRequest(),
//                                    readEntity,
//                                    endPoint + "sipre/ConsultaCapacidad",
//                                    true,
//                                    request.getPayload().getRequest().getSesion()
//                            )
//                    )
//            );
//        } catch (BusinessException ex) {
//            Logger.getLogger(ReadCapacidadCreditoService.class.getName()).log(Level.SEVERE, null, ex);
//        }

        log.log(Level.INFO, "Consulta capacidad {0}", readEntity);
        request.getPayload().setResponse(readEntity);
        return request;
    }
*/

}
