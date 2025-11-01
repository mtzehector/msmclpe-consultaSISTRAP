package mx.gob.imss.dpes.sistrap.restclient;


import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstatusRequest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sipre")
@RegisterRestClient
public interface ReinstalacionPrestamoClient {

    /**
     * http://
     *
     * @param request
     * @return @see request { "idPrestamo":"3200"}
     * response { "idPrestamo": "3200", "idMovimiento": "" }
     */
    @POST
    @Path("/ConsultaStatusPrestamoReinstalacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstatus(ConsultaEstatusRequest request);

    /**
     * http://
     *
     * @param request
     * @return @see request { "nss":"21078875099", "idGrupoFamiliar":"01"}
     * response { "nss": "21078875099", "idGrupoFamiliar": "01", "nombre": "SERGIO",
     * "apellidoPaterno": "SALAZAR", "apellidoMaterno": "GONZALEZ", "curp": "SAGS880419HNTLNR08",
     * "telefono" : [
     *         {
     *             "tipoNumero": 1
     *             "lada": 55
     *             "numero": 5555555555
     *         },
     *         {
     *             "tipoNumero": 2
     *             "lada": 55
     *             "numero": 5555555555
     *         }
     *     ],
     *     "correoElectronico": [
     *         "pruebas@mail.com",
     *         "test@mail.com"
     *     ]}
     */
    @POST
    @Path("/ConsultaDatosPersonalesTitular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDatosTitular(ConsultaDatosTitularRequest request);







}
