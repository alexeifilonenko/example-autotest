package com.omertex;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * by afilonenko on 3/14/2017.
 */

public class ExampleTest {

    private static String USERNAME_CENTRBANK = "USERNAME_CENTRBANK";
    private static String PASSWORD_CENTRBANK = "PASSWORD_CENTRBANK";
    private static final String CHROME_DRIVER = "chromedriver.exe";

    private static final String XML_FILE = "regions.xml";
    private static final String STATISTICS = "Статистика";

    private WebDriver driver;
    private StringBuffer verificationErrors;
    private Document document;

    @Before
    public void setUp() throws Exception {
        prepareSelenium();
        prepareXMLFile();
    }

    @Test
    public void testAuto() throws Exception {
        login();
        NodeList regions = document.getElementsByTagName("region");
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

    private void login() throws Exception {
        driver.get("http://" + System.getenv(USERNAME_CENTRBANK) + ":" + System.getenv(PASSWORD_CENTRBANK) + "@cbr2.demo.pointid.ru/region/");
    }

    private void prepareSelenium() throws Exception {
        verificationErrors = new StringBuffer();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private void prepareXMLFile() throws Exception {
        File file = new File(XML_FILE);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = dBuilder.parse(file);
        // recommended action
        document.getDocumentElement().normalize();
    }

    private void traverseRegions(NodeList regions) {
        for (int i = 0; i < regions.getLength(); i++) {
            Element regionElement = (Element) regions.item(i);

            driver.findElement(By.linkText(regionElement.getAttribute("id"))).click();
            driver.findElement(By.linkText(STATISTICS)).click();

            NodeList years = regionElement.getElementsByTagName("year");
            traverseYears(years);
        }
    }

    private void traverseYears(NodeList years) {
        for (int j = 0; j < years.getLength(); j++) {
            Element yearElement = (Element) years.item(j);

            driver.findElement(By.cssSelector("[href=\"#RegIndicatorList_year" + yearElement.getAttribute("id") + "\"]")).click();
            assertTrue(isElementPresent(By.linkText(yearElement.getElementsByTagName("property1").item(0).getTextContent())));
            assertTrue(isElementPresent(By.linkText(yearElement.getElementsByTagName("property2").item(0).getTextContent())));
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
