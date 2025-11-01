/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.VariableMessageException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sistrap.model.AltModEntFinancierasRQ;
import mx.gob.imss.dpes.interfaces.sistrap.model.AltModEntFinancierasResponse;
import mx.gob.imss.dpes.sistrap.exception.CapacidadCreditoException;
import mx.gob.imss.dpes.sistrap.service.AltModEntFinancierasService;

/**
 *
 * @author juan.garfias
 */
@Path("/AltModEntFinancieras")
@RequestScoped
public class AltModEntFinancierasEndPoint extends BaseGUIEndPoint<BaseModel, AltModEntFinancierasRQ, BaseModel> {

    @Inject
    private AltModEntFinancierasService altModEntFinancierasService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(AltModEntFinancierasRQ altModEntFinancierasRQ) {
        try {
            log.log(Level.INFO, "AltModEntFinancierasEndPoint - altModEntFinancierasRQ: {0}", altModEntFinancierasRQ);

            ServiceDefinition[] steps = {altModEntFinancierasService};

            Message<AltModEntFinancierasRQ> out
                    = altModEntFinancierasService.execute(
                    new Message<AltModEntFinancierasRQ>(altModEntFinancierasRQ));

            log.log(Level.INFO, "AltModEntFinancierasEndPoint - responde: {0}", out);

            String codErrorStr = out.getPayload().getAltModEntFinancierasResponse().getCodigoError();
            String errorMessage = out.getPayload().getAltModEntFinancierasResponse().getMensajeError();

            Message<AltModEntFinancierasResponse> res = new Message<AltModEntFinancierasResponse>();
            res.setPayload(out.getPayload().getAltModEntFinancierasResponse());
            return toResponse(res);
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR AltModEntFinancierasEndPoint.load altModEntFinancierasRQ = [" +
                    altModEntFinancierasRQ + "]", e);

            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new CapacidadCreditoException(
                            CapacidadCreditoException.ERROR_DESCONOCIDO_SERVICIO_CONSULTA_CAPACIDAD_CREDITO),null));
        }
    }
}
