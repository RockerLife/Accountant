package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;

import com.manage.managebusinessrules.rules.RulesResources;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.ormapping.ibatis.SqlResources;
import com.outline.rules.pojo.AuditAcc;
import com.outline.rules.pojo.BasicInfoAcc;
import com.outline.rules.pojo.BusinessManagementAcc;
import com.outline.rules.pojo.ComputerservicesAcc;
import com.outline.rules.pojo.GeneralAcc;
import com.outline.rules.pojo.InvestmentAcc;
import com.outline.rules.pojo.PracticreManagementAcc;

public class RuleEngineUtilityNewApp {
	private static InetLogger logger = InetLogger.getInetLogger(RuleEngineUtilityNewApp.class);
	
	
	@SuppressWarnings("unchecked")
	public static void evaluateFirmNewApp(Context ctx)
	{
 		String ruleid="";
		try
		{
			
			//List<Context> ruleSetContextList = new ArrayList<Context>();
			ctx.put("currentDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			ctx.put("effectiveDateOfPolicy", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
			
			long daysDifferenceForEfectiveDate=0;
			try
			{
			 daysDifferenceForEfectiveDate=(DateUtils.calculateDiffInDays(new Date(ctx.get("effectiveDateOfPolicy").toString()),new Date(ctx.get("currentDate").toString()))+1);

			}
			catch(Exception e)
			{
				logger.error("exeception occured while getting days difference from effective and  current date", e);
			}
			ctx.put("daysDifferenceForEfectiveDate",daysDifferenceForEfectiveDate);
		
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				if(ctx.get("Address1")!=null)
				{
					ruleid="firmAddress_Accountant";	
					boolean containsPOBOX=false;
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					String address1 = ctx.get("Address1") != null ? ctx.get("Address1").toString().trim().toLowerCase() : "";
					//String address2 = ctx.get("Address2") != null ? ctx.get("Address2").toString().trim().toLowerCase()  : "";
					String poBoxNamePatterns="P.O. Box,P. o. Box,p. O. Box,p. o. box,p. O. box,P. o. box,PO Box,po Box,PO box,po box,PO. Box,po. box,PO. box,po.box";
					String[] poPatterns=getTokens(poBoxNamePatterns.toString().trim().toLowerCase(),",");
					for(int i=0;i<poPatterns.length;i++)
					{
						if(address1.contains(poPatterns[i])) /*||address2.contains(poPatterns[i]))*/
						{
							containsPOBOX=true;
						}
						
					}
					ctx.put("containsPOBOXInAddress", containsPOBOX);
					BasicInfoAcc basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				
				ruleid="validateEffectiveDateWithDaysNewBusiness_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				BasicInfoAcc basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				
				ruleid="validateEffectiveDateWithCurrentDateNewBusiness_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isCurrentDateAfterEfectiveDate",DateUtils.isDateBefore(ctx.get("effectiveDateOfPolicy"), ctx.get("currentDate")));
	    		basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
			
			}
			if(policyStatusKey==2)
			{
				List previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
				ctx.putAll((Map) previousPolicyKey.get(0));
				Context oldPolicyContext=new Context();
				oldPolicyContext.setProject(ctx.getProject());
				oldPolicyContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
				oldPolicyContext.put("VersionSequence",ctx.get("previousVersionSequence"));
				List statecodeOld = SqlResources.getSqlMapProcessor(oldPolicyContext).select("SqlStmts.UserStatementdroolQueriesgetAddressDetails",oldPolicyContext);
				oldPolicyContext.putAll((Map) statecodeOld.get(0));
				
				//PO box referral validation
				if(ctx.get("Address1")!=null)
				{
					ruleid="firmAddressRenew_Accountant";	
					boolean containsPOBOX=false;
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					String address1 = ctx.get("Address1") != null ? ctx.get("Address1").toString().trim().toLowerCase() : "";
					String poBoxNamePatterns="P.O. Box,P. o. Box,p. O. Box,p. o. box,p. O. box,P. o. box,PO Box,po Box,PO box,po box,PO. Box,po. box,PO. box,po.box";
					String[] poPatterns=getTokens(poBoxNamePatterns.toString().trim().toLowerCase(),",");
					for(int i=0;i<poPatterns.length;i++)
					{
						if(address1.contains(poPatterns[i]))
							containsPOBOX=true;
						
					}
					ctx.put("containsPOBOXInAddressRenew", containsPOBOX);
					BasicInfoAcc basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="firmAddressRenew_Accountant";	
					archiveFlag(ctx,ruleid);
					
				}
				//Policy Effective date Validation
				ruleid="validateEffectiveDateWithCurrentDateRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isCurrentDateAfterEfectiveDateRenew",DateUtils.isDateBefore(ctx.get("effectiveDateOfPolicy"), ctx.get("currentDate")));
				BasicInfoAcc basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				
				
				ruleid="validateEffectiveDateWithDaysRenewBusiness_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				
				//Validate state change referral
				if(!(ctx.get("StateCode").toString()).equalsIgnoreCase(oldPolicyContext.get("StateCode").toString()))
				{
					ctx.put("newPolicyState", ctx.get("StateCode"));
					ctx.put("oldPolicyState", oldPolicyContext.get("StateCode"));
					ruleid="checkStateRenew_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="checkStateRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				
			}
		
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evaulateFirmNewApp"+e, e);
		}
	}
	
	public  static void evaluateInsuranceHistory(Context ctx)
	{	String ruleid="";
		BasicInfoAcc basicinfo=null;
		try
		{
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetTransactionDataDetails", ctx);
			if(obj != null && obj instanceof Map)
			{
				ctx.putAll((Map)obj);
			}
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(ctx.get("IsFirmCarryingProfLiabilityIns")!=null && ctx.get("IsFirmCarryingProfLiabilityIns").toString().equals("Y"))
			{	
				ruleid="ValidateForPriorCopyofPolicy_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				RuleEngineUtilityNewApp.insertRulesToDatabaseTemp(ctx,ruleid,"Confirm that copy of current policy is received"," ");
			
				
				if( ctx.get("IsPriorActDateFull")!=null && ctx.get("IsPriorActDateFull").toString().equals("Y"))
				{
				ruleid="fullPriorActDateInsuranceCoverage_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("firmYear",Integer.parseInt(ctx.get("FirmYear").toString()));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="fullPriorActDateInsuranceCoverage_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				int limitSequenceDifference=Integer.valueOf(ctx.get("LimitSequence").toString())-Integer.valueOf(ctx.get("LimitSequencePross").toString());
				if(limitSequenceDifference!=0)
				{
				ctx.put("limitSequenceDifference", limitSequenceDifference);
				ruleid="validateLimitsNewBusiness_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="validateLimitsNewBusiness_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("PolicyExpirationDatePross")!=null)
				{
				ctx.put("policyExpiryDateNew",new Date(ctx.get("PolicyExpirationDate").toString()).getTime());
				ctx.put("policyExpiryDateNewPros", new Date(ctx.get("PolicyExpirationDatePross").toString()).getTime());
				ctx.put("effectiveDateOfPolicyNew",new Date(new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString()))).getTime());
				ruleid="validateExpirationDateNewBusiness_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="validateExpirationDateNewBusiness_Accountant";	
					archiveFlag(ctx,ruleid);
					
				}
				
			}
			else
			{
				ruleid="ValidateForPriorCopyofPolicy_Accountant";	
				archiveFlag(ctx,ruleid);
				ruleid="fullPriorActDateInsuranceCoverage_Accountant";	
				archiveFlag(ctx,ruleid);
				ruleid="validateLimitsNewBusiness_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			
		}
		catch(Exception e)
		{
			logger.error("Error Occured evaluateInsuranceHistory method : " + e.getMessage(), e);
		}
	}
	
	public  static void evaluateAboutFirm(Context ctx)
	{
			String ruleid="";
			BasicInfoAcc basicinfo=null;
		try
		{
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
		
			if(policyStatusKey==1)
			{
				int sum=ctx.get("TotalFirmPersonnel")!=null && !ctx.get("TotalFirmPersonnel").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("TotalFirmPersonnel").toString()):0;								
				ruleid="totalNoOfPerson_Accountant";	
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("totalFirmPersonnel", sum);
				 basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
		
				if(ctx.get("Amount_0")!=null)
				{
					int amountLastCompleted,amountOneYear,amountTwoYear=0,annualRevenue_Amount=0;;
					amountLastCompleted=ctx.get("Amount_0")!=null && !ctx.get("Amount_0").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("Amount_0").toString().replaceAll("[-+.^:,$]","")):0;
					amountOneYear=ctx.get("Amount_1")!=null && !ctx.get("Amount_1").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("Amount_1").toString().replaceAll("[-+.^:,$]","")):0;
					amountTwoYear= ctx.get("Amount_2")!=null && !ctx.get("Amount_2").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("Amount_2").toString().replaceAll("[-+.^:,$]","")):0;
					annualRevenue_Amount=(amountLastCompleted+amountOneYear+amountTwoYear)/3;
					
					ctx.put("annualRevenue_Amount0",annualRevenue_Amount);
					
					ruleid="annualRevenueLastBusinessYear_Accountant";		
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					 basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				
					if(annualRevenue_Amount<500000)
					{
						ruleid="hasFirmReceivedNonMonetaryCompensation_Accountant";	
						archiveFlag(ctx,ruleid);
					}
					ctx.put("annualRevenue_Amount0",annualRevenue_Amount);
					
					ruleid="annualRevenueLastBusinessYear2M_Accountant";		
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="annualRevenueLastBusinessYear_Accountant";	
					archiveFlag(ctx,ruleid);
					ruleid="annualRevenueLastBusinessYear2M_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
			if(policyStatusKey==2)
			{
				List previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
				ctx.putAll((Map) previousPolicyKey.get(0));
				Context oldPolicyContext=new Context();
				oldPolicyContext.setProject(ctx.getProject());
				oldPolicyContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
				oldPolicyContext.put("VersionSequence",ctx.get("previousVersionSequence"));
				
				Context oldFirmContext=new Context();
				oldFirmContext.setProject(ctx.getProject());
				List firmDetailsOld= (List) SqlResources.getSqlMapProcessor(oldPolicyContext).select("SqlStmts.UserStatementdroolQueriesgetFirmDetails",oldPolicyContext);
				oldFirmContext.putAll((Map) firmDetailsOld.get(0));
				
			if(ctx.get("TotalFirmPersonnel")!=null && Integer.parseInt(ctx.get("TotalFirmPersonnel").toString()) != Integer.parseInt(oldFirmContext.get("TotalFirmPersonnel").toString()))
			{
				int totalFirmPersonnel=Integer.parseInt(ctx.get("TotalFirmPersonnel").toString());
				int totalFirmPersonnelOld=Integer.parseInt(oldFirmContext.get("TotalFirmPersonnel").toString());
				int result=totalFirmPersonnel-totalFirmPersonnelOld;
				ruleid="totalNoOfPersonRenew_Accountant";	
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("totalFirmPersonnel",result);
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
			
			}
			else
			{
				ruleid="totalNoOfPersonRenew_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			
			if(ctx.get("Amount")!=null)
			{
				
				int amountLastCompleted;
				String annualRevenue_0;
				
				annualRevenue_0=ctx.get("Amount").toString().replaceAll("[-+.^:,$]","");
				 if(annualRevenue_0==null || annualRevenue_0.isEmpty() || annualRevenue_0.equals(""))
					 amountLastCompleted=0;
				
				 else
					 amountLastCompleted=Integer.parseInt(annualRevenue_0);
				 
				 int annualRevenue_Amount=amountLastCompleted;
				ctx.put("annualRevenue_Amount0",annualRevenue_Amount);
				ruleid="annualRevenueLastBusinessYearRenew_Accountant";		
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				 basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				
			}
			else
			{
				ruleid="annualRevenueLastBusinessYearRenew_Accountant";	
				archiveFlag(ctx,ruleid);
				
			}
			
			List grossRevenuePrevious = SqlResources.getSqlMapProcessor(oldPolicyContext).select("SqlStmts.UserStatementdroolQueriesgetGrossRevenueForFirmRenew",oldPolicyContext);
			oldPolicyContext.putAll((Map) grossRevenuePrevious.get(0));
			boolean flagAnnualRevenue =false;
			String oldAmount,newAmount;
			newAmount=ctx.get("Amount").toString().replaceAll("[-+.^:,$]","");
			oldAmount=oldPolicyContext.get("Amount").toString().replaceAll("[-+.^:,$]","");
			{ if (logger.isDebugEnabled()) logger.debug("New amount.."+ newAmount+"...old Amount.."+oldAmount); }
			if(!newAmount.equals(oldAmount) && Integer.parseInt(newAmount)>2000000)
			{
				flagAnnualRevenue=true;
				
			}
			ctx.put("flagAnnualRevenue", flagAnnualRevenue);
			ruleid="annualRevenueRenew_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
			ctx.put("RuleObject",basicinfo);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
	
		}
		else
		{
			ruleid="annualRevenueRenew_Accountant";	
			archiveFlag(ctx,ruleid);
		}	
			
			if(ctx.get("IsAnyFirmMeargedOrAcquired")!=null && ctx.get("IsAnyFirmMeargedOrAcquired").toString().equals("Y") )
			{	
			ruleid="hasFirmMergedBusinessSolePractitionerRenew_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("isAnyFirmMeargedOrAcquired", ctx.get("IsAnyFirmMeargedOrAcquired"));
			 basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
			ctx.put("RuleObject",basicinfo);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
			}
			else
			{
				ruleid="hasFirmMergedBusinessSolePractitionerRenew_Accountant";	
				archiveFlag(ctx,ruleid);
			}	
			
			if(ctx.get("IsControlClientFunds")!=null && ctx.get("IsControlClientFunds").toString().equals("Y") )
			{	
			ruleid="hasFirmEnteredNewEngagementRequiringFirmControlDisburseRenew_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("isControlClientFunds", ctx.get("IsControlClientFunds"));
			basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
			ctx.put("RuleObject",basicinfo);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
			}
			else
			{
				ruleid="hasFirmEnteredNewEngagementRequiringFirmControlDisburseRenew_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			
			if(ctx.get("IsFirmsRevenueMorethan25Percent")!=null && ctx.get("IsFirmsRevenueMorethan25Percent").toString().equals("Y"))
			{	
			int percentOfRevenueFromLargestClient=ctx.get("PercentOfRevenueFromLargestClient")!=null && !ctx.get("PercentOfRevenueFromLargestClient").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PercentOfRevenueFromLargestClient").toString()):0;
			ruleid="percentageRevenueFromLargestRevenueClientRenew_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("percentOfRevenueFromLargestClient", percentOfRevenueFromLargestClient);
			basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
			ctx.put("RuleObject",basicinfo);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
			}
			else
			{
				ruleid="percentageRevenueFromLargestRevenueClientRenew_Accountant";	
				archiveFlag(ctx,ruleid);
			}
		
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evaluateAboutFirm "+e, e);
		}
		
	}
	public static void evalueAOPNewApp(Context ctx)
	{
		String ruleid="";BasicInfoAcc basicinfo=null;
		try
		{
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				int aopKey5=ctx.get("aopPercentage_5")!=null && !ctx.get("aopPercentage_5").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_5").toString()):0;
				if(aopKey5>0)
				{
				ruleid="auditPubliclyheld_Accountant";	
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("a_Percentage_5",aopKey5);
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());	
				}
				else
				{
					ruleid="auditPubliclyheld_Accountant";	
					archiveFlag(ctx,ruleid);
				}
					
				int aopKey12=ctx.get("aopPercentage_12")!=null && !ctx.get("aopPercentage_12").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_12").toString()):0;
				if(aopKey12>0)
				{
					ruleid="otherConsulting_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_12",aopKey12);
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="otherConsulting_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				int aopKey15=ctx.get("aopPercentage_15")!=null && !ctx.get("aopPercentage_15").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_15").toString()):0;
				if(aopKey15>0)
				{
				ruleid="businessValuation_Accountant";	
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("aop_Percentage_15",aopKey15);
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="businessValuation_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				int aopKey21=ctx.get("aopPercentage_21")!=null && !ctx.get("aopPercentage_21").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_21").toString()):0;
				if(aopKey21>0)
				{
					ruleid="SECWorkOtherAudit_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_21",aopKey21);
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="SECWorkOtherAudit_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				int aopKey19=ctx.get("aopPercentage_19")!=null && !ctx.get("aopPercentage_19").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_19").toString()):0;
				if(aopKey19>0)
				{
					ruleid="investmentFinancial_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_19",aopKey19);
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="investmentFinancial_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				int aopKey20=ctx.get("aopPercentage_20")!=null && !ctx.get("aopPercentage_20").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_20").toString()):0;
				if(aopKey20>0)
				{
					ruleid="otherAOP_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_20",aopKey20);
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="otherAOP_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				int aopKey13=ctx.get("aopPercentage_13")!=null && !ctx.get("aopPercentage_13").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_13").toString()):0;
				if(aopKey13>0)
				{
					ruleid="managementConsultingBusinessPlanning_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_13", ctx.get("aopPercentage_13"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="managementConsultingBusinessPlanning_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:22,4Q-AOP*/
				int aopKey16=ctx.get("aopPercentage_16")!=null && !ctx.get("aopPercentage_16").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_16").toString()):0;
				if(aopKey16>0)
				{
					ruleid="forecastsProjections_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_16", ctx.get("aopPercentage_16"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="forecastsProjections_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:23,4R-AOP*/
				int aopKey17=ctx.get("aopPercentage_17")!=null && !ctx.get("aopPercentage_17").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_17").toString()):0;
				if(aopKey17>0)
				{
					ruleid="mergersAcquisitions_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_17", ctx.get("aopPercentage_17"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="mergersAcquisitions_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:24,4R-AOP*/
				int aopKey18=ctx.get("aopPercentage_18")!=null && !ctx.get("aopPercentage_18").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_18").toString()):0;
				if(aopKey18>0)
				{
					ruleid="businessManagement_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_18", ctx.get("aopPercentage_18"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="businessManagement_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:27,4V-AOP*/
				int aopKey23=ctx.get("aopPercentage_23")!=null && !ctx.get("aopPercentage_23").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_23").toString()):0;
				if(aopKey23>0)
				{
					ruleid="erisaPensionPlans_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_23", ctx.get("aopPercentage_23"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="erisaPensionPlans_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:28,4W-AOP*/
				int aopKey24=ctx.get("aopPercentage_24")!=null && !ctx.get("aopPercentage_24").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("aopPercentage_24").toString()):0;
				if(aopKey24>0)
				{
					ruleid="trusteeServices_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_24", ctx.get("aopPercentage_24"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="trusteeServices_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
			if(policyStatusKey==2)
			{
				List previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
				ctx.putAll((Map) previousPolicyKey.get(0));
				Context oldPolicyContext=new Context();
				oldPolicyContext.setProject(ctx.getProject());
				oldPolicyContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
				oldPolicyContext.put("VersionSequence",ctx.get("previousVersionSequence"));
				
				if(ctx.get("aopPercentage_20")!=null && !ctx.get("aopPercentage_20").toString().isEmpty() && Integer.valueOf(ctx.get("aopPercentage_20").toString())>0 )
				{	
				ruleid="otherAOPRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("aop_Percentage_20", ctx.get("aopPercentage_20"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="otherAOPRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}	
				if(ctx.get("aopPercentage_5")!=null && !ctx.get("aopPercentage_5").toString().isEmpty() && Integer.valueOf(ctx.get("aopPercentage_5").toString())>0)
				{
					ruleid="auditPubliclyheldRenew_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("a_Percentage_5", ctx.get("aopPercentage_5"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="auditPubliclyheldRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:18,Q:No-3T(Renew)*/
				if(ctx.get("aopPercentage_21")!=null && !ctx.get("aopPercentage_21").toString().isEmpty() && Integer.valueOf(ctx.get("aopPercentage_21").toString())>0)
				{
					ruleid="SECWorkOtherAuditRenew_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_21", ctx.get("aopPercentage_21"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="SECWorkOtherAuditRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:26,4-Aop*/
				if(ctx.get("aopPercentage_19")!=null && !ctx.get("aopPercentage_19").toString().isEmpty() && Integer.valueOf(ctx.get("aopPercentage_19").toString())>0)
				{
					ruleid="investmentFinancialRenew_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("aop_Percentage_19", ctx.get("aopPercentage_19"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="investmentFinancialRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				Context oldAopData=new Context();
				oldAopData.setProject(ctx.getProject());
				List aopListOld= (List) SqlResources.getSqlMapProcessor(oldPolicyContext).select("SqlStmts.UserStatementdroolQueriesgetAreaOfPractice",oldPolicyContext);
				if(aopListOld!=null)
				{
					for (int i = 0; i < aopListOld.size(); i++) 
					{
						Map dataMap = (Map) aopListOld.get(i);
						
						if(dataMap.get("AOPKey").toString().equals("12"))
						{
							int diffAopOthrConsultant,OthrConsultantNew=0;
							if(ctx.get("aopPercentage_12")!=null && !ctx.get("aopPercentage_12").toString().isEmpty())
								OthrConsultantNew=Integer.valueOf(ctx.get("aopPercentage_12").toString());
							else
								OthrConsultantNew=0;
	
							diffAopOthrConsultant=OthrConsultantNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopOthrConsultant",diffAopOthrConsultant);
							/*rule no:11 basic info Other Consulting aop(Renew) */
							
							if(diffAopOthrConsultant!=0)
							{
								ruleid="otherConsultingRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
							}
							else
							{
								ruleid="otherConsultingRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
						}
						/*rule no:12 basic info Management Consulting / Business Planning aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("13"))
						{
							int diffAopManagement,managementConsultingNew=0;
							if(ctx.get("aopPercentage_13")!=null && !ctx.get("aopPercentage_13").toString().isEmpty())
								 managementConsultingNew=Integer.valueOf(ctx.get("aopPercentage_13").toString());
							else
								managementConsultingNew=0;
							diffAopManagement=managementConsultingNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopManagement",diffAopManagement);
							if(diffAopManagement!=0)
							{
								ruleid="managementConsultingBusinessPlanningRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
							}
							else
							{
								ruleid="managementConsultingBusinessPlanningRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
						}
						/*rule no:14 basic info Business Valuation aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("15"))
						{
							int diffAopBusinessValuation,businessValuationNew=0;
							boolean flagForBusinessValuation=false;
							if(ctx.get("aopPercentage_15")!=null && !ctx.get("aopPercentage_15").toString().isEmpty())
								businessValuationNew=Integer.valueOf(ctx.get("aopPercentage_15").toString());
							else
								businessValuationNew=0;
							diffAopBusinessValuation=businessValuationNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopBusinessValuation",diffAopBusinessValuation);
							ctx.put("businessValuationNew",businessValuationNew);
							if((businessValuationNew>25 && diffAopBusinessValuation!=0 )||diffAopBusinessValuation>0)
							{
								flagForBusinessValuation=true;
							}
							ctx.put("flagForBusinessValuation",flagForBusinessValuation);
							if(flagForBusinessValuation)
							{
								ruleid="businessValuationRenew_Accountant";	
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
							}
							else
							{
								ruleid="businessValuationRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
						}
						/*rule no:15 basic info Forecasts / Projections  aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("16"))
						{
							int diffAopForecastsProjections,forecastsProjectionsNew=0;
							if(ctx.get("aopPercentage_16")!=null && !ctx.get("aopPercentage_16").toString().isEmpty())
							{
								forecastsProjectionsNew=Integer.valueOf(ctx.get("aopPercentage_16").toString());
							}
							else
							{
								forecastsProjectionsNew=0;
							}
							
							diffAopForecastsProjections=forecastsProjectionsNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopForecastsProjections",diffAopForecastsProjections);
							ctx.put("forecastsProjectionsNew",forecastsProjectionsNew);
							
							
							if(((Integer.valueOf(dataMap.get("PercentageValue").toString())==0) && forecastsProjectionsNew>0)
									|| diffAopForecastsProjections>5)
							{
								ruleid="forecastsProjectionsRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								ctx.put("percentageValue", ctx.get("PercentageValue"));
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
								
							}
							else
							{
								
								ruleid="forecastsProjectionsRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
							
							
						}
						/*rule no:16 basic info Mergers and Acquisitions  aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("17"))
						{
							int diffAopMergersAcquisitions,mergersAcquisitionsNew=0;
							{ if (logger.isDebugEnabled()) logger.debug("percentage value..:"+dataMap.get("PercentageValue")+"Ctx"+ctx.get("aopPercentage_17")+"cc.."); }
							
							if(ctx.get("aopPercentage_17")!=null && !ctx.get("aopPercentage_17").toString().isEmpty())
							{
								mergersAcquisitionsNew=Integer.valueOf(ctx.get("aopPercentage_17").toString());
							}
							else
							{
								mergersAcquisitionsNew=0;
							}
	/*						diffAopBusinessValuation=Integer.valueOf(ctx.get("aopPercentage_15").toString())-Integer.valueOf(dataMap.get("PercentageValue").toString());
	*/						
							diffAopMergersAcquisitions=mergersAcquisitionsNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopMergersAcquisitions",diffAopMergersAcquisitions);
							ctx.put("mergersAcquisitionsNew",mergersAcquisitionsNew);
							if(((Integer.valueOf(dataMap.get("PercentageValue").toString())==0) && mergersAcquisitionsNew>0)
									|| diffAopMergersAcquisitions>10)
							{
								ruleid="mergersAcquisitionsRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								ctx.put("percentageValue", ctx.get("PercentageValue"));
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
								
							}
							else
							{
								ruleid="mergersAcquisitionsRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
						}
						/*rule no:17 basic info Business Management  aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("18"))
						{
							int diffAopBusinessManagement,businessManagementNew=0;
							{ if (logger.isDebugEnabled()) logger.debug("percentage value..:"+dataMap.get("PercentageValue")+"Ctx"+ctx.get("aopPercentage_18")+"cc.."); }
							
							if(ctx.get("aopPercentage_18")!=null && !ctx.get("aopPercentage_18").toString().isEmpty())
								businessManagementNew=Integer.valueOf(ctx.get("aopPercentage_18").toString());
							else
								businessManagementNew=0;
	
							diffAopBusinessManagement=businessManagementNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopBusinessManagement",diffAopBusinessManagement);
							ctx.put("businessManagementNew",businessManagementNew);
							
							
							if(((Integer.valueOf(dataMap.get("PercentageValue").toString())==0) && businessManagementNew>0)
									|| diffAopBusinessManagement>10)
							{
								ruleid="businessManagementRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								ctx.put("percentageValue", ctx.get("PercentageValue"));
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
								
							}
							else
							{
								ruleid="businessManagementRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
						}
						/*rule no:20 basic info Erisa / Pension Plans  aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("23"))
						{
							int diffAopErisaPension,erisaPensionNew=0;
							{ if (logger.isDebugEnabled()) logger.debug("percentage value..:"+dataMap.get("PercentageValue")+"Ctx"+ctx.get("aopPercentage_23")+"cc.."); }
							
							if(ctx.get("aopPercentage_23")!=null && !ctx.get("aopPercentage_23").toString().isEmpty())
							{
								erisaPensionNew=Integer.valueOf(ctx.get("aopPercentage_23").toString());
							}
							else
							{
								erisaPensionNew=0;
							}
			
							diffAopErisaPension=erisaPensionNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							if(((Integer.valueOf(dataMap.get("PercentageValue").toString())==0) && erisaPensionNew>0)
									|| diffAopErisaPension>10)
							{
								ruleid="erisaPensionPlansRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								ctx.put("percentageValue", ctx.get("PercentageValue"));
								ctx.put("diffAopErisaPension",diffAopErisaPension);
								ctx.put("erisaPensionNew",erisaPensionNew);
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
								
							}
							else
							{
								
								ruleid="erisaPensionPlansRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
						}
						/*rule no:21 basic info Trustee Services Plans  aop(Renew)*/
						if(dataMap.get("AOPKey").toString().equals("24"))
						{
							int diffAopTrusteeServices,trusteeServicesNew=0;
							{ if (logger.isDebugEnabled()) logger.debug("percentage value..:"+dataMap.get("PercentageValue")+"Ctx"+ctx.get("aopPercentage_24")+"cc.."); }
							
							if(ctx.get("aopPercentage_24")!=null && !ctx.get("aopPercentage_24").toString().isEmpty())
							{
								trusteeServicesNew=Integer.valueOf(ctx.get("aopPercentage_24").toString());
							}
							else
							{
								trusteeServicesNew=0;
							}
			
							diffAopTrusteeServices=trusteeServicesNew-Integer.valueOf(dataMap.get("PercentageValue").toString());
							ctx.put("diffAopTrusteeServices",diffAopTrusteeServices);
							ctx.put("trusteeServicesNew",trusteeServicesNew);
							
							
							if(((Integer.valueOf(dataMap.get("PercentageValue").toString())==0) && trusteeServicesNew>0)
									|| diffAopTrusteeServices>10)
							{
								ruleid="trusteeServicesRenew_Accountant";	
								{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
								ctx.put("percentageValue", ctx.get("PercentageValue"));
								basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
								ctx.put("RuleObject",basicinfo);
								insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
								
							}
							else
							{
								
								ruleid="trusteeServicesRenew_Accountant";	
								archiveFlag(ctx,ruleid);
							}
							
							
						}
				
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evalueAOPNewApp "+e, e);
		}
	}
	
	
	public static void evaluatePracticeManagementNewApp(Context ctx)
	{
		
		logger.debug("going to execute evaluatePracticeManagementNewApp");
		String ruleid="";GeneralAcc general=null;BasicInfoAcc basicinfo=null;
		try
		{
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetTransactionDataDetails", ctx);
			if(obj != null && obj instanceof Map)
			{
				ctx.putAll((Map)obj);
			}
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				if(ctx.get("IsFirmRenderingServices")!=null && ctx.get("IsFirmRenderingServices").toString().equals("Y") )
				{	
					
					ruleid="officersConductBusinessUnderSeparateEntity_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isFirmRenderingServices", ctx.get("IsFirmRenderingServices"));
					general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
					ctx.put("RuleObject",general);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="officersConductBusinessUnderSeparateEntity_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsFirmsRevenueMorethan25Percent")!=null && ctx.get("IsFirmsRevenueMorethan25Percent").toString().equals("Y") && Integer.valueOf(ctx.get("PercentOfRevenueFromLargestClient").toString())>39)
				{
					ruleid="lastYearFirmRevenue_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug "+ ruleid); }
					ctx.put("percentOfRevenueFromLargestClient",ctx.get("PercentOfRevenueFromLargestClient"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="lastYearFirmRevenue_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsAnyFirmMeargedOrAcquired")!=null && ctx.get("IsAnyFirmMeargedOrAcquired").toString().equals("Y") )
				{	
					ruleid="anySolePractitionerAccountingFirm_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAnyFirmMeargedOrAcquired", ctx.get("IsAnyFirmMeargedOrAcquired"));
					general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
					ctx.put("RuleObject",general);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="anySolePractitionerAccountingFirm_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsControlClientFunds")!=null && ctx.get("IsControlClientFunds").toString().equals("Y") )
				{	
					
					ruleid="controlOrDisburseClientFunds_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isControlClientFunds", ctx.get("IsControlClientFunds"));
					general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
					ctx.put("RuleObject",general);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="controlOrDisburseClientFunds_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsReferralsInvestmentVentures")!=null && ctx.get("IsReferralsInvestmentVentures").toString().equals("Y") )
				{	
					
				ruleid="OrganizePromoteReferInvestInvestmentVentures_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isInvestmentVenture", ctx.get("IsReferralsInvestmentVentures"));
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="OrganizePromoteReferInvestInvestmentVentures_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
			if(policyStatusKey==2)
			{
				if(ctx.get("IsServedAsTrustee")!=null && ctx.get("IsServedAsTrustee").toString().equals("Y") )
				{	
				ruleid="hasFirmEnteredEngagementServingTrusteeCoTrusteeRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isServedAsTrustee", ctx.get("IsServedAsTrustee"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="hasFirmEnteredEngagementServingTrusteeCoTrusteeRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}	
				if(ctx.get("IsFirmRenderedServiceForAnyClient")!=null && ctx.get("IsFirmRenderedServiceForAnyClient").toString().equals("Y") )
				{	
				ruleid="hasFirmEnteredEngagementServingOfficerDirectorRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isFirmRenderedServiceForAnyClient", ctx.get("IsFirmRenderedServiceForAnyClient"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="hasFirmEnteredEngagementServingOfficerDirectorRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsAnyRegulatoryInquiry")!=null && ctx.get("IsAnyRegulatoryInquiry").toString().equals("Y") )
				{	
				ruleid="regulatoryInvestigationInquirySuspendedPracticeChargedRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isAnyRegulatoryInquiry", ctx.get("IsAnyRegulatoryInquiry"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="regulatoryInvestigationInquirySuspendedPracticeChargedRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsSuitInstituted")!=null && ctx.get("IsSuitInstituted").toString().equalsIgnoreCase("Y"))
				{
				List firmInitLawsuits=SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetFirmInitLawsuits",ctx);
				{ if (logger.isDebugEnabled()) logger.debug("in suits"+firmInitLawsuits); }
				int count=0;
				int totalAmountfee=0;
				long daysDifferenceForFeeSuits=0;
				boolean flagFeeSuitMonth=true;
				//String totalAmountfee="";
				if(firmInitLawsuits!=null)
				{
					
					for (int i = 0; i < firmInitLawsuits.size(); i++) 
					{
						Map dataMap = (Map) firmInitLawsuits.get(i);
						String str=dataMap.get("SuitFilesDateFees").toString();
						String effectiveDatePolicy=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString()));
						String amountFee=dataMap.get("AmountOfFeesSued").toString();
						int amountFees=Integer.parseInt(amountFee);
						
						ctx.put("effectiveDateOfPolicy", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
						ctx.put("SuitFilesDateFeesFormat", new SimpleDateFormat("MM/dd/yyyy").format(new Date(dataMap.get("SuitFilesDateFees").toString())));
				
						int  feeSuedDate=Integer.valueOf(str.substring(str.length()-4,str.length()));
								
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
						int currentYear=Integer.valueOf(sdf.format(new Date(new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())))).toString());

						//int currentYear=Integer.valueOf(sdf.format(new Date()).toString());
						{ if (logger.isDebugEnabled()) logger.debug(currentYear+" "+(currentYear-1)+" "+(currentYear-2)+" "+(currentYear-3)); }
						{ if (logger.isDebugEnabled()) logger.debug(feeSuedDate); }

						daysDifferenceForFeeSuits=DateUtils.calculateDiffInDays(new Date(ctx.get("SuitFilesDateFeesFormat").toString()),new Date(ctx.get("effectiveDateOfPolicy").toString()));
						if(feeSuedDate==(currentYear-3))
						{
							int monthFeeSuit =Integer.valueOf(str.substring(0, Math.min(str.length(),2)));
							int monthEffectiveDatePolicy=Integer.valueOf(effectiveDatePolicy.substring(0, Math.min(effectiveDatePolicy.length(),2)));
							if(monthFeeSuit<monthEffectiveDatePolicy)
							{
								flagFeeSuitMonth=false;
							}
							
						}

						if((feeSuedDate==(currentYear-1)||feeSuedDate==(currentYear-2)||feeSuedDate==(currentYear-3)||feeSuedDate==(currentYear))&& daysDifferenceForFeeSuits<0 && flagFeeSuitMonth )
						{
							count++;
							totalAmountfee=totalAmountfee+amountFees;
							
						}
					}
					
				}

				ctx.put("totalAmountfeeCal",totalAmountfee);
				ruleid="checkAmountOfFeesSuedRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				
				ctx.put("coutOfFeeSuit",count);
				ruleid="checkCountOfFeeSuitInPastYears_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				
				}
				else
				{
					ruleid="checkAmountOfFeesSuedRenew_Accountant";	
					archiveFlag(ctx,ruleid);
					ruleid="checkCountOfFeeSuitInPastYears_Accountant";	
					archiveFlag(ctx,ruleid);
				
					
				}
				
			}
			
			
		}
		catch(Exception e)
		{
			logger.error("exception occured while executing evaluatePracticeManagementNewApp :"+e, e);
		}
	}
	
	public static void evaluateMorePracticeManagement(Context ctx)
	{
		logger.debug("going to execute evaluateMorePracticeManagement");
		String ruleid="";GeneralAcc general=null;
		try
		{
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetTransactionDataDetails", ctx);
			if(obj != null && obj instanceof Map)
			{
				ctx.putAll((Map)obj);
			}
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				if(ctx.get("IsSyndicationManagers")!=null && ctx.get("IsSyndicationManagers").toString().equals("Y") )
				{	
					
				ruleid="investmentFundsHedgeFundsMutualFunds_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isSyndicationManagers", ctx.get("IsSyndicationManagers"));
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="investmentFundsHedgeFundsMutualFunds_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsNonPublicInvestmentDone")!=null && ctx.get("IsNonPublicInvestmentDone").toString().equals("Y") )
				{	
					
				ruleid="hasFirmPersonnelPredecessorsInvestedNonpublicInvestment_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isNonPublicInvestmentDone", ctx.get("IsNonPublicInvestmentDone"));
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="hasFirmPersonnelPredecessorsInvestedNonpublicInvestment_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsSuitInstituted")!=null && ctx.get("IsSuitInstituted").toString().equalsIgnoreCase("Y"))
				{
				List firmInitLawsuits=SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetFirmInitLawsuits",ctx);
				
				int count=0,totalAmountfee=0;
				long daysDifferenceForFeeSuits=0;
				boolean flagFeeSuitMonth=true;
				if(firmInitLawsuits!=null)
				{
					
					for (int i = 0; i < firmInitLawsuits.size(); i++) 
					{
						
						Map dataMap = (Map) firmInitLawsuits.get(i);
						String str=dataMap.get("SuitFilesDateFees").toString();
						String effectiveDatePolicy=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString()));
						String amountFee=dataMap.get("AmountOfFeesSued").toString();
						int amountFees=Integer.parseInt(amountFee);
						
						ctx.put("effectiveDateOfPolicy", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
						ctx.put("SuitFilesDateFeesFormat", new SimpleDateFormat("MM/dd/yyyy").format(new Date(dataMap.get("SuitFilesDateFees").toString())));
				
						int  feeSuedDate=Integer.valueOf(str.substring(str.length()-4,str.length()));
								
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
						int currentYear=Integer.valueOf(sdf.format(new Date(new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())))).toString());
						{ if (logger.isDebugEnabled()) logger.debug(currentYear+" "+(currentYear-1)+" "+(currentYear-2)+" "+(currentYear-3)); }
						{ if (logger.isDebugEnabled()) logger.debug(feeSuedDate); }

						daysDifferenceForFeeSuits=DateUtils.calculateDiffInDays(new Date(ctx.get("SuitFilesDateFeesFormat").toString()),new Date(ctx.get("effectiveDateOfPolicy").toString()));
						if(feeSuedDate==(currentYear-3))
						{
							int monthFeeSuit =Integer.valueOf(str.substring(0, Math.min(str.length(),2)));
							int monthEffectiveDatePolicy=Integer.valueOf(effectiveDatePolicy.substring(0, Math.min(effectiveDatePolicy.length(),2)));
							{ if (logger.isDebugEnabled()) logger.debug("Fee Siut Month..."+monthFeeSuit+" Effective date Month.."+monthEffectiveDatePolicy); }
							if(monthFeeSuit<monthEffectiveDatePolicy)
							{
								flagFeeSuitMonth=false;
							}
							
						}
						if((feeSuedDate==(currentYear-1)||feeSuedDate==(currentYear-2)||feeSuedDate==(currentYear-3)||feeSuedDate==(currentYear))&& daysDifferenceForFeeSuits<0 && flagFeeSuitMonth )
						{
							count++;
							totalAmountfee=totalAmountfee+amountFees;
							
						}
					}
					
				}
				
				ctx.put("coutOfFeeSuit",count);
				ruleid="checkCountOfFeeSuitInPastYears_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				
				ctx.put("totalAmountfeeCal",totalAmountfee);
				ruleid="checkAmountOfFeesSued_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
						
				}
				else
				{
					ruleid="checkCountOfFeeSuitInPastYears_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsPolicyExcludesCoverage")!=null && ctx.get("IsPolicyExcludesCoverage").toString().equals("Y") )
				{	
					
				ruleid="policyExcludeCoveragePredecessorFirmAffiliatesClients_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isPolicyExcludesCoverage", ctx.get("IsPolicyExcludesCoverage"));
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="policyExcludeCoveragePredecessorFirmAffiliatesClients_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
			}
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evaluateMorePracticeManagement "+e, e);
			
		}
		
	}
	public static void evaluateTrustsAndOfficers(Context ctx)
	{
		logger.debug("going to execute evaluateTrustsAndOfficers");
		String ruleid="";GeneralAcc general=null;
		try
		{
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				if(ctx.get("IsServedAsTrustee")!=null && ctx.get("IsServedAsTrustee").toString().equals("Y") )
				{	
					
				ruleid="servingTrusteeCotrusteeExecutor_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isServedAsTrustee", ctx.get("IsServedAsTrustee"));
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="servingTrusteeCotrusteeExecutor_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsFirmRenderedServiceForAnyClient")!=null && ctx.get("IsFirmRenderedServiceForAnyClient").toString().equals("Y") )
				{	
					
				ruleid="servingOfficerDirectorPartner_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isFirmRenderedServiceForAnyClient", ctx.get("IsFirmRenderedServiceForAnyClient"));
				general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="servingOfficerDirectorPartner_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				
			}
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evaluateTrustsAndOfficers "+e, e);
			
		}
		
	}
	
	public static void evaluatePastClaims(Context ctx)
	{
		logger.debug("going to execute evaluatePastClaims");
		String ruleid="";BasicInfoAcc basicinfo=null;
		try
		{
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				
				if(ctx.get("IsAnyRegulatoryInquiry")!=null && ctx.get("IsAnyRegulatoryInquiry").toString().equals("Y") )
				{	
					ruleid="IsAnyRegulatoryInquiry_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAnyRegulatoryInquiry", ctx.get("IsAnyRegulatoryInquiry"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				
				else
				{
					ruleid="IsAnyRegulatoryInquiry_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsAwarenessOfAnyProfLiability")!=null && ctx.get("IsAwarenessOfAnyProfLiability").toString().equals("Y") )
				{	
					ruleid="IsAwarenessOfAnyProfLiability_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAwarenessOfAnyProfLiability", ctx.get("IsAwarenessOfAnyProfLiability"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="IsAwarenessOfAnyProfLiability_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst")!=null && ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString().equals("Y") )
				{	
					ruleid="IsAwarenessOfAnyProfLiabilitySuitAgainst_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAwarenessOfAnyProfLiabilitySuitAgainst", ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="IsAwarenessOfAnyProfLiabilitySuitAgainst_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsFirmDeclinedOtherthanNonPayment")!=null && ctx.get("IsFirmDeclinedOtherthanNonPayment").toString().equals("Y") && !ctx.get("StateCode").toString().equals("MO") )
				{	
					ruleid="FirmDeclinedCancelledNnonrenewedProfessionalLiability_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isFirmDeclinedOtherthanNonPayment", ctx.get("IsFirmDeclinedOtherthanNonPayment"));
					basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
					ctx.put("RuleObject",basicinfo);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="FirmDeclinedCancelledNnonrenewedProfessionalLiability_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
			if(policyStatusKey==2)
			{
				/*rule no:-29,Q.no:-10a(renew basicinfo)*/
				if(ctx.get("IsAnyRegulatoryInquiry")!=null && ctx.get("IsAnyRegulatoryInquiry").toString().equals("Y") )
				{	
				ruleid="regulatoryInvestigationInquirySuspendedPracticeChargedRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isAnyRegulatoryInquiry", ctx.get("IsAnyRegulatoryInquiry"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="regulatoryInvestigationInquirySuspendedPracticeChargedRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-30,Q.no:-10b(renew basicinfo)*/
				if(ctx.get("IsAwarenessOfAnyProfLiability")!=null && ctx.get("IsAwarenessOfAnyProfLiability").toString().equals("Y") )
				{	
				ruleid="anyAccountantsProfessionalLiabilityClaimsMadeAgainstFirmRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isAwarenessOfAnyProfLiability", ctx.get("IsAwarenessOfAnyProfLiability"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="anyAccountantsProfessionalLiabilityClaimsMadeAgainstFirmRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-31,Q.no:-10c(renew basicinfo)*/
				if(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst")!=null && ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString().equals("Y") )
				{	
				ruleid="anyActOmissionFeeDisputeBasisOfClaimAgainstFirmRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isAwarenessOfAnyProfLiabilitySuitAgainst", ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst"));
				basicinfo=(BasicInfoAcc)initlizePOJO(ctx, new BasicInfoAcc());
				ctx.put("RuleObject",basicinfo);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),basicinfo,ruleid,basicinfo.getDescription(),basicinfo.getTooltip());
				}
				else
				{
					ruleid="anyActOmissionFeeDisputeBasisOfClaimAgainstFirmRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
			}
		}
		catch(Exception e)
		{
			logger.error("Error ocured while executing evaluatePastClaims method :"+e, e);
		}
	}
	public static void evaluatePracticeSupplementNewApp(Context ctx)
	{
		logger.debug("going to execute evaluatePracticeSupplementNewApp");
		String ruleid="";
		try
		{
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
			{
				if(ctx.get("IsNonMonetaryCompensationRecieved")!=null && ctx.get("IsNonMonetaryCompensationRecieved").toString().equals("Y") )
				{	
				ruleid="hasFirmReceivedNonMonetaryCompensation_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isNonMonetaryCompensationRecieved", ctx.get("IsNonMonetaryCompensationRecieved"));
				PracticreManagementAcc practice=(PracticreManagementAcc)initlizePOJO(ctx, new PracticreManagementAcc());
				ctx.put("RuleObject",practice);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),practice,ruleid,practice.getDescription(),practice.getTooltip());
				}
				else
				{
					ruleid="hasFirmReceivedNonMonetaryCompensation_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evaluatePracticeSupplementNewApp", e);
		}
	}
	public static void evaluateInvestmentSupplementNewApp(Context ctx)
	{
		logger.debug("going to execute evaluateInvestmentSupplementNewApp");
		String ruleid="";
		InvestmentAcc investment=null;
		try
		{
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetTransactionDataDetails", ctx);
			if(obj != null && obj instanceof Map)
			{
				ctx.putAll((Map)obj);
			}
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(ctx.get("IsAnyReferralFeesInvestments")!=null && !"".equals(ctx.get("IsAnyReferralFeesInvestments").toString()))
			{	
				
				ruleid="haveRecieveCommissionReferralsInvestment_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isSecuritiesSales", ctx.get("IsAnyReferralFeesInvestments"));
				investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
				ctx.put("RuleObject",investment);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
			}
			else
			{
				ruleid="haveRecieveCommissionReferralsInvestment_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			if(ctx.get("IsAnyFirmAffiliateLicensed")!=null && ctx.get("IsAnyFirmAffiliateLicensed").toString().equals("Y") )
			{	
			ruleid="firmLicensedLifeHealthAccidentDisabilityInsuranceAgentBrokerRenew_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("isAnyFirmAffiliateLicensed", ctx.get("IsAnyFirmAffiliateLicensed"));
			investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
			ctx.put("RuleObject",investment);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
			}
			else
			{
				ruleid="firmLicensedLifeHealthAccidentDisabilityInsuranceAgentBrokerRenew_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			if(policyStatusKey==1)
			{
				if(ctx.get("IsSecuritiesSales")!=null && ctx.get("IsSecuritiesSales").toString().equals("Y") )
				{	
					
					ruleid="financialPlanningServicesSecuritiesSales_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isSecuritiesSales", ctx.get("IsSecuritiesSales"));
					investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
					ctx.put("RuleObject",investment);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="financialPlanningServicesSecuritiesSales_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsContractualRelationShip")!=null && ctx.get("IsContractualRelationShip").toString().equals("Y") )
				{	
					
					ruleid="doesFirmHaveContractualRelationship_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isContractualRelationShip", ctx.get("IsContractualRelationShip"));
					investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
					ctx.put("RuleObject",investment);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="doesFirmHaveContractualRelationship_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsAnyNonPublicInvestments")!=null && ctx.get("IsAnyNonPublicInvestments").toString().equals("Y") )
				{	
					
					ruleid="haveAnyPersonnelRecommendedAnyNonPublicInvestments_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAnyNonPublicInvestments", ctx.get("IsAnyNonPublicInvestments"));
					investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
					ctx.put("RuleObject",investment);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="haveAnyPersonnelRecommendedAnyNonPublicInvestments_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsAnyNonPublicInvestments")!=null && ctx.get("IsAnyNonPublicInvestments").toString().equals("Y") )
				{	
					
					ruleid="anyPersonLicensedLifeHealthAccidentDisability_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAnyNonPublicInvestments", ctx.get("IsAnyNonPublicInvestments"));
					investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
					ctx.put("RuleObject",investment);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="anyPersonLicensedLifeHealthAccidentDisability_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsAnyEmployeeBenefitPlan")!=null && ctx.get("IsAnyEmployeeBenefitPlan").toString().equals("Y") )
				{	
					
					ruleid="FirmPersonnelDiscretionaryAuthorityToInvest_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isAnyEmployeeBenefitPlan", ctx.get("IsAnyEmployeeBenefitPlan"));
					investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
					ctx.put("RuleObject",investment);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="FirmPersonnelDiscretionaryAuthorityToInvest_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsDiscretionaryAssetMangt")!=null && ctx.get("IsDiscretionaryAssetMangt").toString().equals("Y") )
				{	
					
					ruleid="discretionaryInvestmentAuthority_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isDiscretionaryAssetMangt", ctx.get("IsDiscretionaryAssetMangt"));
					
					 investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
					ctx.put("RuleObject",investment);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="discretionaryInvestmentAuthority_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
			if(policyStatusKey==2)
			{

				/*rule no:-35,Q.no:-1b(renew INVESTMENT / FINANCIAL PLANNING SUPPLEMENT)*/
				if(ctx.get("IsDiscretionaryAssetMangt")!=null && ctx.get("IsDiscretionaryAssetMangt").toString().equals("Y") )
				{	
				ruleid="discretionaryAssetManagementServicesRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isDiscretionaryAssetMangt", ctx.get("IsDiscretionaryAssetMangt"));
				investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
				ctx.put("RuleObject",investment);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="discretionaryAssetManagementServicesRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-36,Q.no:-1d(renew INVESTMENT / FINANCIAL PLANNING SUPPLEMENT)*/
				if(ctx.get("IsSecuritiesSales")!=null && ctx.get("IsSecuritiesSales").toString().equals("Y") )
				{	
				ruleid="securitiesSalesRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isSecuritiesSales", ctx.get("IsSecuritiesSales"));
				investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
				ctx.put("RuleObject",investment);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="securitiesSalesRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-37,Q.no:-2(renew INVESTMENT / FINANCIAL PLANNING SUPPLEMENT)*/
				if(ctx.get("IsContractualRelationShip")!=null && ctx.get("IsContractualRelationShip").toString().equals("Y") )
				{	
				ruleid="doesFirmContractualRelationshipWithSecuritiesbrokerDealerRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isContractualRelationShip", ctx.get("IsContractualRelationShip"));
				investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
				ctx.put("RuleObject",investment);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="doesFirmContractualRelationshipWithSecuritiesbrokerDealerRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-38,Q.no:-3(renew INVESTMENT / FINANCIAL PLANNING SUPPLEMENT)*/
				if(ctx.get("IsAnyNonPublicInvestments")!=null && ctx.get("IsAnyNonPublicInvestments").toString().equals("Y") )
				{	
				ruleid="haveAnyPersonnelRecommendedNonPublicInvestmentsToClientsRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isAnyNonPublicInvestments", ctx.get("IsAnyNonPublicInvestments"));
				investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
				ctx.put("RuleObject",investment);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="haveAnyPersonnelRecommendedNonPublicInvestmentsToClientsRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-39,Q.no:-4(renew INVESTMENT / FINANCIAL PLANNING SUPPLEMENT)*/
				
				/*rule no:-40,Q.no:-5(renew INVESTMENT / FINANCIAL PLANNING SUPPLEMENT)*/
				if(ctx.get("IsAnyEmployeeBenefitPlan")!=null && ctx.get("IsAnyEmployeeBenefitPlan").toString().equals("Y") )
				{	
				ruleid="doesFirmPersonnelDiscretionaryAuthorityInvestForEmployeeBenefitPlanRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isAnyEmployeeBenefitPlan", ctx.get("IsAnyEmployeeBenefitPlan"));
				investment=(InvestmentAcc)initlizePOJO(ctx, new InvestmentAcc());
				ctx.put("RuleObject",investment);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),investment,ruleid,investment.getDescription(),investment.getTooltip());
				}
				else
				{
					ruleid="doesFirmPersonnelDiscretionaryAuthorityInvestForEmployeeBenefitPlanRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			
				
				
			}
		}
		catch(Exception e)
		{
			logger.error("error occured while executing evaluateInvestmentSupplementNewApp", e);
		}
	}
	public static void evaluateAuditSupplement(Context ctx) throws Exception
	{
		logger.debug("going to execute evaluateInvestmentSupplementNewApp");
		String ruleid="";
		AuditAcc audit=null;
		try
		{
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetTransactionDataDetails", ctx);
		if(obj != null && obj instanceof Map)
		{
			ctx.putAll((Map)obj);
		}
		
		int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
		if(policyStatusKey==1)
			{
			ctx.put("currentDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			if(ctx.get("IsFirmHaveAWrittenPolicyAtt")!=null && ctx.get("IsFirmHaveAWrittenPolicyAtt").toString().equals("N") )
			{	
				
				ruleid="auditRelatedCPETraining_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isFirmHaveAWrittenPolicyAtt", ctx.get("IsFirmHaveAWrittenPolicyAtt"));
				audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",audit);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="auditRelatedCPETraining_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			if(ctx.get("IsPublicTradedAudited")!=null && !"".equals(ctx.get("IsPublicTradedAudited").toString()) )
			{	
				
				ruleid="isPublicTradedAuditedReferral_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isFirmHaveAWrittenPolicyAtt", ctx.get("IsPublicTradedAudited"));
				audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",audit);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="auditRelatedCPETraining_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			
			if(ctx.get("IsAualityReviewAtt")!=null && ctx.get("IsAualityReviewAtt").toString().equals("N") )
			{	
				
			ruleid="hasFirmUndergonePeerQualityReviewRenew_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("isAualityReviewAtt", ctx.get("IsAualityReviewAtt"));
			AuditAcc auditrenew=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
			ctx.put("RuleObject",auditrenew);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),auditrenew,ruleid,auditrenew.getDescription(),auditrenew.getTooltip());
			}
			else
			{
				ruleid="hasFirmUndergonePeerQualityReviewRenew_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			/*rule no:-87,Q.no:-6(Audit)*/
			/*if(ctx.get("IsAualityReviewAtt")!=null && ctx.get("IsAualityReviewAtt").toString().equals("N") )
			{	
				ruleid="hasFirmUndergonePeerQualityReviewNew_Accountant";
				logger.debug("going to debug : "+ ruleid);
				audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",audit);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="hasFirmUndergonePeerQualityReviewNew_Accountant";	
				archiveFlag(ctx,ruleid);
			}*/
			
			/*rule no:-88,Q.no:-7(Audit)*/
			if(ctx.get("IsLastPeerReviewAtt")!=null && ctx.get("IsLastPeerReviewAtt").toString().equals("Y") )
			{	
				ruleid="hasFirmlastpeerreviewmodifiedqualified_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isLastPeerReviewAtt", ctx.get("IsLastPeerReviewAtt"));
				audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",audit);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="hasFirmlastpeerreviewmodifiedqualified_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			/*rule no:-82,Q-1*/
			if((ctx.get("AuditClients_1")!=null && !ctx.get("AuditClients_1").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_1").toString())>0)
				||(ctx.get("AuditClients_2")!=null && !ctx.get("AuditClients_2").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_2").toString())>0)
				||(ctx.get("AuditClients_8")!=null && !ctx.get("AuditClients_8").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_8").toString())>0)
				||(ctx.get("AuditClients_9")!=null && !ctx.get("AuditClients_9").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_9").toString())>0))
				{
				String AuditClients_1n = ctx.get("AuditClients_1").toString();
				if(AuditClients_1n.equals("")){
					AuditClients_1n="0";
				}
				ctx.put("AuditClients_1n",AuditClients_1n);
				String AuditClients_2n = ctx.get("AuditClients_2").toString();
				if(AuditClients_2n.equals("")){
					AuditClients_2n="0";
				}
				ctx.put("AuditClients_2n",AuditClients_2n);
				String AuditClients_8n = ctx.get("AuditClients_8").toString();
				if(AuditClients_8n.equals("")){
					AuditClients_8n="0";
				}
				ctx.put("AuditClients_8n",AuditClients_8n);
				String AuditClients_9n = ctx.get("AuditClients_9").toString();
				if(AuditClients_9n.equals("")){
					AuditClients_9n="0";
				}
				ctx.put("AuditClients_9n",AuditClients_9n);
				/*ctx.put("containsBankBrokerInsurance", "true");*/
				ruleid="banksBrokersInsuranceCompaniesInvestmentCompanies_Accountant";	
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("auditClients_1n", ctx.get("AuditClients_1n"));
				ctx.put("auditClients_2n", ctx.get("AuditClients_2n"));
				ctx.put("auditClients_8n", ctx.get("AuditClients_8n"));
				ctx.put("auditClients_9n", ctx.get("AuditClients_9n"));
				audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",audit);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			
			
			else
			{
				ruleid="banksBrokersInsuranceCompaniesInvestmentCompanies_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			
			
			/*rule no:-87,Q.no:-6(Audit)*/
			if(ctx.get("FirmRenderedAuditAtt")!=null && !ctx.get("FirmRenderedAuditAtt").toString().isEmpty() && Integer.valueOf(ctx.get("FirmRenderedAuditAtt").toString())>0 )
			{	
				
			ruleid="howManyClientsFirmRenderedAuditAttestation_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("firmRenderedAuditAtt", ctx.get("FirmRenderedAuditAtt"));
			audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
			ctx.put("RuleObject",audit);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="howManyClientsFirmRenderedAuditAttestation_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			/*rule no:-87,Q.no:-6(Audit)*/
			
			Object objAffiliation =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetFirmDetails", ctx);	
	
			if(objAffiliation != null)
			{
				Map affiliationvalue = (Map) objAffiliation;
				
				ctx.put("NumberOfPersonnelValue", affiliationvalue.get("TotalFirmPersonnel"));
				
			}
			if(ctx.get("NumberOfPersonnelValue")!=null && !ctx.get("NumberOfPersonnelValue").toString().isEmpty() && Integer.valueOf(ctx.get("NumberOfPersonnelValue").toString())>0 && ctx.get("IsSecondPartnerReviewAtt")!=null && ctx.get("IsSecondPartnerReviewAtt").toString().equals("N") )
			{	
			
			ruleid="doesSecondPartnerReviewAuditWorkpapers_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("numberOfPersonnelValue", ctx.get("NumberOfPersonnelValue"));
			ctx.put("isSecondPartnerReviewAtt", ctx.get("IsSecondPartnerReviewAtt"));
			audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
			ctx.put("RuleObject",audit);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="doesSecondPartnerReviewAuditWorkpapers_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			/*rule no:-87q.No:5(Audit)new*/
			if(ctx.get("NumberOfPersonnelValue")!=null && !ctx.get("NumberOfPersonnelValue").toString().isEmpty() && Integer.valueOf(ctx.get("NumberOfPersonnelValue").toString())>0 && ctx.get("IsAuditEngagementsAtt")!=null && ctx.get("IsAuditEngagementsAtt").toString().equals("N") )
			{	
				
			ruleid="doesSecondPartnerCommitteeApproveSignOof_Accountant";
			{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
			ctx.put("numberOfPersonnelValue", ctx.get("NumberOfPersonnelValue"));
			ctx.put("isAuditEngagementsAtt", ctx.get("IsAuditEngagementsAtt"));
			audit=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
			ctx.put("RuleObject",audit);
			insertRulesToDatabase(callRuleEngine(ctx,ruleid),audit,ruleid,audit.getDescription(),audit.getTooltip());
			}
			else
			{
				ruleid="doesSecondPartnerCommitteeApproveSignOof_Accountant";	
				archiveFlag(ctx,ruleid);
			}
			
		}
		if(policyStatusKey==2)
			{
			AuditAcc auditr=null;
			int difference=0;
			/*for new policy*/
			List aopValueNewPolicy= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetNewPolicyAopList",ctx);
			ctx.putAll((Map) aopValueNewPolicy.get(0));
			Context newAopValueForAudit=new Context();
			newAopValueForAudit.setProject(ctx.getProject());
			newAopValueForAudit.put("AOPKey",ctx.get("AOPKey"));
			newAopValueForAudit.put("PercentageValue",ctx.get("PercentageValue"));
			
			/*for old policydate*/
			List previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
			ctx.putAll((Map) previousPolicyKey.get(0));
			Context oldPolicyContext=new Context();
			oldPolicyContext.setProject(ctx.getProject());
			oldPolicyContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
			oldPolicyContext.put("VersionSequence",ctx.get("previousVersionSequence"));
			List auditAopListOld = SqlResources.getSqlMapProcessor(oldPolicyContext).select("SqlStmts.UserStatementdroolQueriesgetNewPolicyAopList",oldPolicyContext);
			oldPolicyContext.putAll((Map) auditAopListOld.get(0));
			
				
				/*rule no:-42,Q.no:-6(AUDIT SUPPLEMENT FOR Renew)*/
				if(ctx.get("FirmRenderedAuditAtt")!=null && !ctx.get("FirmRenderedAuditAtt").toString().isEmpty() && Integer.valueOf(ctx.get("FirmRenderedAuditAtt").toString())>0 )
				{	
					
				ruleid="howManyClientsFirmRenderedAuditAttestationRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("firmRenderedAuditAtt", ctx.get("FirmRenderedAuditAtt"));
				auditr=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",auditr);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),auditr,ruleid,auditr.getDescription(),auditr.getTooltip());
				}
				else
				{
					ruleid="howManyClientsFirmRenderedAuditAttestationRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-46,Q.no:-6(AUDIT SUPPLEMENT FOR Renew)*/
				/*if(ctx.get("IsAualityReviewAtt")!=null && ctx.get("IsAualityReviewAtt").toString().equals("N") )
				{	
					
				ruleid="hasFirmUndergonePeerQualityReviewRenew_Accountant";
				logger.debug("going to debug : "+ ruleid);
				ctx.put("isAualityReviewAtt", ctx.get("IsAualityReviewAtt"));
				auditr=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",auditr);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),auditr,ruleid,auditr.getDescription(),auditr.getTooltip());
				}
				else
				{
					ruleid="hasFirmUndergonePeerQualityReviewRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}*/
				/*rule no:-47,Q.no:-7(AUDIT SUPPLEMENT FOR Renew)*/
				if(ctx.get("IsLastPeerReviewAtt")!=null && ctx.get("IsLastPeerReviewAtt").toString().equals("Y") )
				{	
					
				ruleid="hasFirmlastpeerreviewmodifiedqualifiedRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isLastPeerReviewAtt", ctx.get("IsLastPeerReviewAtt"));
				auditr=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",auditr);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),auditr,ruleid,auditr.getDescription(),auditr.getTooltip());
				}
				else
				{
					ruleid="hasFirmlastpeerreviewmodifiedqualifiedRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-10,Q-1(Audit Renew)*/
				if((ctx.get("AuditClients_1")!=null && !ctx.get("AuditClients_1").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_1").toString())>0)
					||(ctx.get("AuditClients_2")!=null && !ctx.get("AuditClients_2").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_2").toString())>0)
					||(ctx.get("AuditClients_8")!=null && !ctx.get("AuditClients_8").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_8").toString())>0)
					||(ctx.get("AuditClients_9")!=null && !ctx.get("AuditClients_9").toString().isEmpty() && Integer.valueOf(ctx.get("AuditClients_9").toString())>0))
					{
					String AuditClients_1n = ctx.get("AuditClients_1").toString();
					if(AuditClients_1n.equals("")){
						AuditClients_1n="0";
					}
					ctx.put("AuditClients_1n",AuditClients_1n);
					String AuditClients_2n = ctx.get("AuditClients_2").toString();
					if(AuditClients_2n.equals("")){
						AuditClients_2n="0";
					}
					ctx.put("AuditClients_2n",AuditClients_2n);
					String AuditClients_8n = ctx.get("AuditClients_8").toString();
					if(AuditClients_8n.equals("")){
						AuditClients_8n="0";
					}
					ctx.put("AuditClients_8n",AuditClients_8n);
					String AuditClients_9n = ctx.get("AuditClients_9").toString();
					if(AuditClients_9n.equals("")){
						AuditClients_9n="0";
					}
					ctx.put("AuditClients_9n",AuditClients_9n);
					ruleid="banksBrokersInsuranceCompaniesInvestmentCompaniesRenew_Accountant";	
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("auditClients_1n", ctx.get("AuditClients_1n"));
					ctx.put("auditClients_2n", ctx.get("AuditClients_2n"));
					ctx.put("auditClients_8n", ctx.get("AuditClients_8n"));
					ctx.put("auditClients_9n", ctx.get("AuditClients_9n"));
					auditr=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
					ctx.put("RuleObject",auditr);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),auditr,ruleid,auditr.getDescription(),auditr.getTooltip());
				}
				else
				{
					ruleid="banksBrokersInsuranceCompaniesInvestmentCompaniesRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsFirmHaveAWrittenPolicyAtt")!=null && ctx.get("IsFirmHaveAWrittenPolicyAtt").toString().equals("N") )
				{	
					
				ruleid="auditRelatedCPETrainingRenew_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isFirmHaveAWrittenPolicyAtt", ctx.get("IsFirmHaveAWrittenPolicyAtt"));
				auditr=(AuditAcc)initlizePOJO(ctx, new AuditAcc());
				ctx.put("RuleObject",auditr);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),auditr,ruleid,auditr.getDescription(),auditr.getTooltip());
				}
				else
				{
					ruleid="auditRelatedCPETrainingRenew_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				
				if(ctx.get("IsDebtOrEquityFinancing")!=null && ctx.get("IsDebtOrEquityFinancing").toString().equals("Y") )
				{	
					
				ruleid="hasFirmArrangedFinancingBusinessBrokerOfferingPublicPrivateSecurities_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isDebtOrEquityFinancing", ctx.get("IsDebtOrEquityFinancing"));
				
				GeneralAcc general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="hasFirmArrangedFinancingBusinessBrokerOfferingPublicPrivateSecurities_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsSyndicationManagers")!=null && ctx.get("IsSyndicationManagers").toString().equals("Y") )
				{	
					
				ruleid="investmentFundsHedgeFundsMutualFunds_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isSyndicationManagers", ctx.get("IsSyndicationManagers"));
				GeneralAcc general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="investmentFundsHedgeFundsMutualFunds_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("error occured while executing ", e);
		}
		
}
	
	public static void evaluateBusinessManagementSupplement(Context ctx) throws Exception
	{
		logger.debug("going to execute evaluateBusinessManagementSupplement");
		String ruleid="";
		BusinessManagementAcc business=null;
		try
		{
			int policyStatusKey=ctx.get("PolicyStatusKey")!=null && !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY) ?Integer.parseInt(ctx.get("PolicyStatusKey").toString()):0;
			if(policyStatusKey==1)
				{
				
				if(ctx.get("HaveInvestmentAuthorityFC")!=null && ctx.get("HaveInvestmentAuthorityFC").toString().equals("Y") )
				{	
				ruleid="doesFirmhaveDiscretionaryInvestment_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("haveInvestmentAuthorityFC", ctx.get("HaveInvestmentAuthorityFC"));
				business=(BusinessManagementAcc)initlizePOJO(ctx, new BusinessManagementAcc());
				ctx.put("RuleObject",business);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),business,ruleid,business.getDescription(),business.getTooltip());
				}
				else
				{
					ruleid="doesFirmhaveDiscretionaryInvestment_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-100,Q.no:-4(BusinessManagement)*/
				if(ctx.get("IsControlFundsForBusinessAndEntimentFc")!=null && ctx.get("IsControlFundsForBusinessAndEntimentFc").toString().equals("Y") )
				{	
				ruleid="provideBusinessManagementFamilyOfficeServices_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isControlFundsForBusinessAndEntimentFc", ctx.get("IsControlFundsForBusinessAndEntimentFc"));
				business=(BusinessManagementAcc)initlizePOJO(ctx, new BusinessManagementAcc());
				ctx.put("RuleObject",business);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),business,ruleid,business.getDescription(),business.getTooltip());
				}
				else
				{
					ruleid="provideBusinessManagementFamilyOfficeServices_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				if(ctx.get("IsManagementServiceProvided")!=null && ctx.get("IsManagementServiceProvided").toString().equals("Y") )
				{	
					
				ruleid="providedManagementServicesForInvestmentVentures_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isManagementServiceProvided", ctx.get("IsManagementServiceProvided"));
				/*ruleSetContextList=initilizeRuleEngineContext(ctx,ruleid,"isManagementServiceProvided");
				insertRulesToDatabase(ctx,ruleSetContextList,ruleid);
				clearRuleSetContextList(ruleid,ruleSetContextList);*/
				GeneralAcc general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
				ctx.put("RuleObject",general);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
				}
				else
				{
					ruleid="providedManagementServicesForInvestmentVentures_Accountant";	
					archiveFlag(ctx,ruleid);
				}
				/*rule no:-103,Q.no:-7(BusinessManagement)*/
				if(ctx.get("IsFirmPersonnelBusinessManagerFc")!=null && ctx.get("IsFirmPersonnelBusinessManagerFc").toString().equals("Y") )
				{	
				ruleid="doFirmPersonnelActasManagerCFOforClients_Accountant";
				{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
				ctx.put("isFirmPersonnelBusinessManagerFc", ctx.get("IsFirmPersonnelBusinessManagerFc"));
				business=(BusinessManagementAcc)initlizePOJO(ctx, new BusinessManagementAcc());
				ctx.put("RuleObject",business);
				insertRulesToDatabase(callRuleEngine(ctx,ruleid),business,ruleid,business.getDescription(),business.getTooltip());
				}
				else
				{
					ruleid="doFirmPersonnelActasManagerCFOforClients_Accountant";	
					archiveFlag(ctx,ruleid);
				}
			}
			if(policyStatusKey==2)
			{
				int difference=0;
				/*for new policy data for Aop Business management*/
				List aopNewValueBusiness= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetNewPolicyAopBusinessMgmt",ctx);
				ctx.putAll((Map) aopNewValueBusiness.get(0));
				Context aopValueBusinessMgmt=new Context();
				aopValueBusinessMgmt.setProject(ctx.getProject());
				aopValueBusinessMgmt.put("AOPKey",ctx.get("AOPKey"));
				aopValueBusinessMgmt.put("PercentageValue",ctx.get("PercentageValue"));
				{ if (logger.isDebugEnabled()) logger.debug("Aop value new policy.."+aopValueBusinessMgmt); }
				
				/*getting old policydate for comparing business management*/
				List previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
				ctx.putAll((Map) previousPolicyKey.get(0));
				Context oldPolicyContext=new Context();
				oldPolicyContext.setProject(ctx.getProject());
				oldPolicyContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
				oldPolicyContext.put("VersionSequence",ctx.get("previousVersionSequence"));
				List auditAopListOld = SqlResources.getSqlMapProcessor(oldPolicyContext).select("SqlStmts.UserStatementdroolQueriesgetNewPolicyAopBusinessMgmt",oldPolicyContext);
				oldPolicyContext.putAll((Map) auditAopListOld.get(0));
				//System.out.println("Aop value old policy.."+oldPolicyContext);
				//difference=Integer.valueOf(aopValueBusinessMgmt.get("PercentageValue").toString())-Integer.valueOf(oldPolicyContext.get("PercentageValue").toString());
				/*rule no:-49,Q.no:-1(BusinessManagement Renew)*/
					if(ctx.get("HaveInvestmentAuthorityFC")!=null && ctx.get("HaveInvestmentAuthorityFC").toString().equals("Y") )
					{	
					ruleid="doesFirmhaveDiscretionaryInvestmentRenew_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("haveInvestmentAuthorityFC", ctx.get("HaveInvestmentAuthorityFC"));
					business=(BusinessManagementAcc)initlizePOJO(ctx, new BusinessManagementAcc());
					ctx.put("RuleObject",business);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),business,ruleid,business.getDescription(),business.getTooltip());
					}
					else
					{
						ruleid="doesFirmhaveDiscretionaryInvestmentRenew_Accountant";	
						archiveFlag(ctx,ruleid);
					}
					/*rule no:-50,Q.no:-4(BusinessManagement Renew)*/
					if(ctx.get("IsControlFundsForBusinessAndEntimentFc")!=null && ctx.get("IsControlFundsForBusinessAndEntimentFc").toString().equals("Y") )
					{	
					ruleid="provideBusinessManagementFamilyOfficeServicesRenew_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isControlFundsForBusinessAndEntimentFc", ctx.get("IsControlFundsForBusinessAndEntimentFc"));
					business=(BusinessManagementAcc)initlizePOJO(ctx, new BusinessManagementAcc());
					ctx.put("RuleObject",business);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),business,ruleid,business.getDescription(),business.getTooltip());
					}
					else
					{
						ruleid="provideBusinessManagementFamilyOfficeServicesRenew_Accountant";	
						archiveFlag(ctx,ruleid);
					}
					if(ctx.get("IsManagementServiceProvided")!=null && ctx.get("IsManagementServiceProvided").toString().equals("Y") )
					{	
						
					ruleid="providedManagementServicesForInvestmentVentures_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isManagementServiceProvided", ctx.get("IsManagementServiceProvided"));
					
					GeneralAcc general=(GeneralAcc)initlizePOJO(ctx, new GeneralAcc());
					ctx.put("RuleObject",general);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),general,ruleid,general.getDescription(),general.getTooltip());
					}
					else
					{
						ruleid="providedManagementServicesForInvestmentVentures_Accountant";	
						archiveFlag(ctx,ruleid);
					}
					/*rule no:-51,Q.no:-7(BusinessManagement Renew)*/
					if(ctx.get("IsFirmPersonnelBusinessManagerFc")!=null && ctx.get("IsFirmPersonnelBusinessManagerFc").toString().equals("Y") )
					{	
					ruleid="doFirmPersonnelActasManagerCFOforClientsRenew_Accountant";
					{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
					ctx.put("isFirmPersonnelBusinessManagerFc", ctx.get("IsFirmPersonnelBusinessManagerFc"));
					business=(BusinessManagementAcc)initlizePOJO(ctx, new BusinessManagementAcc());
					ctx.put("RuleObject",business);
					insertRulesToDatabase(callRuleEngine(ctx,ruleid),business,ruleid,business.getDescription(),business.getTooltip());
					}
					else
					{
						ruleid="doFirmPersonnelActasManagerCFOforClientsRenew_Accountant";	
						archiveFlag(ctx,ruleid);
					}
				
			}
		}
		catch(Exception e)
		{
			logger.error("Error occured while executing evaluateBusinessManagementSupplement method", e);
		}
	}
	
	public static List<Context>  initilizeRuleEngineContext(Context ctx,List dataList,String ruleId,String dataForRuleEngine)
	{	
		try 
		{
			String[] tokens=getTokens(dataForRuleEngine,",");
			List<Context> ruleSetContextList = new ArrayList<Context>();
			if(dataList!=null && dataList instanceof List)
			{
				for (int i = 0; i < dataList.size(); i++) {
					Map dataMap = (Map) dataList.get(i);
					Context newCtx=new Context();
					newCtx.setProject(ctx.getProject());
					//newCtx.put(ruleId,dataMap.get(ruleId));
					for(int j=0;j<tokens.length;j++)
					{
						newCtx.put(tokens[j],dataMap.get(tokens[j]));
						
					}
					newCtx=callRuleEngine(newCtx,ruleId);
					ruleSetContextList.add(newCtx);
					}
					
			}
			return ruleSetContextList;
		}
		catch (Exception e)
		{
			logger.error("Error Occured while initilizing data  : " + e.getMessage(), e);
			return null;
		}
	}
	public static List<Context>  initilizeRuleEngineContext(Context ctx,String ruleId,String dataForRuleEngine)
	{	
		try 
		{
			String[] tokens=getTokens(dataForRuleEngine,",");
			List<Context> ruleSetContextList = new ArrayList<Context>();
			Context newCtx=new Context();
			newCtx.setProject(ctx.getProject());
			for(int j=0;j<tokens.length;j++)
			{
				newCtx.put(tokens[j],ctx.get(tokens[j]));
			}
			newCtx=callRuleEngine(newCtx,ruleId);
			ruleSetContextList.add(newCtx);
			return ruleSetContextList;
		
		}
		catch (Exception e) {
			logger.error("Error Occured while initilizing attorney data  : " + e.getMessage(), e);
			return null;
		}	
	}
	
	public static Context callRuleEngine(Context ctx,String ruleId) throws Exception
	{
		RulesResources.getInstance(ctx).findRule("TestRule."+ruleId).execute(ctx, null);
		return ctx;
	}
	public static void insertRulesToDatabase(Context ctx,List<Context> ruleSetContextList,String ruleId )
	{	String question="",hint="";
		if( ruleSetContextList !=null && ruleSetContextList.size()>0)
		{
			for( int i =0; i< ruleSetContextList.size(); i++)
			{
				Map listMap = ruleSetContextList.get(i);
				if(listMap != null && !listMap.isEmpty()){
					
					if(listMap.get("question") != null && listMap.get("hint") != null)
					{
						if(listMap.get("question")!=null && listMap.get("hint")!=null)
							question=listMap.get("question").toString();
							hint=listMap.get("hint").toString();
							//break;
					}
				}
			}
	
			{ if (logger.isDebugEnabled()) logger.debug("Question= "+question+" Question Hint="+hint); }
			if(!question.equals("") && !hint.equals("") )
			{	
				ctx.put("ruleId",ruleId);
				ctx.put("question",question);
				ctx.put("hint",hint);
				ctx.put("ActionTakenBy", ctx.get("user_id"));
				try {
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
					SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.SETFlagsLawyers_p",ctx);
				} catch (Exception e) {
					logger.error("Error in RuleEngineUtilityNewApp.insertRulesToDatabase", e);
				}
			}
			else
			{	ctx.put("ruleId",ruleId);
				try {
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
					SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.RESETFlagsLawyers_p",ctx);
				} catch (Exception e) {
					logger.error("Error in RuleEngineUtilityNewApp.insertRulesToDatabase", e);
				}
				
			}
		}
	}
	public static String[] getTokens(String str, String delim)
	{
	    StringTokenizer stok = new StringTokenizer(str, delim);
	    String tokens[] = new String[stok.countTokens()];
	    for(int i=0; i<tokens.length; i++){
	        tokens[i] = stok.nextToken();
	        tokens[i] = tokens[i].trim();
	    }
	    return tokens;
	}
	
	public static void archiveFlag(Context ctx,String ruleId)
	{
		
		ctx.put("ruleId",ruleId);
		try {
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.RESETFlagsLawyers_p",ctx);
			} catch (Exception e) {
				logger.error("Error in RuleEngineUtilityNewApp.archiveFlag", e);
			}
		}
		
	public static void clearRuleSetContextList(String ruleId,List<Context> ruleSetContextList)
	{
		try
		{
			if(ruleSetContextList !=null)
			{
				ruleSetContextList.clear();
			}
			
		}
		catch(Exception e)
		{
			logger.error("Rule Id: '"+ ruleId+"' is not found on drool rule file.", e);
		}
	}
	public static String formatDate(Object obj,String format)
	{
		SimpleDateFormat formatter= new SimpleDateFormat(format);
		try
		{
		String dateString=obj.toString();
		Date date = formatter.parse(dateString);
		return formatter.format(date);
		}
		catch(Exception e)
		{
			logger.error("Error in RuleEngineUtilityNewApp.formatDate", e);
			return null;
		}
		
	}
	public static void evaluateQuoteOptions(Context ctx)throws Exception
    {     String ruleid="";
          List<Context> ruleSetContextList = new ArrayList<Context>();
          List getTotalPremiumList=SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetTotalPremiumList",ctx);
          if(getTotalPremiumList!=null &&  getTotalPremiumList.size()>0)
          {           
                      ruleid="renewalPremiumComparision_Lawyers";
                      { if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
                      for (int i = 0; i < getTotalPremiumList.size(); i++) 
                      {
                            Map dataMap = (Map) getTotalPremiumList.get(i);
                            Context newCtx=new Context();
                            newCtx.setProject(ctx.getProject());
                            newCtx.put("totalPremium",dataMap.get("TotalPremium"));
                            newCtx=callRuleEngine(newCtx,ruleid);
                            ruleSetContextList.add(newCtx);
                      }
                      insertRulesToDatabase(ctx,ruleSetContextList,ruleid);
                      clearRuleSetContextList(ruleid,ruleSetContextList);
          }
          else
          {     ruleid="renewalPremiumComparision_Lawyers";     
                archiveFlag(ctx,ruleid);
                
          }
          
    }

	public static void insertRulesToDatabase(Context ctx,Object obj,String ruleId,String description,String toolTip )
	{	
			
			if(description!=null && toolTip!=null &&!description.equals("") && !toolTip.equals("") )
			{	
				ctx.put("ruleId",ruleId);
				ctx.put("question",description);
				ctx.put("hint",toolTip);
				ctx.put("ActionTakenBy", ctx.get("user_id"));
				try {
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
					SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.SETFlagsLawyers_p",ctx);
				} catch (Exception e) {
					logger.error("Error in RuleEngineUtilityNewApp.insertRulesToDatabase", e);
				}
			}
			else
			{	ctx.put("ruleId",ruleId);
				ctx.put("question","");
				ctx.put("hint","");
				try {
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
					SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.RESETFlagsLawyers_p",ctx);
				} catch (Exception e) {
					logger.error("Error in RuleEngineUtilityNewApp.insertRulesToDatabase", e);
				}
				
			}
		}
	
	//temporary solution till drool fixed
		public static void insertRulesToDatabaseTemp(Context ctx,String ruleId,String description,String toolTip )
		{	
				
				if(description!=null && toolTip!=null &&!description.equals("") && !toolTip.equals("") )
				{	
					ctx.put("ruleId",ruleId);
					ctx.put("question",description);
					ctx.put("hint",toolTip);
					ctx.put("ActionTakenBy", ctx.get("user_id"));
					try {
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
						SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.SETFlagsLawyers_p",ctx);
					} catch (Exception e) {
						logger.error("Error in RuleEngineUtilityNewApp.insertRulesToDatabaseTemp", e);
					}
				}
				else
				{	ctx.put("ruleId",ruleId);
					ctx.put("question","");
					ctx.put("hint","");
					try {
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,ruleId,question,hint,ActionTakenBy");
						SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.RESETFlagsLawyers_p",ctx);
					} catch (Exception e) {
						logger.error("Error in RuleEngineUtilityNewApp.insertRulesToDatabaseTemp", e);
					}
					
				}
			}
		
	public static Object initlizePOJO(Context ctx,Object obj)
	{
		String methodName="";
		
		try {

			Class<? extends Object> c=obj.getClass(); 
			obj = c.newInstance();
			Method method=null;
			Field[] fields=c.getDeclaredFields(); 
			for(Field field : fields)
			{
				
				if(field.getType().toString().equalsIgnoreCase("class java.lang.Integer"))
				{
					methodName=field.getName();
					int fieldValue=((ctx.get(field.getName())!=null || "".equals(ctx.get(field.getName().toString())) )?Integer.valueOf(ctx.get(field.getName()).toString().trim()):0);
					BeanUtils.setProperty(obj, field.getName(), fieldValue);
					
				}
				if(field.getType().toString().equalsIgnoreCase("class java.lang.String"))
				{
					methodName=field.getName();
					String fieldValue=(ctx.get(field.getName())!=null?ctx.get(field.getName()).toString():"");
					BeanUtils.setProperty(obj, field.getName(), fieldValue);
					
				}
				if(field.getType().toString().equalsIgnoreCase("class java.lang.Long"))
				{
					methodName=field.getName();
					long fieldValue=((ctx.get(field.getName())!=null || "".equals(ctx.get(field.getName().toString())) )?Long.valueOf(ctx.get(field.getName()).toString().trim()):0);
					BeanUtils.setProperty(obj, field.getName(), fieldValue);
					
				}
				if(field.getType().toString().equalsIgnoreCase("class java.lang.Float"))
				{
					methodName=field.getName();
					float fieldValue=((ctx.get(field.getName())!=null || "".equals(ctx.get(field.getName().toString())) )?Float.valueOf(ctx.get(field.getName()).toString().trim()):0.0F);
					BeanUtils.setProperty(obj, field.getName(), fieldValue);
					
				}
				if(field.getType().toString().equalsIgnoreCase("class java.lang.Character"))
				{
					methodName=field.getName();
					char fieldValue=((ctx.get(field.getName())!=null || "".equals(ctx.get(field.getName().toString())) )?Character.valueOf(ctx.get(field.getName()).toString().charAt(0)):' ');
					BeanUtils.setProperty(obj, field.getName(), fieldValue);
					
				}
				if(field.getType().toString().equalsIgnoreCase("class java.lang.Double"))
				{
					methodName=field.getName();
					double fieldValue=((ctx.get(field.getName())!=null || "".equals(ctx.get(field.getName().toString())) )?Double.valueOf(ctx.get(field.getName()).toString().trim()):0.0);
					BeanUtils.setProperty(obj, field.getName(),fieldValue);
					
				}
				/*if(!methodName.equals(""))
				{
				method=c.getMethod("get"+""+Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1));
				}*/
			}
			
		}
		catch(Exception e)
		{
			logger.error("Error in RuleEngineUtilityNewApp.initlizePOJO", e);
		}
		return obj;
	}	

	public static void evaluateSupplementComputerServicesSuppNewApp(Context ctx) throws Exception
	{
		new com.userbo.AOP().populateITSuppValues(ctx);
		String ruleid="";
		if(ctx.get("PolicyStatusKey")!=null && ctx.get("PolicyStatusKey").toString().equals("1"))
		{
		//ctx.put("currentDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			/*RevenuesFromLastYear_
			EstimatedRevenue_1
	
		
			if((ctx.get("RevenuesFromLastYear_2")!=null && !ctx.get("RevenuesFromLastYear_2").toString().isEmpty() ) || (ctx.get("EstimatedRevenue_2")!=null && !ctx.get("EstimatedRevenue_2").toString().isEmpty() ) 
					|| (ctx.get("RevenuesFromLastYear_3")!=null && !ctx.get("RevenuesFromLastYear_3").toString().isEmpty() ) || (ctx.get("EstimatedRevenue_3")!=null && !ctx.get("EstimatedRevenue_3").toString().isEmpty() )
					|| (ctx.get("RevenuesFromLastYear_4")!=null && !ctx.get("RevenuesFromLastYear_4").toString().isEmpty() ) || (ctx.get("EstimatedRevenue_4")!=null && !ctx.get("EstimatedRevenue_4").toString().isEmpty() )
					|| (ctx.get("RevenuesFromLastYear_5")!=null && !ctx.get("RevenuesFromLastYear_5").toString().isEmpty() ) || (ctx.get("EstimatedRevenue_5")!=null && !ctx.get("EstimatedRevenue_5").toString().isEmpty() ))
			
			{	*/
				int medicalMalpractice=0,productsLiability=0,toxicTort=0,other=0;
				String RevenuesFromLastYear_2s = ctx.get("RevenuesFromLastYear_6").toString().replaceAll("[-+.^:,$]","");
				if(RevenuesFromLastYear_2s.equals("")){
					RevenuesFromLastYear_2s="0";
				}
				ctx.put("RevenuesFromLastYear_2n",RevenuesFromLastYear_2s);
				
				String RevenuesFromLastYear_3s = ctx.get("RevenuesFromLastYear_3").toString().replaceAll("[-+.^:,$]","");
				if(RevenuesFromLastYear_3s.equals("")){
					RevenuesFromLastYear_3s="0";
				}
				ctx.put("RevenuesFromLastYear_3n",RevenuesFromLastYear_3s);
				String RevenuesFromLastYear_4s = ctx.get("RevenuesFromLastYear_4").toString().replaceAll("[-+.^:,$]","");
				if(RevenuesFromLastYear_4s.equals("")){
					RevenuesFromLastYear_4s="0";
				}
				ctx.put("RevenuesFromLastYear_4n",RevenuesFromLastYear_4s);
				String RevenuesFromLastYear_5s = ctx.get("RevenuesFromLastYear_5").toString().replaceAll("[-+.^:,$]","");
				if(RevenuesFromLastYear_5s.equals("")){
					RevenuesFromLastYear_5s="0";
				}
				ctx.put("RevenuesFromLastYear_5n",RevenuesFromLastYear_5s);
				String EstimatedRevenue_2s = ctx.get("EstimatedRevenue_6").toString().replaceAll("[-+.^:,$]","");
				if(EstimatedRevenue_2s.equals("")){
					EstimatedRevenue_2s="0";
				}
				ctx.put("EstimatedRevenue_2n",EstimatedRevenue_2s);
				String EstimatedRevenue_3s = ctx.get("EstimatedRevenue_3").toString().replaceAll("[-+.^:,$]","");
				if(EstimatedRevenue_3s.equals("")){
					EstimatedRevenue_3s="0";
				}
				ctx.put("EstimatedRevenue_3n",EstimatedRevenue_3s);
				String EstimatedRevenue_4s = ctx.get("EstimatedRevenue_4").toString().replaceAll("[-+.^:,$]","");
				if(EstimatedRevenue_4s.equals("")){
					EstimatedRevenue_4s="0";
				}
				ctx.put("EstimatedRevenue_4n",EstimatedRevenue_4s);
				String EstimatedRevenue_5s = ctx.get("EstimatedRevenue_5").toString().replaceAll("[-+.^:,$]","");
				if(EstimatedRevenue_5s.equals("")){
					EstimatedRevenue_5s="0";
				}
				ctx.put("EstimatedRevenue_5n",EstimatedRevenue_5s);
		/*boolean containsAnyAmountInRevenue=false;
			ctx.put("containsAnyAmountInRevenue", "true");*/
		ruleid="computerRelatedServicesAmountInRevenues_Accountant";
		{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
		ctx.put("revenuesFromLastYear_2n", ctx.get("RevenuesFromLastYear_2n"));
		ctx.put("revenuesFromLastYear_3n", ctx.get("RevenuesFromLastYear_3n"));
		ctx.put("revenuesFromLastYear_4n", ctx.get("RevenuesFromLastYear_4n"));
		ctx.put("revenuesFromLastYear_5n", ctx.get("RevenuesFromLastYear_5n"));
		ctx.put("estimatedRevenue_2n", ctx.get("EstimatedRevenue_2n"));
		ctx.put("estimatedRevenue_3n", ctx.get("EstimatedRevenue_3n"));
		ctx.put("estimatedRevenue_4n", ctx.get("EstimatedRevenue_4n"));
		ctx.put("estimatedRevenue_5n", ctx.get("EstimatedRevenue_5n"));
		ComputerservicesAcc computer=(ComputerservicesAcc)initlizePOJO(ctx, new ComputerservicesAcc());
		ctx.put("RuleObject",computer);
		insertRulesToDatabase(callRuleEngine(ctx,ruleid),computer,ruleid,computer.getDescription(),computer.getTooltip());
		
		
	}
		if(ctx.get("PolicyStatusKey")!=null && ctx.get("PolicyStatusKey").toString().equals("2"))
		{
			
			int medicalMalpractice=0,productsLiability=0,toxicTort=0,other=0;
			String RevenuesFromLastYear_2s = ctx.get("RevenuesFromLastYear_2").toString().replaceAll("[-+.^:,$]","");
			if(RevenuesFromLastYear_2s.equals("")){
				RevenuesFromLastYear_2s="0";
			}
			ctx.put("RevenuesFromLastYear_2n",RevenuesFromLastYear_2s);
			String RevenuesFromLastYear_3s = ctx.get("RevenuesFromLastYear_3").toString().replaceAll("[-+.^:,$]","");
			if(RevenuesFromLastYear_3s.equals("")){
				RevenuesFromLastYear_3s="0";
			}
			ctx.put("RevenuesFromLastYear_3n",RevenuesFromLastYear_3s);
			String RevenuesFromLastYear_4s = ctx.get("RevenuesFromLastYear_4").toString().replaceAll("[-+.^:,$]","");
			if(RevenuesFromLastYear_4s.equals("")){
				RevenuesFromLastYear_4s="0";
			}
			ctx.put("RevenuesFromLastYear_4n",RevenuesFromLastYear_4s);
			String RevenuesFromLastYear_5s = ctx.get("RevenuesFromLastYear_5").toString().replaceAll("[-+.^:,$]","");
			if(RevenuesFromLastYear_5s.equals("")){
				RevenuesFromLastYear_5s="0";
			}
			ctx.put("RevenuesFromLastYear_5n",RevenuesFromLastYear_5s);
			String EstimatedRevenue_2s = ctx.get("EstimatedRevenue_2").toString().replaceAll("[-+.^:,$]","");
			if(EstimatedRevenue_2s.equals("")){
				EstimatedRevenue_2s="0";
			}
			ctx.put("EstimatedRevenue_2n",EstimatedRevenue_2s);
			String EstimatedRevenue_3s = ctx.get("EstimatedRevenue_3").toString().replaceAll("[-+.^:,$]","");
			if(EstimatedRevenue_3s.equals("")){
				EstimatedRevenue_3s="0";
			}
			ctx.put("EstimatedRevenue_3n",EstimatedRevenue_3s);
			String EstimatedRevenue_4s = ctx.get("EstimatedRevenue_4").toString().replaceAll("[-+.^:,$]","");
			if(EstimatedRevenue_4s.equals("")){
				EstimatedRevenue_4s="0";
			}
			ctx.put("EstimatedRevenue_4n",EstimatedRevenue_4s);
			String EstimatedRevenue_5s = ctx.get("EstimatedRevenue_5").toString().replaceAll("[-+.^:,$]","");
			if(EstimatedRevenue_5s.equals("")){
				EstimatedRevenue_5s="0";
			}
			ctx.put("EstimatedRevenue_5n",EstimatedRevenue_5s);
			ruleid="computerRelatedServicesAmountInRevenuesRenew_Accountant";
	{ if (logger.isDebugEnabled()) logger.debug("going to debug : "+ ruleid); }
	ctx.put("revenuesFromLastYear_2n", ctx.get("RevenuesFromLastYear_2n"));
	ctx.put("revenuesFromLastYear_3n", ctx.get("RevenuesFromLastYear_3n"));
	ctx.put("revenuesFromLastYear_4n", ctx.get("RevenuesFromLastYear_4n"));
	ctx.put("revenuesFromLastYear_5n", ctx.get("RevenuesFromLastYear_5n"));
	ctx.put("estimatedRevenue_2n", ctx.get("EstimatedRevenue_2n"));
	ctx.put("estimatedRevenue_3n", ctx.get("EstimatedRevenue_3n"));
	ctx.put("estimatedRevenue_4n", ctx.get("EstimatedRevenue_4n"));
	ctx.put("estimatedRevenue_5n", ctx.get("EstimatedRevenue_5n"));
	/*ruleSetContextList=initilizeRuleEngineContext(ctx,ruleid,"revenuesFromLastYear_2n,revenuesFromLastYear_3n,revenuesFromLastYear_4n,revenuesFromLastYear_5n,estimatedRevenue_2n,estimatedRevenue_3n,estimatedRevenue_4n,estimatedRevenue_5n");
	insertRulesToDatabase(ctx,ruleSetContextList,ruleid);
	clearRuleSetContextList(ruleid,ruleSetContextList);*/
	ComputerservicesAcc computerr=(ComputerservicesAcc)initlizePOJO(ctx, new ComputerservicesAcc());
	ctx.put("RuleObject",computerr);
	insertRulesToDatabase(callRuleEngine(ctx,ruleid),computerr,ruleid,computerr.getDescription(),computerr.getTooltip());
			}
		}
	
}
