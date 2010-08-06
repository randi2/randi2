/* 
 * (c) 2008-2009 RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.ui.integration;

import static de.randi2.ui.integration.util.ComponentFinder.findElementById;
import static de.randi2.ui.integration.util.ICEUtil.activateTab;
import static de.randi2.ui.integration.util.ICEUtil.clickSpecificButton;
import static de.randi2.ui.integration.util.ICEUtil.useAutoCompleteInput;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;
import de.randi2.ui.integration.util.Pages;

/**
 * User's self registration function test.
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 17.07.2009
 * Time: 14:44:50
 */
public class RegistrationTest extends AbstractUITest{

    private final static Person tP = new Person();

    @BeforeClass
    public static void setUP() {
        tP.setFirstname(testData.getProperty("newUser.firstName"));
        tP.setSurname(testData.getProperty("newUser.surname"));
        tP.setSex(Gender.MALE);
        tP.setTitle(testData.getProperty("newUser.title"));
        tP.setEmail("t"+(new Random()).nextInt(1000) + testData.getProperty("newUser.mailDomain"));
        tP.setPhone(testData.getProperty("newUser.phone"));
        tP.setMobile(testData.getProperty("newUser.mobile"));
        tP.setFax(testData.getProperty("newUser.fax"));
    }

    /**
     * Registration test.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    //TODO We still need to configure the automatic test execution
    public void registrationTest() throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        // TESTING THE LOGIN PROCESS
        HtmlPage page = webClient.getPage(testData.getProperty("baseURL") + Pages.REGISTER.toString());
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
        findElementById(page, "password").type(testData.getProperty("password"));
        findElementById(page, "pConfirmation").type(testData.getProperty("password"));
        activateTab(page, 2);
        useAutoCompleteInput(page, "trialSite", testData.getProperty("trialSite"));
        findElementById(page, "tsPassword").type(testData.getProperty("password"));
        clickSpecificButton(page, "submitButton");
        webClient.waitForBackgroundJavaScript(5000);
        //TODO We shoudl check if the process was successfull ...
    }
}
