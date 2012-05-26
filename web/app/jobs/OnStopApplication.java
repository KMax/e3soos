package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStop;
import utils.RuleRunner;

/**
 *
 * @author Maxim Kolchin
 */
@OnApplicationStop
public class OnStopApplication extends Job {

    @Override
    public void doJob() throws Exception {
        //Dispose RuleRunner
        RuleRunner.dispose();
    }

}
