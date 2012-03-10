package controllers;

import play.mvc.With;

/**
 *
 * @author Maxim Kolchin
 */
@With(Secure.class)
public class Users extends CRUD {
    
}
