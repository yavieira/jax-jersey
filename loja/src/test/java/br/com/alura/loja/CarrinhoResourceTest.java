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
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals("Sao Paulo", carrinho.getCidade());
	}
	
	@Test
	public void addCarrinho() {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(300l, "Tablet", 999, 1));
		carrinho.setRua("Rua Padre Pompeu de Almeida");
		carrinho.setCidade("Sao Paulo");
		Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
		
		Response resp = target.path("/carrinhos/add").request().post(entity); //Envia pro servidor 
		Assert.assertEquals(201, resp.getStatus());
		String location = resp.getHeaderString("Location"); //Busca pelo valor da location no Header
		
		Carrinho carrinhoAdd = client.target(location).request().get(Carrinho.class); //Devolve o conteudo em String
		Assert.assertEquals("Tablet", carrinhoAdd.getProdutos().get(0)); //Confirma que o objeto contém o conteúdo adicionado independente da location
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