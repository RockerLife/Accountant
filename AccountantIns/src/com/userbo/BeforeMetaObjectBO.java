package com.userbo;

import com.manage.managecomponent.components.Businessobject;
import com.util.IContext;
/*import com.xsd.Label;
import com.xsd.MyHandler;*/

public class BeforeMetaObjectBO extends Businessobject {

	public boolean evaluate(IContext data) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public Object execute(IContext ctx) throws Exception {
		
		if(ctx.get("next_page_for_view") != null && !"".equals(ctx.get("next_page_for_view").toString())){
			ctx.put("object_name", ctx.get("next_page_for_view").toString());
			
		}
		return null;
	}
	
}
