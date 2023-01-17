package simulations.starterkit;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class No3_SimplePostwithGet extends BaseConfigurations {

    //http configuration
    HttpProtocolBuilder httpConfig = initiateSimulation() ;
    //scenario
    ScenarioBuilder createUsers = scenario("Create new user and then fetch all users")
            .exec(
                    http("Create user")
                            .post("/api/users")
                            .body(RawFileBody("jsonRequestBodies/starterkit/createUser.json")).asJson()
                            .check(status().is(201))
            )
            .pause(2)
            .exec(
                    http("Fetch all users")
                            .get("/api/users?page=2")
                            .check(status().is(200))
            );
    //setup
    {
        setUp(
                createUsers.injectOpen(atOnceUsers(5))
        ).protocols(httpConfig);
    }
}
