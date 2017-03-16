package com.omertex;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.omertex.model.Region;
import com.omertex.model.Year;
import com.omertex.service.RegionService;
import com.omertex.service.XMLRegionService;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * by afilonenko on 3/14/2017.
 */

public class ExampleTest {

    private static final String USERNAME_CENTRBANK = "USERNAME_CENTRBANK";
    private static final String PASSWORD_CENTRBANK = "PASSWORD_CENTRBANK";
    private static final String CHROME_DRIVER = "chromedriver.exe";
    private static final String STATISTICS = "Статистика";

    private WebDriver driver;
    private StringBuffer verificationErrors;

    @Before
    public void setUp() throws Exception {
        setUpSelenium();
        login();
    }

    @Test
    public void testAuto() throws Exception {
        RegionService regionService = new XMLRegionService();
        List<Region> regions = regionService.getRegions();
        traverseRegions(regions);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private void setUpSelenium() throws Exception {
        verificationErrors = new StringBuffer();
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private void login() throws Exception {
        driver.get(String.format("http://%s:%s@cbr2.demo.pointid.ru/region/", System.getenv(USERNAME_CENTRBANK), System.getenv(PASSWORD_CENTRBANK)));
    }

    private void traverseRegions(List<Region> regions) {
        for (Region region:regions) {
            driver.findElement(By.linkText(region.getId())).click();
            driver.findElement(By.linkText(STATISTICS)).click();
            List<Year> years = region.getYears();
            traverseYears(years);
        }
    }

    private void traverseYears(List<Year> years) {
        for (Year year:years) {
            driver.findElement(By.cssSelector(String.format("[href=\"#RegIndicatorList_year%s\"]", year.getId()))).click();
            assertTrue(isElementPresent(By.linkText(year.getProperty1())));
            assertTrue(isElementPresent(By.linkText(year.getProperty2())));
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}