package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// to run ./gradlew gatlingRun-tests.SimulationUsers
class SimulationUsersAndAssertions extends Simulation {
  val httpConf = http.baseURL("http://api.football-data.org/")
  val scn2 = scenario("Multiple User Requests")
    .exec(http("request_1")
      .get("v2/competitions"))
  setUp(
    scn2.inject(rampUsers(100) over(10))

  ).assertions(global.responseTime.max.lessThan(950))
    .protocols(httpConf)
}

/*
load example:
rampUsers(10) over (5 seconds), // 3
constantUsersPerSec(20) during (15 seconds), // 4
constantUsersPerSec(20) during (15 seconds) randomized, // 5
rampUsersPerSec(10) to 20 during (10 minutes), // 6
rampUsersPerSec(10) to 20 during (10 minutes) randomized, // 7
splitUsers(1000) into (rampUsers(10) over (10 seconds)) separatedBy (10 seconds), // 8
splitUsers(1000) into (rampUsers(10) over (10 seconds)) separatedBy atOnceUsers(30), // 9
heavisideUsers(1000) over (20 seconds) // 10*/
