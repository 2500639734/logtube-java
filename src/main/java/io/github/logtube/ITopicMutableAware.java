package io.github.logtube;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ITopicMutableAware extends ITopicAware {

    void setTopics(@Nullable Set<String> topics);

}
