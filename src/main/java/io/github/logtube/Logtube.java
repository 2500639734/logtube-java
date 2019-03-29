package io.github.logtube;

import io.github.logtube.event.EventLogger;
import io.github.logtube.outputs.EventConsoleOutput;
import io.github.logtube.outputs.EventJSONFileOutput;
import io.github.logtube.outputs.EventPlainFileOutput;
import io.github.logtube.outputs.EventRemoteOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Logtube {

    private static final EventLogger DEFAULT_LOGGER;

    static {
        LogtubeOptions options = LogtubeOptions.fromClasspath();

        DEFAULT_LOGGER = new EventLogger(LogtubeOptions.getHostname(), options.getProject(), options.getEnv());
        DEFAULT_LOGGER.setTopics(options.getTopics());

        if (options.getConsoleEnabled()) {
            EventConsoleOutput output = new EventConsoleOutput();
            output.setTopics(options.getConsoleTopics());
            DEFAULT_LOGGER.addOutput(output);
        }

        if (options.getFilePlainEnabled()) {
            EventPlainFileOutput output = new EventPlainFileOutput(
                    options.getFilePlainDir(),
                    options.getFilePlainSignal()
            );
            output.setTopics(options.getFilePlainTopics());
            DEFAULT_LOGGER.addOutput(output);
        }

        if (options.getFileJSONEnabled()) {
            EventJSONFileOutput output = new EventJSONFileOutput(
                    options.getFileJSONDir(),
                    options.getFileJSONSignal()
            );
            output.setTopics(options.getFileJSONTopics());
            DEFAULT_LOGGER.addOutput(output);
        }

        if (options.getRemoteEnabled()) {
            EventRemoteOutput output = new EventRemoteOutput(
                    options.getRemoteHosts()
            );
            output.setTopics(options.getRemoteTopics());
            DEFAULT_LOGGER.addOutput(output);
        }
    }

    public static boolean isTopicEnabled(@NotNull String topic) {
        return DEFAULT_LOGGER.isTopicEnabled(topic);
    }

    @NotNull
    public static IEventCommitter topic(@Nullable String topic) {
        return DEFAULT_LOGGER.topic(topic);
    }

    @NotNull
    public static IEventCommitter trace() {
        return DEFAULT_LOGGER.trace();
    }

    @NotNull
    public static IEventCommitter debug() {
        return DEFAULT_LOGGER.debug();
    }

    @NotNull
    public static IEventCommitter info() {
        return DEFAULT_LOGGER.info();
    }

    @NotNull
    public static IEventCommitter warn() {
        return DEFAULT_LOGGER.warn();
    }

    @NotNull
    public static IEventCommitter error() {
        return DEFAULT_LOGGER.error();
    }

}