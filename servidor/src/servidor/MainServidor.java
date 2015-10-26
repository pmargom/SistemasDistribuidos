/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import common.IServidor;
import common.Utils;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author pedro
 */
public class MainServidor {

    public static void main(String[] args) throws Exception {
        
        Utils.setCodeBase(IServidor.class);
        
        Servidor servidor = new Servidor();
        //Remote remote = UnicastRemoteObject.exportObject(servidor, 9000); // no poner puertos menores a 1024 porque necesitan permisos (Linux)
        // preparamos el middleware
        IServidor remote = (IServidor)UnicastRemoteObject.exportObject(servidor, 8888); // no poner puertos menores a 1024 porque necesitan permisos (Linux)
        
        
        Registry registry = LocateRegistry.createRegistry(9000); //LocateRegistry.getRegistry(); // usa el puerto por defecto para el proxy que necesitamos
        registry.rebind("Pepito", remote);
        
        System.out.println("Servidor arrancado, presione enter para terminar");
        System.in.read();
        
        registry.unbind("Pepito");
        UnicastRemoteObject.unexportObject(servidor, true);
    }
    
}
