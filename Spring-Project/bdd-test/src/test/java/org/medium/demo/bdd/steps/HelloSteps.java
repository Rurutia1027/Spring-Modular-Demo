package org.medium.demo.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.medium.demo.bdd.util.HttpClientUtil;
import org.medium.demo.project.common.convention.result.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloSteps {
    private String name;
    private String response;

    @Given("the name {string}")
    public void givenName(String name) {
        this.name = name;
    }

    @When("I call the hello endpoint")
    public void callHelloEndpoint() {
        Result<String> result = HttpClientUtil.get(
                "http://localhost:8101/api/medium/demo/project/hello/" + name,
                Result.class,
                null
        );
        // cast to generic type manually since Java type erasure prevents direct mapping
        response = (String) result.getData();
    }

    @Then("the response should be {string}")
    public void verifyResponse(String expected) {
        assertEquals(expected, response);
    }
}
