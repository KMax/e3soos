package controllers;

import play.mvc.Controller;

/**
 *
 * @author Maxim Kolchin
 */
public class Index extends Controller {

    public static void index() {
        if(Security.isConnected()) {
            Application.dashboard();
        } else {
            render();
        }
    }

}
