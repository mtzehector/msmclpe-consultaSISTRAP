package mx.gob.imss.dpes.sistrap.endpoint;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.PartialContentFlowException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularResponse;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstatusRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstatusResponse;
import mx.gob.imss.dpes.sistrap.exception.ReinstalacionPrestamoException;
import mx.gob.imss.dpes.sistrap.service.ReinstalacionPrestamoDatosTitularService;
import mx.gob.imss.dpes.sistrap.service.ReinstalacionPrestamoObtenerEstatusService;
import mx.gob.imss.dpes.sistrap.validation.ValidaReinstalacionRequest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Path("/reinstalacionPrestamo")
@RequestScoped
public class ReinstalacionPrestamoEndPoint extends BaseGUIEndPoint<BaseModel, BaseModel, BaseModel> {

    @Inject
    private ReinstalacionPrestamoObtenerEstatusService obtenerEstatusService;
    @Inject
    private ValidaReinstalacionRequest valida;
    @Inject
    private ReinstalacionPrestamoDatosTitularService datosTitularService;

    @POST
    @Path("/consultaEstatusPrestamoReinstalacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstatus(ConsultaEstatusRequest request) {
        try {
            if (!(request != null && request.getIdPrestamo() != null && !request.getIdPrestamo().trim().isEmpty()))
                return toResponse(
                    new Message<>(
                        null,
                        ServiceStatusEnum.EXCEPCION,
                        new ReinstalacionPrestamoException(
                            ReinstalacionPrestamoException.MENSAJE_DE_SOLICITUD_INCORRECTO), null
                    )
                );

            ConsultaEstatusResponse estatusResponse = obtenerEstatusService.obtenerEstatusByIdPrestamo(request);

            if (estatusResponse == null)
                return Response.noContent().build();

            return Response.ok(estatusResponse).build();
        } catch (VariableMessageException e) {
            return toResponse(new Message(null, ServiceStatusEnum.PARTIAL_CONTENT,
                new PartialContentFlowException(e.getMessage()), null));
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReinstalacionPrestamoEndPoint.obtenerEstatus()", e);
        }

        return toResponse(
            new Message(
                null,
                ServiceStatusEnum.EXCEPCION,
                new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            )
        );
    }

    @POST
    @Path("/consultaDatosPersonalesTitular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDatosTitular(ConsultaDatosTitularRequest request) {
        try {
            if (!valida.validaRequest(request))
                return toResponse(
                    new Message<>(
                        null,
                        ServiceStatusEnum.EXCEPCION,
                        new ReinstalacionPrestamoException(
                            ReinstalacionPrestamoException.MENSAJE_DE_SOLICITUD_INCORRECTO),
                        null
                    )
                );

            ConsultaDatosTitularResponse datosTitular = datosTitularService.obtenerDatosTitular(request);

            if (datosTitular == null)
                return Response.noContent().build();

            return Response.ok(datosTitular).build();
        } catch (VariableMessageException e) {
            return toResponse(new Message(null, ServiceStatusEnum.PARTIAL_CONTENT,
                    new PartialContentFlowException(e.getMessage()), null));
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReinstalacionPrestamoEndPoint.obtenerDatosTitular()", e);
        }

        return toResponse(
            new Message(
                null,
                ServiceStatusEnum.EXCEPCION,
                new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            )
        );
    }

}
