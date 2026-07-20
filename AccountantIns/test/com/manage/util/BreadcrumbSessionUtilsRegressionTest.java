package com.manage.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.Context;

public class BreadcrumbSessionUtilsRegressionTest {
	public static void main(String[] args) throws Exception {
		Context source = new Context();
		source.put("DocumentRequest", new Object());
		source.put("DocumentResponse", new Object());

		Context snapshot = BreadcrumbSessionUtils.snapshot(source);
		assertFalse(snapshot.containsKey("DocumentRequest"));
		assertFalse(snapshot.containsKey("DocumentResponse"));

		List<Map<Object, Object>> stack = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> page = new HashMap<Object, Object>();
		Map<Object, Object> sessionMap = new HashMap<Object, Object>();
		snapshot.put(PageUtils.PAGE_LIST_STACK, stack);
		sessionMap.put(PageUtils.PAGE_LIST_STACK, stack);
		sessionMap.put("DocumentServletContext", new Object());
		snapshot.put("DocumentSessionMap", sessionMap);
		page.put(PageUtils.BREAD_CRUMB_PAGE_REQUEST, snapshot);
		stack.add(page);

		BreadcrumbSessionUtils.sanitize(stack);
		assertFalse(snapshot.containsKey(PageUtils.PAGE_LIST_STACK));
		assertFalse(sessionMap.containsKey(PageUtils.PAGE_LIST_STACK));
		assertFalse(sessionMap.containsKey("DocumentServletContext"));

		ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
		output.writeObject(stack);
		output.close();
	}

	private static void assertFalse(boolean value) {
		if (value)
			throw new AssertionError("Expected value to be absent");
	}
}
