package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class SimulationToken extends Simulation {
  val httpConf = http.baseURL("http://my-json-server.typicode.com/")
  val scn = scenario("Basic Simulation")
    .exec(http("request_1")
      .get("typicode/demo/profile")
      .check(status is 200)
      .check(jsonPath("$..name").saveAs("name")))
      .exec(http("request_2")
      .get("https://postman-echo.com/get?foo1=${name}")
      .check(status is 200))
    .pause(5)
  setUp(
    scn.inject(atOnceUsers(1))

  ).assertions(global.responseTime.max.lessThan(950))
    .protocols(httpConf)
}

//Sample GET - https://my-json-server.typicode.com/typicode/demo/profile