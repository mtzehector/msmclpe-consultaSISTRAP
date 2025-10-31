/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamoRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaPrestamoResponse;

/**
 *
 * @author antonio
 */
public class ConsultaPrestamo extends BaseModel{
  @Getter @Setter ConsultaPrestamoRequest request;
  @Getter @Setter ConsultaPrestamoResponse  response;
}
