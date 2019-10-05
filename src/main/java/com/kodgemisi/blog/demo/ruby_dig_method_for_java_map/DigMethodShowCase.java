package com.kodgemisi.blog.demo.ruby_dig_method_for_java_map;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class DigMethodShowCase {

	public static void main(String[] args) throws Exception {

		String json = new String(DigMethodShowCase.class.getResourceAsStream("/sample.json").readAllBytes(), StandardCharsets.UTF_8);
		Map<String, ?> map = new ObjectMapper().readValue(json, Map.class); //

		// dig returns an Integer, `+` operator calls its .toString() so there is no casting exception
		System.out.println("existing: " + dig(map, "first", "second", "third"));

		System.out.println("missing: " + dig(map, "first", "X", "third"));
	}

	static <R> Optional<R> dig(Map<String, ?> map, String... keys) {

		if (map == null) {
			throw new IllegalArgumentException("'map' cannot be null!");
		}

		if (keys == null || keys.length == 0) {
			throw new IllegalArgumentException("You should provide at least one key!");
		}

		Map<String, ?> currentMap = map;

		for (int i = 0; i < keys.length; ++i) {
			final String key = keys[i];
			final Object value = currentMap.get(key);
			if (value instanceof Map) { // also checks for null
				currentMap = (Map<String, ?>) value;
			}
			else {
				if (i == keys.length - 1) { // if it's the last element then we've got the result
					final R retVal = (R) value;
					return Optional.ofNullable(retVal);
				}
				return Optional.empty();
			}
		}

		return Optional.empty();
	}
}
