package gestioneScuola;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Prova {
	
	private LocalDateTime dataOra;
	private String cfAllievo;
	private String cfDocente;
	private String materia;
	private int abilitato;

	public Prova(LocalDateTime dataOra, String cfAllievo, String cfDocente, String materia) {
		this.dataOra = dataOra;
		this.cfAllievo = cfAllievo;
		this.cfDocente = cfDocente;
		this.materia = materia;
	}

	public int getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(int abilitato) {
		this.abilitato = abilitato;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public String getCfAllievo() {
		return cfAllievo;
	}

	public void setCfAllievo(String cfAllievo) {
		this.cfAllievo = cfAllievo;
	}

	public String getCfDocente() {
		return cfDocente;
	}

	public void setCfDocente(String cfDocente) {
		this.cfDocente = cfDocente;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}
}
