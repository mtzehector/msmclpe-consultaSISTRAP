package mx.gob.imss.dpes.sistrap.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.sistrap.exception.PrestamoException;
import org.eclipse.microprofile.openapi.annotations.Operation;

import mx.gob.imss.dpes.common.service.ServiceDefinition;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentosPorSolicitudRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstadosSPESRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamoResponse;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoRQ;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoRequest;
import mx.gob.imss.dpes.sistrap.model.ConsultaDescuentos;
import mx.gob.imss.dpes.sistrap.model.ConsultaEstadosSPES;
import mx.gob.imss.dpes.sistrap.model.ConsultaPrestamo;
import mx.gob.imss.dpes.sistrap.service.ConsultaDescuentosPorSolicitudService;
import mx.gob.imss.dpes.sistrap.service.ConsultaEstadosSPESService;
import mx.gob.imss.dpes.sistrap.service.ReadPrestamoService;
import mx.gob.imss.dpes.sistrap.service.RegistroPrestamoService;

@Path("/prestamo")
@RequestScoped
public class PrestamoEndPoint extends BaseGUIEndPoint<BaseModel, ConsultaPrestamoRequest, BaseModel> {

    @Inject
    ReadPrestamoService prestamoService;

    @Inject
    RegistroPrestamoService registroPrestamoService;
    
    @Inject
    ConsultaEstadosSPESService consultaEstadosService;
    
    @Inject  
    ConsultaDescuentosPorSolicitudService consultaDescuentosPorSolicitudService;
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener un prestamo en sipre",
            description = "Obtener un prestamo en sipre")
    @Override
    public Response load(ConsultaPrestamoRequest request) throws BusinessException {

        ConsultaPrestamo consultaPrestamo = new ConsultaPrestamo();
        consultaPrestamo.setRequest(request);
        ServiceDefinition[] steps = {prestamoService};
        Message<ConsultaPrestamo> prestamoResponse
                = prestamoService.executeSteps(steps, new Message<>(consultaPrestamo));
        Message<ConsultaPrestamoResponse> message = new Message<>(prestamoResponse.getPayload().getResponse());
        return toResponse(message);
    }

    @Path("/registroPrestamo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Registrar prestamo en sipre",
            description = "Registrar prestamo en sipre")
    public Response registro(RegistroPrestamoRequest request) throws BusinessException {
        try{
            RegistroPrestamoRQ prestamoRQ = new RegistroPrestamoRQ();
            prestamoRQ.setRegistroPrestamoRequest(request);
            ServiceDefinition[] steps = {registroPrestamoService};
            Message<RegistroPrestamoRQ> prestamoResponse = registroPrestamoService.executeSteps(steps, new Message<>(prestamoRQ));
            return toResponse(prestamoResponse);
        }catch(BusinessException e){
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        }catch (Exception e){
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.registro(); RegistroPrestamoRequest [" + request +"]", e);
        }
        return toResponse(new Message(
                null,
                ServiceStatusEnum.EXCEPCION,
                new PrestamoException(PrestamoException.SAVESISTRA),
                null
        ));
    }

    @Path("/consultaEstadosSPES")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Registrar prestamo en sipre",
            description = "Registrar prestamo en sipre")
    public Response consultaEstadosSPES(ConsultaEstadosSPESRequest request) throws BusinessException {
        log.log(Level.INFO, ">>>>>>>>>>>BACK SISTRAP RegistroPrestamoRequest {0}", request);

        ConsultaEstadosSPES rq = new ConsultaEstadosSPES();
        rq.setRequest(request);
        ServiceDefinition[] steps = {consultaEstadosService};

        Message<ConsultaEstadosSPES> response
                = consultaEstadosService.executeSteps(steps, new Message<>(rq));

        return toResponse(response);

    }

    @Path("/consultaDescuentosPorSolicitud")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Registrar prestamo en sipre",
            description = "Registrar prestamo en sipre")
    public Response consultaDescuentosPorSolicitud(ConsultaDescuentosPorSolicitudRequest request) throws BusinessException {
        log.log(Level.INFO, ">>>>>>>>>>>BACK SISTRAP RegistroPrestamoRequest {0}", request);

        ConsultaDescuentos rq = new ConsultaDescuentos();
        rq.setRequest(request);
        ServiceDefinition[] steps = {consultaDescuentosPorSolicitudService};

        Message<ConsultaDescuentos> response
                = consultaDescuentosPorSolicitudService.executeSteps(steps, new Message<>(rq));

        return toResponse(response);

    }
}
