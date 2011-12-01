package net.sourceforge.greenvine.generator.template;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TemplateContext implements Map<String,Object> {
	
	
	private final HashMap<String,Object> backingMap = new HashMap<String,Object>();

	public TemplateContext() {
		super();
	}
	
	public void clear() {
		backingMap.clear();	
	}

	public boolean containsKey(Object key) {
		return backingMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return backingMap.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return backingMap.entrySet();
	}

	public Object get(Object key) {
		return backingMap.get(key);
	}

	public boolean isEmpty() {
		return backingMap.isEmpty();
	}

	public Set<String> keySet() {
		return backingMap.keySet();
	}

	public Object put(String key, Object value) {
		return backingMap.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> map) {
		backingMap.putAll(map);
	}

	public Object remove(Object key) {
		return backingMap.remove(key);
	}

	public int size() {
		return backingMap.size();
	}

	public Collection<Object> values() {
		return backingMap.values();
	}
}
