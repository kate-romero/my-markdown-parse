import static org.junit.Assert.*;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MarkdownParseReviewedTest {
    @Test
    public void snippet1Test() throws IOException {
        assertEquals(List.of("`google.com", "google.com", "ucse.edu"), 
        MarkdownParseReviewed.getLinks(Files.readString(Path.of
        ("snippet-1.md"))));
    }
    
    @Test
    public void snippet2Test() throws IOException {
        assertEquals(List.of("a.com", "a.com(())", "example.com"), 
        MarkdownParseReviewed.getLinks(Files.readString(Path.of
        ("snippet-2.md"))));
    }

    @Test
    public void snippet3Test() throws IOException {
        assertEquals(List.of("https://www.twitter.com", 
        "https://ucsd-cse15l-w22.github.io/", "https://cse.ucsd.edu/"), 
        MarkdownParseReviewed.getLinks(Files.readString(Path.of
        ("snippet-3.md"))));
    }
}
