/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.api;

import com.grogdj.grogshop.core.model.DrawEvent;
import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author salaboy
 */
public interface SocketsService {

    int getNroOfClients();

    void push(DrawEvent event, Session client) throws IOException, EncodeException;

    void put(String clientId, Session session);
    
}
