package utils;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import play.test.UnitTest;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Schema;

/**
 *
 * @author Maxim Kolchin
 */
public class RuleRunnerTest extends UnitTest {

    public RuleRunnerTest() {
    }

    @Test
    public void testSynthesis() {
        Classification c = new Classification();
        c.setJ(1);
        c.setW(2);
        c.setF(0);
        c.setL(1);
        c.setQ(0);
        c.setS(0);
        c.setD(2);
        RuleRunner.synthesis(c);
    }
}
