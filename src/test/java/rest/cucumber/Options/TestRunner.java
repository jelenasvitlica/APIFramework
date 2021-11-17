package rest.cucumber.Options;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "timeline:test-output-thread/"
        },

        glue = "rest/stepDefinitions",
        features = {"src/test/resources/features"},
        tags = "@Regression"
)
public final class TestRunner extends AbstractTestNGCucumberTests {
}
