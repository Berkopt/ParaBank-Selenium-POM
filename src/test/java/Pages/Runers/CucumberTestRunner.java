package Pages.Runers;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import io.cucumber.core.options.Constants;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("Features") // Megmondja a JUnitnak, hogy a resources/Features mappát keresse
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "StepDefinitions" // Hol vannak a Java lépések (Step Definition-ök)
)
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports.html" // A riport mentési helye
)
public class CucumberTestRunner {
    // JUnit 5 alatt ez az osztály is teljesen üres marad!
}