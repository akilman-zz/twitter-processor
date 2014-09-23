package blog.examples;

import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertFalse;

public class AllTheLambdazTest {

    @Test
    public void testNormalizedWordStream() throws Exception {
        String text = "This is a twitter status text. YOLO";
        Stream<String> stream = AllTheLambdaz.normalizedWordStream(text);
        List<String> ws = stream.collect(toList());
        assertFalse(ws.isEmpty());
    }
}
