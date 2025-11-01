/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.restclient;

/**
 *
 * @author antonio
 */
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.interfaces.sistrap.model.*;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/sistrap")
@RegisterRestClient
public interface SistrapClient {

    /**
     * http://11.254.171.50:8101/IMSSWSPensionesService/servicios/sistrap/ConsultaPensionado
     *
     * @param request
     * @return @see ConsultaPensionadoResponse { "idNss": "01005801210",
     * "idGrupoFamiliar":"01" } response { "pensionado":{ "idNss":"01005801210",
     * "idGrupoFamiliar":"01", "nomApellidoPaterno":"SANTA MARIA",
     * "nomApellidoMaterno":"SANTA MARIA", "nomNombre":"VICTOR",
     * "cveCurp":"SASV580203HDFNNC01", "cveRfc":"SASV5802032G2",
     * "fecNacimiento":"1958-02-03", "cveCtaPago":null, "numClabe":null,
     * "idTipoCuentaPago":null, "cveEntidadFederativa":"09", "sexo":1,
     * "cveSubdelegacion":"06", "cveDelegacion":"15",
     * "descEntidadFederativa":"DF", "descDelegacion":"ESTADO DE MEXICO ORIENTE
     * " }, "codigoError":200, "mensajeError":"Consulta Exitosa." }
     *
     */
    @Path("/ConsultaPensionado")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPensionado(PensionadoRequest request);

    /**
     * http://11.254.171.50:8101/IMSSWSPensionesService/servicios/sistrap/ConsultaTitularGrupo
     *
     * @param request
     * @return @see ConsultaTitularGrupoResponse { "idNss": "01005801210",
     * "idGrupoFamiliar":"01" } response { "titular":{ "idNss":"01005801210",
     * "idGrupoFamiliar":"01", "nomApellidoPaterno":"SANTA MARIA",
     * "nomApellidoMaterno":"SANTA MARIA", "nomNombre":"VICTOR",
     * "cveCurp":"SASV580203HDFNNC01", "fecNacimiento":"1958-02-03",
     * "cveCtaPago":null, "numClabe":null, "idTipoCuentaPago":null,
     * "idTipoValidacion":null }, "codigoError":200, "mensajeError":"Consulta
     * Exitosa." }
     *
     */
    @Path("/ConsultaTitularGrupo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaTitularGrupo(PensionadoRequest request);

    /**
     * http://11.254.171.50:8101/IMSSWSPensionesService/servicios/sistrap/ConsultaIncidenciaTitularGrupo
     *
     * @param request
     * @return @see ConsultaIncidenciaTitularGrupo { "idNss":"01005801210",
     * "idGrupoFamiliar":"01" } response { "incTitularGpo":{ "curp":"01",
     * "idNss":"01005801210", "idGrupoFamiliar":"01", "idIncidencia":"SV" },
     * "codigoError":200, "mensajeError":"Consulta Exitosa." }
     */
    @Path("/ConsultaIncidenciaTitularGrupo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaIncidenciaTitularGrupo(PensionadoRequest request);

    /**
     * http://11.254.171.50:8101/IMSSWSPensionesService/servicios/sistrap/ConsultaCLABENominaAnterior
     *
     * @param request
     * @return @see ConsultaCLABENominaAnterior
     *
     *
     * {
     * "idNss": "01007303454", "idGrupoFamiliar":"01",
     * "fecPeriodoNomina":"201508", "clabe":"021180064247509458" } response {
     * "clabeNominaAnt":{ "idNss":"01007303454", "idGrupoFamiliar":"01",
     * "fecPeriodoNomina":201508, "clabe":"021180064247509458" },
     * "codigoError":200, "mensajeError":"Consulta Exitosa." }
     *
     *
     */
    @Path("/ConsultaCLABENominaAnterior")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaClabeNominaAnteriorResponse(ClabeNominaAnterior request);

    /**
     * http://11.254.171.50:8101/IMSSWSPensionesService/servicios/sistrap/ConsultaPensionGrupoFamiliarTitularPago
     *
     * @param request
     * @return @see List<Pension>
     * {
     * "curp": "SAAP440519HOCNCR04" } response { "pensiones":[ {
     * "idNss":"08634400298", "idGrupoFamiliar":"01", "idIncidencia":"VG",
     * "tipoTrabajador":null, "idTipoPension":"CE", "idRegimen":"73",
     * "numTelefono":null } ], "codigoError":200, "mensajeError":"Consulta
     * Exitosa." }
     *
     */
    @Path("/ConsultaPensionGrupoFamiliar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPensionGrupoFamiliar(CurpRequest curp);

    @Path("/AltModEntFinancieras")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaModificacionEntidadesFinancieras(AltModEntFinancierasRequest request);

}
