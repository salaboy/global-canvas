/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.websocket;


import com.grogdj.grogshop.core.model.DrawEvent;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.global.canvas.services.api.CreditsService;

import org.global.canvas.services.api.DrawService;
import org.global.canvas.services.api.StorageService;
import org.global.canvas.services.api.SocketsService;
import org.global.canvas.services.endpoints.exceptions.ServiceException;
import org.global.canvas.services.websocket.decoders.DrawEventDecoder;
import org.global.canvas.services.websocket.encoders.DrawEventEncoder;

/**
 *
 * @author grogdj
 */
@ServerEndpoint(value = "/draw",
        decoders = {DrawEventDecoder.class},
        encoders = {DrawEventEncoder.class})

public class DrawWSEndpoint {

    @Inject
    private StorageService storageService;

    @Inject
    private SocketsService socketsService;

    @Inject
    private DrawService drawService;
    
    @Inject CreditsService creditsService;

    @OnOpen
    public void onOpen(Session client) {
        List<String> users = client.getRequestParameterMap().get("user");

        if (users != null) {
            System.out.println("users: " + users.size());
            for (String user : users) {
                System.out.println("user: " + user);
                System.out.println("OnOpen  Web Socket: " + user);

                socketsService.put(user, client);
                System.out.println("Number of Clients: " + socketsService.getNroOfClients());
                creditsService.addUser(user);
            }
        } else {
            System.out.println("No User sent");
        }

//     
    }

    @OnMessage
    public void onMessage(DrawEvent event, Session client) throws EncodeException, IOException {

        drawService.draw(event);
        socketsService.push(event, client);
    }

    @OnClose
    public void onClose(Session client) throws ServiceException {
        System.out.println("OnClose  Web Socket: ");
        drawService.dispose();
    }

}
