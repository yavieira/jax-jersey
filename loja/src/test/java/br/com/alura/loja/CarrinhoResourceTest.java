package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class CarrinhoResourceTest {
	
	private Client client;
	private WebTarget target;

	@Before
	public void before() {
		Server.startServer();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8080");
	}

	@After
	public void after() {
		Server.stopServer();
	}

	@Test
	public void buscaCarrinho() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String content = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(new String(content));
		Assert.assertEquals("Sao Paulo", carrinho.getCidade());
	}
	
	@Test
	public void addCarrinho() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(300l, "Tablet", 999, 1));
		carrinho.setRua("Rua Padre Pompeu de Almeida");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();
		
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response resp = target.path("/carrinhos/add").request().post(entity); //Envia pro servidor 
		Assert.assertEquals(201, resp.getStatus());
		
		String location = resp.getHeaderString("Location"); //Busca pelo valor da location no Header
		String content = client.target(location).request().get(String.class); //Devolve o conteudo em String
		Assert.assertTrue(content.contains("Tablet")); //Confirma que o objeto contém o conteúdo adicionado independente da location
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setTarget(WebTarget target) {
		this.target = target;
	}
	
	public WebTarget getTarget() {
		return target;
	}
}