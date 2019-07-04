To run multiple gatling simulations using gradle:

apply plugin: 'scala'

repositories {
    mavenCentral()
    maven {
        url 'http://repository.excilys.com/content/groups/public'
    }
}

dependencies {
    compile 'org.scala-lang:scala-library:2.11.6'
    compile 'org.scala-lang:scala-compiler:2.11.6'
    compile 'com.fasterxml.jackson.core:jackson-databind:2.7.4'
    compile 'com.fasterxml.jackson.module:jackson-module-scala_2.11:2.7.4'
    testCompile 'io.gatling.highcharts:gatling-charts-highcharts:2.2.1'
}

task gatling(dependsOn: 'compileTestScala') << {
    delete "build/reports/gatling"

    for (simulation in ["scenarios.DataFlush", "scenarios.DataUploadSimulation", "scenarios.ScenarioSetup",
                        "scenarios.EditSpend", "scenarios.AddActivity", "scenarios.Portfolio"]) {
        javaexec {
            main = 'io.gatling.app.Gatling'
            classpath = sourceSets.test.output + sourceSets.test.runtimeClasspath
            systemProperty 'env', 'dev'
            args '-s', simulation, '-rf', 'build/reports/gatling'
        }
    }

    logger.lifecycle(" ---" +
            "- Done executing all Gatling scenarios ----")
}

task wrapper(type: Wrapper) {
    gradleVersion = '2.13'
}