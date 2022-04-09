package selenium;

import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

public class DriverManager {
  private static WebDriver driver;

  private DriverManager() {
  }

  static {
    ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
  }

  public static WebDriver getDriver() {
    if(Objects.isNull(driver))
      driver = new ChromeDriver();
    return driver;
  }

  public static void quit() {
    driver.quit();
    driver = null;
  }
}