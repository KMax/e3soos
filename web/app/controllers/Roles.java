package controllers;

import play.mvc.With;

/**
 *
 * @author Maxim Kolchin
 */
@With(Secure.class)
public class Roles extends CRUD {
    
}
