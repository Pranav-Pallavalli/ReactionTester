package com.example.prederiq.PrederaAiq;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
public class StoryConfiguration extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(this.getClass())).useStoryReporterBuilder(new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass())).withDefaultFormats().withFormats(StoryReporterBuilder.Format.CONSOLE, StoryReporterBuilder.Format.HTML));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(),new PrederaAiqApplicationTests());
    }

    @Override
    public List<String> storyPaths() {
        String codeLoc = codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()).getFile(), "**/test.story" ,"");
    }
}
