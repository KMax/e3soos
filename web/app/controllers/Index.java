package controllers;

import play.mvc.Controller;

/**
 *
 */
public class Index extends Controller {

    public static void index() {
        if(Security.isConnected()) {
            Application.dashboard();
        } else {
            render();
        }
    }

    public static void about() {
        render();
    }

    public static void contact() {
        render();
    }

}
