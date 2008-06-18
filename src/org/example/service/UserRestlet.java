package org.example.service;

import org.restlet.Restlet;
import org.restlet.resource.StringRepresentation;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;
import org.springframework.security.annotation.Secured;

public class UserRestlet extends Restlet {
	@Override
	@Secured("ROLE_USER")
	public void handle(Request request, Response response) {
		response.setEntity(new StringRepresentation("USER CONTENT", MediaType.TEXT_PLAIN));
	}
}