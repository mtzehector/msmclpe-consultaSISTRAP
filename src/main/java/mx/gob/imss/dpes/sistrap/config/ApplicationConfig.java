package mx.gob.imss.dpes.sistrap.config;

import mx.gob.imss.dpes.common.model.Message;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        //addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(mx.gob.imss.dpes.common.exception.AlternateFlowMapper.class);
        resources.add(mx.gob.imss.dpes.common.exception.BusinessMapper.class);
        resources.add(mx.gob.imss.dpes.common.rule.MontoTotalRule.class);
        resources.add(mx.gob.imss.dpes.common.rule.PagoMensualRule.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.AltModEntFinancierasEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.CapacidadCreditoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.CapacidadCreditoPensionadoEndPoint.class);
        //resources.add(mx.gob.imss.dpes.sistrap.endpoint.GrupoFamiliarEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.CuentaClabeEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.DescuentosEmitidosEndpoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.GuardarPrestamoSistrapEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.IncidenciaTitularGrupoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.PensionEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.PensionadoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.PrestamoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.PrestamosEnRecuperacionEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.TitularGrupoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.ReinstalacionPrestamoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.endpoint.PrestamosDescuentoNoAplicadoEndPoint.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.AltModEntFinancierasService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ConsultaDescuentosEmitidosService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ConsultaDescuentosPorSolicitudService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ConsultaEstadosSPESService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ConsultaPensionadoSistrapService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ConsultaPrestamosConDescuentoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ConsultaPrestamosEnCursoPorMovimientoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.CreateBitacoraService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.GetCuentaClabeService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.GuardarPrestamoSistrapService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.PerfilPersonaService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.PrestamosEnRecuperacionService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadCapacidadCreditoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadIncidenciaTitularGrupoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadPensionRegistroPensionadoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadPensionService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadPensionadoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadPrestamoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReadTitularGrupoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.RegistroPrestamoService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReinstalacionPrestamoObtenerEstatusService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReinstalacionPrestamoDatosTitularService.class);
        resources.add(mx.gob.imss.dpes.sistrap.service.ReinstalacionPrestamoDescuentoNoAplicadoService.class);
    }

}
