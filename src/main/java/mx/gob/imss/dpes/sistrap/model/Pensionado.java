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
public class Pensionado extends BaseModel{

        
    private String idNss; //": "32703211261",
    private String idGrupoFamiliar; //": "01", 
    private String nomApellidoPaterno; //": "ALDAPE",       
    private String nomApellidoMaterno; //": "ANZUA",
    private String nomNombre; //": "MARIA DEL CARMEN",
    private String cveCurp; //": "AAAC370101MCLLNR00",
    private String cveRfc; //": "AAAC370101RW3",
    private String fecNacimiento; //": "1937-01-01",
    private String cveCtaPago; //": 1476190766,
    private String numClabe; //": "012075014761907668",
    private String idTipoCuentaPago; //": null,
    private String cveEntidadFederativa; //": "05",   
    private String descEntidadFederativa; 
    private String cveSubdelegacion; //": "12",
    private String cveDelegacion; //": "05"F
    private String descDelegacion; 
    private Integer sexo;
}
