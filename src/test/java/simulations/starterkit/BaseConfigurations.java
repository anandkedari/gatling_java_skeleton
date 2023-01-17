package simulations.starterkit;

import config.starterkit.constants;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public class BaseConfigurations extends Simulation {
    constants co = new constants();
    public String getBaseUrl(){
        return co.BASE_URL;
    }

    public HttpProtocolBuilder initiateSimulation(){
        return http.baseUrl(getBaseUrl())
                .header("Accept", "application/json")
                .header("content-type","application/json");
    }

    public void runSimulation(){

    }
}
