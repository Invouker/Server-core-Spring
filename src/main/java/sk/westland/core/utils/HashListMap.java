package sk.westland.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class HashListMap<K, V, ListType extends List<V>> extends HashMap<K, ListType> {

    @SuppressWarnings("unchecked")
    public void putToList(K key, V value) {
        ListType list = getList(key);

        if(list == null) {
            try {
                ParameterizedType hashMapParametrizedType = (ParameterizedType) getClass().getGenericSuperclass();
                ParameterizedType hashMapType = (ParameterizedType)hashMapParametrizedType.getActualTypeArguments()[2];
                Class<ListType> listTypeClass = (Class)hashMapType.getRawType();

                list = listTypeClass.newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
                e.printStackTrace();
            }

            put(key, list);
        }

        if(list == null) {
            return;
        }

        if(!list.contains(value)) {
            list.add(value);
        }
    }

    public void removeFromList(K key, V value) {
        ListType list = getList(key);

        if(list == null) {
            return;
        }

        list.remove(value);
    }

    public ListType removeList(K key) {
        return this.remove(key);
    }

    public ListType getList(K key) {
        return getOrDefault(key, null);
    }
}
