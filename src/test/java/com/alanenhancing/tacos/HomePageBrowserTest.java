package com.alanenhancing.tacos;


import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


/*
  SpringBootTest:

    Annotation that can be specified on a test class that runs Spring Boot based tests. Provides the following features over and above the regular Spring TestContext Framework:
      Uses SpringBootContextLoader as the default ContextLoader when no specific @ContextConfiguration(loader=...) is defined.
      Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used, and no explicit classes are specified.
      Allows custom Environment properties to be defined using the properties attribute.
      Provides support for different webEnvironment modes, including the ability to start a fully running web server listening on a defined or random port.
      Registers a TestRestTemplate and/or WebTestClient bean for use in web tests that are using a fully running web server.

      https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/context/SpringBootTest.html
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

  private static HtmlUnitDriver browser;
  @LocalServerPort
  private int port;

  @BeforeClass
  public static void setup() {
    browser = new HtmlUnitDriver();
    browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @AfterClass
  public static void teardown() {
    browser.quit();
  }

  @Test
  public void testHomePage() {
    String homePage = "http://localhost:" + port;
    browser.get(homePage);

    String titleText = browser.getTitle();
    Assert.assertEquals("Taco Cloud", titleText);

    String h1Text = browser.findElementByTagName("h1").getText();
    Assert.assertEquals("Welcome to...", h1Text);

    String imgSrc = browser.findElementByTagName("img").getAttribute("src");
    Assert.assertEquals(homePage + "/images/TacoCloud.png", imgSrc);
  }
}
