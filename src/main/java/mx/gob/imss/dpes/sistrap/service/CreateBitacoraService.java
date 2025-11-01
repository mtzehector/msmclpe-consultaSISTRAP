package mx.gob.imss.dpes.sistrap.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BadRequestException;
import mx.gob.imss.dpes.interfaces.bitacora.model.BitacoraInterfaz;
import mx.gob.imss.dpes.sistrap.restclient.BitacoraClient;

@Provider
public class CreateBitacoraService extends ServiceDefinition<BitacoraInterfaz, BitacoraInterfaz> {

    @Inject
    @RestClient
    private BitacoraClient client;

    @Override
    public Message<BitacoraInterfaz> execute(Message<BitacoraInterfaz> request) throws BusinessException {

        try {
            if (request.getPayload().getIdTipoServicio() == null)
                throw new BadRequestException();

            Response bitacoraInterfazResponse = client.createInterfaz(request.getPayload());
            if(bitacoraInterfazResponse != null && bitacoraInterfazResponse.getStatus() == 200)
                return request;
            else
                log.log(Level.WARNING, "CreateBitacoraService.execute request = [" + request +
                        "] - Hubo un problema con la respuesta del servicio");
        }
        catch (Exception e) {
            log.log(Level.WARNING, "CreateBitacoraService.execute request = [" + request + "]", e);
        }

        return request;
    }

/*
    public Message<BitacoraInterfaz> createInterfaz(Message<BitacoraInterfaz> request) throws BusinessException {

        log.log(Level.INFO, ">>>CreateBitacoraService createInterfaz={0}", request.getPayload());

        if (request.getPayload().getIdTipoServicio() == null) {
            return response(null, ServiceStatusEnum.EXCEPCION, new BadRequestException(), null);
        }

        Response event = client.createInterfaz(request.getPayload());
        if (event.getStatus() == 200) {
            return request;
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new BadRequestException(), null);
    }
*/

}
