/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.websocket.encoders;

import com.grogdj.grogshop.core.model.DrawEvent;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author grogdj
 */
public class DrawEventEncoder implements Encoder.Text<DrawEvent> {

    @Override
    public String encode(DrawEvent drawEvent) throws EncodeException {
        
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("user", drawEvent.getUser())
                .add("x", drawEvent.getX())
                .add("y", drawEvent.getY())
                .add("type", drawEvent.getType())
                .add("color", drawEvent.getColor())
                .build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("DrawEventEncoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("DrawEventEncoder - destroy method called");
    }

}
