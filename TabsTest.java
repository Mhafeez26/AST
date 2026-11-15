package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class TabsTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void switchBetweenTabs() {
        // 1. Pehle tab me pehli site kholo
        driver.get("https://automationexercise.com");

        // 2. Is tab ka handle (unique ID) save kar lo, wapas aane ke liye zaroori hai
        String firstTab = driver.getWindowHandle();

        // 3. Naya tab kholo (Selenium khud us par switch kar deta hai)
        driver.switchTo().newWindow(WindowType.TAB);

        // 4. Naye tab me dusri site kholo
        driver.get("https://automationexercise.com/contact_us");
        String secondTab = driver.getWindowHandle();

        // 5. Check: ab 2 tabs khule hone chahiye
        Set<String> allTabs = driver.getWindowHandles();
        Assert.assertEquals(allTabs.size(), 2);

        // 6. Pehle tab par wapas jao
        driver.switchTo().window(firstTab);
        Assert.assertFalse(driver.getCurrentUrl().contains("contact_us"));

        // 7. Loop se dusra tab dhoondo: jo handle pehle tab ka nahi, wahi dusra hai
        for (String tab : allTabs) {
            if (!tab.equals(firstTab)) {
                driver.switchTo().window(tab);
            }
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("contact_us"));

        // 8. Current (dusra) tab band karo, phir pehle tab par wapas aao
        driver.close();
        driver.switchTo().window(firstTab);
        Assert.assertEquals(driver.getWindowHandles().size(), 1);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
