package com.excel.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oauthLogin")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private Client client;
	
	public LoginController() {
		this.client = ClientBuilder.newClient();
	}
	
//	@POST
	@RequestMapping("token")
    @Produces("application/json")
	@ResponseBody
    public String exchangeAuthorizationCode(@RequestParam("code") String code, @Context final HttpServletResponse servletResponse) throws IOException {
		logger.info("/auth/oauth2 step 1 start");
        // Step 1. Exchange authorization code for access token.
        final MultivaluedStringMap accessData = new MultivaluedStringMap();
        accessData.add("client_id", "TEST_LOGIN_CLIENT");
        accessData.add("redirect_uri", "http://localhost:8090");
        accessData.add("client_secret", "f39cc0ab-9223-403e-9cc0-ab9223703e96");
        accessData.add("code", code);
        accessData.add("grant_type", "authorization_code");
        //anita.zhu added
        //accessData.add("scope", "read");
        
        //logger.info("/auth/oauth2 serverConfiguration.getTokenEndpoint()=" + serverConfiguration.getTokenEndpoint());
        //logger.info("/auth/oauth2 serverConfiguration.accessData CLIENT_ID_KEY=" + accessData.get(CLIENT_ID_KEY) + " "
        //		+ " REDIRECT_URI_KEY = " + accessData.get(REDIRECT_URI_KEY)
        //		+ " CLIENT_SECRET= " + accessData.get(CLIENT_SECRET)
        //		+ " CODE_KEY= " + accessData.get(CODE_KEY)
        //		+ " GRANT_TYPE_KEY=" + accessData.get(GRANT_TYPE_KEY));
        //anita.zhu added
        
        String url = "http://10.4.2.76:8092/portal/oauth/token";
        Response response = client.target(url)
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.form(accessData));
        accessData.clear();
       return response.readEntity(String.class);
       // return response;
        //logger.info("/auth/oauth2 serverConfiguration.getAccessTokenProperty()=" + serverConfiguration.getAccessTokenProperty());
        //final String accessToken = (String) getResponseEntity(response).get(serverConfiguration.getAccessTokenProperty());
        //return authenticateUser(accessToken, servletResponse);
    }

	
	
}
