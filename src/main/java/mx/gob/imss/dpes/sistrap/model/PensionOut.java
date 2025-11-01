/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;

/**
 *
 * @author eduardo.loyo
 */
@Data
public class PensionOut extends BaseModel{
    
    private String nss;//": "56967917545",
    private String grupoFamiliar;//": "01",
    private String tipoPension;//": "AS",
    private String regimen;//": "73",
    private String indIncidencia;//": "VG",
    private String tipoTrabajador;//"//: "Pensionado"

}
