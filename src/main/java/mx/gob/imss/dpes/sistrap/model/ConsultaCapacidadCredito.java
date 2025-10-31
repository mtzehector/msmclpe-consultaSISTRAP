/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadRequestAux;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaCapacidadResponse;
/**
 *
 * @author antonio
 */
public class ConsultaCapacidadCredito extends BaseModel{
  @Getter @Setter ConsultaCapacidadRequestAux request;
  @Getter @Setter ConsultaCapacidadResponse  response;
}
