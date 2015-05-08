/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.impl;

import org.global.canvas.services.api.SocketsService;
import com.grogdj.grogshop.core.model.DrawEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class SocketsServiceImpl implements SocketsService {

    private Map<String, Session> clients = new HashMap<String, Session>();

    @Override
    public void put(String clientId, Session session) {
        clients.put(clientId, session);
    }

    @Override
    public int getNroOfClients() {
        return clients.size();
    }

    @Override
    public void push(DrawEvent event, Session client) throws IOException, EncodeException {
        for (String clientName : clients.keySet()) {
            Session c = clients.get(clientName);
            if (c != client) {
                if (c.isOpen()) {
                    c.getBasicRemote().sendObject(event);
                }
            }
        }
    }
}
