package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {

	private static HttpServer server;

	//Grizzly -> Framework para criação de Servers escalonáveis
	//Parsing e Serializing de requisições e respostas HTTP 
	
	public static void main(String[] args) throws IOException {
		startServer();
		System.out.println("Servidor rodando");
		System.in.read();
		stopServer();
	}
	
	static HttpServer startServer() {
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja"); //Pacote do servidor
		URI uri = URI.create("http://localhost:8080/");
		server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		return server;
	}
	
	static void stopServer() {
		server.stop();
	}
}
