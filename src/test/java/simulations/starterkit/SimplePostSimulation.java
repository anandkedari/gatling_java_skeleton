package simulations.starterkit;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
public class SimplePostSimulation extends BaseConfigurations {

    //http configuration
    HttpProtocolBuilder httpConfig = initiateSimulation() ;
    //scenario
    ScenarioBuilder createUsers = scenario("Create new user").exec(
              http("Create user").post("/api/users")
                      .body(RawFileBody("jsonRequestBodies/starterkit/createUser.json")).asJson()
                      .check(status().is(201))
    );
    //setup
    {
        setUp(
                createUsers.injectOpen(atOnceUsers(10))
        ).protocols(httpConfig);
    }
}
