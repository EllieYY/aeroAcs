package com.wim.aero.acs.util.cache;

import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 带过期时间的内存缓存。 参考：https://blog.csdn.net/wab719591157/article/details/78029861
 * 
 * 单例模式创建本地缓存实例 + 定时器定时扫描缓存是否过期
 * 
 * @author sp42 frank@ajaxjs.com
 *
 */
@Data
public class ExpireCache<K, V> extends ConcurrentHashMap<K, V> implements Cache<K, V> {
	private static final long serialVersionUID = 3850668473354271847L;

	/**
	 * 获取缓存中的数据
	 * 
	 * @param key 缓存 KEY
	 * @return 缓存的数据，找不到返回或者过期则直接返回 null
	 */
	@Override
	public V get(Object key) {
		ExpireCacheData<Object> data = (ExpireCacheData<Object>) super.get(key);
		long now = new Date().getTime();

		if (data != null && (data.expire <= 0 || data.saveTime >= now)) {
			return (V) data.data;
		} else if (data != null && data.load != null) {
			Object value = data.load.get();
			data.data = value;
			data.saveTime = now + data.expire; // 重新算过存活时间

			return (V) value;
		}

		return null;
	}

	@Override
	public V put(K key, V value) {
		return super.put(key, (V) new ExpireCacheData<>(value, 2700));
	}

	@Override
	public V put(K key, V value, int expire) {
		if (expire <= 0) {
			expire = 60;
		}
		return super.put(key, (V) new ExpireCacheData<>(value, expire));
	}

	/**
	 * 获取缓存中的数据（避免强类型转换的麻烦）
	 * 
	 * @param <T> 缓存的类型
	 * @param key 缓存 KEY
	 * @param clz 缓存的类型
	 * @return 缓存的数据，找不到返回或者过期则直接返回 null
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> T get(String key, Class<T> clz) {
		Object obj = get(key);
		return obj == null ? null : (T) obj;
	}


	/**
	 * 设置 key 与 cache
	 *
	 * @param key    缓存 KEY
	 * @param data   要缓存的数据
	 * @param load   数据装载器，如果缓存中没有数据或者已经过期，则调用数据装载器加载最新的数据并且加入缓存，并返回
	 */
	@Override
	public V put(K key, V data, Supplier<Object> load) {
		return super.put(key, (V) new ExpireCacheData<>(data, 2700, load));
	}


	@Override
	public boolean containsKey(Object key) {
		if (get(key) != null) {
			return true;
		}
		return false;
	}



	//	@Override
//	public Set<Entry<K, V>> entrySet() {
//		return super.entrySet();
//	}
}
