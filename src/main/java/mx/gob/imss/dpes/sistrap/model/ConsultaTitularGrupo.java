/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaTitularGrupoResponse;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
/**
 *
 * @author antonio
 */
public class ConsultaTitularGrupo extends BaseModel{
  @Getter @Setter PensionadoRequest request;
  @Getter @Setter ConsultaTitularGrupoResponse response;
}
