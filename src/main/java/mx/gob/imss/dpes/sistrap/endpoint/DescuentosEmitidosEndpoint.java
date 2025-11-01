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
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentosEmitidos;
import mx.gob.imss.dpes.sistrap.service.ConsultaDescuentosEmitidosService;
import mx.gob.imss.dpes.sistrap.service.ConsultaPrestamosConDescuentoService;
import mx.gob.imss.dpes.sistrap.service.ConsultaPrestamosEnCursoPorMovimientoService;

/**
 *
 * @author edgar.arenas
 */
@Path("/descuentosEmitidos")
@RequestScoped
public class DescuentosEmitidosEndpoint extends BaseGUIEndPoint<BaseModel, ConsultaDescuentosEmitidos, BaseModel> {

    @Inject
    ConsultaDescuentosEmitidosService service;

    @Inject
    ConsultaPrestamosConDescuentoService servicePresDesc;

    @Inject
    ConsultaPrestamosEnCursoPorMovimientoService servicePresMov;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(ConsultaDescuentosEmitidos request) throws BusinessException {

        ServiceDefinition[] steps = {service};
        Message<ConsultaDescuentosEmitidos> reponse
                = service.executeSteps(steps, new Message<>(request));

        return toResponse(reponse);
    }

    @Path("/consultaPrestamosconDescuento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosconDescuento(ConsultaDescuentosEmitidos request) throws BusinessException {

        ServiceDefinition[] steps = {servicePresDesc};
        Message<ConsultaDescuentosEmitidos> reponse
                = servicePresDesc.executeSteps(steps, new Message<>(request));

        return toResponse(reponse);
    }

    @Path("/consultaPrestamosEnCursoPorMovimiento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosEnCursoPorMovimiento(ConsultaDescuentosEmitidos request) throws BusinessException {

        ServiceDefinition[] steps = {servicePresMov};
        Message<ConsultaDescuentosEmitidos> reponse
                = servicePresMov.executeSteps(steps, new Message<>(request));

        return toResponse(reponse);
    }

}
