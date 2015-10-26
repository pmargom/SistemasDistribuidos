/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import common.IServidor;
import common.Mensaje;
import static java.lang.Math.random;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author pedro
 */
public class Servidor implements IServidor {

    private Map<Integer, String> sesion_nombre = new HashMap<Integer, String>();
    private Map<String, Integer> nombre_sesion = new HashMap<String, Integer>();
    private Map<Integer, List<Integer>> contactos = new HashMap<Integer, List<Integer>>();
    private Map<Integer, List<Mensaje>> buffer = new HashMap<Integer, List<Mensaje>>();
    
    @Override
    public int autenticar(String nombre) throws RemoteException {
        int sesionUsuario = getSesion();
        
        sesion_nombre.put(sesionUsuario, nombre);
        nombre_sesion.put(nombre, sesionUsuario);
        
        return sesionUsuario;
    }

    @Override
    public int agregar(String nombre, int sesion) throws RemoteException {
        if (!sesion_nombre.containsKey(sesion)) {
            throw new RuntimeException("Sesion invalida");
        }
        
        if (!nombre_sesion.containsKey(nombre)) {
            throw new RuntimeException(nombre + " no está conectado");
        }
        
        List<Integer> misContactos = contactos.get(sesion);
        if (misContactos == null) {
            misContactos = new LinkedList<Integer>();
            contactos.put(sesion, misContactos);
        }
        
        misContactos.add(nombre_sesion.get(nombre));
        
        return nombre_sesion.get(nombre);

    }

    @Override
    public void enviar(String mensaje, int sesionDe, int sesionA) throws RemoteException {
        if (!sesion_nombre.containsKey(sesion)) {
            throw new RuntimeException("Sesion invalida");
        }
        
        if (!sesion_nombre.containsKey(sesionA)) {
            throw new RuntimeException("Contacto no está conectado");
        }
        
        if (!contactos.get(sesionDe).contains(sesionA)){            
            throw new RuntimeException(sesion_nombre.get(sesionA) + " No es parte de sus contactos");
        }
        
        List <Mensaje> mensajes = buffer.get(sesionA);
        
        if (mensajes == null) {
            mensajes = new LinkedList<Mensaje>();
            buffer.put(sesionA, mensajes);
        }
        
        mensajes.add(new Mensaje(mensaje, sesion_nombre.get(sesionDe)));
    }

    @Override
    public List<Mensaje> recibir(int sesion) throws RemoteException {
        if (!sesion_nombre.containsKey(sesion)) {
            throw new RuntimeException("Sesion inválida");
        }
        
        return buffer.get(sesion);
    }
    
    @Override
    public void limpiarBuffer(int sesion) throws RemoteException {
        if (!sesion_nombre.containsKey(sesion)) {
            throw new RuntimeException("Sesion inválida");
        }
        
        buffer.get(sesion).clear();
    }
    
    private static int sesion = new Random().nextInt();
    
    public static int getSesion() {
        return ++sesion;
    }

    
}
