/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;

/**
 *
 * @author eduardo.loyo
 */
@Data
public class Sistra extends BaseModel {
    private String folio;
    private String idNss;//                      NOT NULL  
    private String idGrupoFamiliar;//           NOT NULL  
    private String cveSolicitud;//               NOT NULL  
    private String numFolio; //                   NOT NULL  
    private String idConsecutivoConcepto;
    private String fecRegSolicitud;
    private String impPrestamo;
    private String numMesesAplicar;
    private String impDescuentoMensual;
    private String impSaldo;
    private String impPendiente;
    private String impAplicado;
    private String impCuentiaBasePrestamo;
    private String idAntecedente;
    private String cveMotivoRechazo;
    private String cveEstadoSolPrestamo;
    private String cveOrdenIngreso;
    private String indBaja;
    private String fecBaja;
    private String cveNominaAplicacion;
    private String cveNominaPrimerDescuento;
    private String cveNominaUltimoDescuento;
    private String fecInicioDescuento;
    private String fecTerminoDescuento;
    private String cveDelegacion;
    private String cveSubdelegacion;
    private String idLogin;
    private String fecMovimiento;
    private String cvePrei;
    private String cveCtoCosto;
    private String desObservacionesBaja;
    private String idEntidadFinanciera;//
    private String cveEstadoPrestamo;// = A.
    private String cveCurp;//
    private String nomNombre;// 
    private String nomApellidoPaterno;//
    private String nomApellidoMaterno;//
    private String numClabe;//
    private String montoSolicitado;//
    private String plazo;//
    private String fecRegistro;//
    private String nominaPrimerDescuento;//
    private String numTasaInteres;//
    private String numCat;//
    private String impTotalPagar;//
    private String tipoDocumento;//
    private String numeroRegistros;
}
