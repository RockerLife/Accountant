package com.userbo;

import com.util.Context;

public class BeforeMetaObjectBORegressionTest {
	public static void main(String[] args) throws Exception {
		Context ctx = new Context();
		ctx.put("next_page_for_view", "ManageAccounts");

		new BeforeMetaObjectBO().execute(ctx);

		assert "ManageAccounts".equals(ctx.get("object_name"));
		assert "ManageAccounts".equals(ctx.get("inet_page"));
		assert "ManageAccounts".equals(ctx.get("NEXT_PAGE_FOR_PARSE"));

		ctx.put("inet_page", "workq");
		ctx.put("NEXT_PAGE_FOR_PARSE", "workq");
		new BeforeMetaObjectBO().execute(ctx);
		assert "workq".equals(ctx.get("inet_page"));
		assert "workq".equals(ctx.get("NEXT_PAGE_FOR_PARSE"));

		ctx.put("inet_page", " ");
		ctx.put("NEXT_PAGE_FOR_PARSE", "");
		new BeforeMetaObjectBO().execute(ctx);
		assert "ManageAccounts".equals(ctx.get("inet_page"));
		assert "ManageAccounts".equals(ctx.get("NEXT_PAGE_FOR_PARSE"));
	}
}
