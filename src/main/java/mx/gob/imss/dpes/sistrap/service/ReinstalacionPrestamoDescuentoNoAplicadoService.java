package mx.gob.imss.dpes.sistrap.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.PageRequestModel;
import mx.gob.imss.dpes.interfaces.solicitud.model.SolicitudOEFRequest;
import mx.gob.imss.dpes.sistrap.entity.Reinstalacion;
import mx.gob.imss.dpes.sistrap.exception.ReinstalacionPrestamoException;
import mx.gob.imss.dpes.sistrap.repository.ReinstalacionRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ReinstalacionPrestamoDescuentoNoAplicadoService {

    @Autowired
    private ReinstalacionRepository repository;
    private Logger log = Logger.getLogger(this.getClass().getName());

    public List<Reinstalacion> obtenerListPrestamosDescuentoNoAplicado(PageRequestModel<SolicitudOEFRequest> request)
        throws BusinessException {

        try {
            return repository.ejecutarConsulta(request);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR ReinstalacionPrestamoDescuentoNoAplicadoService.obtenerListPrestamosDescuentoNoAplicado(" +
                    request + ")", e);

            throw new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }

    public Reinstalacion obtenerPrestamoPorFolioSIPRE (String folioSIPRE) throws BusinessException {
        try {
            return repository.ejecutarConsultaPorFolioSIPRE(folioSIPRE);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR ReinstalacionPrestamoDescuentoNoAplicadoService.obtenerPrestamoPorFolioSIPRE(" +
                folioSIPRE + ")", e);

            throw new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }
}
