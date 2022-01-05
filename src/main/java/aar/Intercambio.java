package aar;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.xml.txw2.annotation.XmlElement;

/**
 * 
 * @author Oscar
 *
 */

@Entity
@XmlElement
public class Intercambio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false)
	private Kpi kpi1;

	@Column(nullable = false)
	private Kpi kpi2;

	@Column(nullable = true)
	private Double valor;

	@Column(nullable = true)
	private Double valorMax;

	@Column(nullable = true)
	private String date;

	public Intercambio() {

	}

	public Intercambio(Kpi kpi1, Kpi kp2) {

		this.kpi1 = kpi1;
		this.kpi2 = kp2;

	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public Kpi getKpi1() {
		return kpi1;
	}

	@XmlElement
	public void setKpi1(Kpi kpi1) {
		this.kpi1 = kpi1;
	}

	public Kpi getKpi2() {
		return kpi2;
	}

	@XmlElement
	public void setKpi2(Kpi kpi2) {
		this.kpi2 = kpi2;
	}

	public Double getValor() {
		return valor;
	}

	@XmlElement
	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDate() {
		return date;
	}

	@XmlElement
	public void setDate(String date) {
		this.date = date;
	}

	public Double getValorMax() {
		return valorMax;
	}

	@XmlElement
	public void setValorMax(Double valorMax) {
		this.valorMax = valorMax;
	}

}
