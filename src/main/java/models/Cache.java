package models;

import CommentInfo.model.Item;

import java.util.Map;
import java.util.concurrent.*;

public class Cache {
    private volatile ConcurrentHashMap<Key, Item> globalMap = new ConcurrentHashMap<Key, Item>();
    private long default_timeout;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);
            return th;
        }
    });

    /**
     * @param default_timeout  количество милисекунд - время которое обьект будет кранится в кеше.
     */
    public Cache(long default_timeout) throws Exception {
        if (default_timeout < 10) {
            throw new Exception("Too short interval for storage in the cache. Interval should be more than 10 ms");
        }
        this.default_timeout = default_timeout;
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                long current = System.currentTimeMillis();
                for (Key k : globalMap.keySet()) {
                    if (!k.isLive(current)) {
                        globalMap.remove(k);
                    }
                }
            }
        }, 1, default_timeout/5, TimeUnit.MILLISECONDS);
    }

    /**
     * @param default_timeout количество милисекунд - время которое обьект будет кранится в кеше
     */
    public void setDefault_timeout(long default_timeout) throws Exception {
        if (default_timeout < 100) {
            throw new Exception("Too short interval for storage in the cache. Interval should be more than 10 ms");
        }
        this.default_timeout = default_timeout;
    }

    /**
     * Метод для вставки обьекта в кеш
     * Время зранения берётся по умолчанию
     * @param <Key>
     * @param <Item>
     * @param key ключ в кеше
     * @param data данные
     */
    public void put(Key key, Item data) {
        globalMap.put(new Key(key, default_timeout), data);
    }

    /**
     * Метод для вставки обьекта в кеш
     * @param <Key>
     * @param <Item>
     * @param key ключ в кеше
     * @param data данные
     * @param timeout время хранения обьекта в кеше в милисекундах
     */
    public void put(Key key, Item data, long timeout) {
        globalMap.put(new Key(key, timeout), data);
    }

    /**
     * получение значения по ключу
     * @param <K>
     * @param <V>
     * @param key ключ для поиска с кеша
     * @return Обьект данных храняшийся в кеше
     */
    public Item get(Key key) {
        return globalMap.get(new Key(key));
    }

    /**
     * удаляет все значения по ключу из кеша
     * @param <Key>
     * @param key - ключ
     */
    public void remove(Key key) {
        globalMap.remove(new Key(key));
    }

    /**
     * Удаляет все значения из кеша
     */
    public void removeAll() {
        globalMap.clear();
    }

    /**
     * Полностью заменяет весь существующий кеш.
     * Время хранения по умолчанию.
     * @param <Key>
     * @param <Item>
     * @param map Карта с данными
     */
    public void setAll(Map<Key, Item> map) {
        ConcurrentHashMap tempmap = new ConcurrentHashMap<Key, Item>();
        for (Map.Entry<Key, Item> entry : map.entrySet()) {
            tempmap.put(new Key(entry.getKey(), default_timeout), entry.getValue());
        }
        globalMap = tempmap;
    }

    /**
     * Добавляет к сущесвуещему кешу переданую карту
     * Время хранения по умолчанию.
     * @param <Key>
     * @param <Item>
     * @param map Карта с данными
     */
    public void addAll(Map<Key, Item map) {
        for (Map.Entry<Key, Item> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    private static class Key {

        private final Object key;
        private long timelife;

        public Key(Object key, long timeout) {
            this.key = key;
            this.timelife = System.currentTimeMillis() + timeout;
        }

        public Key(Object key) {
            this.key = key;
        }

        public Object getKey() {
            return key;
        }

        public boolean isLive(long currentTimeMillis) {
            return currentTimeMillis < timelife;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + (this.key != null ? this.key.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "Key{" + "key=" + key + '}';
        }
    }
}
