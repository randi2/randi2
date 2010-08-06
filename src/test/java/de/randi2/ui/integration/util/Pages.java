package de.randi2.ui.integration.util;

/**
 * Contains the names of the RANDI2 jsf pages for the test purpose.
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 21.07.2009
 * Time: 17:26:03
 */
public enum Pages {
    LOGIN("login.jspx"), GOODBYE("goodbye.jspx"), REGISTER("register.jspx");

    private String fileName = "NOT SET!";

    private Pages(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return this.fileName;
    }
}
