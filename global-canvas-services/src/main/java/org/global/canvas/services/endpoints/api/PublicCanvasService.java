package org.global.canvas.services.endpoints.api;



import org.global.canvas.services.endpoints.exceptions.ServiceException;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by salaboy on 27/04/15.
 */
@Local
@Path("/public/canvas")
public interface PublicCanvasService {
    @Path("{id}/image")
    @GET
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    Response getImageByUser(@NotNull @PathParam("id") String user) throws ServiceException;


    @Path("merge")
    @GET
    @Produces({"application/json"})
    Response merge() throws ServiceException;

}
