package controllers;

import play.mvc.Controller;

/**
 *
 * @author Maxim Kolchin
 */
public class Index extends Controller {
    
    public static void index() {
        renderArgs.put("isConnected", Security.isConnected());
        render();
    }
    
}
