package br.com.alura.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoDAO {

	private static Map<Long, Projeto> banco = new HashMap<Long, Projeto>();
	private static AtomicLong contador = new AtomicLong(1);
	
	static {
		banco.put(1l, new Projeto(1l, "Minha loja", 2019));
		banco.put(2l, new Projeto(2l, "Alura", 2018));
	}
	
	public void adiciona(Projeto projeto) {
		long id = contador.incrementAndGet();
		projeto.setId(id);
		banco.put(id, projeto);
	}
	
	public Projeto busca(long id) {
		return banco.get(id);
	}
	
	public Projeto remove(long id) {
		return banco.remove(id);
	}
}
