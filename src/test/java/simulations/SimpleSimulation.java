package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class SimpleSimulation extends Simulation {
    //http configuration
    HttpProtocolBuilder httpConfig = http.baseUrl("https://reqres.in")
            .header("Accept", "application/json")
            .header("content-type","application/json");

    //scenario
    ScenarioBuilder getListofAllUsers = scenario("List of users")
            .exec(
                    http("get all users").get("/api/users?page=2")
                            .check(status().is(200))
            );

    //setup
    {
        setUp(
                getListofAllUsers.injectOpen(atOnceUsers(1))
        ).protocols(httpConfig);
    }
}
