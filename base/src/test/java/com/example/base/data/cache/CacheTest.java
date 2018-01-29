package com.example.base.data.cache;

import com.example.base.BaseTest;
import com.example.base.common.providers.TimestampProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class CacheTest extends BaseTest {

    private static final long timeoutMs = 1000;

    @Mock
    private TimestampProvider timestampProvider;

    private Cache<String, TestObject> cache;

    @Before
    public void setUp() {
        cache = new Cache<>(TestObject::id, timestampProvider, timeoutMs);
    }

    @Test
    public void getSingularCompletesWithNoEmissionsWhenCacheIsEmpty() {
        cache.getSingular("KEY").test().assertNoValues();
    }

    @Test
    public void getAllCompletesWithNoEmissionsWhenCacheIsEmpty() {
        cache.getAll().test().assertNoValues();
    }

    @Test
    public void storedObjectIsEmittedWhenItHasNotExpired() {
        TestObject object = new TestObject("1");
        ArrangeBuilder arrangeBuilder = new ArrangeBuilder().withCurrentMoment(1);

        cache.putSingular(object);

        arrangeBuilder.withCurrentMoment(timeoutMs - 500);
        cache.getSingular(object.id).test().assertValue(object);
    }

    @Test
    public void storedObjectIsNotEmittedWhenItHasExpired() {
        TestObject object = new TestObject("1");
        ArrangeBuilder arrangeBuilder = new ArrangeBuilder().withCurrentMoment(1);

        cache.putSingular(object);

        arrangeBuilder.withCurrentMoment(timeoutMs + 500);
        cache.getSingular(object.id).test().assertNoValues();
    }

    @Test
    public void storedObjectsAreEmittedWhenTheyHaveNotExpired() {
        List<TestObject> listToStore = new ArrayList<TestObject>() {{
            add(new TestObject("1"));
            add(new TestObject("2"));
            add(new TestObject("3"));
        }};
        ArrangeBuilder arrangeBuilder = new ArrangeBuilder();

        arrangeBuilder.withCurrentMoment(1);
        cache.putAll(listToStore);

        arrangeBuilder.withCurrentMoment(timeoutMs - 500);
        cache.getAll().test().assertValue(listToStore);
    }

    @Test
    public void storedObjectsAreNotEmittedWhenTheyHaveExpired() {
        List<TestObject> listToStore = new ArrayList<TestObject>() {{
            add(new TestObject("1"));
            add(new TestObject("2"));
            add(new TestObject("3"));
        }};
        ArrangeBuilder arrangeBuilder = new ArrangeBuilder();

        arrangeBuilder.withCurrentMoment(1);
        cache.putAll(listToStore);

        arrangeBuilder.withCurrentMoment(timeoutMs + 500);
        cache.getAll().test().assertNoValues();
    }

    @Test
    public void getAllEmitsListWithAllNonExpiredValues() {
        TestObject object1 = new TestObject("1");
        TestObject object2 = new TestObject("2");
        TestObject object3 = new TestObject("3");
        ArrangeBuilder arrangeBuilder = new ArrangeBuilder();

        arrangeBuilder.withCurrentMoment(1);
        cache.putSingular(object1);

        arrangeBuilder.withCurrentMoment(100);
        cache.putSingular(object2);

        arrangeBuilder.withCurrentMoment(500);
        cache.putSingular(object3);

        arrangeBuilder.withCurrentMoment(timeoutMs + 50);
        List<TestObject> expected = new ArrayList<TestObject>() {{
            add(object2);
            add(object3);
        }};
        cache.getAll().test().assertValue(expected);
    }

    @Test
    public void clearRemovesAllTheItemsFromTheCache() {
        List<TestObject> listToStore = new ArrayList<TestObject>() {{
            add(new TestObject("1"));
            add(new TestObject("2"));
            add(new TestObject("3"));
        }};
        new ArrangeBuilder().withCurrentMoment(1);
        cache.putAll(listToStore);

        cache.clear();

        cache.getAll().test().assertNoValues();
        cache.getSingular("1").test().assertNoValues();
        cache.getSingular("2").test().assertNoValues();
        cache.getSingular("3").test().assertNoValues();
    }

    private static class TestObject {

        private final String id;

        private TestObject(final String id) {
            this.id = id;
        }

        private String id() {
            return id;
        }

        @Override
        public String toString() {
            return id;
        }
    }

    private class ArrangeBuilder {

        private ArrangeBuilder withCurrentMoment(long currentMoment) {
            when(timestampProvider.currentTimeMillis()).thenReturn(currentMoment);
            return this;
        }
    }
}