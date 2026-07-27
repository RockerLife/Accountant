package com.manage.util;

import java.util.List;
import java.util.Map;

import com.util.Context;

public final class BreadcrumbSessionUtils {
	private static final int MAX_PAGES = 10;
	private static final String[] CONTAINER_KEYS = {
		"DocumentRequest", "DocumentResponse", "DocumentServletContext", "DocumentUriResolver"
	};

	private BreadcrumbSessionUtils() {
	}

	public static Context snapshot(Context source) {
		Context snapshot = (Context) source.clone();
		removeContainerReferences(snapshot);
		return snapshot;
	}

	@SuppressWarnings("rawtypes")
	public static void sanitize(Object stackValue) {
		if (!(stackValue instanceof List))
			return;

		List pages = (List) stackValue;
		if (pages.size() > MAX_PAGES)
			pages.subList(0, pages.size() - MAX_PAGES).clear();

		for (Object pageValue : pages) {
			if (!(pageValue instanceof Map))
				continue;

			Object contextValue = ((Map) pageValue).get(PageUtils.BREAD_CRUMB_PAGE_REQUEST);
			if (!(contextValue instanceof Context))
				continue;

			Context storedContext = (Context) contextValue;
			removeContainerReferences(storedContext);
			storedContext.remove(PageUtils.PAGE_LIST_STACK);

			Object sessionValue = storedContext.get("DocumentSessionMap");
			if (sessionValue instanceof Map) {
				Map sessionMap = (Map) sessionValue;
				sessionMap.remove(PageUtils.PAGE_LIST_STACK);
				for (String key : CONTAINER_KEYS)
					sessionMap.remove(key);
			}
		}
	}

	private static void removeContainerReferences(Context context) {
		for (String key : CONTAINER_KEYS)
			context.remove(key);
	}
}
