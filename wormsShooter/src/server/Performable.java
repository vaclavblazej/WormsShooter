/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;

/**
 *
 * @author plach_000
 */
public interface Performable {
    public void perform(Socket socket);
}
