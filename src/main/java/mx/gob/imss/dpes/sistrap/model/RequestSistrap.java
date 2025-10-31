/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.prestamo.model.Prestamo;
import mx.gob.imss.dpes.interfaces.solicitud.model.Solicitud;


/**
 *
 * @author eduardo.loyo
 */
@Data
public class RequestSistrap extends BaseModel{
    private Sistra sistra = new Sistra();
    private Prestamo prestamo  = new Prestamo();
    private Solicitud solicitud = new Solicitud();
    private Pensionado pensionado = new Pensionado();
    private String registros;
}
