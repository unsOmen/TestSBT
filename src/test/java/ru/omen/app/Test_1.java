package ru.omen.app;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by OmeN on 14.05.2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/main/java/ru/omen/app/features"},
        glue={"ru.omen.app.stepDefinitions"}
)
public class Test_1 {

}
