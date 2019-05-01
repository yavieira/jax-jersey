package br.com.alura.loja.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Projeto {

	private long id;
	private String nome;
	private int anoDeInicio;
	private List<Projeto> projetos = new ArrayList<Projeto>();

	public Projeto(Long id, String nome, int anoDeInicio) {
		this.id = id;
		this.nome = nome;
		this.anoDeInicio = anoDeInicio;
	}

	public Projeto() {
		//Jax-B necessita de um construtor sem argumentos
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public int getAnoDeInicio() {
		return anoDeInicio;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setAnoDeInicio(int anoDeInicio) {
		this.anoDeInicio = anoDeInicio;
	}

	public String toXML() {
		return new XStream().toXML(this);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	public void remove(long id) {
		for (Iterator<Projeto> iterator = projetos.iterator(); iterator.hasNext();) {
			Projeto projeto = (Projeto) iterator.next();
			if (projeto.getId() == id) {
				iterator.remove();
			}
		}
	}
}
