package de.randi2.ui;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;
import static de.randi2.ui.ComponentFinder.findElementById;
import static de.randi2.ui.ICEUtil.*;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

/**
 * User's self registration function test.
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 17.07.2009
 * Time: 14:44:50
 */
public class RegistrationTest {

    private final static String baseURL = "http://localhost:8080/RANDI2/";
    private final static Person tP = new Person();
    private final static String password = "1$UnitTest";
    private final static String siteName = "DKFZ";
    private final static String sitePass = "1$heidelberg";

    @BeforeClass
    public static void setUP() {
        tP.setFirstname("TestFName");
        tP.setSurname("TestSName");
        tP.setSex(Gender.MALE);
        tP.setTitle("TT");
        tP.setEmail("test" + (new Random()).nextInt(1000) + "@test.de");
        tP.setPhone("013131321231");
        tP.setMobile("031731823713");
        tP.setFax("1039123321");
    }

    @Test
    @Ignore
    //TODO We still need to configure the automatic test execution
    public void registrationTest() throws IOException {
        WebClient webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        // TESTING THE LOGIN PROCESS
        HtmlPage page = webClient.getPage(baseURL + Pages.REGISTER.toString());
        page = clickSpecificButton(page, "termsAccept");
        ((HtmlSelect) findElementById(page, "gender")).setSelectedAttribute("Mr.", true);
        findElementById(page, "userTitle").type(tP.getTitle());
        findElementById(page, "firstname").type(tP.getFirstname());
        findElementById(page, "surname").type(tP.getSurname());
        findElementById(page, "email").type(tP.getEmail());
        findElementById(page, "phone").type(tP.getPhone());
        findElementById(page, "mobile").type(tP.getMobile());
        findElementById(page, "fax").type(tP.getFax());
        activateTab(page, 1);
        assertEquals(findElementById(page, "username").asText(), tP.getEmail());
        findElementById(page, "password").type(password);
        findElementById(page, "pConfirmation").type(password);
        activateTab(page, 2);
        useAutoCompleteInput(page, "trialSite", siteName);
        findElementById(page, "tsPassword").type(sitePass);
        clickSpecificButton(page, "submitButton");
        webClient.waitForBackgroundJavaScript(5000);
        //TODO We shoudl check if the process was successfull ...
    }
}
