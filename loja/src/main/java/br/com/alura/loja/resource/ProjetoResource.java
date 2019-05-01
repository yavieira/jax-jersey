package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Projeto buscaXML(@PathParam("id") long id) {
		Projeto projeto = new ProjetoDAO().busca(id);
		return projeto;
	}
	
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	@GET
//	public String buscaJson(@PathParam("id") long id) {
//		Projeto projeto = new ProjetoDAO().busca(id);
//		return projeto.toJson();
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("add")
	public Response addProjeto(Projeto projeto) {
		new ProjetoDAO().adiciona(projeto);
		URI uri = URI.create("/projetos/" + projeto.getId()); //Caminho de onde o objeto foi criado
		return Response.created(uri).build();
	}
	@Path("remove")
	public Response removeProjeto(@PathParam("id") long id) {
		Projeto projeto = new ProjetoDAO().busca(id);
		projeto.remove(id);
		return Response.ok().build();
	}
}
