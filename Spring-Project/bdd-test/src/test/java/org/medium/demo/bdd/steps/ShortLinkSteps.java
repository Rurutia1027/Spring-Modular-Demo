package org.medium.demo.bdd.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.medium.demo.bdd.util.HttpClientUtil;
import org.medium.demo.project.entity.ShortLinkDO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShortLinkSteps {
    private ShortLinkDO lastCreated;

    @Given("a new short link with url {string}")
    public void createShortLink(String fullUrl) {
        ShortLinkDO request = new ShortLinkDO();
        request.setFullShortUrl(fullUrl);
        lastCreated = HttpClientUtil.post(
                "http://localhost:8101/api/medium/demo/project/create",
                request,
                ShortLinkDO.class,
                null
        );
        assertNotNull(lastCreated.getShortUri());
    }

    @When("I fetch short link by shortUri")
    public void fetchShortLink() {
        ShortLinkDO fetched = HttpClientUtil.get(
                "http://localhost:8101/api/medium/demo/project/short-uri/" + lastCreated.getShortUri(),
                ShortLinkDO.class,
                null
        );
        assertEquals(lastCreated.getFullShortUrl(), fetched.getFullShortUrl());
    }

    @Then("the short link click count increments by {int}")
    public void incrementClickCount(int increment) {
        ShortLinkDO updated = HttpClientUtil.post(
                "http://localhost:8101/api/medium/demo/project/increment-click/" + lastCreated.getShortUri(),
                null,
                ShortLinkDO.class,
                null
        );
        assertEquals(increment, updated.getClickNum());
    }

    @After
    public void cleanup() {
        if (lastCreated != null) {
            HttpClientUtil.delete(
                    "http://localhost:8101/api/medium/demo/project/delete/" + lastCreated.getShortUri(),
                    Void.class
            );
        }
    }
}
