package ru.tchallenge.participant.service.utility.experimental;

public final class ExperimentalContext {

    public static final ExperimentalContext INSTANCE = new ExperimentalContext();

    private static final String VARIABLE_NAME = "TCHALLENGE_EXPERIMENTAL_FEATURES_ENABLED";

    private static boolean environmentVariableAsBoolean(final String name) {
        final String value = System.getenv(name);
        return value != null && Boolean.parseBoolean(value);
    }

    private final boolean experimentalFeaturesEnabled;

    private ExperimentalContext() {
        this.experimentalFeaturesEnabled = environmentVariableAsBoolean(VARIABLE_NAME);
    }

    public boolean isExperimentalFeaturesEnabled() {
        return experimentalFeaturesEnabled;
    }

    public void ensureExperimentalFeaturesEnabled() {
        if (!experimentalFeaturesEnabled) {
            throw new RuntimeException("Experimental features are disabled");
        }
    }
}
