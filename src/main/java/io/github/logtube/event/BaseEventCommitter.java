package io.github.logtube.event;

import io.github.logtube.IEvent;
import io.github.logtube.IEventCommitter;
import io.github.logtube.utils.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * BaseEventCommitter builder for logtube Event
 */
public class BaseEventCommitter implements IEventCommitter {

    private final Event event = new Event();

    public BaseEventCommitter(@NotNull String hostname, @NotNull String project, @NotNull String env) {
        this.event.setHostname(hostname);
        this.event.setProject(project);
        this.event.setEnv(env);
    }

    /**
     * set timestamp
     *
     * @param timestamp timestamp
     * @return builder
     */
    @Override
    @NotNull
    public BaseEventCommitter timestamp(long timestamp) {
        this.event.setTimestamp(timestamp);
        return this;
    }

    /**
     * set topic
     *
     * @param topic topic
     * @return builder
     */
    @Override
    @NotNull
    public BaseEventCommitter topic(@Nullable String topic) {
        this.event.setTopic(StringUtil.safeString(topic, IEvent.EMPTY_TOPIC));
        return this;
    }

    /**
     * set crid
     *
     * @param crid crid
     * @return builder
     */
    @Override
    @NotNull
    public BaseEventCommitter crid(@Nullable String crid) {
        this.event.setCrid(StringUtil.safeString(crid, IEvent.EMPTY_CRID));
        return this;
    }

    /**
     * set message
     *
     * @param message message
     * @return builder
     */
    @Override
    @NotNull
    public BaseEventCommitter message(@NotNull String message) {
        this.event.setMessage(message);
        return this;
    }

    /**
     * append keyword
     *
     * @param keywords keyword
     * @return builder
     */
    @Override
    @NotNull
    public BaseEventCommitter keyword(@NotNull String... keywords) {
        if (this.event.getKeyword() == null) {
            this.event.setKeyword(String.join(",", keywords));
        } else {
            this.event.setKeyword(this.event.getKeyword() + "," + String.join(",", keywords));
        }
        return this;
    }

    /**
     * append extra field, only basic json value are allowed
     *
     * @param kvs array of key-value
     * @return builder
     */
    @Override
    @NotNull
    public BaseEventCommitter extra(@NotNull Object... kvs) {
        if (kvs.length == 0 || kvs.length % 2 != 0) {
            throw new IllegalArgumentException("extra key value not match");
        }
        if (this.event.getExtra() == null) {
            this.event.setExtra(new HashMap<>());
        }
        for (int i = 0; i < kvs.length; i += 2) {
            String k = kvs[i].toString();
            Object v = kvs[i + 1];
            if (v instanceof String || v instanceof Number || v instanceof Boolean) {
                this.event.getExtra().put(k, v);
            } else {
                this.event.getExtra().put(k, v.toString());
            }
        }
        return this;
    }

    @NotNull
    public Event build() {
        return this.event;
    }


    @Override
    public void commit() {
        throw new RuntimeException("never call BaseEventCommitter#commit");
    }

}