package com.example.base.data.store;

import com.example.base.BaseTest;
import com.example.base.data.cache.Cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import polanski.option.Option;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

public class MemoryReactiveStoreTest extends BaseTest {

    @Mock
    private Cache<String, TestObject> cache;

    private MemoryReactiveStore<String, TestObject> reactiveStore;

    private static List<TestObject> createTestObjectList() {
        return new ArrayList<TestObject>() {{
            add(new TestObject("ID1"));
            add(new TestObject("ID2"));
            add(new TestObject("ID3"));
        }};
    }

    @Before
    public void setUp() {
        reactiveStore = new MemoryReactiveStore<>(TestObject::id, cache);
    }

    @Test
    public void noneIsEmittedWhenCacheIsEmpty() {
        new ArrangeBuilder().withEmptyCache();

        reactiveStore.getSingular("ID1").test().assertValue(none());
        reactiveStore.getAll().test().assertValue(none());
    }

    @Test
    public void lastStoredObjectIsEmittedAfterSubscription() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObject(model)
                .withCachedObjectList(Collections.singletonList(model));

        reactiveStore.storeSingular(model);
        reactiveStore.getSingular("ID1").test().assertValue(ofObj(model));
    }

    @Test
    public void singularStreamEmitsWhenSingleObjectIsStored() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withEmptyCache();

        TestObserver<Option<TestObject>> to = reactiveStore.getSingular("ID1").test();
        reactiveStore.storeSingular(model);

        to.assertValueAt(1, testObjectOption -> testObjectOption.equals(ofObj(model)));
    }

    @Test
    public void allStreamEmitsWhenSingleObjectIsStored() {
        List<TestObject> list = createTestObjectList();
        new ArrangeBuilder().withCachedObjectList(list);

        TestObserver<Option<List<TestObject>>> to = reactiveStore.getAll().test();
        reactiveStore.storeSingular(new TestObject(""));

        Mockito.verify(cache, Mockito.times(2)).getAll();
        to.assertValueAt(1, listOption -> listOption.equals(ofObj(list)));
    }

    @Test
    public void singularStreamEmitsWhenObjectListIsStored() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObject(model);

        TestObserver<Option<TestObject>> to = reactiveStore.getSingular("ID1").test();
        reactiveStore.storeAll(createTestObjectList());

        to.assertValueAt(1, testObjectOption -> testObjectOption.equals(ofObj(model)));
    }

    @Test
    public void allStreamEmitsWhenObjectListIsStored() {
        List<TestObject> list = createTestObjectList();
        new ArrangeBuilder().withCachedObjectList(list);

        TestObserver<Option<List<TestObject>>> to = reactiveStore.getAll().test();
        reactiveStore.storeAll(list);

        to.assertValueAt(1, listOption -> listOption.equals(ofObj(list)));
    }

    @Test
    public void singularStreamEmitsWhenObjectListIsReplaced() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObject(model);

        TestObserver<Option<TestObject>> to = reactiveStore.getSingular("ID1").test();
        reactiveStore.replaceAll(createTestObjectList());

        to.assertValueAt(1, testObjectOption -> testObjectOption.equals(ofObj(model)));
    }

    @Test
    public void allStreamEmitsWhenObjectListIsReplaced() {
        List<TestObject> list = createTestObjectList();
        new ArrangeBuilder().withCachedObjectList(list);

        TestObserver<Option<List<TestObject>>> to = reactiveStore.getAll().test();
        reactiveStore.replaceAll(list);

        to.assertValueAt(1, listOption -> listOption.equals(ofObj(list)));
    }

    @Test
    public void objectIsStoredInCache() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(Collections.singletonList(model));

        reactiveStore.storeSingular(model);

        Mockito.verify(cache).putSingular(model);
    }

    @Test
    public void objectListIsStoredInCache() {
        List<TestObject> list = createTestObjectList();

        reactiveStore.storeAll(list);

        Mockito.verify(cache).putAll(list);
    }

    @Test
    public void cacheIsClearedInReplaceAll() {
        List<TestObject> list = createTestObjectList();

        reactiveStore.replaceAll(list);

        Mockito.verify(cache).clear();
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

        private ArrangeBuilder withCachedObject(TestObject object) {
            Mockito.when(cache.getSingular(object.id())).thenReturn(Maybe.just(object));
            return this;
        }

        private ArrangeBuilder withCachedObjectList(List<TestObject> objectList) {
            Mockito.when(cache.getAll()).thenReturn(Maybe.just(objectList));
            return this;
        }

        private ArrangeBuilder withEmptyCache() {
            Mockito.when(cache.getSingular(Mockito.anyString())).thenReturn(Maybe.empty());
            Mockito.when(cache.getAll()).thenReturn(Maybe.empty());
            return this;
        }
    }
}
