package com.userbo;

public class AccountUtilsRegressionTest {
	public static void main(String[] args) {
		assert AccountUtils.isValidPolicyEffectiveDate("07/18/2026");
		assert !AccountUtils.isValidPolicyEffectiveDate("TqdiaznrkyfTlQaD");
		assert !AccountUtils.isValidPolicyEffectiveDate("02/30/2026");
		assert !AccountUtils.isValidPolicyEffectiveDate(null);

		com.util.Context ctx = new com.util.Context();
		ctx.put("StateCode", "CA");
		ctx.put("MarketableProductKey", Integer.valueOf(1));
		String lookupKey = AccountUtils.companyLookupKey(ctx, "08/27/2026");
		assert lookupKey.equals(AccountUtils.companyLookupKey(ctx, "08/27/2026"));
		assert !lookupKey.equals(AccountUtils.companyLookupKey(ctx, "08/28/2026"));
		ctx.put("StateCode", "NY");
		assert !lookupKey.equals(AccountUtils.companyLookupKey(ctx, "08/27/2026"));
	}
}
