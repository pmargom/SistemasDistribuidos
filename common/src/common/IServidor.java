/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author pedro
 */
public interface IServidor extends Remote {
    
    public int autenticar(String nombre) throws RemoteException;
    
    public int agregar(String nombre, int sesion) throws RemoteException;
    
    public void enviar(String mensaje, int sesionDe, int sesionA) throws RemoteException;
    
    public List<Mensaje>recibir(int sesion) throws RemoteException;
 
    public void limpiarBuffer(int sesion) throws RemoteException;
}
