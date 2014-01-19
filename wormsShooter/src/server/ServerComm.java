/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.awt.Dimension;
import java.rmi.Remote;
import java.rmi.RemoteException;
import utilities.Action;
import utilities.SerializableBufferedImage;

/**
 *
 * @author plach_000
 */
public interface ServerComm extends Remote {

    Color getPixel(int x, int y) throws RemoteException;

    Dimension getSize() throws RemoteException;

    SerializableBufferedImage getMapSerializable() throws RemoteException;

    void sendAction(Action action) throws RemoteException;
    
}
