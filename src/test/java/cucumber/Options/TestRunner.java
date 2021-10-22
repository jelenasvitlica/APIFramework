package cucumber.Options;

import io.cucumber.junit.Cucumber;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;


@CucumberOptions(
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "timeline:test-output-thread/"
        },

        glue = "stepDefinitions",
        features = {"src/test/java/features"}
)
public final class TestRunner extends AbstractTestNGCucumberTests {
}
