package com.userbo;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.InetLogger;

final class CompanyLookup {
	private static final String CACHE_KEY = "GetCompanyListACC_p_CacheKey";
	private static final String CACHE_RESULT = "GetCompanyListACC_p_CacheResult";
	private static final InetLogger logger = InetLogger.getInetLogger(CompanyLookup.class);

	private CompanyLookup() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void populate(Context ctx) {
		Object originalEffectiveDate = ctx.get("PolicyEffectiveDate");
		try {
			String effectiveDate = normalize(originalEffectiveDate);
			if (effectiveDate != null && !isValidEffectiveDate(effectiveDate))
				return;

			String key = cacheKey(ctx, effectiveDate);
			Object cachedResult = ctx.get(CACHE_RESULT);
			if (key.equals(ctx.get(CACHE_KEY)) && cachedResult instanceof Map) {
				ctx.putAll((Map) cachedResult);
				return;
			}

			ctx.put("PolicyEffectiveDate", effectiveDate);
			new SetParametersForStoredProcedures().setParametersInContext(ctx,
					"StateCode,MarketableProductKey,PolicyEffectiveDate");
			Object result = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.GetCompanyListACC_p", ctx);
			if (result instanceof Map) {
				Map resultMap = (Map) result;
				ctx.putAll(resultMap);
				// ponytail: policy-context cache assumes StateCompany mappings stay fixed during the policy session.
				ctx.put(CACHE_RESULT, new HashMap(resultMap));
				ctx.put(CACHE_KEY, key);
			}
		} catch (Exception e) {
			logger.error("Error while setting up company", e);
		} finally {
			ctx.put("PolicyEffectiveDate", originalEffectiveDate);
		}
	}

	static String cacheKey(Context ctx, String effectiveDate) {
		return String.valueOf(ctx.get("StateCode")) + "|" + String.valueOf(ctx.get("MarketableProductKey")) + "|"
				+ effectiveDate;
	}

	static boolean isValidEffectiveDate(Object value) {
		if (value instanceof Date)
			return true;
		if (value == null || value.toString().trim().isEmpty())
			return false;

		String date = value.toString().trim();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setLenient(false);
		ParsePosition position = new ParsePosition(0);
		Date parsed = format.parse(date, position);
		return parsed != null && position.getIndex() == date.length() && date.equals(format.format(parsed));
	}

	private static String normalize(Object value) {
		if (value instanceof Date)
			return new SimpleDateFormat("MM/dd/yyyy").format((Date) value);
		if (value == null)
			return null;
		String normalized = value.toString().trim();
		return normalized.isEmpty() ? null : normalized;
	}
}
