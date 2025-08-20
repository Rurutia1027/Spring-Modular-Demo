package org.medium.demo.bdd.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.medium.demo.bdd.util.HttpClientUtil;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortLinkSteps {
    private JsonNode responseBody;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Given("the name is {string}")
    public void theNameIs(String name) throws Exception {
        // Build JSON request
        Map<String, String> request = Map.of("name", name);

        // Call the endpoint
        String rawResponse = HttpClientUtil.post(
                "http://localhost:8101/api/medium/demo/project/hello/" + name,
                request,
                String.class,
                null
        );

        responseBody = objectMapper.readTree(rawResponse);
    }

    @Then("the greeting is {string}")
    public void theGreetingIs(String expectedGreeting) {
        assertEquals(expectedGreeting, responseBody.get("data").asText());
    }
}
