package mx.gob.imss.dpes.sistrap.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Reinstalacion {

    private static final long serialVersionUID = 1L;

    @Column(name = "FOLIO")
    @Getter
    @Setter
    private String folio;

    @Id
    @Column(name = "FOLIO_SIPRE")
    @Getter
    @Setter
    private String folioSipre;

    @Column(name = "NSS")
    @Getter
    @Setter
    private String nss;

    @Column(name = "CURP")
    @Getter
    @Setter
    private String curp;

    @Column(name = "NOMBRE")
    @Getter
    @Setter
    private String nombre;

    @Column(name = "PRIMER_APELLIDO")
    @Getter
    @Setter
    private String primerApellido;

    @Column(name = "SEGUNDO_APELLIDO")
    @Getter
    @Setter
    private String segundoApellido;

    @Column(name = "CORREO_ELECTRONICO")
    @Getter
    @Setter
    private String correoElectronico;

    @Column(name = "TELEFONO_LOCAL")
    @Getter
    @Setter
    private String telefonoLocal;

    @Column(name = "TELEFONO_CELULAR")
    @Getter
    @Setter
    private String telefonoCelular;

    @Column(name = "DELEGACION")
    @Getter
    @Setter
    private String delegacion;

    @Column(name = "FECHA_INICIO")
    @Getter
    @Setter
    private Integer fechaInicio;

    @Column(name = "ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private String entidadFInanciera;

    @Column(name = "CAT_IVA")
    @Getter
    @Setter
    private BigDecimal cat;

    @Column(name = "MONTO_SOLICITADO")
    @Getter
    @Setter
    private BigDecimal montoSolicitado;

    @Column(name = "DESCUENTO_MENSUAL")
    @Getter
    @Setter
    private BigDecimal descuentoMensual;

    @Column(name = "PLAZO")
    @Getter
    @Setter
    private Integer plazo;

    @Column(name = "IMPORTE_TOTAL_PAGAR")
    @Getter
    @Setter
    private BigDecimal importeTotalPagar;

    @Column(name = "NUMERO_MESES_SIN_RECUPERACION")
    @Getter
    @Setter
    private Integer numMesesSinRecuperacion;

    @Column(name = "CVE_GRUPO_FAMILIAR")
    @Getter
    @Setter
    private String grupoFamiliar;

    @Column(name = "CVE_ENTIDAD_FINANCIERA_SIPRE")
    @Getter
    @Setter
    private String entidadFinancieraSIPRE;

    @Column(name = "IMP_SALDO_PENDIENTE")
    @Getter
    @Setter
    private BigDecimal saldoCapital;

    @Column(name = "NUM_MESES_CONSECUTIVOS")
    @Getter
    @Setter
    private Integer numMesesConsecutivos;

    @Column(name = "IMP_PENSION")
    @Getter
    @Setter
    private BigDecimal pension;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reinstalacion that = (Reinstalacion) o;
        return folioSipre.equals(that.folioSipre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folioSipre);
    }
}
