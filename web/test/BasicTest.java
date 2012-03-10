import models.User;
import org.junit.Test;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

    @Test
    public void createAndRetrieveUser() {
        //Create a new user and save it
        new User("bob@localhost", "secret", "bob").save();
        
        User bob = User.find("byEmail", "bob@localhost").first();
        
        assertNotNull(bob);
        assertEquals("bob", bob.fullName);
    }
    
    @Test
    public void tryConnectAsUser() {
        // Create a new user and save it
        new User("bob@gmail.com", "secret", "Bob").save();

        // Test 
        assertNotNull(User.connect("bob@gmail.com", "secret"));
        assertNull(User.connect("bob@gmail.com", "badpassword"));
        assertNull(User.connect("tom@gmail.com", "secret"));
    }

}
