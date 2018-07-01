package ru.tchallenge.pilot.service.utility.experimental;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedConfiguration;

@ManagedConfiguration
public class ExperimentalContext extends GenericApplicationComponent {

    private static final String VARIABLE_NAME = "TCHALLENGE_EXPERIMENTAL_FEATURES_ENABLED";

    private static boolean environmentVariableAsBoolean(final String name) {
        final String value = System.getenv(name);
        return value != null && Boolean.parseBoolean(value);
    }

    private boolean experimentalFeaturesEnabled;

    public boolean isExperimentalFeaturesEnabled() {
        return experimentalFeaturesEnabled;
    }

    public void ensureExperimentalFeaturesEnabled() {
        if (!experimentalFeaturesEnabled) {
            throw new RuntimeException("Experimental features are disabled");
        }
    }

    @Override
    public void init() {
        super.init();
        this.experimentalFeaturesEnabled = environmentVariableAsBoolean(VARIABLE_NAME);
    }
}
