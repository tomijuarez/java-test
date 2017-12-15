import javax.ws.rs.*;
import javax.ws.rs.core.*;

import dto.PersonaDTO;
import model.Persona;
import java.net.HttpURLConnection;
import java.util.List;

@Path("/persona")
public class PersonService {

    @POST
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPersona(
            @PathParam("userid") long userId,
            PersonaDTO personaDto) {

        //Verificación de parámetros ya que jax-rs no posee annotation @NotNull
        if (personaDto == null || personaDto.getDni() == null || personaDto.getApellido() == null || personaDto.getNombre() == null || personaDto.getEdad() == -1)
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("Los parámetros 'nombre', 'apellido', 'edad' y 'dni' son requeridos.")
                            .build()
            );

        if (DBManager.getInstance().exists(userId))
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("El usuario con id "+userId+" ya existe en la base de datos.")
                            .build()
            );

        Persona persona = new Persona(userId, personaDto.getDni(), personaDto.getNombre(), personaDto.getApellido(), personaDto.getEdad());
        DBManager.getInstance().insert(persona);

        return Response
                .status(HttpURLConnection.HTTP_OK)
                .entity("El usuario con id "+ userId+" fue creado.")
                .build();
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPersonas() {

        List<Persona> personas = DBManager.getInstance().getPersonas();

        return Response
                .status(HttpURLConnection.HTTP_OK)
                .entity(personas)
                .build();
    }
}