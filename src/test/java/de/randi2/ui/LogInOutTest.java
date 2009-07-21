package de.randi2.ui;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import static de.randi2.ui.ComponentFinder.findAnchorById;
import static de.randi2.ui.ComponentFinder.findElementById;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Log IN / Log OUT function test.
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 17.07.2009
 * Time: 14:44:50
 */
public class LogInOutTest {

    private final static String username = "admin@test.de";
    private final static String password = "1$heidelberg";
    private final static String name = "Max Administrator";
    private final static String baseURL = "http://localhost:8080/RANDI2/";

    @Test
    @Ignore
    //TODO We still need to configure the automatic test execution
    public void loginAndlogoutTest() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage randi2page;
        // TESTING THE LOGIN PROCESS
        HtmlPage page = webClient.getPage(baseURL + Pages.LOGIN.toString());
        HtmlForm form = page.getFormByName("loginForm");
        HtmlButton button = form.getButtonByName("submitButton");
        HtmlTextInput usernameInput = form.getInputByName("j_username");
        HtmlPasswordInput passwordInput = form.getInputByName("j_password");
        usernameInput.setValueAttribute(username);
        passwordInput.setValueAttribute(password);
        randi2page = button.click();
        assertEquals(findElementById(randi2page, "userHeader")
                .getTextContent(), name);
        // TESTING THE LOGOUT PROCESS
        HtmlPage goodbyePage = findAnchorById(randi2page, "logout").click();
        assertEquals(goodbyePage.getFullyQualifiedUrl("").toString(), baseURL + Pages.GOODBYE.toString());
    }
}
