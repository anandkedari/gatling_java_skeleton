package simulations.starterkit;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class No7_Feeder_TestData extends BaseConfigurations {
    HttpProtocolBuilder httpConfig = initiateSimulation();

    FeederBuilder<String> testData = csv("testData/starterkit/testdata.csv").circular();

    ChainBuilder getListOfAllUsers = exec(
                http("Get all users").get("/api/users?page=2")
                        .check(status().is(200))
    );
    ChainBuilder createNewUser = exec(
            http("Create user").post("/api/users")
                    .body(RawFileBody("jsonRequestBodies/starterkit/createUser.json")).asJson()

                    .check(status().is(201))
    );

    ScenarioBuilder scenarioMethodReuse = scenario("Run test with method").exec( getListOfAllUsers);
    ScenarioBuilder scenarioMethodReuseAgain = scenario("Run test with sequent of methods").exec( createNewUser, getListOfAllUsers);

    //setup
    {
        setUp(
                scenarioMethodReuse.injectOpen(atOnceUsers(1)),
                scenarioMethodReuseAgain.injectOpen(rampUsers(10).during(5))
        ).protocols(httpConfig);
    }
}
