/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.endpoint;

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
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoResponse;
import mx.gob.imss.dpes.sistrap.model.CrearRegistroPrestamo;
import mx.gob.imss.dpes.sistrap.service.GuardarPrestamoSistrapService;
import org.eclipse.microprofile.openapi.annotations.Operation;

/**
 *
 * @author eduardo.loyo
 */
@Path("/prestamoSistrap")
@RequestScoped
public class GuardarPrestamoSistrapEndPoint extends BaseGUIEndPoint<RegistroPrestamoRequest, BaseModel, BaseModel> {

    @Inject
    private GuardarPrestamoSistrapService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Guardar info en sistrap",
            description = "Guardar info en sistrap")
    public Response create(RegistroPrestamoRequest request) throws BusinessException {
        CrearRegistroPrestamo crearRegistroPrestamo = new CrearRegistroPrestamo();
        crearRegistroPrestamo.setRequest(request);
        Message<CrearRegistroPrestamo> respuesta
                = service.execute(new Message<>(crearRegistroPrestamo));
        Message<RegistroPrestamoResponse> response = new Message<>(respuesta.getPayload().getResponse());
        return toResponse(response);
    }

}
