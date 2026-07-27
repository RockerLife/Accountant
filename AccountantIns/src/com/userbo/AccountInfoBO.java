package com.userbo;

import com.util.InetLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.managemetadata.functions.commonfunctions.DateUtils;
import com.ormapping.ibatis.SqlResources;
import com.util.IContext;

public class AccountInfoBO {
	private static final InetLogger logger = InetLogger.getInetLogger(AccountInfoBO.class);
	@SuppressWarnings("unchecked")
	public void populateAccountID(IContext ctx) throws Exception
	{   
		int accountID=0;
// 		Map maxAccountIDMap = getMaxAccountID(ctx);
//		if(!maxAccountIDMap.isEmpty())
//			if(maxAccountIDMap.get(com.userbo.Constants.MAXACCOUNTID)==null)
//				accountID=0;
//			else
//				accountID = Integer.parseInt(maxAccountIDMap.get(com.userbo.Constants.MAXACCOUNTID).toString() );
//			
//		accountID = accountID+1;
		
		ctx.put(com.userbo.Constants.ACCOUNT_ID, accountID);
		ctx.put(com.userbo.Constants.ACOCUNT_VERSION, "1");
		ctx.put("AppCreatedDate", DateUtils.getTodaysDate());
		
		AccountUtils.populateLastUpdateTimeStamp(ctx);
//		if(ctx.get(com.userbo.Constants.ACCOUNTNAME)!=null && ctx.get(com.userbo.Constants.ACCOUNTNAME)!="")
//			ctx.put("isUncompletedData", "N");
		 
	}
	
	public Map getMaxAccountID(IContext ctx) throws Exception{
		Map maxAccountMap=null;
		try {
			maxAccountMap = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetMaxAccountID", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountInfoBO.getMaxAccountID", e);
			throw e;
		}
		return maxAccountMap;
	}
	
	public Map getMaxAccountNUM(IContext ctx) throws Exception{
		Map maxAccountMap=null;
		try {
			maxAccountMap = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeMaxAccountNum", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountInfoBO.getMaxAccountNUM", e);
			throw e;
		}
		 
		return maxAccountMap;
	}
	
	@SuppressWarnings("unchecked")
	public void getFirmNameAndStateCodeExists(IContext ctx) throws Exception
	{
	  if( "insured".equals(ctx.get("User"))) 
		if(ctx.get("AccountAddressList")!=null)
		{
			List  accAddrsList =(List) ctx.get("AccountAddressList");
			
			 for(int i =0;i< accAddrsList.size();i++)
			 {
				  Map  map =(HashMap)accAddrsList.get(i);
				   
				 if( map.get(com.userbo.Constants.STATE_CODE) != null && map.get(com.userbo.Constants.STATE_CODE).toString().
						 equals(ctx.get(com.userbo.Constants.STATE_CODE) )){
					 
					 if( map.get(com.userbo.Constants.ACCOUNTNAME) != null && map.get(com.userbo.Constants.ACCOUNTNAME).toString().
					 equals(ctx.get(com.userbo.Constants.ACCOUNTNAME))){
				 	
				 	ctx.put(com.userbo.Constants.ACOCUNT_VERSION, accAddrsList.size()+1);
				 	 
			 		}
				 }
			 }
		}
			ctx.remove("AccountAddressList");
	 }
	
	
}
