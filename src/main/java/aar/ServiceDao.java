package aar;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Oscar
 *
 */

public class ServiceDao {

	Logger log = Logger.getLogger(ServiceDao.class.getName());

	final DatabaseService d = new DatabaseService();

	public int addKpi(String nombre) {
		try {
			Kpi kpi = new Kpi(nombre);
			d.insert(kpi);
		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
		}
		return 1;
	}

	public int addValor(Intercambio intercambio) {
		d.addValorMax(intercambio);
		return 1;
	}

	public int addIntercambio(Kpi kpi1, Kpi kpi2) {

		try {

			Intercambio intercambio = new Intercambio(kpi1, kpi2);

			boolean existe = false;

			for (Intercambio intercambioAux : getAllIntercambios()) {
				if (intercambio.getKpi1().getId() == intercambioAux.getKpi1().getId()
						&& intercambio.getKpi2().getId() == intercambioAux.getKpi2().getId()) {

					existe = true;
					log.log(Level.INFO, "EL INTERCAMBIO YA EXISTE");

				}
			}

			if (existe == false) {
				intercambio.setValorMax(0.0);
				d.insertIntercambio(intercambio);
			}

		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
		}
		return 1;
	}

	public List<Kpi> getAllKpi() {
		try {
			return d.findAll();
		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public List<Intercambio> getAllIntercambios() {

		try {
			return d.findAllIntercambios();
		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public Kpi getKpi(int id) {
		return d.read(id);
	}

	public Intercambio getIntercambio(int id) {

		return d.readIntercambio(id);
	}

	public boolean deleteKpi(int id) {

		for (Intercambio intercambio : getAllIntercambios()) {
			if ((id == intercambio.getKpi1().getId()) || (id == intercambio.getKpi2().getId())) {

				deleteIntercambio(intercambio.getId());

			}
		}
		return d.delete(id);
	}

	public boolean deleteIntercambio(int id) {
		return d.deleteIntercambio(id);
	}

}