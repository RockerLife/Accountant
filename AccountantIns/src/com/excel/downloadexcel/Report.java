package com.excel.downloadexcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.excel.oldpim.ExcelUtility;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.ormapping.ibatis.SqlResources;
import com.userbo.AccountantUtilities;
import com.util.Context;

public class Report {
	
	public void getDataForReport(Context ctx) throws Exception
	{
		Object listReport = null;
		
		if("BROKERAGE".equals(ctx.get("ReportType")))
		{
			if("Acc".equals(ctx.get("ProjectType").toString())){
				/*new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate");
				 listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateCrumFosterReportAcc_p",ctx);*/
				return;
				
			}
			else if("Lwy".equals(ctx.get("ProjectType").toString())){
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate");
				listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateBrokerageReportLW_p",ctx);
				
			}	
				
		}
		else if("MCGOWAN".equals(ctx.get("ReportType")))
		{
			if("Acc".equals(ctx.get("ProjectType").toString())){
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
				 listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateCrumFosterReportAcc_p",ctx);
				
			}
			else if("Lwy".equals(ctx.get("ProjectType").toString())){
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
				listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateMcGowanReportLW_p",ctx);
				
			}	
		}
		else
		{
			if("Acc".equals(ctx.get("ProjectType").toString())){
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
				 listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateCrumFosterReportAcc_p",ctx);
				
			}
			else if("Lwy".equals(ctx.get("ProjectType").toString())){
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
				listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateCrumFosterReportLW_p",ctx);
				
			}		
		}
		ctx.put(Constants.REPORT_LIST, listReport);		
		
	}	
	
	public void populateReportData(Context ctx, Workbook wb, String sheetsReport) throws Exception
	{
		Sheet sheet = wb.getSheet(sheetsReport);
        CellStyle string= wb.createCellStyle();
        string.setDataFormat((short) 38);
        CellStyle numeric= wb.createCellStyle();
        numeric.setDataFormat((short) 38);
        
        populateReportList(ctx, sheet);
	}
	
	@SuppressWarnings("rawtypes")
	public void populateReportList(Context ctx, Sheet sheet) throws Exception	
	{
		Object attestationObj = ctx.get(Constants.REPORT_LIST);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.PolicyNumber);
		
		for(int i = rb_federal.getRownum() + attestationList.size(); i<=rb_federal.getRownum(); i++){
        	Row row = sheet.getRow(i);   
        	sheet.removeRow(row);
        }
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);
			
			setCellValue(ctx, i, map, excelFieldMapping, sheet);
		}
	}

	@SuppressWarnings("rawtypes")
	public void setCellValue(Context ctx, int i, Map map, Map excelFieldMapping, Sheet sheet)  throws Exception {
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyNumber, false), excelFieldMapping.get(Constants.PolicyNumber), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ContactPerson, false), excelFieldMapping.get(Constants.ContactPerson), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AccountName, false), excelFieldMapping.get(Constants.AccountName), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Address1, false), excelFieldMapping.get(Constants.Address1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Address2, false), excelFieldMapping.get(Constants.Address2), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.City, false), excelFieldMapping.get(Constants.City), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.StateDesc, false), excelFieldMapping.get(Constants.StateDesc), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Zip, false), excelFieldMapping.get(Constants.Zip), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyEffectiveDate, false), excelFieldMapping.get(Constants.PolicyEffectiveDate), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TransactionEffectiveDate, false), excelFieldMapping.get(Constants.TransactionEffectiveDate), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyExpirationDate, false), excelFieldMapping.get(Constants.PolicyExpirationDate), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ERPExpirationDate, false), excelFieldMapping.get(Constants.ERPExpirationDate), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TotalPremium, false), excelFieldMapping.get(Constants.TotalPremium), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TotalPolicyTaxAmount, false), excelFieldMapping.get(Constants.TotalPolicyTaxAmount), i);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.InvoicedPremium, false), excelFieldMapping.get(Constants.InvoicedPremium), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AggregateLimit, false), excelFieldMapping.get(Constants.AggregateLimit), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.OccuranceLimit, false), excelFieldMapping.get(Constants.OccuranceLimit), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AggregateDeductible, false), excelFieldMapping.get(Constants.AggregateDeductible), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsClaimExpensesType, false), excelFieldMapping.get(Constants.IsClaimExpensesType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsClaimOptionType, false), excelFieldMapping.get(Constants.IsClaimOptionType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ERPExpirationDate, false), excelFieldMapping.get(Constants.ERPExpirationDate), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.FirstReviewer, false), excelFieldMapping.get(Constants.FirstReviewer), i);
		if(map != null && map.get("IsFirmCarryingProfLiabilityIns") != null && map.get("IsFirmCarryingProfLiabilityIns").equals("Y")){
			if(map.get("IsPriorActDateFull")!= null && map.get("IsPriorActDateFull").equals("N") ){
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PriorActDatePross", false), excelFieldMapping.get(Constants.RetroDate), i);
			}else{
				ExcelUtility.setCellValue(sheet, "No Limitation", excelFieldMapping.get(Constants.RetroDate), i);
			}
		}
		else{
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PriorActDatePross", false), excelFieldMapping.get(Constants.RetroDate), i);
		}
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IssuedDate, false), excelFieldMapping.get(Constants.IssuedDate), i);
		
		if(map != null && map.get(Constants.PaymentMode)==null)
		{
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.PaymentMode), i);
		}else if(map != null && map.get(Constants.PaymentMode).equals("eft"))
		{
			ExcelUtility.setCellValue(sheet, "Manual Check", excelFieldMapping.get(Constants.PaymentMode), i);
		}else if(map != null && map.get(Constants.PaymentMode).equals("finance")){	
			
			ExcelUtility.setCellValue(sheet, "Finance", excelFieldMapping.get(Constants.PaymentMode), i);			
		}else if(map != null && map.get(Constants.PaymentMode).equals("paypal")){
			
			ExcelUtility.setCellValue(sheet, "Paypal", excelFieldMapping.get(Constants.PaymentMode), i);
		}else{
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PaymentMode, false), excelFieldMapping.get(Constants.PaymentMode), i);
		}
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IssuedDate, false), excelFieldMapping.get(Constants.MailDate), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyStatus, false), excelFieldMapping.get(Constants.PolicyStatus), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.RenewOfPolicyNumber, false), excelFieldMapping.get(Constants.RenewOfPolicyNumber), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Is9010Policy, false), excelFieldMapping.get(Constants.Is9010Policy), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ProducerCode, false), excelFieldMapping.get(Constants.ProducerCode), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsDollarDefense, false), excelFieldMapping.get(Constants.IsDollarDefense), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AccountID, false), excelFieldMapping.get(Constants.AccountID), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsIndicationPage, false), excelFieldMapping.get(Constants.IsIndicationPage), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Fees, false), excelFieldMapping.get(Constants.Fees), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.LOB, false), excelFieldMapping.get(Constants.LOB), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Broker, false), excelFieldMapping.get(Constants.Broker), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Carrier, false), excelFieldMapping.get(Constants.Carrier), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Company, false), excelFieldMapping.get(Constants.Company), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.MarkAsRenewal, false), excelFieldMapping.get(Constants.MarkAsRenewal), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Commission, false), excelFieldMapping.get(Constants.Commission), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NetPremium, false), excelFieldMapping.get(Constants.NetPremium), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TotalDue, false), excelFieldMapping.get(Constants.TotalDue), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClientType, false), excelFieldMapping.get(Constants.ClientType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.InvoicePageBreak, false), excelFieldMapping.get(Constants.InvoicePageBreak), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.StateMentPageBreak, false), excelFieldMapping.get(Constants.StateMentPageBreak), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.StatementLayout, false), excelFieldMapping.get(Constants.StatementLayout), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BusinessType_YES, false), excelFieldMapping.get(Constants.BusinessType_YES), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BusinessType_No, false), excelFieldMapping.get(Constants.BusinessType_No), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Country, false), excelFieldMapping.get(Constants.Country), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.County, false), excelFieldMapping.get(Constants.County), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsDirect, false), excelFieldMapping.get(Constants.IsDirect), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.SPAddress, false), excelFieldMapping.get(Constants.SPAddress), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Contact_FirstName, false), excelFieldMapping.get(Constants.Contact_FirstName), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Contact_LastName, false), excelFieldMapping.get(Constants.Contact_LastName), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Contact_PhoneNo, false), excelFieldMapping.get(Constants.Contact_PhoneNo), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Contact_Email, false), excelFieldMapping.get(Constants.Contact_Email), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyBusinessType, false), excelFieldMapping.get(Constants.PolicyBusinessType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PremiumPayableType, false), excelFieldMapping.get(Constants.PremiumPayableType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.CommissionType, false), excelFieldMapping.get(Constants.CommissionType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.CommissionPercentage, false), excelFieldMapping.get(Constants.CommissionPercentage), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BPAYType1, false), excelFieldMapping.get(Constants.BPAYType1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BPAY_PPAY_ProdCredit1, false), excelFieldMapping.get(Constants.BPAY_PPAY_ProdCredit1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BPAY_PPAY_CommissionType1, false), excelFieldMapping.get(Constants.BPAY_PPAY_CommissionType1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BPAY_PPAY_Percentage1, false), excelFieldMapping.get(Constants.BPAY_PPAY_Percentage1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Prospective_Contracted, false), excelFieldMapping.get(Constants.Prospective_Contracted), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.WorkPhone, false), excelFieldMapping.get(Constants.WorkPhone), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Agriculture, false), excelFieldMapping.get(Constants.Business_Agriculture), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Benifits, false), excelFieldMapping.get(Constants.Business_Benifits), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Bonds, false), excelFieldMapping.get(Constants.Business_Bonds), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Commercial, false), excelFieldMapping.get(Constants.Business_Commercial), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Finencial, false), excelFieldMapping.get(Constants.Business_Finencial), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Health, false), excelFieldMapping.get(Constants.Business_Health), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Personal, false), excelFieldMapping.get(Constants.Business_Personal), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Business_Other, false), excelFieldMapping.get(Constants.Business_Other), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PopulateMainBusinessContact, false), excelFieldMapping.get(Constants.PopulateMainBusinessContact), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.MainBusinessAddress, false), excelFieldMapping.get(Constants.MainBusinessAddress), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.MainBusinessContact, false), excelFieldMapping.get(Constants.MainBusinessContact), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsShowPrimaryContactFromAccount, false), excelFieldMapping.get(Constants.IsShowPrimaryContactFromAccount), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PrimaryContact_UseAccountAddress, false), excelFieldMapping.get(Constants.PrimaryContact_UseAccountAddress), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BRECProductionCredit, false), excelFieldMapping.get(Constants.BRECProductionCredit), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BRECCommissionType, false), excelFieldMapping.get(Constants.BRECCommissionType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BREC_percentage, false), excelFieldMapping.get(Constants.BREC_percentage), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TransactionTypeDesc, false), excelFieldMapping.get(Constants.TransactionTypeDesc), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IssuingCompany, false), excelFieldMapping.get(Constants.IssuingCompany), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PayingCompany, false), excelFieldMapping.get(Constants.PayingCompany), i);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillMode, false), excelFieldMapping.get(Constants.BillMode), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillToEntityType, false), excelFieldMapping.get(Constants.BillToEntityType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillToEntityID, false), excelFieldMapping.get(Constants.BillToEntityID), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillToBrokerCommissionType, false), excelFieldMapping.get(Constants.BillToBrokerCommissionType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillToBrokerCommissionPercent, false), excelFieldMapping.get(Constants.BillToBrokerCommissionPercent), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillToBrokerProductionCredit, false), excelFieldMapping.get(Constants.BillToBrokerProductionCredit), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.EpicTransactionCode, false), excelFieldMapping.get(Constants.EpicTransactionCode), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.BillForAccount, false), excelFieldMapping.get(Constants.BillForAccount), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.EpicAccountAgency, false), excelFieldMapping.get(Constants.EpicAccountAgency), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.EpicAccountBranch, false), excelFieldMapping.get(Constants.EpicAccountBranch), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.EpicPolicyType, false), excelFieldMapping.get(Constants.EpicPolicyType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TransactionDescription, false), excelFieldMapping.get(Constants.TransactionDescription), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.GenerateInvoice, false), excelFieldMapping.get(Constants.GenerateInvoice), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IssuedMonth, false), excelFieldMapping.get(Constants.IssuedMonth), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.EpicPremiumCode, false), excelFieldMapping.get(Constants.EpicPremiumCode), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AgencyCommissionType, false), excelFieldMapping.get(Constants.AgencyCommissionType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AgencyCommissionPercent, false), excelFieldMapping.get(Constants.AgencyCommissionPercent), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ProducerCode1, false), excelFieldMapping.get(Constants.ProducerCode1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ProducerCode1CommissionType, false), excelFieldMapping.get(Constants.ProducerCode1CommissionType), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ProducerCode1CommissionPercent, false), excelFieldMapping.get(Constants.ProducerCode1CommissionPercent), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ProducerCode1ProductionCreditTotal, false), excelFieldMapping.get(Constants.ProducerCode1ProductionCreditTotal), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyEffectiveDate1, false), excelFieldMapping.get(Constants.PolicyEffectiveDate1), i);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IssuedMonth1, false), excelFieldMapping.get(Constants.IssuedMonth1), i);
		
	}
	
	public void getDataForQuickBookReport(Context ctx) throws Exception
	{
		Object listReport = null;
		if("Acc".equals(ctx.get("ProjectType").toString())){
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
			 listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateQuickBookReportAcc_p",ctx);
		} else if("Lwy".equals(ctx.get("ProjectType").toString())){
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
			listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.CreateQuickBookReportLW_p",ctx);
		}		
		
		ctx.put(Constants.REPORT_LIST, listReport);		
		
	}
	
	@SuppressWarnings("rawtypes")
	public StringBuffer createQBReportData(Context ctx) throws Exception {
		
		Object attestationObj = ctx.get(Constants.REPORT_LIST);
		if(attestationObj == null)
			return null;
		
		List macroMainList = createMacroData(ctx, (List) attestationObj);
		
		StringBuffer qbMacroData = new StringBuffer();
		for (int i = 0; i < macroMainList.size(); i++) {
			List innerList = (List) macroMainList.get(i);
			for(int j = 0; j < innerList.size(); j++){
				if(j<(innerList.size()-1)){
					qbMacroData.append(innerList.get(j) + "" + "\t");
				} else {
					qbMacroData.append(innerList.get(j) + "");
				}
			}
			qbMacroData.append("\n");
		}
		return qbMacroData;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List createMacroData(Context ctx, List attestationList) throws Exception{
		
		List quickBookRepsList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementreportgenerationgetQuickBookRepsLKU", ctx);
		
		List macroMainList = new ArrayList();
		List innerList = new ArrayList();
		innerList.add("!TRNS");innerList.add("TRNSTYPE");innerList.add("DATE");innerList.add("ACCNT");innerList.add("NAME");
		innerList.add("CLASS");innerList.add("AMOUNT");innerList.add("DOCNUM");innerList.add("MEMO");innerList.add("DUEDATE");
		innerList.add("SHIPDATE");innerList.add("REP");innerList.add("SHIPVIA");
		macroMainList.add(innerList);
		innerList = new ArrayList();
		innerList.add("!SPL");innerList.add("TRNSTYPE");innerList.add("DATE");innerList.add("ACCNT");innerList.add("NAME");
		innerList.add("CLASS");innerList.add("AMOUNT");innerList.add("DOCNUM");innerList.add("MEMO");innerList.add("QNTY");
		innerList.add("PRICE");innerList.add("INVITEM");
		macroMainList.add(innerList);
		innerList = new ArrayList();
		innerList.add("!ENDTRNS");
		macroMainList.add(innerList);
		
		for (int i = 0; i < attestationList.size(); i++) {
			Map dataMap = (HashMap) attestationList.get(i);
			String issuedDate = dataMap.get(Constants.IssuedDate).toString();
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(dataMap.get(Constants.PolicyEffectiveDate).toString()));
			int month = (cal.get(Calendar.MONTH) == 11 ? 2 : (cal.get(Calendar.MONTH) == 10) ? 1 : cal.get(Calendar.MONTH) + 3);
			String dueDate = month + "/" + 15 + "/" + year;
			double invoicedPremium = Double.parseDouble(dataMap.get(Constants.InvoicedPremium).toString());
			double totalPolicyTaxAmount = Double.parseDouble(dataMap.get(Constants.TotalPolicyTaxAmount).toString());
			double totalPremium = Double.parseDouble(dataMap.get(Constants.TotalPremium).toString());
			double commission = totalPremium * .21 ;
			double netPremium = totalPremium - commission;
			
			if(dataMap.get(Constants.ProducerCode) == null || "".equalsIgnoreCase(dataMap.get(Constants.ProducerCode).toString())){
				if(totalPolicyTaxAmount > 0){
					setMacroDataNotSubproducerWithPremium(ctx, innerList, macroMainList, dataMap, null, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium);
				} else {
					setMacroDataNotSubproducerNotPremium(ctx, innerList, macroMainList, dataMap, null, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium);
				}
			} else {
				Map spRepsMap = null;
				for (int repsCount = 0; repsCount < quickBookRepsList.size(); repsCount++) {
					spRepsMap = (HashMap) quickBookRepsList.get(repsCount);
					if(spRepsMap.get(Constants.ProducerCode).toString().equals(dataMap.get(Constants.ProducerCode).toString())){
						break;
					}
				}
				if(totalPolicyTaxAmount > 0){
					setMacroDataWithSubproducerWithPremium(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium);
				} else {
					setMacroDataWithSubproducerNotPremium(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium);
				}
			}
		}
		return macroMainList;
	}
	
	@SuppressWarnings("rawtypes")
	public void setMacroDataNotSubproducerNotPremium(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium){
		
		setCommunInvoiceData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerNotPremium");
		
		setEndTrans(innerList, macroMainList);
		
		setCommunBillData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerNotPremium");
		
		setEndTrans(innerList, macroMainList);
	}
	
	@SuppressWarnings("rawtypes")
	public void setMacroDataNotSubproducerWithPremium(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium){
		
		setCommunInvoiceData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerWithPremium");

		setCommunSPLInvoiceData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerWithPremium");
		
		setEndTrans(innerList, macroMainList);
		
		setCommunBillData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerWithPremium");
		
		setCommunSPLBillData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerWithPremium");
		
		setEndTrans(innerList, macroMainList);
	}
	
	@SuppressWarnings("rawtypes")
	public void setMacroDataWithSubproducerNotPremium(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium){
		
		setCommunInvoiceData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerNotPremium");
		
		setCommunSPLInvoiceSPData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerNotPremium");
		
		setEndTrans(innerList, macroMainList);
		
		setCommunBillData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerNotPremium");
		
		setEndTrans(innerList, macroMainList);
	}
	
	@SuppressWarnings("rawtypes")
	public void setMacroDataWithSubproducerWithPremium(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium){
		
		setCommunInvoiceData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerWithPremium");
		
		setCommunSPLInvoiceData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerWithPremium");
		
		setCommunSPLInvoiceSPData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerWithPremium");
		
		setEndTrans(innerList, macroMainList);
		
		setCommunBillData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataWithSubproducerWithPremium");
		
		setCommunSPLBillData(ctx, innerList, macroMainList, dataMap, spRepsMap, issuedDate, dueDate, totalPremium, commission, netPremium, totalPolicyTaxAmount, invoicedPremium, "setMacroDataNotSubproducerWithPremium");
		
		setEndTrans(innerList, macroMainList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setEndTrans(List innerList, List macroMainList){
		innerList = new ArrayList();
		innerList.add("ENDTRNS");
		macroMainList.add(innerList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPolicyStatus(Context ctx, List innerList, Map dataMap){

		if("Lwy".equals(ctx.get("ProjectType").toString())){
			if("NB".equalsIgnoreCase(dataMap.get(Constants.PolicyStatus).toString())) {
				innerList.add("Lawyers");
			} else {
				innerList.add("Lawyers:Renewal");
			}
		} else {
			if("NB".equalsIgnoreCase(dataMap.get(Constants.PolicyStatus).toString())) {
				innerList.add("Accountants");
			} else {
				innerList.add("Accountants:Renewal");
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPolicyStatusSP(Context ctx, List innerList, Map dataMap, Map spRepsMap, double totalPremium, double invoicedPremium){

		if("Lwy".equals(ctx.get("ProjectType").toString())){
			if("NB".equalsIgnoreCase(dataMap.get(Constants.PolicyStatus).toString())) {
				double lawyersNBRate = Double.parseDouble(spRepsMap.get(Constants.QBLawyersNBRate).toString());
				innerList.add("Lawyers");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(invoicedPremium - (totalPremium * (lawyersNBRate/100))));
			} else {
				double lawyersRenewalRate = Double.parseDouble(spRepsMap.get(Constants.QBLawyersRenewalRate).toString());
				innerList.add("Lawyers:Renewal");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(invoicedPremium - (totalPremium * (lawyersRenewalRate/100))));
			}
		} else {
			if("NB".equalsIgnoreCase(dataMap.get(Constants.PolicyStatus).toString())) {
				double accountantsNBRate = Double.parseDouble(spRepsMap.get(Constants.QBAccountantsNBRate).toString());
				innerList.add("Accountants");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(invoicedPremium - (totalPremium * (accountantsNBRate/100))));
			} else {
				double accountantsRenewalRate = Double.parseDouble(spRepsMap.get(Constants.QBAccountantsRenewalRate).toString());
				innerList.add("Accountants:Renewal");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(invoicedPremium - (totalPremium * (accountantsRenewalRate/100))));
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPolicyStatusSPC(Context ctx, List innerList, Map dataMap, Map spRepsMap, double totalPremium, double invoicedPremium){
		
		if("Lwy".equals(ctx.get("ProjectType").toString())){
			if("NB".equalsIgnoreCase(dataMap.get(Constants.PolicyStatus).toString())) {
				double lawyersNBRate = Double.parseDouble(spRepsMap.get(Constants.QBLawyersNBRate).toString());
				innerList.add("Lawyers");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(totalPremium * (lawyersNBRate/100)));
				innerList.add("");
				innerList.add(dataMap.get(Constants.PolicyNumber));
				innerList.add((lawyersNBRate/100) * -1);
			} else {
				double lawyersRenewalRate = Double.parseDouble(spRepsMap.get(Constants.QBLawyersRenewalRate).toString());
				innerList.add("Lawyers:Renewal");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(totalPremium * (lawyersRenewalRate/100)));
				innerList.add("");
				innerList.add(dataMap.get(Constants.PolicyNumber));
				innerList.add((lawyersRenewalRate/100) * -1);
			}
		} else {
			if("NB".equalsIgnoreCase(dataMap.get(Constants.PolicyStatus).toString())) {
				double accountantsNBRate = Double.parseDouble(spRepsMap.get(Constants.QBAccountantsNBRate).toString());
				innerList.add("Accountants");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(totalPremium * (accountantsNBRate/100)));
				innerList.add("");
				innerList.add(dataMap.get(Constants.PolicyNumber));
				innerList.add((accountantsNBRate/100) * -1);
			} else {
				double accountantsRenewalRate = Double.parseDouble(spRepsMap.get(Constants.QBAccountantsRenewalRate).toString());
				innerList.add("Accountants:Renewal");
				innerList.add(AccountantUtilities.getTwoDecimalPlaces(totalPremium * (accountantsRenewalRate/100)));
				innerList.add("");
				innerList.add(dataMap.get(Constants.PolicyNumber));
				innerList.add((accountantsRenewalRate/100) * -1);
			}
		}
	}
	
//	INVOICE
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCommunInvoiceData(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium, String type){
		innerList = new ArrayList();
		innerList.add("TRNS");
		innerList.add("INVOICE");
		innerList.add(issuedDate);
		innerList.add("Premiums Receivable");
		innerList.add("\"" + dataMap.get(Constants.AccountName).toString().trim() + "\"");
		if("setMacroDataNotSubproducerNotPremium".equals(type) || "setMacroDataNotSubproducerWithPremium".equals(type)){
			setPolicyStatus(ctx, innerList, dataMap);
			innerList.add(invoicedPremium);
		} else {
			setPolicyStatusSP(ctx, innerList, dataMap, spRepsMap, totalPremium, invoicedPremium);
		}
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add("");
		innerList.add(dueDate);
		innerList.add(dataMap.get(Constants.PolicyEffectiveDate));
		if("setMacroDataWithSubproducerWithPremium".equals(type) || "setMacroDataWithSubproducerNotPremium".equals(type)){
			String QBSubproducer = (spRepsMap.get(Constants.QBSubproducer).toString().trim().length() < 40) ? spRepsMap.get(Constants.QBSubproducer).toString().trim() : spRepsMap.get(Constants.QBSubproducer).toString().trim().substring(0, 40);
			innerList.add("\"" + QBSubproducer + "\"");
		}
		macroMainList.add(innerList);
		
		innerList = new ArrayList();
		innerList.add("SPL");
		innerList.add("INVOICE");
		innerList.add(issuedDate);
		innerList.add("Premiums Clearing Account");
		innerList.add("");
		setPolicyStatus(ctx, innerList, dataMap);
		innerList.add((AccountantUtilities.getTwoDecimalPlaces(netPremium)) * (-1));
		innerList.add("");
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add(-0.79);
		innerList.add(totalPremium);
		innerList.add("ISMIE Premium");
		macroMainList.add(innerList);
		
		innerList = new ArrayList();
		innerList.add("SPL");
		innerList.add("INVOICE");
		innerList.add(issuedDate);
		innerList.add("Unearned commission income");
		innerList.add("");
		setPolicyStatus(ctx, innerList, dataMap);
		innerList.add((AccountantUtilities.getTwoDecimalPlaces(commission)) * (-1));
		innerList.add("");
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add(-0.21);
		innerList.add(totalPremium);
		innerList.add("Commission");
		macroMainList.add(innerList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCommunSPLInvoiceData(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium, String type){
		innerList = new ArrayList();
		innerList.add("SPL");
		innerList.add("INVOICE");
		innerList.add(issuedDate);
		innerList.add("Premiums Clearing Account");
		innerList.add("");
		setPolicyStatus(ctx, innerList, dataMap);
		innerList.add((AccountantUtilities.getTwoDecimalPlaces(totalPolicyTaxAmount)) * (-1));
		innerList.add("");
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add(-1);
		innerList.add(totalPolicyTaxAmount);
		innerList.add("ISMIE Premium");
		macroMainList.add(innerList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCommunSPLInvoiceSPData(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium, String type){
		innerList = new ArrayList();
		innerList.add("SPL");
		innerList.add("INVOICE");
		innerList.add(issuedDate);
		innerList.add("Unearned commission income");
		innerList.add("");
		setPolicyStatusSPC(ctx, innerList, dataMap, spRepsMap, totalPremium, invoicedPremium);
		innerList.add(totalPremium * -1);
		innerList.add("Subproducer Commission");
		macroMainList.add(innerList);
	}
	
//	BILL
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCommunBillData(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium, String type){
		innerList = new ArrayList();
		innerList.add("TRNS");
		innerList.add("BILL");
		innerList.add(issuedDate);
		innerList.add("Accounts Payable - Premiums");
		innerList.add("ISMIE");
		setPolicyStatus(ctx, innerList, dataMap);
		if("setMacroDataNotSubproducerNotPremium".equals(type)){
			innerList.add((AccountantUtilities.getTwoDecimalPlaces(netPremium)) * (-1));
		} else {
			innerList.add(AccountantUtilities.getTwoDecimalPlaces((netPremium * -1) + (totalPolicyTaxAmount * -1)));
		}
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add("");
		innerList.add(dueDate);
		macroMainList.add(innerList);
		
		innerList = new ArrayList();
		innerList.add("SPL");
		innerList.add("BILL");
		innerList.add(issuedDate);
		innerList.add("Premiums Clearing Account");
		innerList.add("\"" + dataMap.get(Constants.AccountName).toString().trim() + "\"");
		setPolicyStatus(ctx, innerList, dataMap);
		innerList.add(netPremium);
		innerList.add("");
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add(0.79);
		innerList.add(totalPremium);
		innerList.add("ISMIE Premium");
		macroMainList.add(innerList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCommunSPLBillData(Context ctx, List innerList, List macroMainList, Map dataMap, Map spRepsMap, String issuedDate, String dueDate, double totalPremium, double commission, double netPremium, double totalPolicyTaxAmount, double invoicedPremium, String type){
		innerList = new ArrayList();
		innerList.add("SPL");
		innerList.add("BILL");
		innerList.add(issuedDate);
		innerList.add("Premiums Clearing Account");
		innerList.add("\"" + dataMap.get(Constants.AccountName).toString().trim() + "\"");
		setPolicyStatus(ctx, innerList, dataMap);
		innerList.add(totalPolicyTaxAmount);
		innerList.add("");
		innerList.add(dataMap.get(Constants.PolicyNumber));
		innerList.add(1);
		innerList.add(totalPolicyTaxAmount);
		innerList.add("ISMIE Premium");
		macroMainList.add(innerList);
	}
	public void populateReportDataDynamic(Context ctx, Workbook wb, String sheetsReport) throws Exception
	{
		Sheet sheet = wb.getSheet(sheetsReport);
        CellStyle string= wb.createCellStyle();
        string.setDataFormat((short) 38);
        CellStyle numeric= wb.createCellStyle();
        numeric.setDataFormat((short) 38);
        
        populateReportDataList(ctx, sheet);
	}
	@SuppressWarnings("rawtypes")
	public void populateReportDataList(Context ctx, Sheet sheet) 
	{
		Object dataObject=null;
		List dataList = null;
		Map excelFieldMapping=null;
		try
		{
			 dataObject = ctx.get(Constants.REPORT_LIST);
			if(dataObject == null)
				return;
				if (dataObject != null)
			dataList = (ArrayList) dataObject;
			excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
			RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.PolicyNumber);
			for (int i = 0; i < dataList.size(); i++) 
			{
				Map map = (HashMap) dataList.get(i);
				setCellValue(ctx, i, map, excelFieldMapping, sheet);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void getDataForMcGowanReport(Context ctx,String sheet,String template) throws Exception
	{
		Object listReport = null;
				
			if("Acc".equals(ctx.get("ProjectType").toString())){
				if(template.equals("ManualEntryClientLoadTemplate.xls"))
				{
					if(sheet.equals("Business Clients"))
					{
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
						listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.FetchDataForBusinessClients_ClientLoad_ACC",ctx);
					}
				}
				if(template.equals("ManualEntryPolicyLoadTemplate.xls"))
				{
					if(sheet.equals("Monoline Policies"))
					{
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
						listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.FetchDataForMonoLinePolicies_PolicyLoad_ACC",ctx);
					}
				}
				if(template.equals("TransactionTemplate.xls"))
				{
					if(sheet.equals("Prebills"))
					{
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
						listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.FetchDataForTransaction_ACC",ctx);
					}
				}
			}
			else if("Lwy".equals(ctx.get("ProjectType").toString())){
				if(template.equals("ManualEntryClientLoadTemplate.xls"))
				{
					if(sheet.equals("Business Clients"))
					{
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
						listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.FetchDataForBusinessClients_ClientLoad_LW",ctx);
					}
				}
				if(template.equals("ManualEntryPolicyLoadTemplate.xls"))
				{
					if(sheet.equals("Monoline Policies"))
					{
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
						listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.FetchDataForMonoLinePolicies_PolicyLoad_LW",ctx);
					}
				}
				if(template.equals("TransactionTemplate.xls"))
				{
					if(sheet.equals("Prebills"))
					{
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "ReportType,SearchType,FromDate,ToDate,CompanyKey");
						listReport=SqlResources.getSqlMapProcessor(ctx).select("Firm.FetchDataForTransaction_LW",ctx);
					}
				}
		}
		
		ctx.put(Constants.REPORT_LIST, listReport);		
		
	}
}
