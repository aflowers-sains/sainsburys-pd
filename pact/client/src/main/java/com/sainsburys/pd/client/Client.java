package com.sainsburys.pd.client;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.slf4j.LoggerFactory;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;

public class Client {
    private final String serverAddress;

    public static void main(String[] args) {
        Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);

        new Client("http://localhost:8080").addNumbers(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int addNumbers(int first, int second) {
        ResteasyClient client = new ResteasyClientBuilderImpl().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(serverAddress))
                .path("/add")
                .queryParam("first", first)
                .queryParam("second", second);

        Response response = target.request().buildGet().invoke();

        if(response.getStatus() == 200) {
            return new Gson().fromJson(response.readEntity(String.class), JsonObject.class).get("answer").getAsInt();
        } else {
            System.err.println("Error :: " + new Gson().fromJson(response.readEntity(String.class), JsonObject.class).get("error").getAsString());
            return 0;
        }
    }
}
