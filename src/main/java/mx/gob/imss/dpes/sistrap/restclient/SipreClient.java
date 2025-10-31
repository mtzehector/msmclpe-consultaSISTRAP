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
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentoEmitidoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentosAplicadosRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDescuentosPorSolicitudRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstadosRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaEstadosSPESRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamosEnCursoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaRegistrosPrestamoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.RegistroPrestamoRequest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/sipre")
@RegisterRestClient
public interface SipreClient {

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaCapacidad
     *
     * @param request
     * @return @see ConsultaCapacidadResponse request { "nss":"01614156493",
     * "grupoFamiliar" :"01" } response { "capacidad":{ "nss":"01614156493",
     * "impCapacidadFija":2122.03, "impCapacidadVariable":0.0,
     * "impCapacidadTotal":2122.03 }, "codigoError":200,
     * "mensajeError":"Consulta Exitosa." }
     */
    @Path("/ConsultaCapacidad")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaCapacidad(ConsultaCapacidadRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaDescuentoEmitido
     *
     * @param request
     * @return @see ConsultaDescuentoEmitidoResponse request {
     * "periodoNomina":"201407" } response { "descuentosEmitidos":[ {
     * "nss":"01735274753", "idGrupoFamiliar":"01", "movimiento":"NA",
     * "delegacion":"15", "impRecuperado":1044.0, "aplicacionPeriodo":"201407",
     * "idSolPrestFinanciero":"000076038", "idInstFincanciera":"001",
     * "conceptoNomina":"301" } ], "codigoError":200, "mensajeError":"Consulta
     * Exitosa." }
     */
    @Path("/ConsultaDescuentoEmitido")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaDescuentoEmitido(ConsultaDescuentoEmitidoRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaDescuentosAplicados
     *
     * @param request
     * @return @see ConsultaDescuentosAplicadosResponse request {
     * "nss":"01674867328", "grupoFamiliar":"01", "periodoNomina":"201303" }
     * response { "descuentosAplicados":[ { "nss":"01674867328",
     * "idInstFinanciera":"001", "grupoFamiliar":"01", "razonSocial":"Consupago
     * S.A. DE C.V. SOFOM E.R.", "movimiento":"DA", "impRecuperado":1595.24,
     * "idSolPrestFinanciero":"000001192", "conceptoNomina":"301",
     * "peridoNomina":"201303" } ], "codigoError":200, "mensajeError":"Consulta
     * Exitosa." }
     */
    @Path("/ConsultaDescuentosAplicados")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaDescuentosAplicados(ConsultaDescuentosAplicadosRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaEstados
     *
     * @param request
     * @return @see ConsultaEstadosResponse request { "folio":"001327870",
     * "estadoPrestamo":"" } response { "estadoPrestamo":{ "idEstado":"RP",
     * "folio":"001327870", "rowAffected":1 } , "codigoError":200,
     * "mensajeError":"Consulta Exitosa." }
     */
    @Path("/ConsultaEstados")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaEstados(ConsultaEstadosRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaPrestamo
     *
     * @param request
     * @return @see ConsultaPrestamoResponse request { "idNss":"43816452858",
     * "grupoFamiliar":"01", "numFolio":"000004081", "idPeriodo":"200810" }
     * response { "prestamo":{ "curp":"SUMA640204HNLRRL07",
     * "idNss":"43816452858", "idGrupoFamiliar":"01", "numFolio":"000004081",
     * "idInstFinanciera":"001", "grupoFamiliar":null, "nombre":"JOSE ALBERTO ",
     * "apellidoPaterno":"SUAREZ", "apellidoMaterno":"MORQUECHO",
     * "impPrestamo":28429.2, "impRealPrestamo":15000.0, "telefonoTitular":"0",
     * "email":null, "tipoTrabajador":"PENSIONADO", "tipoCredito":"NUEVO",
     * "tipoPension":null, "nomFinanciera":"Paguitos", "razonSocial":"Consupago
     * S.A. DE C.V. SOFOM E.R.", "telefonoFinanciera":"01-800-507-6645",
     * "desUrlFinanciera":"www.consupago.com", "catPromedio":50.9,
     * "impDescuentoMensual":789.7, "numTasaIntAnual":3.42,
     * "impPrestamoOtor":28429.2, "numVecesAplicar":"20130213000004081",
     * "numPlazoMeses":36.0, "fecInicioPago":"", "idSolPrFinanciero":null,
     * "movimiento":null, "registrosCargados":3 }, "codigoError":200,
     * "mensajeError":"Consulta Exitosa." }
     *
     */
    @Path("/ConsultaPrestamo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamo(ConsultaPrestamoRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaPrestamosEnCurso
     *
     * @param request
     * @return @see ConsultaPrestamosEnCursoResponse request {
     * "nss":"01674867328", "grupoFamiliar":"01", "periodoNomina":"201303" }
     * response { "prestamosEnCursoVoList":[ { "nss":"01674867328",
     * "grupoFamiliar":"01", "impPrestamo":12761.92, "numVecesAplicar":4,
     * "movimiento":"DA", "delegacion":"16", "idPago":"201303",
     * "impRecuperado":1595.24, "insFinanciera":"001",
     * "idSolPrestamo":"000001192" } ], "codigoError":200,
     * "mensajeError":"Consulta Exitosa." }
     *
     */
    @Path("/ConsultaPrestamosEnCurso")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosEnCurso(ConsultaPrestamosEnCursoRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaRegistrosPrestamo
     *
     * @param request
     * @return @see ConsultaRegistrosPrestamoResponse request {
     * "idFolioNegocio":"000003276" } response { "selectRegistrosPrestamoVo":{
     * "numRegistros":1 }, "codigoError":200, "mensajeError":"Consulta Exitosa."
     * }
     *
     */
    @Path("/ConsultaRegistrosPrestamo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaRegistrosPrestamo(ConsultaRegistrosPrestamoRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/RegistroPrestamo
     *
     * @param request
     * @return @see RegistroPrestamoResponse request { "numFolio":"001327871",
     * "idInstFinanciera":"064", "nss":"01674894447", "grupoFamiliar":"01",
     * "curp":"MABL490924HDFRNZ08", "nombre":"PRUEBA",
     * "apellidoPaterno":"APELLIDO PATERNO", "apellidoMaterno":"APELLIDO
     * MATERNO", "clabe":"72905008463878823", "impPrestamo":10000.00,
     * "numPlazo":"6", "fecAlta":"2019-11-29", "impMensual":2000,
     * "nominaPrimerDescuento":"", "numTasaActual":3.42, "catPrestamo":51.38,
     * "impRealPrestamo":15000, "imgCartaInstruccion":"hola" } response {
     * "exito":"true", "codigoError":200, "mensajeError":"Consulta Exitosa." }
     *
     */
    @Path("/RegistroPrestamo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registroPrestamo(RegistroPrestamoRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaPrestamosEnRecuperacion
     *
     * @param request
     * @return @see PrestamosEnRecuperacion request {
     * "prestamosEnRecuperacionVoList": [ { "impMensual": 1696.92,
     * "catPrestamo": 42.1, "impRealPrestamo": 40000.0, "numMeses": 48.0,
     * "idSolPrestamo": "000038566", "insFinanciera": "002", "mesesRecuperados":
     * 29 }, { "impMensual": 2000.0, "catPrestamo": 51.38, "impRealPrestamo":
     * 15000.0, "numMeses": 6.0, "idSolPrestamo": "1429611", "insFinanciera":
     * "002", "mesesRecuperados": 0 }, { "impMensual": 2000.0, "catPrestamo":
     * 51.38, "impRealPrestamo": 15000.0, "numMeses": 6.0, "idSolPrestamo":
     * "001429620", "insFinanciera": "002", "mesesRecuperados": 0 }, {
     * "impMensual": 2000.0, "catPrestamo": 51.38, "impRealPrestamo": 15000.0,
     * "numMeses": 6.0, "idSolPrestamo": "001429622", "insFinanciera": "002",
     * "mesesRecuperados": 0 }, { "impMensual": 2000.0, "catPrestamo": 51.38,
     * "impRealPrestamo": 15000.0, "numMeses": 6.0, "idSolPrestamo":
     * "001429625", "insFinanciera": "002", "mesesRecuperados": 0 } ],
     * "codigoError": 200, "mensajeError": "Consulta Exitosa." }
     *
     */
    @Path("/ConsultaPrestamosEnRecuperacion")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosEnRecuperacion(ConsultaCapacidadRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaEstadosSPES
     *
     * {
     * "nss": "13815705010", "grupoFamiliar": "01" } {
     * "estadoPrestamoSPESVoList": [ { "impPrestamo": 9000.0, "impMensual":
     * 750.0, "catPrestamo": 49.9, "numFolioSolicitud": null, "nomFinanciera":
     * "Kondinero", "movimiento": "BL", "desMovimiento": "Préstamo Liquidado",
     * "fecInicioPrestamo": "2019-01-30", "fecTermPrestamo": null,
     * "idSolPrestamo": "001220425", "insFinanciera": "043" }, { "impPrestamo":
     * 21600.0, "impMensual": 900.0, "catPrestamo": 49.9, "numFolioSolicitud":
     * null, "nomFinanciera": "Kondinero", "movimiento": "PE", "desMovimiento":
     * "Préstamo Enviado a SPES", "fecInicioPrestamo": null, "fecTermPrestamo":
     * null, "idSolPrestamo": "001427259", "insFinanciera": "043" } ],
     * "codigoError": 200, "mensajeError": "Consulta Exitosa." }
     */
    @Path("/ConsultaEstadosSPES")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaEstadosSPES(ConsultaEstadosSPESRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaDescuentosPorSolicitud
     *
     * {
     * "idSolPrFinanciero":"", "numFolioSolicitud":"1429924" }
     *
     * {
     * "descuentosAplicados": [ { "nss": null, "razonSocial": "Paguitos",
     * "idInstFinanciera": null, "grupoFamiliar": null, "movimiento": null,
     * "desMovimiento": "Descuento Aplicado", "saldoPendiente": 90710.14,
     * "peridoNomina": "201307", "idSolPrestFinanciero": null, "conceptoNomina":
     * null, "impRecuperado": 1537.46 }], "codigoError": 200, "mensajeError":
     * "Consulta Exitosa." }
     *
     * @param request
     * @return
     */
    @Path("/ConsultaDescuentosPorSolicitud")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaDescuentosPorSolicitud(ConsultaDescuentosPorSolicitudRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaPrestamosconDescuento
     * { "periodoNomina":"202005" }
     *
     * @param request
     * @return
     */
    @Path("/ConsultaPrestamosconDescuento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosconDescuento(ConsultaDescuentoEmitidoRequest request);

    /**
     * http://osbdpes-stage.imss.gob.mx/TSPI/sipre/ConsultaPrestamosEnCursoPorMovimiento
     * { "periodoNomina":"202005" }
     *
     * @param request
     * @return
     */
    @Path("/ConsultaPrestamosEnCursoPorMovimiento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosEnCursoPorMovimiento(ConsultaDescuentoEmitidoRequest request);

}
