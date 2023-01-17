package simulations.starterkit;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class No1_SimpleGet extends BaseConfigurations {
    //http configuration
    HttpProtocolBuilder httpConfig = initiateSimulation();

    //scenario
    ScenarioBuilder getListOfAllUsers = scenario("Fetch list of users")
            .exec(
                    http("Get all users").get("/api/users?page=2")
                            .check(status().is(200))
            );

    //setup
    {
        setUp(
                getListOfAllUsers.injectOpen(atOnceUsers(10))
        ).protocols(httpConfig);
    }
}
