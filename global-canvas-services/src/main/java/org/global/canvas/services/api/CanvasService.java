package org.global.canvas.services.api;

/**
 * Created by salaboy on 27/04/15.
 */
public interface CanvasService {
    byte[] getImageByUser(String user);
    byte[] getCurrentState();
    void setCurrentState(byte[] currentState);
}
