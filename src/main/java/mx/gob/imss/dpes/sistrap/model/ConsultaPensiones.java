/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaPensionesResponse;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.sistrap.model.CurpRequest;

/**
 *
 * @author antonio
 */
public class ConsultaPensiones extends BaseModel {

    @Getter
    @Setter
    CurpRequest request;
    @Getter
    @Setter
    ConsultaPensionesResponse response;
    @Getter
    @Setter
    private Long sesion;
}
