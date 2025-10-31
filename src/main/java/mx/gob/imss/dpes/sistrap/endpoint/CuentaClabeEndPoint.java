/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.endpoint;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.sistrap.model.ClabeNominaAnterior;
import mx.gob.imss.dpes.sistrap.service.GetCuentaClabeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import mx.gob.imss.dpes.common.exception.AlternateFlowException;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaClabeNominaAnteriorResponse;
import mx.gob.imss.dpes.sistrap.model.ConsultaClabeNominaAnterior;

/**
 *
 * @author eduardo.loyo
 */
@Path("/cuentaclabe")
@RequestScoped
public class CuentaClabeEndPoint extends BaseGUIEndPoint<ClabeNominaAnterior, ClabeNominaAnterior, ClabeNominaAnterior> {

    @Inject
    private GetCuentaClabeService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener cuenta clabe",
            description = "Obtener cuenta clabe")
    @Override
    public Response load(ClabeNominaAnterior request) throws BusinessException,AlternateFlowException {

        log.log(Level.INFO,">>>>> Get clabe nomina: {0}", request);
        Message<ConsultaClabeNominaAnteriorResponse> response
                = service.execute(new Message<>(request));
        
        if (response.getPayload() != null) {            
            return toResponse(response);
        }
        throw new AlternateFlowException();
    }
}
