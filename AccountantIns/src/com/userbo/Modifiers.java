package com.userbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.managemetadata.functions.commonfunctions.DBUtils;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.IContext;
import com.util.StringUtils;
import com.util.SystemProperties;

public class Modifiers {		
	
	public static boolean validateModifiers(Context ctx) throws Exception
    {	
		if(ctx.get("saveModifier") == null)
			return true;		
		
		int percent1 = 0,percent2 = 0,percent3 = 0,totalModifier=0;
		if(ctx.get("SchduleRatingModifier1") != null && !"".equals(ctx.get("SchduleRatingModifier1")))
		{
			if(!StringUtils.isNumeric(ctx.get("SchduleRatingModifier1").toString()))
				AccountUtils.populateError(ctx, "SchduleRatingModifier1", "Claim modifier is not valid");
			
			percent1 = Integer.parseInt(ctx.get("SchduleRatingModifier1").toString());
			if(percent1 < -25 || percent1 > 25)
				AccountUtils.populateError(ctx, "SchduleRatingModifier1", "Claim Modifier Percentage should be between -25 to +25");
		}
		
		if(ctx.get("SchduleRatingModifier2") != null  && !"".equals(ctx.get("SchduleRatingModifier2")))
		{
			if(!StringUtils.isNumeric(ctx.get("SchduleRatingModifier2").toString()))
				AccountUtils.populateError(ctx, "SchduleRatingModifier2", "Claim modifier is not valid");
			
			
			percent2 = Integer.parseInt(ctx.get("SchduleRatingModifier2").toString());
			if(percent2 < -25 || percent2 > 25)
				AccountUtils.populateError(ctx, "SchduleRatingModifier2", "Firm Practice Modifier Percentage should be between -25 to +25");
		}
		
		if(ctx.get("SchduleRatingModifier3") != null  && !"".equals(ctx.get("SchduleRatingModifier3")))
		{
			if(!StringUtils.isNumeric(ctx.get("SchduleRatingModifier3").toString()))
				AccountUtils.populateError(ctx, "SchduleRatingModifier3", "Claim modifier is not valid");			
			
			percent3 = Integer.parseInt(ctx.get("SchduleRatingModifier3").toString());
			if(percent3 < -25 || percent3 > 25)
				AccountUtils.populateError(ctx, "SchduleRatingModifier3", "Firm Ed. and Training Modifier Percentage should be between -25 to +25");
		}
		
		totalModifier=percent1+percent2+percent3;
		if(totalModifier < -25 || totalModifier > 25)
			AccountUtils.populateError(ctx, "totalModifier", "Total Modifier Percentage should be between -25 to +25");
		
		if((ctx.get("ModifierComment") == null || "".equals(ctx.get("ModifierComment")))&& ((ctx.get("SchduleRatingModifier1") != null && !"".equals(ctx.get("SchduleRatingModifier1").toString().trim()) && !"0".equals(ctx.get("SchduleRatingModifier1").toString().trim())) || (ctx.get("SchduleRatingModifier2") != null && !"".equals(ctx.get("SchduleRatingModifier2").toString().trim()) && !"0".equals(ctx.get("SchduleRatingModifier2").toString().trim())) || (ctx.get("SchduleRatingModifier3") != null && !"".equals(ctx.get("SchduleRatingModifier3").toString().trim()) && !"0".equals(ctx.get("SchduleRatingModifier3").toString().trim()))))
			AccountUtils.populateError(ctx, "ModifierComment", "Modifier comment is required");
		
		//if((ctx.get("SchduleRatingModifier1") == null && ctx.get("SchduleRatingModifier2") == null && ctx.get("SchduleRatingModifier3") == null) || ((ctx.get("SchduleRatingModifier1") != null && "".equals(ctx.get("SchduleRatingModifier1").toString().trim())) && (ctx.get("SchduleRatingModifier2") != null && "".equals(ctx.get("SchduleRatingModifier2").toString().trim())) && (ctx.get("SchduleRatingModifier3") != null && "".equals(ctx.get("SchduleRatingModifier3").toString().trim()))))
		
		if((ctx.get("SchduleRatingModifier1") == null && ctx.get("SchduleRatingModifier2") == null && ctx.get("SchduleRatingModifier3") == null) || ((ctx.get("SchduleRatingModifier1") != null && ("".equals(ctx.get("SchduleRatingModifier1").toString().trim()) || "0".equals(ctx.get("SchduleRatingModifier1").toString().trim()))) && (ctx.get("SchduleRatingModifier2") != null && ("".equals(ctx.get("SchduleRatingModifier2").toString().trim()) || "0".equals(ctx.get("SchduleRatingModifier2").toString().trim()))) && (ctx.get("SchduleRatingModifier3") != null && ("".equals(ctx.get("SchduleRatingModifier3").toString().trim()) || "0".equals(ctx.get("SchduleRatingModifier3").toString().trim()))))) 
			ctx.put("ModifierComment",null);
		
		
		return true;
    }	
	
	public void saveCoverageModifiers(IContext ctx)throws Exception{
		
		List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
		if(quoteList!=null)
		{
			try{
				Map  map= new HashMap ();
				for(int i=0;i<quoteList.size();i++){	
					map = (HashMap)quoteList.get(i);
					ctx.put("TransactionSequence", map.get("TransactionSequence"));
					ctx.put("CoverageSequence", map.get("CoverageSequence"));
					ctx.put("PolicyKey", map.get("PolicyKey"));
					addCoverageModifiers(ctx);				
				}
			}catch (Exception e) {
				 e.printStackTrace() ;
				 throw e;
			}
			
			saveModifierComment(ctx);
		}
	
	}	
	
	public void saveRenewCoverageModifiers(IContext ctx)throws Exception{
		
		List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
		if(quoteList!=null)
		{
			try{
				Map  map= new HashMap ();
				for(int i=0;i<quoteList.size();i++){	
					map = (HashMap)quoteList.get(i);
					ctx.put("TransactionSequence", map.get("TransactionSequence"));
					ctx.put("CoverageSequence", map.get("CoverageSequence"));
					ctx.put("PolicyKey", map.get("PolicyKey"));
					addRenewCoverageModifiers(ctx);				
				}
			}catch (Exception e) {
				 e.printStackTrace() ;
				 throw e;
			}
			
			//saveModifierComment(ctx);
		}
	
	}	
	
	

	public void addCoverageModifiers(IContext ctx)throws Exception{
		List previousRenewalModifierList = new ArrayList();
		if(ctx.get("PolicyStatusKey")!= null && "2".equals(ctx.get("PolicyStatusKey").toString())){
			previousRenewalModifierList = getPriorYearRenewModifierList(ctx);
		}
		
		List modifiersList =SqlResources.getSqlMapProcessor(ctx).select("Modifiers.findAll", ctx);	
		
		Object objDefaultModifier = RuleUtils.executeRule(ctx, "AccountantRule.checkDefaultModifierAndComment");
		
		Object objhideModifier = RuleUtils.executeRule(ctx, "AccountantRule.hideModifier");
		
		//Object objIsNotRenewal = RuleUtils.executeRule(ctx, "AccountantRule.IsNotRenewalPolicy");
		
		boolean flaghideModifier = false;
		boolean flag = false;
		//boolean isNotRenewalPolicy = false ;
		
		
		if(objDefaultModifier != null && objDefaultModifier instanceof Boolean)
			flag = (Boolean) objDefaultModifier;
		
		if(objhideModifier != null && objhideModifier instanceof Boolean)
			flaghideModifier = (Boolean) objhideModifier;
		
//		if(objIsNotRenewal != null && objIsNotRenewal instanceof Boolean)
//			isNotRenewalPolicy = (Boolean) objIsNotRenewal;
		
		if(modifiersList!=null)
		{
			try{
				Map  map= new HashMap ();
				for(int i=0;i<modifiersList.size();i++){	
					map = (HashMap)modifiersList.get(i);
					String percentageValue = ctx.get("SchduleRatingModifier"+map.get("ModifierKey").toString()) == null ? null:ctx.get("SchduleRatingModifier"+map.get("ModifierKey").toString()).toString().trim();
					
					Context newCtx = new Context();
					newCtx.put("TransactionSequence", ctx.get("TransactionSequence"));
					newCtx.put("CoverageSequence", ctx.get("CoverageSequence"));
					newCtx.put("PolicyKey", ctx.get("PolicyKey"));
					newCtx.put("VersionSequence", ctx.get("VersionSequence"));
					newCtx.put("ModifierKey", map.get("ModifierKey"));
					newCtx.put("LastUpdateTimeStamp", ctx.get("LastUpdateTimeStamp"));	
					newCtx.setProject(ctx.getProject());					
					
					if(ctx.get("saveModifier") == null)
					{
						if((ctx.get("IsAwarenessOfAnyProfLiability") != null && "N".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString()))
								&& (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null && "N".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))
								)
							{
								
							if(!flaghideModifier)
								{
									if(percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
									{	
										if(flag && "1".equals(map.get("ModifierKey").toString()))
										{
											percentageValue = "-10";									
										}
									
									}
								}
							}
							
							if(ctx.get("IsSignedEngagement") != null && "Y".equals(ctx.get("IsSignedEngagement").toString()))
							{
								if(!flaghideModifier)
								{
									if(percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
									{
										if(flag && "2".equals(map.get("ModifierKey").toString()))
										{
											percentageValue = "-5";
										}
									}
								}
							}
							
							if(ctx.get("PolicyStatusKey")!= null && "2".equals(ctx.get("PolicyStatusKey").toString())){
								for(int k = 0 ; k < previousRenewalModifierList.size() ; k++){
								Map previousMap = (Map)previousRenewalModifierList.get(k);
									if(previousMap.get("ModifierKey").toString().equals(newCtx.get("ModifierKey").toString())){
										String previousRenewalPercentage = previousMap.get("RenewalPercentage")!= null ? previousMap.get("RenewalPercentage").toString() : "";
										if(!previousRenewalPercentage.equals("") && !"0".equals(previousRenewalPercentage))
											percentageValue = previousRenewalPercentage ;
									}
								}
							}
							
							if(i == 0){
								List aopLimtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);
								String aopPercentageValue = "";
								if (aopLimtTypes != null) {
									try {
										Map aopMap = new HashMap();
										for (int aopCount = 0; aopCount < aopLimtTypes.size(); aopCount++) {
											aopMap = (HashMap) aopLimtTypes.get(aopCount);
											aopPercentageValue = ctx.get("AOP_Percentage_" + aopMap.get("AOPKey").toString()) == null ? null : ctx.get("AOP_Percentage_" + aopMap.get("AOPKey").toString()).toString().trim();
											if (aopPercentageValue != null && !"".equals(aopPercentageValue) && !"0".equals(aopPercentageValue) && 
													!"AOP_Percentage_1".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && !"AOP_Percentage_2".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && 
													!"AOP_Percentage_3".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && !"AOP_Percentage_4".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && 
													!"AOP_Percentage_6".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && !"AOP_Percentage_7".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && 
													!"AOP_Percentage_8".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString()) && !"AOP_Percentage_22".equals("AOP_Percentage_" + aopMap.get("AOPKey").toString())){
												aopPercentageValue = "OTHERAOP";
												break;
											}
										}
									}catch(Exception e){
										e.printStackTrace();
									}
								}
								if(!"OTHERAOP".equals(aopPercentageValue)){
									Object objAmount = AccountUtils.removeAmountFormat(ctx.get("Amount"));
									long amount = objAmount!=null && !"".equals(objAmount.toString())? Long.parseLong(objAmount.toString()):0;
									long businessAOP = (ctx.get("AOP_Percentage_1") == null || "".equals(ctx.get("AOP_Percentage_1"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_1").toString());
									long estateAOP = (ctx.get("AOP_Percentage_2") == null || "".equals(ctx.get("AOP_Percentage_2"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_2").toString());
									long individualAOP = (ctx.get("AOP_Percentage_3") == null || "".equals(ctx.get("AOP_Percentage_3"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_3").toString());
									long bookkeepingAOP = (ctx.get("AOP_Percentage_4") == null || "".equals(ctx.get("AOP_Percentage_4"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_4").toString());
									long privateAuditAOP = (ctx.get("AOP_Percentage_6") == null || "".equals(ctx.get("AOP_Percentage_6"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_6").toString());
									long reviewAOP = (ctx.get("AOP_Percentage_7") == null || "".equals(ctx.get("AOP_Percentage_7"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_7").toString());
									long compilationAOP = (ctx.get("AOP_Percentage_8") == null || "".equals(ctx.get("AOP_Percentage_8"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_8").toString());
									long generalBusinessAOP = (ctx.get("AOP_Percentage_22") == null || "".equals(ctx.get("AOP_Percentage_22"))) ? 0l : Long.parseLong(ctx.get("AOP_Percentage_22").toString());
									if((0 > amount && amount < 500000) && (businessAOP != 0 || estateAOP != 0 || individualAOP != 0
										|| bookkeepingAOP != 0 || privateAuditAOP != 0 || reviewAOP != 0
										|| compilationAOP != 0 || generalBusinessAOP != 0)){
										percentageValue = SystemProperties.getInstance().getString("appl."+ctx.getProject()+".ModifierQQ");
										ctx.put("QuickQuoteModificationFactorFlag", "Y");
									} else {
										ctx.put("QuickQuoteModificationFactorFlag", "N");
										//percentageValue = "";
									}
								} else {
									ctx.put("QuickQuoteModificationFactorFlag", "N");
									percentageValue = "";
								}
							}
					}					
					
					
					
					if(percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
						percentageValue = "0";
					
					newCtx.put("Percentage", percentageValue);	 
					Object obj2 = DBUtils.executeDBOperation(newCtx, "CoverageModifiers", "3");
					
					AccountUtils.populateLastUpdateTimeStamp(newCtx);
					newCtx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
			   	 	 
					 if(obj2==null)
					 {
						 DBUtils.executeDBOperation(newCtx, "CoverageModifiers", "1");
						 
					 }else{
						  DBUtils.executeDBOperation(newCtx, "CoverageModifiers", "2");
		        	 	  DBUtils.executeDBOperation(newCtx, "CoverageModifiers", "1");
					 }		
					 
				}
			}catch (Exception e) {
				 e.printStackTrace() ;
				 throw e;
			}
		
		}
	
	}
	
	public List getPriorYearRenewModifierList(IContext ctx) throws Exception{
		
		List previousRenewalModifierList = new ArrayList();
		
		Map previousMap = new HashMap();
		Object obj = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",ctx);
		if (obj != null && obj instanceof List) {
			Context previousContext = new Context();

			List policyList = (List) obj;
			if (policyList != null && policyList.size() > 0) {

				Map policyMap = (Map) policyList.get(policyList.size() - 2);

				previousContext.putAll(ctx);
				previousContext.put("PolicyKey", policyMap.get("PolicyKey"));

				previousRenewalModifierList = SqlResources.getSqlMapProcessor(previousContext).select("SqlStmts.UserStatementsaveAccountstmtspopulateCoverageModifiers", previousContext);						
			}
		}
		return previousRenewalModifierList;
		
	}
	
	public void addRenewCoverageModifiers(IContext ctx)throws Exception{
		
		List modifiersList =SqlResources.getSqlMapProcessor(ctx).select("Modifiers.findAll", ctx);	
		
		
		if(modifiersList!=null)
		{
			try{
				Map  map= new HashMap ();
				for(int i=0;i<modifiersList.size();i++){	
					map = (HashMap)modifiersList.get(i);
					String percentageValue = ctx.get("SchduleRatingModifierR"+map.get("ModifierKey").toString()) == null ? null:ctx.get("SchduleRatingModifierR"+map.get("ModifierKey").toString()).toString().trim();
					
					Context newCtx = new Context();
					newCtx.put("TransactionSequence", ctx.get("TransactionSequence"));
					newCtx.put("CoverageSequence", ctx.get("CoverageSequence"));
					newCtx.put("PolicyKey", ctx.get("PolicyKey"));
					newCtx.put("VersionSequence", ctx.get("VersionSequence"));
					newCtx.put("ModifierKey", map.get("ModifierKey"));
					newCtx.put("Percentage", map.get("Percentage"));
					newCtx.setProject(ctx.getProject());	
					
					if(percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
						percentageValue = "0";
					
					newCtx.put("RenewalPercentage", percentageValue);	 
					Object obj2 = DBUtils.executeDBOperation(newCtx, "CoverageModifiers", "3");
					
					AccountUtils.populateLastUpdateTimeStamp(newCtx);
					newCtx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
					
					SqlResources.getSqlMapProcessor(newCtx).update("SqlStmts.UserStatementsaveAccountstmtsupdateRenewalCoverageModifier", newCtx);	
					 
				}
			}catch (Exception e) {
				 e.printStackTrace() ;
				 throw e;
			}
		
		}
	
	}	
	
	public void saveModifierComment(IContext ctx)throws Exception{
		
		Context newCtx = new Context();
		newCtx.put("PolicyKey", ctx.get("PolicyKey"));
		newCtx.put("VersionSequence", ctx.get("VersionSequence"));
		newCtx.setProject(ctx.getProject());
		
		String ModifierComment = null;
		Object objDefaultModifier = RuleUtils.executeRule(ctx, "AccountantRule.checkDefaultModifierAndComment");
		boolean flag = false;
		if(objDefaultModifier != null && objDefaultModifier instanceof Boolean)
			flag = (Boolean) objDefaultModifier;
		
		
		if(ctx.get("ModifierComment") != null)
			ModifierComment = ctx.get("ModifierComment").toString();
		else
			ModifierComment = "";
		
		if(ctx.get("saveModifier") == null)
		{
			if(flag)
			{					
				if((ctx.get("IsAwarenessOfAnyProfLiability") != null && "N".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString()))
						&& (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null && "N".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))
						)
					{
						if(ctx.get("ModifierComment") == null)
							ModifierComment = ModifierComment + "Claims free credit";				
					}
				
					if(ctx.get("IsSignedEngagement") != null && "Y".equals(ctx.get("IsSignedEngagement").toString()))
					{
						if(ctx.get("ModifierComment") == null)
							ModifierComment = ModifierComment + ",Firm Practice credit";					
					}
					
					newCtx.put("ModifierComment", ModifierComment);
					SqlResources.getSqlMapProcessor(newCtx).update("SqlStmts.UserStatementprocessquoteupdateModifierComment", newCtx);
			}
			
		}					
		else
		{
			newCtx.put("ModifierComment", ctx.get("ModifierComment"));
			SqlResources.getSqlMapProcessor(newCtx).update("SqlStmts.UserStatementprocessquoteupdateModifierComment", newCtx);
		}	
		
	}

	public void populateCoverageModifiers(IContext ctx)throws Exception{
		
		List modifiersList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateCoverageModifiers", ctx);
		ctx.remove("CoverageModifiers_freeform_01");
		
		Map covMap = new HashMap();
		if(modifiersList!=null){
			Map map= new HashMap();
			for(int i=0;i<modifiersList.size();i++){	
				map = (HashMap)modifiersList.get(i);
				covMap.put("SchduleRatingModifier"+map.get("ModifierKey"), map.get("Percentage")!=null && "0".equals(map.get("Percentage").toString())?"":map.get("Percentage"));
				covMap.put("SchduleRatingModifierR"+map.get("ModifierKey"), map.get("RenewalPercentage")!=null && "0".equals(map.get("RenewalPercentage").toString())?"":map.get("RenewalPercentage"));
			}
		}
		
		ctx.put("SchduleRatingModifier1", covMap.get("SchduleRatingModifier1"));
		ctx.put("SchduleRatingModifier2", covMap.get("SchduleRatingModifier2"));
		ctx.put("SchduleRatingModifier3", covMap.get("SchduleRatingModifier3"));			
		covMap.put("ModifierComment", ctx.get("ModifierComment"));
		
		
		
		ctx.put("SchduleRatingModifierR1", covMap.get("SchduleRatingModifierR1"));
		ctx.put("SchduleRatingModifierR2", covMap.get("SchduleRatingModifierR2"));
		ctx.put("SchduleRatingModifierR3", covMap.get("SchduleRatingModifierR3"));	
		
		ctx.put("Modifiers_freeform_01",covMap);
		
		
		
		
	}
	

	
	public void saveQuoteSentToPDF(IContext ctx) throws Exception
    {
		if(ctx.get("isQuoteSelected") != null && "Y".equals(ctx.get("isQuoteSelected").toString()))
		{
			//RuleUtils.executeRule(ctx, "AccountantRule.fillQuoteDates");
			//SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementpdfquoteletterupdateFirm", ctx);
			
			List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
			
			if(quoteList!=null)
			{        	
	    		for(int i=0;i<quoteList.size();i++)
	    		{    			
	    			 Map map = (Map) quoteList.get(i);	        			 
	    			 Context newctx = new Context();
	    			 newctx.putAll(map);
	    			 newctx.setProject(ctx.getProject());
	    			 newctx.put("PolicyKey", map.get("PolicyKey"));
	    			 newctx.put("TransactionSequence", map.get("TransactionSequence"));
	    			 
	    			 if(ctx.get("IsQuoteSelected"+"_"+i) != null && ("on".equals(ctx.get("IsQuoteSelected"+"_"+i).toString())||"Y".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()) ))
	    				 newctx.put("IsQuoteSelected","Y");
	    			 else
	    				 newctx.put("IsQuoteSelected",ctx.get("IsQuoteSelected"+"_"+i));
					 
					 Object obj2 = DBUtils.executeDBOperation(newctx, "Quote", "3");           	 	 
	    			 if(obj2==null)
	    			 {
	    				 DBUtils.executeDBOperation(newctx, "Quote", "1");
	    			 }
	    			 else
	    			 {
					     DBUtils.executeDBOperation(newctx, "Quote", "2");
	        	 	     DBUtils.executeDBOperation(newctx, "Quote", "1");
	    			 }		       				 
	    			 
	    		}
	        }
		}			
	}
	
	
    
}
