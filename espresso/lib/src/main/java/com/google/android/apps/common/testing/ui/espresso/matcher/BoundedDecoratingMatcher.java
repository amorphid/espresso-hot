package com.google.android.apps.common.testing.ui.espresso.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Some matcher sugar that lets you create a matcher for a given type
 * but only process items of a specific subtype of that matcher.
 * Unlike {@link BoundedMatcher} this one does it by composition,
 * not inheritance, the flexibility of which allows us to wrap
 * (decorate) any existing matcher to just add the type-checking
 * and get a supertype matcher for a subtype one. Also this uses
 * {@link TypeSafeMatcher} and so gains the ability to reflectively
 * find the type rather than requiring it to be passed in.
 *
 * @param <T> The desired type of the Matcher.
 * @param <S> the subtype of T that your matcher applies safely to.
*/
public class BoundedDecoratingMatcher<T, S> extends BaseMatcher<T> {
    private final DelegatingTypeSafeMatcher<S> matcher;

    public static <T,S> BoundedDecoratingMatcher<T, S> withCorrectType(Matcher<S> matcher) {
        return new BoundedDecoratingMatcher<T, S>(matcher);
    }

    public BoundedDecoratingMatcher(Matcher<S> matcher) {
        this.matcher = new DelegatingTypeSafeMatcher<S>(matcher);
    }

    @Override
    public boolean matches(Object item) {
        return matcher.matches(item);
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }

    // Using TypeSafeMatcher rather than BoundedMatcher because it can reflectively find type rather
    // than always requiring a class in the constructor.
    private static class DelegatingTypeSafeMatcher<T> extends TypeSafeMatcher<T> {
        private final Matcher<T> matcher;

        public DelegatingTypeSafeMatcher(Matcher<T> matcher) {
            this.matcher = matcher;
        }

        @Override
        protected boolean matchesSafely(T item) {
            return matcher.matches(item);
        }

        @Override
        public void describeTo(Description description) {
            matcher.describeTo(description);
        }
    }
}
