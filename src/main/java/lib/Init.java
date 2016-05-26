package lib;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by OmeN on 17.05.2016.
 */
public class Init {

    private static WebDriver driver;
    private static HashMap<Object, Object> stash;

    public static void initProperties() {
        String cfg = "src/test/java/config/application.properties";
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(cfg));

            if(stash!=null)
                stash.clear();
            else
                stash = new HashMap<>();

            for(String str : prop.stringPropertyNames()) {
                stash.put(str, prop.getProperty(str));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getProperty(String prop) {
        return stash.get(prop);
    }

    public static WebDriver getDriver() {
        if(null==driver) {
            createWebDriver();
        }

        return driver;
    }

    static void setDriver(WebDriver driver) {
        Init.driver = driver;
    }

    static void createWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (stash.get("browser").toString()) {
            case "Firefox":
                //capabilities.setBrowserName("firefox");
                //setDriver(new FirefoxDriver(capabilities));
                ProfilesIni profile = new ProfilesIni();
                FirefoxProfile ffprofile = profile.getProfile("SELENIUM");
                setDriver(new FirefoxDriver(ffprofile));
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
