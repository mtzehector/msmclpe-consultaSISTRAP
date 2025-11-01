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
import mx.gob.imss.dpes.sistrap.exception.PensionadoException;
import org.eclipse.microprofile.openapi.annotations.Operation;

import mx.gob.imss.dpes.common.service.ServiceDefinition;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pensionado;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.sistrap.model.ConsultaPensionado;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequest;
import mx.gob.imss.dpes.sistrap.model.ConsultaTitularYPensionado;
import mx.gob.imss.dpes.sistrap.service.ConsultaPensionadoSistrapService;

import mx.gob.imss.dpes.sistrap.service.ReadPensionadoService;

@Path("/pensionado")
@RequestScoped
public class PensionadoEndPoint extends BaseGUIEndPoint<PensionadoRequest, PensionadoRequest, PensionadoRequest> {

    @Inject
    ReadPensionadoService pensionadoService;

    @Inject
    ConsultaPensionadoSistrapService sistrapService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener el periodo de nomina de sistrap de acuerdo con una fecha",
            description = "Obtener el periodo de nomina de sistrap de acuerdo con una fecha")
    public Response load(PensionadoRequest request) {
        try {
            ConsultaPensionado model = new ConsultaPensionado();
            model.setRequest(request);

            ServiceDefinition[] steps = {pensionadoService};
            Message<ConsultaPensionado> prestamoResponse
                    = pensionadoService.executeSteps(steps, new Message<>(model));
            Message<Pensionado> response = new Message<>(prestamoResponse.getHeader(),
                prestamoResponse.getPayload().getResponse().getPensionado());

            return toResponse(response);
        } catch (VariableMessageException e) {
            return toResponse(new Message(null, ServiceStatusEnum.PARTIAL_CONTENT,
                new PartialContentFlowException(e.getMessage()), null));
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PensionadoEndPoint.load request = [" + request + "]", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                new PensionadoException(PensionadoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),null));
        }
    }

    @GET
    @Path("/{curp}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPensionadoPorCurp(@PathParam("curp") String curp) throws BusinessException {
        log.log(Level.INFO, ">>> consultaPensionadoPorCurp {0}:", curp);

        ConsultaTitularYPensionado model = new ConsultaTitularYPensionado();
        model.setRequest(new CurpRequest(curp));

        ServiceDefinition[] steps = {sistrapService};
        Message<ConsultaTitularYPensionado> prestamoResponse
                = sistrapService.executeSteps(steps, new Message<>(model));

        log.log(Level.INFO, ">>> consultaPensionadoPorCurp {0}:", prestamoResponse.getPayload());       
        Message<Pensionado> response = new Message<>(prestamoResponse.getHeader(), prestamoResponse.getPayload().getResponse().getPensionado() );
        return toResponse(response);
    }
    
    @GET
    @Path("/{curp}/{nss}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPensionadoPorCurpYNss(@PathParam("curp") String curp, @PathParam("nss") String nss) throws BusinessException {
        log.log(Level.INFO, ">>> consultaPensionadoPorCurpYNSS {0}:", curp);
        log.log(Level.INFO, ">>> consultaPensionadoPorCurpYNSS {0}:", nss);

        ConsultaTitularYPensionado model = new ConsultaTitularYPensionado();
        model.setRequest(new CurpRequest(curp));
        model.setNss(nss);

        ServiceDefinition[] steps = {sistrapService};
        Message<ConsultaTitularYPensionado> prestamoResponse
                = sistrapService.executeSteps(steps, new Message<>(model));

        log.log(Level.INFO, ">>> consultaPensionadoPorCurp {0}:", prestamoResponse.getPayload());       
        Message<Pensionado> response = new Message<>(prestamoResponse.getHeader(), prestamoResponse.getPayload().getResponse().getPensionado() );
        return toResponse(response);
    }

}
