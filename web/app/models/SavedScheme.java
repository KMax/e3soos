package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 *
 * @author Maxim Kolchin
 */
@Entity
public class SavedScheme extends Model {

    @Required
    @ManyToOne
    public User user;

    @Required
    public Date date;

    @Required
    public String scheme;

    public SavedScheme(final User user, final String scheme) {
        this.user = user;
        this.scheme = scheme;
        this.date = new Date();
    }

}
