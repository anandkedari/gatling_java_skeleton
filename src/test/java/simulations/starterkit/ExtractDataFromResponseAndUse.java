package simulations.starterkit;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ExtractDataFromResponseAndUse extends BaseConfigurations {
    HttpProtocolBuilder httpConfig = initiateSimulation();

    ScenarioBuilder correlationScenario = scenario("Extract Value from Response and use it in next  api")
            .exec(
                    http("Get all users").get("/api/users?page=2")
                            .check(jsonPath("$.data[0].id").saveAs("userId_0"))
            )
            .pause(2)
            .exec(
                    http("Get specific user").get("/api/users/#{userId_0}")
                            .check(jsonPath("$.data.first_name").is("Michael"))
                            .check(jsonPath("$.data.last_name").is("Lawson"))
                            .check(status().is(200))
            );

    {
        setUp(correlationScenario.injectOpen(atOnceUsers(1))).protocols(httpConfig);
    }
}
