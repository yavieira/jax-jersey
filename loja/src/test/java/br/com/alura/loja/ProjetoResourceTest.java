package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoResourceTest {

	@Before
	public void before() {
		Server.startServer();
	}

	@After
	public void after() {
		Server.stopServer();
	}
	
	@Test
	public void buscaProjeto() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String content = target.path("/projetos/1")
				.request().get(String.class);
		Projeto projeto = (Projeto) new XStream().fromXML(content);
		Assert.assertEquals(1l, projeto.getId());
	}
	
	@Test
	public void addProjeto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		Projeto projeto = new Projeto(3l, "Projeto Teste", 2019);
		String xml = projeto.toXML();
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response resp = target.path("/projetos").request().post(entity);
		Assert.assertEquals(201, resp.getStatus());

		String location = resp.getHeaderString("Location");
		String content = client.target(location).request().get(String.class);
		Assert.assertTrue(content.contains("Teste")); //Confirma que o objeto contém o conteúdo adicionado independente da location
	}
}