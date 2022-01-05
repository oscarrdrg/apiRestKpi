package aar;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Date;
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

@Path("/pairs")
public class PairsService {

	ServiceDao serviceDao = new ServiceDao();

	Logger log = Logger.getLogger(PairsService.class.getName());

	/* GET DE TODOS LOS INTERCAMBIOS */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Intercambio> getIntercambioJson() {
		return serviceDao.getAllIntercambios();
	}

	/* GET DE LOS INTERCAMBIOS ACTUALIZADOS */

	@GET
	@Path("/current/{id1}/{id2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIntercambioActualizado(@PathParam("id1") int id, @PathParam("id2") int id2)
			throws IOException, Exception {

		boolean exists = false;
		Intercambio aux = null;

		for (Intercambio intercambio : serviceDao.getAllIntercambios()) {
			if (id == intercambio.getKpi1().getId() && id2 == intercambio.getKpi2().getId()) {

				String webService = "http://www.randomnumberapi.com/api/v1.0/random"; // WEB SERVICE QUE VAMOS A
																						// UTILIZAR

				HttpClient client = HttpClient.newHttpClient(); // INICIALIZAMOS EL CLIENTE HTTP
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(webService)).GET().build(); // LLAMAMOS AL
																											// WEB
																											// SERVICE

				HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString()); // NOS DEVUELVE UN VALOR
																								// RANDOM
				/* CONVERTIRMOS EL VALOR [valor] EN UN DOUBLE */

				String valor = respuesta.body().split("\\[")[1];
				valor = valor.split("\\]")[0];

				double valorDouble = Double.parseDouble(valor);

				if (valorDouble > intercambio.getValorMax()) {
					intercambio.setValorMax(valorDouble);
					intercambio.setDate(new Date().toString());
					serviceDao.addValor(intercambio);
				}

				intercambio.setValor(valorDouble); // AGREGAMOS EL VALOR CONVERTIDO
				intercambio.setDate(new Date().toString()); // AÑADIMOS FECHA ACTUAL

				exists = true;
				aux = intercambio; // PASAMOS EL INTERCAMBIO AL AUXILIAR

			}

		}

		if (exists)
			return Response.status(200).entity(aux).build(); // ENVIAMOS EL AUX
		else
			return Response.status(400).entity("El intercambio no existe").build();

	}

	/* GET DE LOS INTERCAMBIOS CON EL VALOR MAX */

	@GET
	@Path("/max/{id1}/{id2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIntercambioMax(@PathParam("id1") int id, @PathParam("id2") int id2) {

		boolean exists = false;
		Intercambio aux = null;

		for (Intercambio intercambio : serviceDao.getAllIntercambios()) {
			if (id == intercambio.getKpi1().getId() && id2 == intercambio.getKpi2().getId()) {

				exists = true;
				aux = intercambio;

			}

		}

		if (exists) {
			return Response.status(200).entity("VALOR MAXIMO DEL INTERCAMBIO: " + aux.getValorMax() + "\n"
					+ "FECHA DE LA ACTUALIZACION: " + aux.getDate()).build();
		} else {

			return Response.status(400).entity("El intercambio no existe").build();
		}
	}

	/* AÑADIR INTERCAMBIO */

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addIntercambio(@FormParam("kpi1") int id, @FormParam("kpi2") int id2) {

		boolean existsKpi1 = false;
		boolean existsKpi2 = false;

		try {

			Kpi kpi1 = serviceDao.getKpi(id);
			Kpi kpi2 = serviceDao.getKpi(id2);

			for (Kpi kpi : serviceDao.getAllKpi()) {

				if (id == kpi.getId()) {
					existsKpi1 = true;
				}

			}

			for (Kpi kpi : serviceDao.getAllKpi()) {

				if (id2 == kpi.getId()) {
					existsKpi2 = true;
				}

			}

			if (kpi1.getId() == kpi2.getId())
				return Response.status(400).entity("Los kpis no pueden ser iguales dado que tienen el mismo valor")
						.build();

			if (existsKpi1 && existsKpi2) {
				serviceDao.addIntercambio(kpi1, kpi2);
				return Response.status(200).entity("addIntercambio -> Kpi1: " + id + ", Kpi2: " + id2).build();
			} else {
				return Response.status(400)
						.entity("El intercambio no se ha podido añadir puesto que no existe el kpi especificado")
						.build();
			}

		} catch (Exception e) {
			return Response.status(400)
					.entity("El intercambio no se ha podido añadir puesto que no existe el kpi especificado").build();
		}
	}

	/* BORRAR INTERCAMBIO */

	@DELETE
	@Path("/{id1}/{id2}")
	public Response removeIntercambio(@PathParam("id1") int id, @PathParam("id2") int id2) {
		boolean deletedOk = false;
		try {
			for (Intercambio intercambio : serviceDao.getAllIntercambios()) {
				if (id == intercambio.getKpi1().getId() && id2 == intercambio.getKpi2().getId()) {

					serviceDao.deleteIntercambio(intercambio.getId());
					deletedOk = true;

				} else {

					deletedOk = false;

				}
			}

			if (deletedOk == true) {
				log.log(Level.INFO, "deleted intercambio correctly ");
			} else {

				return Response.status(400).entity("El intercambio que intentas eliminar no existe").build();
			}

			return Response.status(200).entity("Deleted intercambio with Kpi1: " + id + " Kpi2: " + id2 + " correctly ")
					.build();
		} catch (Exception e) {
			return Response.status(400).entity("El intercambio que intentas eliminar no existe").build();
		}
	}

}
