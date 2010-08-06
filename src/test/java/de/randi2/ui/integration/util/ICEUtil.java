package de.randi2.ui.integration.util;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import static de.randi2.ui.integration.util.ComponentFinder.findElementById;

import java.io.IOException;

/**
 * Bunch of usefull methods for testing of an webapp realised with ICEfaces.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 21.07.2009
 * Time: 15:16:15
 */
public abstract class ICEUtil {

    /**
     * Activates the tab with the given No.
     *
     * @param page  The page containing the tab.
     * @param tabNr Tab No - starting with 0 for the first tab
     * @throws IOException if tab not fount or due to other erros
     */
    public static void activateTab(HtmlPage page, int tabNr) throws IOException {
        for (HtmlAnchor a : page.getAnchors()) {
            if (a.getId().endsWith("0." + tabNr))
                a.click();
        }
    }

    /**
     * Clicks on a specified button and returns tha HtmlPage.
     *
     * @param page Page with the button
     * @param id   Button's id
     * @return Refreshed page.
     * @throws IOException if an error occurs
     */
    public static HtmlPage clickSpecificButton(HtmlPage page, String id) throws IOException {
        return findElementById(page, id).click();
    }

    /**
     * Types the given name in the AutoComplete element and tries to select it.
     *
     * @param page        HtmlPage object with the AutoComplete Input Box.
     * @param id          ID of the autocomplete input box
     * @param elementName Name which will be typed into the autocomplete element
     * @throws IOException if an error occurs
     */
    public static void useAutoCompleteInput(HtmlPage page, String id, String elementName) throws IOException {
        findElementById(page, id).type(elementName + "\n");
    }
}
