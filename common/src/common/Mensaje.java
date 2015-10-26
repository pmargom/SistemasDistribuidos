/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

import java.io.Serializable;

/**
 *
 * @author pedro
 */
public class Mensaje implements Serializable {

    private final String cuerpo, remitente;
    
    public Mensaje(String cuerpo, String remitente){
    
        this.cuerpo = cuerpo;
        this.remitente = remitente;
        
    }
    
    public String getCuerpo() {
        return cuerpo;
    }
    
    public String getRemitente() {
        return remitente;
    }
    
}
