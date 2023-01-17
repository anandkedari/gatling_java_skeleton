package simulations.starterkit;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SimplePutAndDeleteSimulation extends BaseConfigurations {

    //http configuration
    HttpProtocolBuilder httpConfig = initiateSimulation() ;
    //scenario
    ScenarioBuilder createUsers = scenario("Update user details and then delete")
            .exec(
                    http("Update user 2").put("/api/users/2")
                            .body(RawFileBody("jsonRequestBodies/starterkit/createUser.json")).asJson()
                            .check(status().in(200, 201))
            )
            .exec(
                    http("Delete user 2").delete("/api/users/2")
                            .check(status().is(204))
            );
    //setup
    {
        setUp(
                createUsers.injectOpen(atOnceUsers(5))
        ).protocols(httpConfig);
    }
}
