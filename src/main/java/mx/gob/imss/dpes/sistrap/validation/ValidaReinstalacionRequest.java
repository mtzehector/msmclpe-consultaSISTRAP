package mx.gob.imss.dpes.sistrap.validation;

import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularRequest;

import javax.ws.rs.ext.Provider;

@Provider
public class ValidaReinstalacionRequest {

    public boolean validaRequest(ConsultaDatosTitularRequest req){
        if (req == null)
            return false;

        if (req.getNss() == null || req.getNss().isEmpty())
            return false;

        if (req.getIdGrupoFamiliar() == null || req.getIdGrupoFamiliar().isEmpty())
            return false;

        return true;
    }

}
