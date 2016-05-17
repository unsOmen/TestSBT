package lib;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by OmeN on 17.05.2016.
 */
public class Init {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if(null==driver) {
            createWebDriver();
        }

        return driver;
    }

    public static void setDriver(WebDriver driver) {
        Init.driver = driver;
    }

    public static void createWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (System.getProperty("browser")) {
            case "Firefox":
                capabilities.setBrowserName("firefox");
                setDriver(new FirefoxDriver(capabilities));
                break;

            case "Chrome":
                File chromeDriver = new File("src/test/java/resources/webdrivers/chromedriver.exe");
                System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
                capabilities.setBrowserName("chrome");
                setDriver(new ChromeDriver(capabilities));
                break;

            case "IE":
                File IEDriver = new File("src/test/java/resources/webdrivers/IEDriverServer.exe");
                System.setProperty("webdriver.ie.driver", IEDriver.getAbsolutePath());
                capabilities.setBrowserName("internet explorer");
                setDriver(new InternetExplorerDriver(capabilities));
                break;
        }

        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
}
