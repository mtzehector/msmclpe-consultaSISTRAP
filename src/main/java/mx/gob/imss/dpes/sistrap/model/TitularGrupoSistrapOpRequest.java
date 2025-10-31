/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.sistrap.model;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.TitularGrupoRequest;

/**
 *
 * @author antonio
 */
@Data
public class TitularGrupoSistrapOpRequest extends TitularGrupoSistrapRequest {
    private String op;
}
