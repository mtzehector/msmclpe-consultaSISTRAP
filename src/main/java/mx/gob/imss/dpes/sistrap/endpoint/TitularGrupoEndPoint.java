/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.TitularGrupoRequest;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoClabeRequest;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.interfaces.sistrap.model.TitularGrupo;
import mx.gob.imss.dpes.sistrap.model.ConsultaTitularGrupo;
import mx.gob.imss.dpes.sistrap.model.TitularGrupoResponse;
import mx.gob.imss.dpes.sistrap.service.ReadTitularGrupoService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author antonio
 */
@Path("/titularPago")
@RequestScoped
public class TitularGrupoEndPoint extends BaseGUIEndPoint<PensionadoRequest, PensionadoRequest, PensionadoRequest> {

    @Inject
    private ReadTitularGrupoService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener los grupos familiares",
            description = "Obtener los datos del pensionado")
    @Override
    public Response load(PensionadoRequest request) throws BusinessException {

        ConsultaTitularGrupo model = new ConsultaTitularGrupo();
        model.setRequest(request);

        ServiceDefinition[] steps = {service};
        Message<ConsultaTitularGrupo> respuesta
                = service.executeSteps(steps, new Message<>(model));
        Message<TitularGrupo> msg = new Message<>(respuesta.getPayload().getResponse().getTitular());
        return toResponse(msg);
    }

    @Path("/valida-clabe")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validaClabe(PensionadoClabeRequest request) throws BusinessException {

        PensionadoRequest requestAux = new PensionadoRequest();
        
        requestAux.setIdNss(request.getIdNss());
        requestAux.setIdGrupoFamiliar(request.getIdGrupoFamiliar());
        
        ConsultaTitularGrupo model = new ConsultaTitularGrupo();
        model.setRequest(requestAux);

        ServiceDefinition[] steps = {service};
        Message<ConsultaTitularGrupo> respuesta
                = service.executeSteps(steps, new Message<>(model));
        Message<TitularGrupo> msg = new Message<>(respuesta.getPayload().getResponse().getTitular());

        if (msg.getPayload().getNumClabe().equals(request.getClabe())) {
            return Response.accepted(msg).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}
