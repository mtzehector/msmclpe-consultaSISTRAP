package mx.gob.imss.dpes.sistrap.endpoint;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.PartialContentFlowException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.model.TitularGrupoRequest;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.Capacidad;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadRequestAux;
import mx.gob.imss.dpes.sistrap.exception.CapacidadCreditoException;
import mx.gob.imss.dpes.sistrap.model.ConsultaCapacidadCredito;
import mx.gob.imss.dpes.sistrap.service.PerfilPersonaService;
import mx.gob.imss.dpes.sistrap.service.ReadCapacidadCreditoService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.logging.Level;

@Path("/capacidadCreditoPensionado")
@RequestScoped
public class CapacidadCreditoPensionadoEndPoint extends BaseGUIEndPoint<BaseModel, TitularGrupoRequest, BaseModel> {

    @Inject
    private ReadCapacidadCreditoService capacidadCreditoService;

    @Inject
    private PerfilPersonaService perfilPersonaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener el periodo de nomina de sistrap de acuerdo con una fecha",
            description = "Obtener el periodo de nomina de sistrap de acuerdo con una fecha")
    public Response load(@Context HttpHeaders headers, ConsultaCapacidadRequestAux consultaCapacidadRequest) {

        try {
            if(
                !(
                    consultaCapacidadRequest != null &&
                    consultaCapacidadRequest.getGrupoFamiliar() != null &&
                    consultaCapacidadRequest.getNss() != null &&
                    consultaCapacidadRequest.getSesion() != null &&
                    consultaCapacidadRequest.getSesion() > 0
                )
            )
                return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new CapacidadCreditoException(CapacidadCreditoException.MENSAJE_DE_SOLICITUD_INCORRECTO),
                    null));

            String tokenSeguridad = this.obtenerTokenSeguridad(headers.getRequestHeaders());
            consultaCapacidadRequest.setToken(tokenSeguridad);

            ConsultaCapacidadCredito consultaCapacidadCredito = new ConsultaCapacidadCredito();
            consultaCapacidadCredito.setRequest(consultaCapacidadRequest);

            ServiceDefinition[] steps = {perfilPersonaService, capacidadCreditoService};

            Message<ConsultaCapacidadCredito> consultaCapacidadCreditoMessage
                = perfilPersonaService.executeSteps(steps, new Message<>(consultaCapacidadCredito));

            Message<Capacidad> capacidadMessage =
                new Message<>(consultaCapacidadCreditoMessage.getHeader(),
                    consultaCapacidadCreditoMessage.getPayload().getResponse().getCapacidad());

            return toResponse(capacidadMessage);
        } catch (VariableMessageException e) {
            return toResponse(new Message(null, ServiceStatusEnum.PARTIAL_CONTENT,
                new PartialContentFlowException(e.getMessage()), null));
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR CapacidadCreditoEndPoint.load consultaCapacidadRequest = [" +
                consultaCapacidadRequest + "]", e);

            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                new CapacidadCreditoException(
                    CapacidadCreditoException.ERROR_DESCONOCIDO_SERVICIO_CONSULTA_CAPACIDAD_CREDITO),null));
        }
    }

}
