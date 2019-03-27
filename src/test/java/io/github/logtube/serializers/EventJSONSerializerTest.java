package io.github.logtube.serializers;

import io.github.logtube.Event;
import io.github.logtube.EventBuilder;
import org.junit.Test;

import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class EventJSONSerializerTest {

    @Test
    public void serialize() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        EventBuilder eb = new EventBuilder("test.example.com", "test-proj", "test");
        Event e = eb.timestamp(1553678195987L).message("hello, world").keyword("hello", "world").extra("duration", 1, "e", true).build();
        EventJSONSerializer s = new EventJSONSerializer();
        assertEquals(s.serialize(e), "{\"t\":1553678195987,\"h\":\"test.example.com\",\"e\":\"test\",\"p\":\"test-proj\",\"o\":\"info\",\"c\":\"-\",\"m\":\"hello, world\",\"k\":\"hello,world\",\"x\":{\"duration\":1,\"e\":true}}");
    }
}