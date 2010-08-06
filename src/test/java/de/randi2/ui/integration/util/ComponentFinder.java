package de.randi2.ui.integration.util;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.velocity.anakia.NodeList;

/**
 * Help class providing the html elements retrival for UI testing.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 17.07.2009
 * Time: 14:52:19
 */
public abstract class ComponentFinder {

    /**
     * This Method tries to find the HTML INPUT element with the given ID in the given HtmlPage object.
     * If successful then the found element will be returne if not NULL.
     *
     * @param page The page with the input element.
     * @param id   ID of the html input element
     * @return the desired html input component
     */
    public static HtmlInput findHtmlInputById(HtmlPage page, String id) {
        NodeList elementsByTagName = (NodeList) page
                .getElementsByTagName("input");
        for (int i = 0; i < ((org.w3c.dom.NodeList) elementsByTagName)
                .getLength(); i++) {
            HtmlInput input = (HtmlInput) ((org.w3c.dom.NodeList) elementsByTagName)
                    .item(i);
            String currentId = input.getId();

            if (currentId.endsWith(id)) {
                return input;
            }
        }

        return null;
    }

    /**
     * This method returns a specific anchor element (a-elemnt) of a web page (HtmlPage object)
     *
     * @param page The html-page within we're going to search the a-element
     * @param id   ID of the anchor we're searching for
     * @return If successfull then aproperiate HtmlAnchor object will be returned, if not: NULL
     */
    public static HtmlAnchor findAnchorById(HtmlPage page, String id) {
        for (HtmlAnchor anchor : page.getAnchors()) {
            String currentId = anchor.getId();
            if (currentId.contains(id)) {
                return anchor;
            }
        }
        return null;
    }

    /**
     * Method for the retrival of a specific html element within a html page.
     *
     * @param page The html page within we'll be looking for the specific element.
     * @param id   ID of the element, we're looking for.
     * @return The HTMLElement if found, null otherwise
     */
    public static HtmlElement findElementById(HtmlPage page, String id) {
        for (HtmlElement e : page.getAllHtmlChildElements()) {
            String currentId = e.getId();
            if (currentId.endsWith(id)) {
                return e;
            }
        }
        return null;
    }
}
