package aar;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Oscar
 *
 */

@Path("/kpis")
public class KpiService {

	ServiceDao serviceDao = new ServiceDao();

	Logger log = Logger.getLogger(KpiService.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kpi> getKpisJson() {
		return serviceDao.getAllKpi();
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addKpi(@FormParam("nombre") String nombre) {
		try {
			serviceDao.addKpi(nombre);
			log.log(Level.INFO, "Inserted kpi " + nombre);

			return Response.status(200).entity("addKpi -> nombre: " + nombre).build();
		} catch (Exception e) {
			return Response.status(400).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response removeKpi(@PathParam("id") int id) {
		try {
			boolean deletedOk = serviceDao.deleteKpi(id);

			if (deletedOk == true) {
				log.log(Level.INFO, "deleted kpi " + id + " correctly ");
				return Response.status(200).entity("Deleted kpi with id: " + id + " correctly ").build();

			} else {

				return Response.status(400).entity("El kpi que intentas eliminar no existe").build();
			}

		} catch (Exception e) {
			return Response.status(400).entity("El kpi que intentas eliminar no existe").build();
		}
	}

}
