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

import static de.randi2.ui.integration.util.ComponentFinder.findAnchorById;
import static de.randi2.ui.integration.util.ComponentFinder.findElementById;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import de.randi2.ui.integration.util.Pages;

/**
 * Log IN / Log OUT function test.
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 17.07.2009
 * Time: 14:44:50
 */
public class LogInOutTest extends AbstractUITest{
	
	
	@Test
    //TODO We still need to configure the automatic test execution
    public void loginAndlogoutTest() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage randi2page;
        // TESTING THE LOGIN PROCESS
        HtmlPage page = webClient.getPage(testData.getProperty("baseURL") + Pages.LOGIN.toString());
        HtmlForm form = page.getFormByName("loginForm");
        HtmlButton button = form.getButtonByName("submitButton");
        HtmlTextInput usernameInput = form.getInputByName("j_username");
        HtmlPasswordInput passwordInput = form.getInputByName("j_password");
        usernameInput.setValueAttribute(testData.getProperty("username"));
        passwordInput.setValueAttribute(testData.getProperty("password"));
        randi2page = button.click();
        assertEquals(findElementById(randi2page, "userHeader")
                .getTextContent(), testData.getProperty("name"));
        // TESTING THE LOGOUT PROCESS
        HtmlPage goodbyePage = findAnchorById(randi2page, "logout").click();
        assertEquals(goodbyePage.getFullyQualifiedUrl("").toString(), testData.getProperty("baseURL") + Pages.GOODBYE.toString());
    }
}
