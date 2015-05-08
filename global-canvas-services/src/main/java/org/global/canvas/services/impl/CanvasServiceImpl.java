package org.global.canvas.services.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.global.canvas.services.api.CanvasService;
import org.global.canvas.services.api.StorageService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by salaboy on 27/04/15.
 */
@ApplicationScoped
public class CanvasServiceImpl implements CanvasService {

    private byte[] currentState;

    public byte[] getImageByUser(String user) {
        try {

            InputStream resourceAsStream = new FileInputStream("/tmp/canvas/last-snapshot.png");
            if(resourceAsStream != null) {
                System.out.println(">>>Found the file: ");
                return Base64.encodeBase64(IOUtils.toByteArray(resourceAsStream));
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public byte[] getCurrentState(){
        return Base64.encodeBase64(currentState);
    }

    public void setCurrentState(byte[] currentState){
        this.currentState = currentState;
    }


}
