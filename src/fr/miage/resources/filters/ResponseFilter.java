package fr.miage.resources.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
 
public class ResponseFilter implements ContainerResponseFilter {


	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHttpHeaders().add("Access-Control-Allow-Referer", "*");
		response.getHttpHeaders().add("Access-Control-Allow-Headers", "accept, origin, referer, content-type");
		response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		response.getHttpHeaders().add("Access-Control-Expose-Headers", "Location");
		return response;
	}
 
}