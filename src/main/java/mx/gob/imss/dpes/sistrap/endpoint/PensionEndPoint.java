package mx.gob.imss.dpes.sistrap.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.PartialContentFlowException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.sistrap.exception.PensionException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequest;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequestAux;
import mx.gob.imss.dpes.sistrap.model.ConsultaPensiones;
import mx.gob.imss.dpes.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.sistrap.service.ReadPensionRegistroPensionadoService;
import mx.gob.imss.dpes.sistrap.service.ReadPensionService;

@Path("/pension")
@RequestScoped
public class PensionEndPoint extends BaseGUIEndPoint<BaseModel, PensionadoRequest, BaseModel> {

    @Inject
    private ReadPensionService service;

    @Inject
    private ReadPensionRegistroPensionadoService serviceReadPensionRegistroPensionadoService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener los grupos familiares",
            description = "Obtener la capacidad de credito del pensionado")
    public Response load(CurpRequest request) throws BusinessException {

        ConsultaPensiones model = new ConsultaPensiones();
        model.setRequest(request);

        ServiceDefinition[] steps = {service};
        Message<ConsultaPensiones> respuesta
                = service.executeSteps(steps, new Message<>(model));

        return Response.ok(respuesta.getPayload().getResponse()).build();
    }

    @POST
    @Path("/consultaTitular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener los grupos familiares",
            description = "Obtener la capacidad de credito del pensionado")
    public Response consultaTitular(CurpRequestAux request) {
        //log.log(Level.INFO, "Consulta Titular Pago para registro de pensionado: {0} ", request);
        try {
            CurpRequest cr = new CurpRequest();
            cr.setCurp(request.getCurp());

            ConsultaPensiones model = new ConsultaPensiones();
            model.setRequest(cr);

            ServiceDefinition[] steps = {serviceReadPensionRegistroPensionadoService};

            Message<ConsultaPensiones> respuesta
                    = serviceReadPensionRegistroPensionadoService.executeSteps(steps, new Message<>(model));
            //log.log(Level.INFO, "RESPONSE : Consulta Titular Pago para registro de pensionado: {0} ", respuesta.getPayload());

            return Response.ok(respuesta.getPayload().getResponse()).build();
        } catch (VariableMessageException e) {
            return toResponse(new Message(null, ServiceStatusEnum.PARTIAL_CONTENT,
                new PartialContentFlowException(e.getMessage()), null));
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PensionEndPoint.consultaTitular request = [" + request + "]", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                new PensionException(PensionException.
                    ERROR_DESCONOCIDO_SERVICIO_CONSULTA_PENSION_GRUPO_FAMILIAR),null));
        }
    }
}
