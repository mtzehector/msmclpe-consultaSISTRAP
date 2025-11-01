package mx.gob.imss.dpes.sistrap.repository;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.PageRequestModel;
import mx.gob.imss.dpes.sistrap.entity.Reinstalacion;
import mx.gob.imss.dpes.interfaces.solicitud.model.SolicitudOEFRequest;
import mx.gob.imss.dpes.sistrap.exception.ReinstalacionPrestamoException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ReinstalacionRepository {
    protected final transient Logger log = Logger.getLogger( getClass().getName() );

    @PersistenceContext
    private EntityManager em;

    private String obtenerConsultaSQL(PageRequestModel<SolicitudOEFRequest> req){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append("    reinst.num_folio_solicitud as folio,");
        sb.append("    reinst.folio_sipre as folio_sipre,");
        sb.append("    reinst.num_nss as nss,");
        sb.append("    reinst.cve_curp as curp,");
        sb.append("    reinst.nombre as nombre,");
        sb.append("    reinst.primer_apellido as primer_apellido,");
        sb.append("    reinst.segundo_apellido as segundo_apellido,");
        sb.append("    usuario.nom_usuario as correo_electronico,");
        sb.append("    pers.tel_local as telefono_local,");
        sb.append("    pers.tel_celular as telefono_celular,");
        sb.append("    deleg.des_delegacion as delegacion,");
        sb.append("    reinst.periodo_primer_descuento as fecha_inicio,");
        sb.append("    ef.nom_comercial as entidad_financiera,");
        sb.append("    reinst.cat_prestamo as cat_iva,");
        sb.append("    reinst.imp_monto_sol as monto_solicitado,");
        sb.append("    reinst.imp_descuento_nomina as descuento_mensual,");
        sb.append("    reinst.num_meses as plazo,");
        sb.append("    reinst.imp_total_pagar as importe_total_pagar,");
        sb.append("    reinst.num_mes_pendiente as numero_meses_sin_recuperacion, ");
        sb.append("    reinst.cve_grupo_familiar as cve_grupo_familiar, ");
        sb.append("    reinst.cve_entidad_financiera_sipre as cve_entidad_financiera_sipre, ");
        sb.append("    reinst.imp_saldo_pendiente as imp_saldo_pendiente, ");
        sb.append("    reinst.num_meses_consecutivos as num_meses_consecutivos, ");
        sb.append("    reinst.imp_pension as imp_pension ");
        sb.append("FROM mgpmclpe04.mclt_reinstalacion reinst");
        sb.append("    INNER JOIN mgpmclpe04.mclc_entidad_financiera ef");
        sb.append("        ON reinst.cve_entidad_financiera_sipre = ef.cve_entidad_financiera_sipre");
        sb.append("    INNER JOIN mgpmclpe04.mclc_delegacion deleg");
        sb.append("        ON reinst.cve_delegacion = deleg.num_delegacion");
        sb.append("    LEFT OUTER JOIN mgpmclpe04.mclt_persona pers");
        sb.append("        ON reinst.num_nss = pers.num_nss");
        sb.append("    LEFT OUTER JOIN mgpmclpe04.mclt_usuario usuario");
        sb.append("        ON pers.cve_usuario = usuario.cve_usuario ");
        sb.append("WHERE ");

        if(req.getModel().getCveEntidadFinanciera() != null && !req.getModel().getCveEntidadFinanciera().equals(0L))
            sb.append("    ef.cve_entidad_financiera = ").append(req.getModel().getCveEntidadFinanciera()).append(" AND ");

        sb.append("    ef.convenio = 1 AND ");
        sb.append("    ef.cve_estado_ent_financiera = 1 AND ");

        if (req.getModel().getFolio() != null && !req.getModel().getFolio().isEmpty())
            sb.append("    reinst.num_folio_solicitud = '").append(req.getModel().getFolio()).append("' AND ");

        if (req.getModel().getNss() != null && !req.getModel().getNss().isEmpty())
            sb.append("    reinst.num_nss='").append(req.getModel().getNss()).append("' AND ");

        sb.append("    reinst.periodo = (SELECT MAX(ri.periodo) FROM mgpmclpe04.mclt_reinstalacion ri) ");
        sb.append("ORDER BY reinst.cve_entidad_financiera_sipre ASC, reinst.folio_sipre DESC");
        return sb.toString();
    }

    public List<Reinstalacion> ejecutarConsulta(PageRequestModel<SolicitudOEFRequest> req) throws BusinessException {
        try {
            return em.createNativeQuery(this.obtenerConsultaSQL(req), Reinstalacion.class).getResultList();
        } catch (Exception e){
            log.log(Level.SEVERE, "ERROR ReinstalacionRepository.ejecutarConsulta - [" +
                req + "] ", e);
            throw new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DE_LECTURA_DE_BD);
        }
    }

    private String obtenerConsultaConFiltroFolioSIPRE(String folioSIPRE) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append("    reinst.num_folio_solicitud as folio,");
        sb.append("    reinst.folio_sipre as folio_sipre,");
        sb.append("    reinst.num_nss as nss,");
        sb.append("    reinst.cve_curp as curp,");
        sb.append("    reinst.nombre as nombre,");
        sb.append("    reinst.primer_apellido as primer_apellido,");
        sb.append("    reinst.segundo_apellido as segundo_apellido,");
        sb.append("    usuario.nom_usuario as correo_electronico,");
        sb.append("    pers.tel_local as telefono_local,");
        sb.append("    pers.tel_celular as telefono_celular,");
        sb.append("    deleg.des_delegacion as delegacion,");
        sb.append("    reinst.periodo_primer_descuento as fecha_inicio,");
        sb.append("    ef.nom_comercial as entidad_financiera,");
        sb.append("    reinst.cat_prestamo as cat_iva,");
        sb.append("    reinst.imp_monto_sol as monto_solicitado,");
        sb.append("    reinst.imp_descuento_nomina as descuento_mensual,");
        sb.append("    reinst.num_meses as plazo,");
        sb.append("    reinst.imp_total_pagar as importe_total_pagar,");
        sb.append("    reinst.num_mes_pendiente as numero_meses_sin_recuperacion, ");
        sb.append("    reinst.cve_grupo_familiar as cve_grupo_familiar, ");
        sb.append("    reinst.cve_entidad_financiera_sipre as cve_entidad_financiera_sipre, ");
        sb.append("    reinst.imp_saldo_pendiente as imp_saldo_pendiente, ");
        sb.append("    reinst.num_meses_consecutivos as num_meses_consecutivos, ");
        sb.append("    reinst.imp_pension as imp_pension ");
        sb.append("FROM mgpmclpe04.mclt_reinstalacion reinst");
        sb.append("    INNER JOIN mgpmclpe04.mclc_entidad_financiera ef");
        sb.append("        ON reinst.cve_entidad_financiera_sipre = ef.cve_entidad_financiera_sipre");
        sb.append("    INNER JOIN mgpmclpe04.mclc_delegacion deleg");
        sb.append("        ON reinst.cve_delegacion = deleg.num_delegacion");
        sb.append("    LEFT OUTER JOIN mgpmclpe04.mclt_persona pers");
        sb.append("        ON reinst.num_nss = pers.num_nss");
        sb.append("    LEFT OUTER JOIN mgpmclpe04.mclt_usuario usuario");
        sb.append("        ON pers.cve_usuario = usuario.cve_usuario ");
        sb.append("WHERE reinst.folio_sipre = '" + folioSIPRE + "' ");
        sb.append("AND reinst.periodo = (SELECT MAX(ri.periodo) FROM mgpmclpe04.mclt_reinstalacion ri) ");
        sb.append("AND ef.convenio = 1 ");
        sb.append("AND ef.cve_estado_ent_financiera = 1");
        return sb.toString();
    }

    public Reinstalacion ejecutarConsultaPorFolioSIPRE(String folioSIPRE) throws BusinessException {
        try {
            List<Reinstalacion> listaReinstalaciones = em.createNativeQuery(
                this.obtenerConsultaConFiltroFolioSIPRE(folioSIPRE), Reinstalacion.class).getResultList();

            if (listaReinstalaciones != null && listaReinstalaciones.size() > 0)
                return listaReinstalaciones.get(0);

            return null;
        } catch (Exception e){
            log.log(Level.SEVERE, "ERROR ReinstalacionRepository.ejecutarConsultaPorFolioSIPRE - [" +
                folioSIPRE + "] ", e);
            throw new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DE_LECTURA_DE_BD);
        }
    }

}
