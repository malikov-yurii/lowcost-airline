package com.malikov.ticketsystem.matchers;

import org.junit.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
public class ModelMatcher<T> {

    private static final Comparator DEFAULT_COMPARATOR =
            (Object expected, Object actual) -> expected == actual || String.valueOf(expected).equals(String.valueOf(actual));

    private Comparator<T> comparator;
    private Class<T> entityClass;

    public interface Comparator<T> {
        boolean compare(T expected, T actual);
    }

    private ModelMatcher(Class<T> entityClass) {
        this(entityClass, (Comparator<T>) DEFAULT_COMPARATOR);
    }

    private ModelMatcher(Class<T> entityClass, Comparator<T> comparator) {
        this.entityClass = entityClass;
        this.comparator = comparator;
    }

    public static <T> ModelMatcher<T> of(Class<T> entityClass) {
        return new ModelMatcher<>(entityClass);
    }

    public static <T> ModelMatcher<T> of(Class<T> entityClass, Comparator<T> comparator) {
        return new ModelMatcher<>(entityClass, comparator);
    }

    private class Wrapper {
        private T entity;

        private Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper that = (Wrapper) o;
            return entity != null ? comparator.compare(entity, that.entity) : that.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }
    }

    // TODO: 5/5/2017 add form json value??

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public Wrapper wrap(T entity) {
        return new Wrapper(entity);
    }

    public List<Wrapper> wrap(Collection<T> collection) {
        return collection.stream().map(this::wrap).collect(Collectors.toList());
    }

    // TODO: 5/5/2017 add content matchers for string controller ??

}
