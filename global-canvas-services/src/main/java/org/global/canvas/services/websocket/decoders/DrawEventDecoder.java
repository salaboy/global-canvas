/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.websocket.decoders;

import com.grogdj.grogshop.core.model.DrawEvent;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author grogdj
 */
public class DrawEventDecoder implements Decoder.Text<DrawEvent> {

    @Override
    public DrawEvent decode(String jsonMessage) throws DecodeException {
        
        JsonObject jsonObject = Json
                .createReader(new StringReader(jsonMessage)).readObject();
        DrawEvent drawEvent = new DrawEvent(jsonObject.getString("user"), jsonObject.getInt("x"),
                jsonObject.getInt("y"), jsonObject.getString("type"), jsonObject.getString("color"));
        
        return drawEvent;
        
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            Json.createReader(new StringReader(jsonMessage)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("DrawEventDecoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("DrawEventDecoder - destroy method called");
    }
}
