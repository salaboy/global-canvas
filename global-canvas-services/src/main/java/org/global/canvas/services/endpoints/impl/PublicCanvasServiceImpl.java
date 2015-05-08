package org.global.canvas.services.endpoints.impl;


import org.apache.batik.transcoder.TranscoderException;
import org.global.canvas.services.api.CanvasService;
import org.global.canvas.services.api.StorageService;
import org.global.canvas.services.endpoints.api.PublicCanvasService;
import org.global.canvas.services.endpoints.exceptions.ServiceException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by salaboy on 27/04/15.
 */
@Stateless
public class PublicCanvasServiceImpl implements PublicCanvasService {
    @Inject
    private CanvasService canvasService;

    @Inject
    private StorageService storageService;

    @Override
    public Response getImageByUser(@NotNull @PathParam("id") String user) throws ServiceException {
        System.out.println("User in getImageByUser: "+user);
        final byte[] userImage = canvasService.getCurrentState();
        if(userImage != null){

            return Response.ok().entity(new StreamingOutput() {
                @Override
                public void write(OutputStream output)
                        throws IOException, WebApplicationException {
                    output.write(userImage);
                    output.flush();
                }
            }).build();
        }else{
            System.out.println("image null: "+user);
            return Response.ok().entity("user image not found").build();
        }
    }

    public Response merge() throws ServiceException {
        try {
            storageService.merge();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TranscoderException e) {
            e.printStackTrace();
        }
        return Response.ok().entity("merged!").build();
    }


}
