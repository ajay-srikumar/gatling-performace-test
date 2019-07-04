package tests

import io.gatling.http.Predef._
import io.gatling.core.Predef._

// can be run using ./gradlew gatlingRun

class BasicSimulation extends Simulation {
  val httpConf = http.baseURL("http://api.football-data.org/")
  val scn = scenario("Only One User")
    .exec(http("request_1")
      .get("v2/competitions"))
    .pause(5)
  setUp(
    scn.inject(atOnceUsers(1))

  ).assertions(global.responseTime.max.lessThan(950))
    .protocols(httpConf)
}

