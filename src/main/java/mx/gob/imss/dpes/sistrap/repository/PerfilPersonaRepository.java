package mx.gob.imss.dpes.sistrap.repository;

import mx.gob.imss.dpes.common.exception.BusinessException;

import mx.gob.imss.dpes.sistrap.entity.PerfilPersona;
import mx.gob.imss.dpes.sistrap.exception.TokenLoginException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PerfilPersonaRepository {

    protected final transient Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    private Date agregarHoras(Date fecha, int horas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR, horas);
        return calendar.getTime();
    }

    private String obtenerConsultaPerfilPersonaPorToken() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("    t.cve_persona AS cve_persona, ")
                .append("    t.cve_perfil AS cve_perfil, ")
                .append("    t.num_sesion AS num_sesion, ")
                .append("    p.num_nss AS num_nss, ")
                .append("    p.cve_curp AS cve_curp ")
                .append("FROM ")
                .append("    mgpmclpe04.mclt_token t ")
                .append("    INNER JOIN mgpmclpe04.mclt_persona p ")
                .append("        ON (t.cve_persona = p.cve_persona) ")
                .append("WHERE ")
                .append("    t.token = :tokenSeguridad ")
                .append("    AND t.fec_registro_baja IS NULL ")
                .append("    AND t.fec_registro_alta >= :fechaInicio ")
                .append("    AND t.fec_registro_alta <= :fechaFin ")
                .append("    AND p.fec_registro_baja IS NULL ");
        return sb.toString();
    }

    public PerfilPersona buscarPerfilPersonaPorToken(String tokenSeguridad) throws BusinessException {
        try {
            Date fechaActual = new Date();

            List<PerfilPersona> personaList = entityManager.createNativeQuery(
                this.obtenerConsultaPerfilPersonaPorToken(), PerfilPersona.class)
            .setParameter("tokenSeguridad", tokenSeguridad)
            .setParameter("fechaInicio", agregarHoras(fechaActual, -4))
            .setParameter("fechaFin", agregarHoras(fechaActual, 4))
            .getResultList();

            if(personaList != null && !personaList.isEmpty())
                return personaList.get(0);
        }
        catch(Exception e) {
            log.log(Level.SEVERE, "PerfilPersonaRepository.buscarPerfilPersonaPorToken - tokenSeguridad = [" +
                    tokenSeguridad + "]", e);
        }

        throw new TokenLoginException();
    }

}
