package org.medium.demo.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.medium.demo.bdd.steps",
        plugin = {"pretty", "json:target/cucumber.json"},
        tags = "@smoke"
)
public class RunCucumberTest {
}