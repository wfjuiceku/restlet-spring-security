package org.example.service;

import org.restlet.Restlet;
import org.restlet.resource.StringRepresentation;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;
import org.springframework.security.annotation.Secured;

public class AnonymousRestlet extends Restlet {

	public void handle(Request request, Response response) {
		response.setEntity(new StringRepresentation("ANONYMOUS CONTENT", MediaType.TEXT_PLAIN));
	}
}