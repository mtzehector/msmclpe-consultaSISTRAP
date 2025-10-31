
package mx.gob.imss.dpes.sistrap.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;

public class PrestamoRequest extends BaseModel{
  @Getter @Setter private String nss;
  @Getter @Setter private String grupoFamiliar;
  @Getter @Setter private String folio;
  @Getter @Setter private String periodoNomina;
  
  @Getter @Setter private String apellidoPaterno;
  @Getter @Setter private String apellidoMaterno;
  @Getter @Setter private String nombre;
  @Getter @Setter private String curp;
  @Getter @Setter private String telefono;
  @Getter @Setter private String correoElectronico;
  @Getter @Setter private String tipoTrabajador;
  @Getter @Setter private String tipoCredito;
  @Getter @Setter private String tipoPension;
  @Getter @Setter private String nombreComercialEntidadFinanciera;
  @Getter @Setter private String razonSocialEntidadFinanciera;
  @Getter @Setter private String telefonoEntidadFinanciera;
  @Getter @Setter private String paginaWebEntidadFinanciera;
  @Getter @Setter private String cat;
  @Getter @Setter private String totalPrestamo;
  @Getter @Setter private String descuentoMensual;
  @Getter @Setter private String tasaAnual;
  @Getter @Setter private String montoSolicitado;
  @Getter @Setter private String totalMensualidadesDescontadas;
  @Getter @Setter private String plazo;  
  
  @Getter @Setter private String totalConInteres;
  @Getter @Setter private String nominaPrimerDescuento;
  @Getter @Setter private String idPrestamoFinanciero;
  @Getter @Setter private String idEntidadFinanciera;
  @Getter @Setter private String idGrupoFamiliar;
  @Getter @Setter private String idMovimiento;
  @Getter @Setter private String registrosCargados;
  
}
