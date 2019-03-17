package com.alanenhancing.tacos;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;

/*
  SpringRunner:
    an alias for  SpringJUnit4ClassRunner,
    a custom extension of JUnit's BlockJUnit4ClassRunner which provides functionality of the Spring
    TestContext Framework to standard JUnit tests by means of the TestContextManager and associated
    support classes and annotations.

    https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/SpringRunner.html
 */


/*
  RunWith:
    http://junit.sourceforge.net/javadoc/org/junit/runner/RunWith.html
*/

/*
  WebMvcTest:
    https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html

    Annotation that can be used in combination with @RunWith(SpringRunner.class) for a typical Spring
    MVC test. Can be used when a test focuses only on Spring MVC components.
    Using this annotation will disable full auto-configuration and instead apply only configuration
    relevant to MVC tests (i.e. @Controller, @ControllerAdvice, @JsonComponent,
    Converter/GenericConverter, Filter, WebMvcConfigurer and HandlerMethodArgumentResolver beans but
    not @Component, @Service or @Repository beans).

    By default, tests annotated with @WebMvcTest will also auto-configure Spring Security and MockMvc
    (include support for HtmlUnit WebClient and Selenium WebDriver). For more fine-grained control of
    MockMVC the @AutoConfigureMockMvc annotation can be used.

    Typically @WebMvcTest is used in combination with @MockBean or @Import to create any
    collaborators required by your @Controller beans.
*/

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

  // MockMvc:
  // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html
  @Autowired private MockMvc mockMvc;

  @Test
  public void testHomePage() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("home"))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Welcome to")));
  }

  @Test
  public void testHomePageInDetails() throws Exception {
    // MockMvcRequestBuilders:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html
    // MockHttpServletRequestBuilder:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/");

    // ResultActions:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html
    // RequestBuilder:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/RequestBuilder.html
    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

    /* --------- check status --------- */
    // MockMvcResultMatchers:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html
    // StatusResultMatchers:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/StatusResultMatchers.html
    // ResultMatcher:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultMatcher.html
    StatusResultMatchers statusResultMatchers = MockMvcResultMatchers.status();
    ResultMatcher statusResultMatcher = statusResultMatchers.isOk();
    resultActions.andExpect(statusResultMatcher);

    /* --------- check view name --------- */
    // ViewResultMatchers:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/ViewResultMatchers.html
    ViewResultMatchers viewResultMatchers = MockMvcResultMatchers.view();
    ResultMatcher viewResultMatcher = viewResultMatchers.name("home");
    resultActions.andExpect(viewResultMatcher);

    /* --------- check content --------- */
    // ContentResultMatchers:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/ContentResultMatchers.html
    // Matchers: http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html
    // Matcher: http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matcher.html
    ContentResultMatchers contentResultMatchers = MockMvcResultMatchers.content();
    Matcher<String> matcher = Matchers.containsString("Welcome to...");
    ResultMatcher contentResultMatcher = contentResultMatchers.string(matcher);
    resultActions.andExpect(contentResultMatcher);
  }
}
