package com.malikov.ticketsystem.matchers;

// TODO: 5/5/2017 What is hamcrest?? For what?
import org.hamcrest.BaseMatcher;

/**
 * @author Yurii Malikov
 */
abstract public class TestMatcher<T> extends BaseMatcher<String> {

    protected T expected;

    public TestMatcher(T expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        return compare(expected, (String) actual);
    }

    abstract protected boolean compare(T expected, String actual);

    // TODO: 5/5/2017 add describeTo ???

}
