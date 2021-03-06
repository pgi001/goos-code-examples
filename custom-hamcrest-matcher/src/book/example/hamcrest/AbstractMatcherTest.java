package book.example.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractMatcherTest {
    /**
     * Create an instance of the Matcher so some generic safety-net tests can be run on it.
     */
    protected abstract Matcher<?> createMatcher();

    protected static final Object ARGUMENT_IGNORED = new Object();
    protected static final Object ANY_NON_NULL_ARGUMENT = new Object();

    public static <T> void assertMatches(String message, Matcher<? super T> c, T arg) {
        assertTrue(message, c.matches(arg));
    }

    public static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg) {
        assertFalse(message, c.matches(arg));
    }

    public static void assertDescription(String expected, Matcher<?> matcher) {
        Description description = new StringDescription();
        description.appendDescriptionOf(matcher);
        assertEquals("Expected description", expected, description.toString());
    }

    public static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, T arg) {
        Description description = new StringDescription();
        assertFalse("Precondtion: Matcher should not match item.", matcher.matches(arg));
        matcher.describeMismatch(arg, description);
        assertEquals("Expected mismatch description", expected, description.toString());
    }

    @Test
    public void isNullSafe() {
        // should not throw a NullPointerException
        createMatcher().matches(null);
    }

    @Test
    public void copesWithUnknownTypes() {
        // should not throw ClassCastException
        createMatcher().matches(new UnknownType());
    }

    public static class UnknownType {
    }
}
