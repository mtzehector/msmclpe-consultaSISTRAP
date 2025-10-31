package mx.gob.imss.dpes.sistrap.endpoint;

import mx.gob.imss.dpes.common.constants.Constantes;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.PageRequestModel;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.interfaces.solicitud.model.SolicitudOEFRequest;
import mx.gob.imss.dpes.sistrap.entity.Reinstalacion;
import mx.gob.imss.dpes.sistrap.exception.ReinstalacionPrestamoException;
import mx.gob.imss.dpes.sistrap.repository.PaginationUtil;
import mx.gob.imss.dpes.sistrap.service.ReinstalacionPrestamoDescuentoNoAplicadoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;

@Path("/prestamo")
@RequestScoped
public class PrestamosDescuentoNoAplicadoEndPoint extends BaseGUIEndPoint<BaseModel, BaseModel, BaseModel> {

    @Inject
    private ReinstalacionPrestamoDescuentoNoAplicadoService descuentoNoAplicadoService;


    @POST
    @Path("/descuentosNoAplicados")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerListPrestamosDescuentoNoAplicado(PageRequestModel<SolicitudOEFRequest> request) {
        try {
            List<Reinstalacion> listPrestamos =
                descuentoNoAplicadoService.obtenerListPrestamosDescuentoNoAplicado(request);

            if (listPrestamos == null || listPrestamos.isEmpty())
                return Response.noContent().build();

            Pageable pageable = new PageRequest((request.getPage() - 1), 10);
            Page<Reinstalacion> page = PaginationUtil.paginateList(pageable, listPrestamos);
            return Response.ok(page).build();
        } catch (BusinessException e) {
            return toResponse(new Message<>(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE,
                "ERROR PrestamosDescuentoNoAplicadoEndPoint.obtenerListPrestamosDescuentoNoAplicado()", e);
        }
        return toResponse(
            new Message<>(
                null,
                ServiceStatusEnum.EXCEPCION,
                new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            )
        );
    }

    @POST
    @Path("/reporteExcel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerListDescuentoNoAplicado(PageRequestModel<SolicitudOEFRequest> request) {
        try {
            List<Reinstalacion> listPrestamos =
                descuentoNoAplicadoService.obtenerListPrestamosDescuentoNoAplicado(request);

            if (listPrestamos == null || listPrestamos.isEmpty())
                return Response.noContent().build();

            return Response.ok(listPrestamos).build();
        } catch (BusinessException e) {
            return toResponse(new Message<>(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE,
                "ERROR PrestamosDescuentoNoAplicadoEndPoint.obtenerListDescuentoNoAplicado()", e);
        }
        return toResponse(
            new Message<>(
                null,
                ServiceStatusEnum.EXCEPCION,
                new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            )
        );
    }

    @GET
    @Path("/descuentoNoAplicado/{idSolPrestamo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamoDescuentoNoAplicado(@PathParam("idSolPrestamo") String idSolPrestamo) {
        try {
            Reinstalacion reinstalacion = descuentoNoAplicadoService.obtenerPrestamoPorFolioSIPRE(idSolPrestamo);
            if (reinstalacion == null)
                return Response.noContent().build();

            return Response.ok(reinstalacion).build();
        } catch (BusinessException e) {
            return toResponse(new Message<>(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE,
                "ERROR PrestamosDescuentoNoAplicadoEndPoint.obtenerPrestamoDescuentoNoAplicado(" + idSolPrestamo +
                    ")", e);
        }

        return toResponse(
            new Message<>(
                null,
                ServiceStatusEnum.EXCEPCION,
                new ReinstalacionPrestamoException(ReinstalacionPrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            )
        );
    }

}
