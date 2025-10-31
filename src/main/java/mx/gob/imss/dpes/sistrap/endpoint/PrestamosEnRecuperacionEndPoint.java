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
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamosEnRecuperacionRQ;
import mx.gob.imss.dpes.sistrap.service.PrestamosEnRecuperacionService;

/**
 *
 * @author juan.garfias
 */
@Path("/prestamosEnRecuperacion")
@RequestScoped
public class PrestamosEnRecuperacionEndPoint extends BaseGUIEndPoint<BaseModel, ConsultaPrestamosEnRecuperacionRQ, BaseModel> {

    @Inject
    private PrestamosEnRecuperacionService consultaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(ConsultaPrestamosEnRecuperacionRQ request) throws BusinessException {

        log.log(Level.INFO, ">>>>>>>>>>>BACK SISTRAP EndPoint {0}", request);

        ServiceDefinition[] steps = {consultaService};
        Message<ConsultaPrestamosEnRecuperacionRQ> out
                = consultaService.execute(
                        new Message<ConsultaPrestamosEnRecuperacionRQ>(request));
        log.log(Level.INFO, "ConsultaPrestamosEnRecuperacionEndPoint - responde: {0}", out);

        return toResponse(out);
    }

}
