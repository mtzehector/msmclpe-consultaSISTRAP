/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;

/**
 *
 * @author antonio
 */
public class Pension extends BaseModel{
  @Getter @Setter String nss;
  @Getter @Setter String grupoFamiliar;
  @Getter @Setter String tipoPension;
  @Getter @Setter String regimen;
  @Getter @Setter String incidenciaPension;
  @Getter @Setter String tipoTrabajador;
  @Getter @Setter String telefono;
}
