package com.manage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.servlet.ServletContextURIResolver;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.manage.framework.ProcessAction;
import com.manage.managebusinessrules.rules.RuleImpl;
import com.manage.managebusinessrules.rules.RulesResources;
import com.manage.managecomponent.components.ComponentImpl;
import com.manage.managecomponent.components.ComponentResources;
import com.manage.managecomponent.components.DocumentImpl;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.manage.managemetadata.messages.MessageResources;
import com.manage.managemetadata.metadata.DisplayfieldImpl;
import com.manage.managemetadata.metadata.DropdownImpl;
import com.manage.managemetadata.metadata.FieldImpl;
import com.manage.managemetadata.metadata.MetaDataResources;
import com.manage.managemetadata.metadata.MetaobjectImpl;
import com.manage.managemetadata.metadata.PropertyImpl;
import com.manage.managemetadata.metadata.ValuefieldImpl;
import com.manage.managemetadata.security.SecurityResources;
import com.manage.managemetadata.util.exception.ValidationException;
import com.manage.managereporting.graphs.GraphResources;
import com.manage.managereporting.graphs.GraphdefImpl;
import com.manage.managereporting.graphs.GraphworkflowImpl;
import com.manage.managereporting.graphs.ParameteroperationImpl;
import com.manage.managereporting.reports.ColumnImpl;
import com.manage.managereporting.reports.ColumnsetImpl;
import com.manage.managereporting.reports.ReportResources;
import com.manage.managereporting.reports.ReportdefImpl;
import com.manage.managereporting.reports.ReportworkflowImpl;
import com.manage.managereporting.reports.StoredprocedureoperationImpl;
import com.manage.parser.ParseUtil;
import com.manage.parser.SecurityParser;
import com.manage.process.reporting.ReportDataBlock;
import com.manage.process.reporting.ReportFilterBlock;
import com.manage.process.reporting.ReportFreeformBlock;
import com.ormapping.ibatis.SqlResources;
import com.processor.RequestProcessor;
import com.util.CacheManager;
import com.util.Context;
import com.util.DateUtils;
import com.util.HTML2XML;
import com.util.HTMLUtils;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.IOUtils;
import com.util.ObjectCloner;
import com.util.StringUtils;
import com.util.SystemProperties;
import com.util.TripleDESEncryptionDecryption;

import net.sourceforge.jtds.jdbc.ClobImpl;

public class ReportUtils {
	private static Logger logger = Logger.getLogger(ReportUtils.class);

	public static void openManageReport(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		cleanFilterFields(ctx, reportDefImpl, request);
		ctx.remove("old_FieldList");
		request.getSession().removeAttribute("old_FieldList");
		addFieldToPage(ctx, reportDefImpl, request);
		executeDefaultView(ctx, reportDefImpl);
		ctx.put("groupby", getGroupByColumns(ctx, reportDefImpl));
		ctx.put("outputcolumns", getOutputColumns(ctx, reportDefImpl));
		ctx.put("popupsearchcolumns", getPopupSearchFields(reportDefImpl));
	}

	public static String getReportFilterPage(Context ctx,
			HttpServletRequest request, HttpServletResponse response,
			String inet_method, String reportID, ReportdefImpl reportDefImpl)
			throws Exception {
		String pageContent = "";
		if (StringUtils.isBlank(inet_method)) {
			return pageContent;
		} else if (!inet_method.equals("INET_REFRESH_MANAGE_REPORT")
				&& !inet_method.equals("INET_SHOW_ACCORDIAN_REPORT")) {
			if (!inet_method.equals("INET_SHOW_REPORT")
					&& !inet_method.equals("INET_SHOW_REPORT_SAVEDSEARCH")
					|| ctx.get("inet_form_dirty") != null
					&& ctx.get("inet_form_dirty").equals("Y")) {
				if (inet_method.equals("INET_SHOW_SAVEDSEARCH_LIST")) {
					ctx.put("TEMPLATE_NAME",
							"managereportsavedsearchdatatemplate");
					pageContent = parseReportHTML(ctx,
							"managereportsavedsearchdatatemplate",
							"reports/managereportsavedsearchdatatemplate.html",
							request, reportDefImpl);
				} else if (!inet_method.equals("INET_SUBMIT_EXPORT_POPUP")
						&& !inet_method
								.equals("INET_SUBMIT_ACCORDIAN_EXPORT_POPUP")) {
					if (inet_method.equals("INET_LIST_PAGINATION")) {
						ctx.put("TEMPLATE_NAME", ctx.get("TEMPLATE_NAME"));
						String templateName = ctx.get("TEMPLATE_NAME") != null
								? ctx.get("TEMPLATE_NAME").toString()
								: "";
						if (templateName.equals("managereporttemplate")) {
							pageContent = parseReportHTML(ctx, templateName,
									"reports/managereporttemplate.html",
									request, reportDefImpl);
						} else if (templateName
								.equals("managereportdatatemplate")) {
							pageContent = parseReportHTML(ctx, templateName,
									"reports/managereportdatatemplate.html",
									request, reportDefImpl);
						}
					} else if (inet_method.equals("INET_DOWNLOAD_BATCH_REPORT")) {
						ctx.put("TEMPLATE_NAME", "managereporttemplate");
						pageContent = parseReportHTML(ctx,
								"managereporttemplate",
								"reports/managereporttemplate.html", request,
								reportDefImpl);
					} else if (inet_method.equals("INET_SHOW_DRILDOWN_REPORT")) {
						ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
						pageContent = parseReportHTML(ctx,
								"managereportdatatemplate",
								"reports/managereportdatatemplate.html",
								request, reportDefImpl);
					} else if (inet_method
							.equals("INET_SHOW_DRILLDOWN_REPORT_BACK")) {
						ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
						pageContent = parseReportHTML(ctx,
								"managereportdatatemplate",
								"reports/managereportdatatemplate.html",
								request, reportDefImpl);
					} else if (inet_method.equals("INET_SHOW_GRAPH_POPUP")) {
						ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
						pageContent = parseReportHTML(ctx,
								"managereportdatatemplate",
								"reports/managereportdatatemplate.html",
								request, reportDefImpl);
					} else if (inet_method.equals("INET_CLOSE_GRAPH_POPUP")) {
						ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
						pageContent = parseReportHTML(ctx,
								"managereportdatatemplate",
								"reports/managereportdatatemplate.html",
								request, reportDefImpl);
					} else {
						ctx.put("TEMPLATE_NAME", "managereporttemplate");
						pageContent = parseReportHTML(ctx,
								"managereporttemplate",
								"reports/managereporttemplate.html", request,
								reportDefImpl);
					}
				} else {
					ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
					pageContent = parseReportHTML(ctx,
							"managereportdatatemplate",
							"reports/managereportdatatemplate.html", request,
							reportDefImpl);
				}
			} else if (ctx.get("outputtype") != null
					&& (ctx.get("outputtype") == null || !ctx.get("outputtype")
							.equals("S"))) {
				openManageReport(ctx, request, reportDefImpl);
				ctx.put("TEMPLATE_NAME", "managereporttemplate");
				pageContent = parseReportHTML(ctx, "managereporttemplate",
						"reports/managereporttemplate.html", request,
						reportDefImpl);
			} else {
				ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
				pageContent = parseReportHTML(ctx, "managereportdatatemplate",
						"reports/managereportdatatemplate.html", request,
						reportDefImpl);
			}

			return pageContent;
		} else {
			return pageContent;
		}
	}

	public static void addReportFilter(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		ctx.put("issearch", "N");
		if (ctx.get("FieldList") != null && !"".equals(ctx.get("FieldList"))) {
			String selectedField = ctx.get("FieldList").toString();
			ArrayList old_field_list = null;
			if (ctx.get("old_FieldList") != null) {
				old_field_list = (ArrayList) ctx.get("old_FieldList");
			} else {
				old_field_list = new ArrayList();
			}

			HashMap map = new HashMap();
			map.put("selected_filter_field_name", selectedField);
			ColumnImpl column = reportDefImpl.getFirstColumnset() != null
					? reportDefImpl.getFirstColumnset()
							.getColumn(selectedField) : null;
			String display_value = column != null ? column.getColumnlabel() : selectedField;
			if (column != null
					&& (column.getColumnlabel() == null || "".equals(column
							.getColumnlabel()))
					&& column.getMetaproperty() != null
					&& !"".equals(column.getMetaproperty().trim())) {
				FieldImpl fieldImpl = MetaDataResources.getInstance(ctx)
						.getField(column.getMetaproperty());
				if (fieldImpl != null && fieldImpl.getDisplayname() != null) {
					display_value = fieldImpl.getDisplayname();
				}
			}

			map.put("selected_filter_field_display_name", display_value);
			old_field_list.add(map);
			ctx.put("old_FieldList", old_field_list);
			request.getSession().setAttribute("old_FieldList", old_field_list);
		}

	}

	public static void removeFilterField(Context ctx, HttpServletRequest request)
			throws Exception {
		String currentFieldName = ctx.get("currentFieldName") == null
				? ""
				: ctx.get("currentFieldName").toString();
		ArrayList old_field_list = (ArrayList) ctx.get("old_FieldList");
		ArrayList old_field_list_1 = new ArrayList();
		Iterator arg5 = old_field_list.iterator();

		while (arg5.hasNext()) {
			Map record = (Map) arg5.next();
			if (!record.get("selected_filter_field_name").toString()
					.equalsIgnoreCase(currentFieldName)) {
				old_field_list_1.add(record);
			}
		}

		ctx.put("old_FieldList", old_field_list_1);
		request.getSession().setAttribute("old_FieldList", old_field_list_1);
		request.getSession().removeAttribute(
				"to_filter_data_" + currentFieldName);
	}

	public static void showReportData(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		(new ReportUtils()).getDynaKeysValues(ctx, reportDefImpl,
				(ColumnImpl) null);
		ctx.put("PAGE_SIZE", Integer.valueOf(100));
		ctx.put("PAGE_NUMBER", Integer.valueOf(1));
		if (ctx.get("ISCOMINGFORDATAONLY") != null
				&& ctx.get("ISCOMINGFORDATAONLY").toString().equals("Y")) {
			generateSelectedFilterDataForDataOnly(ctx, reportDefImpl);
		}

		if (ctx.get("inet_skip_validation") == null
				|| !ctx.get("inet_skip_validation").toString().equals("Y")) {
			validatePage(ctx, reportDefImpl);
			validateWorkflow(ctx, reportDefImpl);
		}

		(new ReportUtils()).createPageError(ctx);
		if (ctx.get("inet_form_dirty") != null
				&& ctx.get("inet_form_dirty").equals("Y")) {
			executeDefaultView(ctx, reportDefImpl);
		} else {
			updateFieldListWithData(ctx, request);
			populateParameter(ctx, reportDefImpl, request);
			if (ctx.get("outputtype") != null
					&& !ctx.get("outputtype").equals("S")) {
				generateRequestXml(ctx, reportDefImpl, request, false);
				submitBatch(ctx, request, reportDefImpl);
				cleanFilterFields(ctx, reportDefImpl, request);
				ctx.remove("search_id");
				ctx.remove("report_id");
				ctx.remove("last_updated_by");
				ctx.remove("last_updated_ts");
				ctx.remove("search_name");
				ctx.remove("search_xml");
				ctx.remove("isglobal");
				ctx.remove("created_by");
				return;
			}

			List workflowList = reportDefImpl.getReportworkflowList();
			if (workflowList != null) {
				for (int i = 0; i < workflowList.size(); ++i) {
					ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
							.get(i);
					if (reportWorkflowImpl.getIsexecuteonview() == null
							|| !reportWorkflowImpl.getIsexecuteonview().equals(
									"Y")) {
						String eval = reportWorkflowImpl.getEval();
						boolean isExecuteWorkflow = true;
						if (!StringUtils.isBlank(eval)) {
							try {
								isExecuteWorkflow = RulesResources
										.getInstance(ctx).findRule(eval)
										.evaluate(ctx, (String[]) null);
							} catch (Exception arg8) {
								logger.error(ctx, "Unable to execute rule : "
										+ eval + " for report : "
										+ reportDefImpl.getName(), arg8);
							}
						}

						if (isExecuteWorkflow) {
							if (reportWorkflowImpl.getCheckfornullparams() != null
									&& !reportWorkflowImpl
											.getCheckfornullparams().equals("")) {
								checkForNullParams(ctx,
										reportWorkflowImpl
												.getCheckfornullparams());
							}

							ctx.put("reportoutputtype",
									reportWorkflowImpl.getOutputtype());
							populateContextWithPopupSearchColumn(ctx,
									reportDefImpl);
							reportWorkflowImpl.executeWorkflow(ctx,
									reportWorkflowImpl.getOutputtype(),
									reportDefImpl.getName(),
									reportWorkflowImpl.getIsdynacolumns());
						}
					}
				}
			}

			generateRequestXml(ctx, reportDefImpl, request, false);
		}

	}

	public static void showFilterPopup(Context ctx, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.put("issearch", "Y");
		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
	}

	public static void showFilterData(Context ctx, ReportdefImpl reportDefImpl)
			throws Exception {
		if (ctx.get("currentFieldName") != null
				&& !ctx.get("currentFieldName").equals("")) {
			if (ctx.get("inet_page") != null
					&& ctx.get("inet_page").toString()
							.equals("MANAGE_REPORTING")) {
				getSavedSearchList(ctx, reportDefImpl);
				getPoneBranchReportsList(ctx, reportDefImpl);
			}

			ColumnImpl column = null;
			if (reportDefImpl.getIsshowmultiplegrid()) {
				if (reportDefImpl.getOrderedColumnSetList() != null
						&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
					List tokens = reportDefImpl.getOrderedColumnSetList();

					for (int metaobject = 0; metaobject < tokens.size(); ++metaobject) {
						ColumnsetImpl property = (ColumnsetImpl) tokens
								.get(metaobject);
						List moImpl = property.getOrderedColumnsList();

						for (int prop = 0; prop < moImpl.size(); ++prop) {
							ColumnImpl dropdownImpl = (ColumnImpl) moImpl
									.get(prop);
							if (dropdownImpl.getIsreportfilter()
									&& dropdownImpl.getName().equals(
											ctx.get("currentFieldName")
													.toString())) {
								column = dropdownImpl;
								break;
							}
						}

						if (column != null) {
							break;
						}
					}
				}
			} else {
				column = reportDefImpl.getFirstColumnset() != null
						? reportDefImpl.getFirstColumnset().getColumn(
								ctx.get("currentFieldName").toString())
						: null;
			}

			if (column != null) {
				(new ReportUtils()).getDynaKeysValues(ctx, reportDefImpl,
						column);
				StringTokenizer arg22 = new StringTokenizer(
						column.getMetaproperty(), ".", false);
				if (arg22.countTokens() == 2) {
					String arg23 = arg22.nextToken();
					String arg24 = arg22.nextToken();
					MetaobjectImpl arg25 = MetaDataResources.getInstance(ctx)
							.getMetaobject(arg23);
					PropertyImpl arg26 = arg25.getFirstPropertyversion()
							.getProperty(arg24);
					DropdownImpl arg27 = (new ReportFilterBlock(reportDefImpl))
							.getDropdown(ctx, arg26);
					if (arg27 != null) {
						DisplayfieldImpl disFld = arg27.getFirstDisplayfield();
						ValuefieldImpl valFld = arg27.getFirstValuefield();
						List optionList = arg27.getRange(ctx);
						ArrayList newOptionsDataList = new ArrayList();
						if (optionList != null && optionList.size() > 0) {
							for (int replayid = 0; replayid < optionList.size(); ++replayid) {
								Map xml = (Map) optionList.get(replayid);
								HashMap list = new HashMap();
								list.put(String.valueOf(xml.get(disFld
										.getField())), String.valueOf(xml
										.get(valFld.getField())));
								newOptionsDataList.add(list);
							}
						}

						String arg28 = ctx.get("datadisplayid").toString();
						ctx.put(arg28, newOptionsDataList);
						if (arg28 != null && arg28.startsWith("ajaxxml")) {
							String arg29 = "";
							if (ctx.get(arg28) != null
									&& ctx.get(arg28) instanceof List) {
								List arg30 = (List) ctx.get(arg28);
								Element xmlroot = new Element("response");
								int i = 0;

								while (true) {
									if (i >= arg30.size()) {
										arg29 = (new XMLOutputter(
												Format.getPrettyFormat()))
												.outputString(xmlroot);
										break;
									}

									Element row = new Element(arg28);
									Map rowMap = (Map) arg30.get(i);
									Set entrySet = rowMap.entrySet();
									Iterator itr = entrySet.iterator();

									while (itr.hasNext()) {
										Entry entry = (Entry) itr.next();
										row.setText((String) entry.getKey()
												+ "="
												+ (String) entry.getValue());
									}

									xmlroot.addContent(row);
									++i;
								}
							}

							ctx.put("ajaxxml", arg29);
						}
					}
				}

			}
		}
	}

	public static void closeFilterPopup(Context ctx, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.put("issearch", (Object) null);
		ctx.put("popupsearch_data", (Object) null);
		ctx.put("from_select_filter_data", (Object) null);
		ctx.put("to_select_filter_data", (Object) null);
		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
	}

	public static void submitFilterPopup(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.put("issearch", (Object) null);
		ctx.put("popupsearch_data", (Object) null);
		ctx.put("from_select_filter_data", (Object) null);
		ctx.put("to_select_filter_data", (Object) null);
		if (ctx.get("to_filter_data") != null
				&& !ctx.get("to_filter_data").equals("")
				&& ctx.get("currentFieldName") != null) {
			ArrayList optionsList = new ArrayList();
			String[] tokens = ctx.get("to_filter_data").toString()
					.split("#Hash;#");

			for (int i = 0; i < tokens.length; ++i) {
				String token = tokens[i];
				if (token != null && token.contains("=")) {
					String key = token.substring(0, token.indexOf("="));
					String value = token.substring(token.indexOf("=") + 1,
							token.length());
					HashMap map = new HashMap();
					map.put(key, value);
					optionsList.add(map);
				}
			}

			request.getSession().setAttribute(
					"to_filter_data_" + ctx.get("currentFieldName"),
					optionsList);
		}

		ctx.put("to_filter_data", (Object) null);
		if (ctx.get("inet_page") == null
				|| !ctx.get("inet_page").toString().equals("MANAGE_GRAPH")) {
			getSavedSearchList(ctx, reportDefImpl);
			getPoneBranchReportsList(ctx, reportDefImpl);
		}
	}

	public static void saveSearch(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		if (ctx.get("search_name") == null || ctx.get("search_name").equals("")) {
			ctx.put("report_page_error", "Please fill search name");
			ctx.put("inet_form_dirty", "Y");
		}

		if (ctx.get("report_page_error") == null
				|| ctx.get("report_page_error").equals("")) {
			ctx.put("report_id", reportDefImpl.getName());
			ctx.put("last_updated_by", ctx.get("user_id"));
			ctx.put("last_updated_ts", DateUtils.getTodaysDate());
			ctx.put("created_by", ctx.get("user_id"));
			ctx.put("isglobal",
					ctx.get("isglobal") != null
							&& !ctx.get("isglobal").toString().equals("") ? ctx
							.get("isglobal").toString() : "N");
			ctx.put("search_type",
					ctx.get("search_type") == null ? Integer.valueOf(1) : ctx
							.get("search_type"));
			generateRequestXml(ctx, reportDefImpl, request, false);
			/*SqlResources.getSqlMapProcessor(ctx).insert(
					"reportsavedsearch.insert", ctx);*/
			ctx.put("search_report_id", ctx.get("report_id"));
			SqlResources.getSqlMapProcessor(ctx).update("Firm.InsertupdateReportFrameworkSavedSearch_p",ctx);
			cleanFilterFields(ctx, reportDefImpl, request);
			ctx.remove("search_id");
			ctx.remove("isglobal");
			ctx.remove("created_by");
			ctx.put("pageMsg",
					MessageResources.getInstance(ctx)
							.getMessage("searchSavedSuccessFully").getMessage());
		}

		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
		ctx.remove("report_id");
		ctx.remove("last_updated_by");
		ctx.remove("last_updated_ts");
		ctx.remove("search_name");
		ctx.remove("search_xml");
		updateFieldListWithData(ctx, request);
		populateParameter(ctx, reportDefImpl, request);
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() != null
						&& reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg8) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg8);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

		if (ctx.get("report_page_error") == null
				|| ctx.get("report_page_error").equals("")) {
			ctx.put("groupby", getGroupByColumns(ctx, reportDefImpl));
			ctx.put("outputcolumns", getOutputColumns(ctx, reportDefImpl));
			ctx.put("popupsearchcolumns", getPopupSearchFields(reportDefImpl));
		}

	}

	public static void updateSearch(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		if (ctx.get("search_name") == null || ctx.get("search_name").equals("")) {
			ctx.put("report_page_error", "Please fill search name");
			ctx.put("inet_form_dirty", "Y");
		}

		if (ctx.get("report_page_error") == null
				|| ctx.get("report_page_error").equals("")) {
			ctx.put("report_id", reportDefImpl.getName());
			ctx.put("last_updated_by", ctx.get("user_id"));
			ctx.put("last_updated_ts", DateUtils.getTodaysDate());
			//ctx.put("isglobal",	ctx.get("isglobal") != null	&& !ctx.get("isglobal").toString().equals("") ? ctx	.get("isglobal").toString() : "N");
			//	ctx.put("created_by",ctx.get("created_by") != null	&& !ctx.get("created_by").toString().equals("")	? ctx.get("created_by").toString(): ctx.get("user_id").toString());
			ctx.put("isglobal",ctx.get("isglobal")!=null && !ctx.get("isglobal").equals(HtmlConstants.EMPTY)?ctx.get("isglobal"):"Y");
			ctx.put("created_by",ctx.get("UserNo"));
			
			generateRequestXml(ctx, reportDefImpl, request, false);
			SqlResources.getSqlMapProcessor(ctx).update(
					"reportsavedsearch.update", ctx);
			cleanFilterFields(ctx, reportDefImpl, request);
			ctx.remove("search_id");
			ctx.remove("isglobal");
			ctx.remove("created_by");
			ctx.put("pageMsg",
					MessageResources.getInstance(ctx)
							.getMessage("searchUpdatedSuccessFully")
							.getMessage());
		}

		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
		ctx.remove("report_id");
		ctx.remove("last_updated_by");
		ctx.remove("last_updated_ts");
		ctx.remove("search_name");
		ctx.remove("search_xml");
		updateFieldListWithData(ctx, request);
		populateParameter(ctx, reportDefImpl, request);
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() != null
						&& reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg8) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg8);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

		if (ctx.get("report_page_error") == null
				|| ctx.get("report_page_error").equals("")) {
			ctx.put("groupby", getGroupByColumns(ctx, reportDefImpl));
			ctx.put("outputcolumns", getOutputColumns(ctx, reportDefImpl));
			ctx.put("popupsearchcolumns", getPopupSearchFields(reportDefImpl));
		}

	}

	public static void editSavedSearch(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		ctx.put("report_id", reportDefImpl.getName());
		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
		cleanFilterFields(ctx, reportDefImpl, request);
		Map map = (Map) SqlResources.getSqlMapProcessor(ctx).findByKey(
				"reportsavedsearch.findByKey", ctx);
		ctx.putAll(map);
		if (map != null && map.get("search_xml") != null) {
			populateContextWithSearchXml(ctx, map, reportDefImpl, request);
		}

		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() != null
						&& reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg9) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg9);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

	}

	public static void deleteSavedSearch(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		SqlResources.getSqlMapProcessor(ctx).delete("reportsavedsearch.delete",
				ctx);
		ctx.put("pageMsg",
				MessageResources.getInstance(ctx)
						.getMessage("searchDeletedSuccessFully").getMessage());
		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
		ctx.remove("report_id");
		ctx.remove("last_updated_by");
		ctx.remove("last_updated_ts");
		ctx.remove("search_name");
		ctx.remove("search_xml");
		ctx.remove("search_id");
		ctx.remove("isglobal");
		ctx.remove("created_by");
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() != null
						&& reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg8) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg8);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

		ctx.put("groupby", getGroupByColumns(ctx, reportDefImpl));
		ctx.put("outputcolumns", getOutputColumns(ctx, reportDefImpl));
		ctx.put("popupsearchcolumns", getPopupSearchFields(reportDefImpl));
	}

	public static void showSavedSearchList(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		List obj = SqlResources.getSqlMapProcessor(ctx).select(
				"reportsavedsearch.findAll", ctx);
		if (obj != null && obj instanceof List) {
			ctx.put("savedSearchList_list_1", (List) obj);
		}

	}

	public static void showReportSavedSearch(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"reportsavedsearch.findByKey", ctx);
		ReportworkflowImpl reportWorkflowImpl;
		if (obj != null && obj instanceof Map) {
			Map workflowList = (Map) obj;
			ctx.put("selected_filter_data", workflowList.get("search_xml_desc"));
			if (workflowList.get("search_xml") != null) {
				String i = workflowList.get("search_xml").toString();
				reportWorkflowImpl = null;

				try {
					SAXBuilder eval = new SAXBuilder();
					Document isExecuteWorkflow = eval
							.build(new StringReader(i));
					if (isExecuteWorkflow != null) {
						Element e = isExecuteWorkflow.getRootElement();
						Element requestDataElement = (Element) e.getContent(0);
						if (requestDataElement.getContent() != null
								&& requestDataElement.getContent().size() > 0) {
							List contentList = requestDataElement.getContent();

							for (int i1 = 0; i1 < contentList.size(); ++i1) {
								Element row = (Element) contentList.get(i1);
								if (row.getName() != null
										&& !row.getName().equals("")) {
									String value = row.getText();
									if (value != null
											&& value.indexOf("=") != -1) {
										String value1 = null;
										if (value.indexOf(",") != -1) {
											StringTokenizer tokens = new StringTokenizer(
													value, ",");

											while (tokens.hasMoreTokens()) {
												String token = tokens
														.nextToken();
												if (token.indexOf("=") != -1) {
													if (value1 == null) {
														value1 = token
																.substring(
																		token.indexOf("=") + 1,
																		token.length());
													} else {
														value1 = value1
																+ ","
																+ token.substring(
																		token.indexOf("=") + 1,
																		token.length());
													}
												}
											}
										} else if (value.indexOf("=") != -1) {
											value1 = value.substring(
													value.indexOf("=") + 1,
													value.length());
										}

										value = value1;
									}

									if (value != null
											&& !value.equals("")
											&& row.getName()
													.equals("SortOrder")) {
										value = value + ",";
									}

									ctx.put(row.getName(), value != null
											&& !value.equals("") ? value : null);
								}
							}
						}
					}
				} catch (Exception arg18) {
					logger.error(ctx, "Invalid search_xml found", arg18);
				}
			}
		}

		List arg19 = reportDefImpl.getReportworkflowList();
		if (arg19 != null) {
			for (int arg20 = 0; arg20 < arg19.size(); ++arg20) {
				reportWorkflowImpl = (ReportworkflowImpl) arg19.get(arg20);
				if (reportWorkflowImpl.getIsexecuteonview() == null
						|| !reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String arg21 = reportWorkflowImpl.getEval();
					boolean arg22 = true;
					if (!StringUtils.isBlank(arg21)) {
						try {
							arg22 = RulesResources.getInstance(ctx)
									.findRule(arg21)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg17) {
							logger.error(
									ctx,
									"Unable to execute rule : " + arg21
											+ " for report : "
											+ reportDefImpl.getName(), arg17);
						}
					}

					if (arg22) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

	}

	public static void editReport(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		cleanFilterFields(ctx, reportDefImpl, request);
		ctx.remove("old_FieldList");
		request.getSession().removeAttribute("old_FieldList");
		addFieldToPage(ctx, reportDefImpl, request);
		ctx.put("report_id", reportDefImpl.getName());
		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
		cleanFilterFields(ctx, reportDefImpl, request);
		Map map = (Map) SqlResources.getSqlMapProcessor(ctx).findByKey(
				"reportsavedsearch.findByKey", ctx);
		ctx.putAll(map);
		if (map != null && map.get("search_xml") != null) {
			populateContextWithSearchXml(ctx, map, reportDefImpl, request);
		}

	}

	private static void getSavedSearchList(Context ctx,
			ReportdefImpl reportdefImpl) throws Exception {
		if (reportdefImpl.getIsshowsavedsearch()) {
			List savedSearchList = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.sqlStatementsviewgetSavedSearchListByReportId",
					ctx);
			if (savedSearchList != null && savedSearchList.size() > 0) {
				label129 : for (int i = 0; i < savedSearchList.size(); ++i) {
					Map row = (Map) savedSearchList.get(i);
					if (row.get("search_xml_desc") != null
							&& !row.get("search_xml_desc").toString()
									.equals("")) {
						String search_xml_desc = null;
						String row_search_xml_desc = row.get("search_xml_desc")
								.toString();
						StringTokenizer tokens = new StringTokenizer(
								row_search_xml_desc, "|");

						while (true) {
							String key;
							String value;
							String arg17;
							RuleImpl arg18;
							while (true) {
								String token;
								do {
									do {
										if (!tokens.hasMoreTokens()) {
											if (search_xml_desc != null
													&& search_xml_desc
															.endsWith("; ")) {
												search_xml_desc = search_xml_desc
														.substring(
																0,
																search_xml_desc
																		.length());
											}

											row.put("search_xml_desc",
													search_xml_desc);
											continue label129;
										}

										token = tokens.nextToken();
									} while (token == null);
								} while (token.indexOf("=") == -1);

								key = token.substring(0, token.indexOf("="));
								value = token.substring(token.indexOf("=") + 1,
										token.length());
								if (key.equalsIgnoreCase("groupby")) {
									key = "Group By";
									value = parseGroupByColumnInSavedSearch(
											reportdefImpl, value);
									break;
								}

								try {
									ColumnImpl colLabel = null;
									if (reportdefImpl.getIsshowmultiplegrid()) {
										if (reportdefImpl
												.getOrderedColumnSetList() != null
												&& reportdefImpl
														.getOrderedColumnSetList()
														.size() > 0) {
											for (int e = 0; e < reportdefImpl
													.getOrderedColumnSetList()
													.size(); ++e) {
												ColumnsetImpl ruleImpl = (ColumnsetImpl) reportdefImpl
														.getOrderedColumnSetList()
														.get(e);
												colLabel = ruleImpl
														.getColumn(key);
												if (colLabel != null) {
													break;
												}
											}
										}
									} else {
										colLabel = ((ColumnsetImpl) reportdefImpl
												.getColumnsetList().get(0))
												.getColumn(key);
									}

									if (colLabel == null)
										continue;

									if (!StringUtils
											.isBlank(colLabel.getEval())) {
										arg17 = colLabel.getEval();
										if (arg17.startsWith("eval:")) {
											try {
												arg17 = arg17
														.substring(
																arg17.indexOf("eval:") + 5,
																arg17.length());
												arg18 = RulesResources
														.getInstance(ctx)
														.findRule(arg17);
												if (!arg18.evaluate(ctx,
														(String[]) null)) {
													continue;
												}
											} catch (Exception arg14) {
												logger.error(
														ctx,
														"Unable to execute rule "
																+ colLabel
																		.getEval()
																+ " due to error : "
																+ arg14.getMessage(), arg14);
											}
										} else {
											HashMap arg19 = CacheManager
													.get("Tabs_Conf") == null
													? null
													: (HashMap) CacheManager
															.get("Tabs_Conf");
											if (arg19 != null
													&& arg19.containsKey(arg17)
													&& arg19.get(arg17) != null
													&& "Y".equalsIgnoreCase(arg19
															.get(arg17)
															.toString())) {
												continue;
											}
										}
									}
								} catch (Exception arg15) {
									logger.error(ctx,
											"Error while getting label for default display column due to error : "
													+ arg15.getMessage(), arg15);
								}

								if (value != null && value.equals("Select")) {
									value = "All";
								}
								break;
							}

							String arg16 = key;
							if (!StringUtils.isBlank(key)) {
								if (key.startsWith("eval:")) {
									try {
										arg17 = arg16.substring(
												arg16.indexOf("eval:") + 5,
												arg16.length());
										arg18 = RulesResources.getInstance(ctx)
												.findRule(arg17);
										arg16 = arg18.execute(ctx,
												(String[]) null).toString();
									} catch (Exception arg13) {
										logger.error(ctx,
												"Unable to execute rule for column : "
														+ key, arg13);
									}
								}

								new ReportUtils();
								arg16 = getLabelData(arg16);
							}

							if (search_xml_desc == null) {
								search_xml_desc = arg16 + " = " + value;
							} else {
								search_xml_desc = search_xml_desc + "; "
										+ arg16 + " = " + value;
							}
						}
					}
				}
			}

			ctx.put("report_savedsearch_list_1", savedSearchList);
		}
	}

	private static void validatePage(Context ctx, ReportdefImpl reportDefImpl)
			throws Exception {
		int groupby;
		ColumnsetImpl outputcolumns;
		String outputcolumnsTokens;
		String arg21;
		if (reportDefImpl.getIsshowmultiplegrid()) {
			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				for (groupby = 0; groupby < reportDefImpl
						.getOrderedColumnSetList().size(); ++groupby) {
					outputcolumns = (ColumnsetImpl) reportDefImpl
							.getOrderedColumnSetList().get(groupby);
					List groupbyTokens = outputcolumns.getOrderedColumnsList();

					for (int groupbyToken = 0; groupbyToken < groupbyTokens
							.size(); ++groupbyToken) {
						ColumnImpl isFound = (ColumnImpl) groupbyTokens
								.get(groupbyToken);
						if (isFound.getIsreportfilter()) {
							if (isFound.getIsrequired()) {
								if (ctx.get("#REPORTING_PAGE_ERROR#") != null
										&& !ctx.get("#REPORTING_PAGE_ERROR#")
												.toString().equals("")) {
									break;
								}

								checkForRequired(ctx, isFound.getName());
							}

							if (ctx.get(isFound.getName()) != null
									&& !ctx.get(isFound.getName()).toString()
											.equals("")) {
								try {
									outputcolumnsTokens = isFound
											.getMetaproperty();
									if (outputcolumnsTokens.contains(".")) {
										outputcolumnsTokens = outputcolumnsTokens
												.substring(outputcolumnsTokens
														.indexOf(".") + 1,
														outputcolumnsTokens
																.length());
									}

									FieldImpl outputcolumnsToken = MetaDataResources
											.getInstance(ctx).getField(
													outputcolumnsTokens);
									if (outputcolumnsToken.getType() != null
											&& (outputcolumnsToken.getType()
													.equals("D") || outputcolumnsToken
													.getType().equals("TS"))) {
										String colImpl = "MM/dd/yyyy";
										if (outputcolumnsToken.getPattern() != null
												&& !outputcolumnsToken
														.getPattern()
														.equals("")) {
											colImpl = outputcolumnsToken
													.getPattern();
										}

										try {
											SimpleDateFormat metaproperty = new SimpleDateFormat(
													colImpl);
											metaproperty.setLenient(false);
											metaproperty.parse(ctx.get(
													isFound.getName())
													.toString());
											if (DateUtils.isDateBefore(
													ctx.get(isFound.getName())
															.toString(),
													new Date("01/01/1753"))) {
												DataUtils.populateError(ctx,
														isFound.getName(),
														"incorrect_date");
												break;
											}

											if (DateUtils.isDateAfter(
													ctx.get(isFound.getName())
															.toString(),
													new Date("12/31/9999"))) {
												DataUtils.populateError(ctx,
														isFound.getName(),
														"incorrect_date");
												break;
											}
										} catch (Exception arg12) {
											logger.error("Error in ReportUtils.validatePage", arg12);
											DataUtils.populateError(ctx,
													isFound.getName(),
													"incorrect_date");
											break;
										}
									}
								} catch (Exception arg13) {
									logger.error("Error in ReportUtils.validatePage", arg13);
									;
								}
							}
						}
					}

					if (ctx.get("inet_form_dirty") != null
							&& ctx.get("inet_form_dirty").toString()
									.equals("Y")) {
						return;
					}

					if (ctx.get("report_page_error") == null
							|| ctx.get("report_page_error").toString()
									.equals("")) {
						ctx.put("inet_form_dirty", (Object) null);
						ctx.put("managereport_errorfound", "Y");
					}
				}
			}
		} else if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			List arg14 = reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList();

			for (int arg15 = 0; arg15 < arg14.size(); ++arg15) {
				ColumnImpl arg17 = (ColumnImpl) arg14.get(arg15);
				if (arg17.getIsreportfilter()) {
					if (arg17.getIsrequired()) {
						if (ctx.get("#REPORTING_PAGE_ERROR#") != null
								&& !ctx.get("#REPORTING_PAGE_ERROR#")
										.toString().equals("")) {
							break;
						}

						checkForRequired(ctx, arg17.getName());
					}

					if (ctx.get(arg17.getName()) != null
							&& !ctx.get(arg17.getName()).toString().equals("")) {
						try {
							arg21 = arg17.getMetaproperty();
							if (arg21.contains(".")) {
								arg21 = arg21.substring(arg21.indexOf(".") + 1,
										arg21.length());
							}

							FieldImpl arg22 = MetaDataResources
									.getInstance(ctx).getField(arg21);
							if (arg22.getType() != null
									&& (arg22.getType().equals("D") || arg22
											.getType().equals("TS"))) {
								outputcolumnsTokens = "MM/dd/yyyy";
								if (arg22.getPattern() != null
										&& !arg22.getPattern().equals("")) {
									outputcolumnsTokens = arg22.getPattern();
								}

								try {
									SimpleDateFormat arg26 = new SimpleDateFormat(
											outputcolumnsTokens);
									arg26.setLenient(false);
									arg26.parse(ctx.get(arg17.getName())
											.toString());
									if (DateUtils
											.isDateBefore(
													ctx.get(arg17.getName())
															.toString(),
													new Date("01/01/1753"))) {
										DataUtils.populateError(ctx,
												arg17.getName(),
												"incorrect_date");
										break;
									}

									if (DateUtils
											.isDateAfter(
													ctx.get(arg17.getName())
															.toString(),
													new Date("12/31/9999"))) {
										DataUtils.populateError(ctx,
												arg17.getName(),
												"incorrect_date");
										break;
									}
								} catch (Exception arg10) {
									logger.error("Error in ReportUtils.validatePage", arg10);
									DataUtils.populateError(ctx,
											arg17.getName(), "incorrect_date");
									break;
								}
							}
						} catch (Exception arg11) {
							logger.error("Error in ReportUtils.validatePage", arg11);
							;
						}
					}
				}
			}

			if (ctx.get("inet_form_dirty") != null
					&& ctx.get("inet_form_dirty").toString().equals("Y")) {
				return;
			}

			if (ctx.get("report_page_error") == null
					|| ctx.get("report_page_error").toString().equals("")) {
				ctx.put("inet_form_dirty", (Object) null);
				ctx.put("managereport_errorfound", "Y");
			}
		}

		if (!reportDefImpl.getIsshowmultiplegrid()) {
			if (reportDefImpl.getIsshowoutputcolumns()
					&& (ctx.get("outputcolumns") == null || ctx
							.get("outputcolumns").toString().equals(""))) {
				DataUtils.populateError(ctx, "report_page_error",
						"atleastOneReportParameterErrorKey");
				return;
			}
		} else {
			if (reportDefImpl.getIsshowoutputcolumns()
					&& (ctx.get("outputcolumns") == null || ctx
							.get("outputcolumns").toString().equals(""))) {
				DataUtils.populateError(ctx, "report_page_error",
						"atleastOneReportParameterErrorKey");
				return;
			}

			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				for (groupby = 0; groupby < reportDefImpl
						.getOrderedColumnSetList().size(); ++groupby) {
					outputcolumns = (ColumnsetImpl) reportDefImpl
							.getOrderedColumnSetList().get(groupby);
					if (outputcolumns.getIsskipvalidation() == null
							|| !outputcolumns.getIsskipvalidation().equals("Y")) {
						boolean arg19 = false;
						List arg23 = outputcolumns.getOrderedColumnsList();

						for (int arg24 = 0; arg24 < arg23.size(); ++arg24) {
							ColumnImpl arg28 = (ColumnImpl) arg23.get(arg24);
							if (arg28.getIsreportdisplay()
									&& (new ReportUtils())
											.checkForHideShowColumn(ctx, arg28)
									&& arg28.getIsdefaultoutputcolumn()
									&& ctx.get("outputcolumns")
											.toString()
											.contains(
													outputcolumns.getName()
															+ "="
															+ arg28.getName())) {
								arg19 = true;
								break;
							}
						}

						if (!arg19) {
							DataUtils.populateError(ctx, "report_page_error",
									"atleastOneReportParameterErrorKey");
							return;
						}
					}
				}
			}
		}

		if (ctx.get("groupby") != null && ctx.get("outputcolumns") != null) {
			String arg16 = ctx.get("groupby").toString().trim();
			String arg18 = ctx.get("outputcolumns").toString().trim();
			StringTokenizer arg20 = new StringTokenizer(arg16, ",");
			if (!arg16.equals("") && arg18.equals("")) {
				DataUtils.populateError(ctx, "report_page_error",
						"matchingReportParameterWithGroupByErrorKey");
				return;
			}

			while (arg20.hasMoreTokens()) {
				arg21 = arg20.nextToken();
				boolean arg25 = false;
				StringTokenizer arg29 = new StringTokenizer(arg18, ",");

				while (arg29.hasMoreTokens()) {
					String arg27 = arg29.nextToken();
					ColumnImpl arg30 = reportDefImpl.getFirstColumnset()
							.getColumn(arg27);
					if (arg30 != null) {
						String arg31 = arg30.getMetaproperty();
						if (arg31.indexOf(".") != -1) {
							arg31 = arg31.substring(arg31.indexOf(".") + 1,
									arg31.length());
						} else {
							arg31 = arg27;
						}

						if (arg21.equals(arg31)) {
							arg25 = true;
							break;
						}
					}
				}

				if (!arg25) {
					DataUtils.populateError(ctx, "report_page_error",
							"matchingReportParameterWithGroupByErrorKey");
					return;
				}
			}
		}

		if (ctx.get("inet_form_dirty") != null
				&& ctx.get("inet_form_dirty").toString().equals("Y")) {
			ctx.put("managereport_errorfound", "Y");
		}

	}

	private static void checkForRequired(Context ctx, String field)
			throws Exception {
		if (ctx.get(field) == null || ctx.get(field).equals("")) {
			DataUtils.populateError(ctx, "report_page_error",
					"Please fill all required fields");
		}

	}

	private static void updateFieldListWithData(Context ctx,
			HttpServletRequest request) throws Exception {
		if (ctx.get("old_FieldList") != null) {
			ArrayList old_field_list = (ArrayList) ctx.get("old_FieldList");
			Iterator arg3 = old_field_list.iterator();

			while (arg3.hasNext()) {
				Map record = (Map) arg3.next();
				record.put(
						"selected_filter_field_value",
						ctx.get(record.get("selected_filter_field_name")) != null
								? ctx.get(
										record.get("selected_filter_field_name"))
										.toString()
								: null);
			}

			ctx.put("old_FieldList", old_field_list);
			request.getSession().setAttribute("old_FieldList", old_field_list);
		}
	}

	private static void populateParameter(Context ctx,
			ReportdefImpl reportDefImpl, HttpServletRequest request)
			throws Exception {
		List columns = null;
		if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			columns = reportDefImpl.getFirstColumnset().getOrderedColumnsList();
		}

		if (columns != null) {
			Iterator arg4 = columns.iterator();

			label66 : while (true) {
				ColumnImpl selectedVal;
				String selectedVal1;
				do {
					do {
						do {
							do {
								if (!arg4.hasNext()) {
									break label66;
								}

								selectedVal = (ColumnImpl) arg4.next();
							} while (!selectedVal.getIsreportfilter());
						} while (!"multiselect".equalsIgnoreCase(selectedVal
								.getFilterdisplaytype()));

						selectedVal1 = ctx.get(selectedVal.getName()) != null
								&& !ctx.get(selectedVal.getName()).toString()
										.equals("")
								&& !ctx.get(selectedVal.getName()).toString()
										.equals("All") ? ctx.get(
								selectedVal.getName()).toString() : null;
						ctx.put(selectedVal.getName(), selectedVal1);
					} while (selectedVal1 == null);
				} while (!selectedVal1.contains(","));

				StringBuilder newStr = new StringBuilder();
				StringTokenizer tokens = new StringTokenizer(selectedVal1, ",");

				while (tokens.hasMoreTokens()) {
					newStr.append(tokens.nextToken()).append(",");
				}

				ctx.put(selectedVal.getName(),
						newStr.substring(0, newStr.lastIndexOf(",")));
			}
		}

		String selectedVal2 = ctx.get("SortOrder") != null
				&& !ctx.get("SortOrder").toString().equals("") ? ctx.get(
				"SortOrder").toString()
				+ "," : null;
		ctx.put("SortOrder", selectedVal2);
		ctx.put("ispaginationrequired",
				reportDefImpl.getIsreportdatapagination());
	}

	private static void cleanFilterFields(Context ctx,
			ReportdefImpl reportDefImpl, HttpServletRequest request)
			throws Exception {
		if (reportDefImpl.getIsshowmultiplegrid()) {
			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				for (int obj = 0; obj < reportDefImpl.getOrderedColumnSetList()
						.size(); ++obj) {
					ColumnsetImpl list = (ColumnsetImpl) reportDefImpl
							.getOrderedColumnSetList().get(obj);
					List j = list.getOrderedColumnsList();

					for (int row = 0; row < j.size(); ++row) {
						ColumnImpl column = (ColumnImpl) j.get(row);
						if (column.getIsreportfilter()) {
							ctx.remove(column.getName());
							request.getSession().removeAttribute(
									column.getName());
							request.getSession().removeAttribute(
									"to_filter_data_" + column.getName());
						}
					}
				}
			}
		} else if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			List arg7 = reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList();

			for (int arg9 = 0; arg9 < arg7.size(); ++arg9) {
				ColumnImpl arg11 = (ColumnImpl) arg7.get(arg9);
				if (arg11.getIsreportfilter()) {
					ctx.remove(arg11.getName());
					request.getSession().removeAttribute(arg11.getName());
					request.getSession().removeAttribute(
							"to_filter_data_" + arg11.getName());
				}
			}
		}

		Object arg8 = request.getSession().getAttribute("old_FieldList");
		if (arg8 != null && arg8 instanceof List) {
			List arg10 = (List) arg8;

			for (int arg12 = 0; arg12 < arg10.size(); ++arg12) {
				Map arg13 = (Map) arg10.get(arg12);
				arg13.remove("selected_filter_field_value");
			}
		}

		ctx.remove("SortOrder");
	}

	private static void addFieldToPage(Context ctx,
			ReportdefImpl reportDefImpl, HttpServletRequest request)
			throws Exception {
		if (ctx.get("old_FieldList") == null) {
			ArrayList old_field_list = new ArrayList();
			List list;
			int i;
			if (reportDefImpl.getIsshowmultiplegrid()) {
				if (reportDefImpl.getOrderedColumnSetList() != null
						&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
					list = reportDefImpl.getOrderedColumnSetList();

					for (i = 0; i < list.size(); ++i) {
						ColumnsetImpl column = (ColumnsetImpl) list.get(i);
						List map = column.getOrderedColumnsList();

						for (int display_value = 0; display_value < map.size(); ++display_value) {
							ColumnImpl fieldImpl = (ColumnImpl) map
									.get(display_value);
							if (fieldImpl.getIsdefaultdisplay()
									&& fieldImpl.getIsreportfilter()
									&& (new ReportUtils())
											.checkForHideShowColumn(ctx,
													fieldImpl)) {
								HashMap map1 = new HashMap();
								map1.put("selected_filter_field_name",
										fieldImpl.getName());
								String display_value1 = fieldImpl
										.getColumnlabel();
								if ((fieldImpl.getColumnlabel() == null || ""
										.equals(fieldImpl.getColumnlabel()))
										&& fieldImpl.getMetaproperty() != null
										&& !"".equals(fieldImpl
												.getMetaproperty().trim())) {
									FieldImpl fieldImpl1 = MetaDataResources
											.getInstance(ctx)
											.getField(
													fieldImpl.getMetaproperty());
									if (fieldImpl1 != null
											&& fieldImpl1.getDisplayname() != null) {
										display_value1 = fieldImpl1
												.getDisplayname();
									}
								}

								new ReportUtils();
								display_value1 = getLabelData(display_value1);
								map1.put("selected_filter_field_display_name",
										display_value1);
								map1.put("is_required", Boolean
										.valueOf(fieldImpl.getIsrequired()));
								old_field_list.add(map1);
								ctx.put("old_FieldList", old_field_list);
								request.getSession().setAttribute(
										"old_FieldList", old_field_list);
							}
						}
					}
				}
			} else if (reportDefImpl != null
					&& reportDefImpl.getFirstColumnset() != null
					&& reportDefImpl.getFirstColumnset()
							.getOrderedColumnsList() != null) {
				list = reportDefImpl.getFirstColumnset()
						.getOrderedColumnsList();

				for (i = 0; i < list.size(); ++i) {
					ColumnImpl arg12 = (ColumnImpl) list.get(i);
					if (arg12.getIsdefaultdisplay()
							&& arg12.getIsreportfilter()
							&& (new ReportUtils()).checkForHideShowColumn(ctx,
									arg12)) {
						HashMap arg13 = new HashMap();
						arg13.put("selected_filter_field_name", arg12.getName());
						String arg14 = arg12.getColumnlabel();
						if ((arg12.getColumnlabel() == null || "".equals(arg12
								.getColumnlabel()))
								&& arg12.getMetaproperty() != null
								&& !"".equals(arg12.getMetaproperty().trim())) {
							FieldImpl arg15 = MetaDataResources
									.getInstance(ctx).getField(
											arg12.getMetaproperty());
							if (arg15 != null && arg15.getDisplayname() != null) {
								arg14 = arg15.getDisplayname();
							}
						}

						new ReportUtils();
						arg14 = getLabelData(arg14);
						arg13.put("selected_filter_field_display_name", arg14);
						arg13.put("is_required",
								Boolean.valueOf(arg12.getIsrequired()));
						old_field_list.add(arg13);
						ctx.put("old_FieldList", old_field_list);
						request.getSession().setAttribute("old_FieldList",
								old_field_list);
					}
				}
			}
		}

	}

	private static void generateRequestXml(Context ctx,
			ReportdefImpl reportDefImpl, HttpServletRequest request,
			boolean isDrillDownReportClicked) throws Exception {
		LinkedHashMap map = new LinkedHashMap();
		new LinkedHashMap();
		String search_xml = null;
		String newValue;
		String arg35;
		String arg36;
		String arg38;
		int arg46;
		if (reportDefImpl.getIsshowmultiplegrid()) {
			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				for (int selected_filter_data = 0; selected_filter_data < reportDefImpl
						.getOrderedColumnSetList().size(); ++selected_filter_data) {
					ColumnsetImpl selected_filter_data_new = (ColumnsetImpl) reportDefImpl
							.getOrderedColumnSetList()
							.get(selected_filter_data);
					List tokens = selected_filter_data_new
							.getOrderedColumnsList();

					for (int token = 0; token < tokens.size(); ++token) {
						ColumnImpl key = (ColumnImpl) tokens.get(token);
						if (key.getIsreportfilter()) {
							if (!isDrillDownReportClicked
									&& key.getIspopupsearch()) {
								arg38 = null;
								Object arg39 = request.getSession()
										.getAttribute(
												"to_filter_data_"
														+ key.getName());
								if (arg39 != null
										&& ctx.get(key.getName()) != null) {
									List arg41 = (List) arg39;

									for (arg46 = 0; arg46 < arg41.size(); ++arg46) {
										Map arg47 = (Map) arg41.get(arg46);
										Set entry = arg47.entrySet();
										Iterator key1 = entry.iterator();

										while (key1.hasNext()) {
											Entry value1 = (Entry) key1.next();
											String ctxValues = (String) value1
													.getKey();
											String k = (String) value1
													.getValue();
											String[] ctxValues1 = ctx
													.get(key.getName())
													.toString().split(",");

											for (int l = 0; l < ctxValues1.length; ++l) {
												if (k.equals(ctxValues1[l])) {
													arg38 = arg38 == null
															? ctxValues + "="
																	+ k
															: arg38 + "#Hash;#"
																	+ ctxValues
																	+ "=" + k;
												}
											}
										}
									}

									map.put(key.getName(), arg38);
								} else {
									map.put(key.getName(), arg38);
								}
							} else {
								Object value = ctx.get(key.getName());
								newValue = key.getMetaproperty();
								if (newValue.contains(".")) {
									String tokens1 = newValue.substring(
											newValue.indexOf(".") + 1,
											newValue.length());

									try {
										if (MetaDataResources.getInstance(ctx)
												.getField(tokens1) != null) {
											String j = "MM/dd/yyyy";
											FieldImpl token1 = MetaDataResources
													.getInstance(ctx).getField(
															tokens1);
											if (token1.getType() != null
													&& token1.getType().equals(
															"D")) {
												if (token1.getPattern() != null
														&& !token1.getPattern()
																.equals("")) {
													j = token1.getPattern();
												}

												if (value != null
														&& !value.toString()
																.equals("")) {
													value = (new SimpleDateFormat(
															j)).format(value);
												}
											}
										}
									} catch (Exception arg24) {
										logger.error(ctx,
												"Error found while generating search xml due to error : "
														+ arg24.getMessage(), arg24);
									}
								}

								map.put(key.getName(), value);
							}
						}
					}
				}
			}
		} else if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			List arg26 = reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList();

			for (int arg28 = 0; arg28 < arg26.size(); ++arg28) {
				ColumnImpl arg30 = (ColumnImpl) arg26.get(arg28);
				if (arg30.getIsreportfilter()) {
					if (!isDrillDownReportClicked && arg30.getIspopupsearch()) {
						arg36 = null;
						Object arg37 = request.getSession().getAttribute(
								"to_filter_data_" + arg30.getName());
						if (arg37 != null && ctx.get(arg30.getName()) != null) {
							List arg40 = (List) arg37;

							for (int arg42 = 0; arg42 < arg40.size(); ++arg42) {
								Map arg44 = (Map) arg40.get(arg42);
								Set arg48 = arg44.entrySet();
								Iterator arg49 = arg48.iterator();

								while (arg49.hasNext()) {
									Entry arg51 = (Entry) arg49.next();
									String arg52 = (String) arg51.getKey();
									String arg53 = (String) arg51.getValue();
									String[] arg54 = ctx.get(arg30.getName())
											.toString().split(",");

									for (int arg55 = 0; arg55 < arg54.length; ++arg55) {
										if (arg53.equals(arg54[arg55])) {
											arg36 = arg36 == null ? arg52 + "="
													+ arg53 : arg36 + "#Hash;#"
													+ arg52 + "=" + arg53;
										}
									}
								}
							}

							map.put(arg30.getName(), arg36);
						} else {
							map.put(arg30.getName(), arg36);
						}
					} else {
						Object arg34 = ctx.get(arg30.getName());
						arg35 = arg30.getMetaproperty();
						if (arg35.contains(".")) {
							arg38 = arg35.substring(arg35.indexOf(".") + 1,
									arg35.length());

							try {
								if (MetaDataResources.getInstance(ctx)
										.getField(arg38) != null) {
									newValue = "MM/dd/yyyy";
									FieldImpl arg43 = MetaDataResources
											.getInstance(ctx).getField(arg38);
									if (arg43.getType() != null
											&& arg43.getType().equals("D")) {
										if (arg43.getPattern() != null
												&& !arg43.getPattern().equals(
														"")) {
											newValue = arg43.getPattern();
										}

										if (arg34 != null
												&& !arg34.toString().equals("")) {
											arg34 = (new SimpleDateFormat(
													newValue)).format(arg34);
										}
									}
								}
							} catch (Exception arg23) {
								logger.error(ctx,
										"Error found while generating search xml due to error : "
												+ arg23.getMessage(), arg23);
							}
						}

						map.put(arg30.getName(), arg34);
					}
				}
			}
		}

		String arg27;
		if (StringUtils.isNotBlank(reportDefImpl.getDynakeys())) {
			arg27 = reportDefImpl.getDynakeys();
			StringTokenizer arg29 = new StringTokenizer(arg27, ",");

			while (arg29.hasMoreTokens()) {
				String arg32 = arg29.nextToken();
				arg36 = ctx.get(arg32) != null ? ctx.get(arg32).toString() : "";
				map.put(arg32, arg36);
			}
		}

		if (reportDefImpl.getIsshowsortordersearch()) {
			map.put("SortOrder", ctx.get("SortOrder"));
		}

		if (reportDefImpl.getIsshowgroupbysearch()) {
			map.put("groupby", ctx.get("groupby"));
		}

		if (reportDefImpl.getIsshowoutputcolumns()) {
			map.put("outputcolumns", ctx.get("outputcolumns"));
		}

		search_xml = (new DataUtils()).generateRequestXml(map);
		ctx.put("search_xml", search_xml);
		if (ctx.get("selected_filter_data") != null) {
			arg27 = ctx.get("selected_filter_data").toString();
			String arg31;
			StringTokenizer arg33;
			if (arg27 != null) {
				arg31 = null;
				arg33 = new StringTokenizer(arg27, "|");

				while (true) {
					while (arg33.hasMoreTokens()) {
						arg36 = arg33.nextToken();
						arg35 = arg36.substring(0, arg36.indexOf("="));
						arg38 = arg36.substring(arg36.indexOf("=") + 1,
								arg36.length());
						if (!isPopupSearchColumnFound(ctx, arg35)) {
							if (arg31 == null) {
								arg31 = arg35 + "=" + arg38 + "|";
							} else {
								arg31 = arg35 + "=" + arg38 + "|" + arg31;
							}
						} else {
							newValue = null;

							try {
								String[] arg45 = arg38.split("#Hash;#");

								for (arg46 = 0; arg46 < arg45.length; ++arg46) {
									String arg50 = arg45[arg46];
									if (newValue == null) {
										newValue = arg50.substring(0,
												arg50.indexOf("="));
									} else {
										newValue = newValue
												+ ", "
												+ arg50.substring(0,
														arg50.indexOf("="));
									}
								}
							} catch (Exception arg25) {
								logger.error("Error in ReportUtils.generateRequestXml", arg25);
								;
							}

							if (arg31 == null) {
								arg31 = arg35 + "=" + newValue + "|";
							} else {
								arg31 = arg35 + "=" + newValue + "|" + arg31;
							}
						}
					}

					arg27 = arg31;
					break;
				}
			}

			if (arg27 != null && !reportDefImpl.getIsshowgroupbysearch()) {
				arg31 = null;
				arg33 = new StringTokenizer(arg27, "|");

				while (arg33.hasMoreTokens()) {
					arg36 = arg33.nextToken();
					arg35 = arg36.substring(0, arg36.indexOf("="));
					arg38 = arg36.substring(arg36.indexOf("=") + 1,
							arg36.length());
					if (!arg35.equals("groupby")) {
						if (arg31 == null) {
							arg31 = arg35 + "=" + arg38 + "|";
						} else {
							arg31 = arg35 + "=" + arg38 + "|" + arg31;
						}
					}
				}

				arg27 = arg31;
			}

			ctx.put("search_xml_desc", arg27);
		}

	}

	private static void populateContextWithSearchXml(Context ctx, Map map,
			ReportdefImpl reportDefImpl, HttpServletRequest request)
			throws Exception {
		(new RequestProcessor()).populateContext(ctx, map.get("search_xml")
				.toString());
		if (reportDefImpl != null
				&& reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			List list = reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList();

			for (int i = 0; i < list.size(); ++i) {
				ColumnImpl column = (ColumnImpl) list.get(i);
				if (column.getIspopupsearch()
						&& ctx.get(column.getName()) != null) {
					ArrayList optionsList = new ArrayList();
					String[] tokens = ctx.get(column.getName()).toString()
							.split("#Hash;#");

					for (int j = 0; j < tokens.length; ++j) {
						String token = tokens[j];
						if (token != null && token.contains("=")) {
							String key = token.substring(0, token.indexOf("="));
							String value = token.substring(
									token.indexOf("=") + 1, token.length());
							HashMap arg12 = new HashMap();
							arg12.put(key, value);
							optionsList.add(arg12);
						}
					}

					request.getSession().setAttribute(
							"to_filter_data_" + column.getName(), optionsList);
				}
			}
		}

	}

	public static String parseReportHTML(Context ctx, String template,
			String templatePath, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		PageUtils pageUtils = new PageUtils();
		HTML2XML htm2xml = new HTML2XML();
		htm2xml.setHtmlPath(SystemProperties.getInstance().getString(
				"html.basedir"));
		htm2xml.convert(SystemProperties.getInstance()
				.getString("html.basedir") + templatePath, template, ctx);
		String html = IOUtils.readFile(SystemProperties.getInstance()
				.getString("html.basedir") + "temp.xml");
		String nextPagDocType = pageUtils.getDocType(html);
		Document document = pageUtils.getDocument(html, template);
		document = parseReportDocument(ctx, document, template, request,
				reportDefImpl);
		String htmlDoc = HTMLUtils.beautifyHTML(document);
		htmlDoc = htmlDoc.replaceAll("#space#", "&nbsp;");
		String htmlcontent = pageUtils.appendDOCTYPE(htmlDoc, nextPagDocType);
		document = null;
		return htmlcontent;
	}

	public static Document parseReportDocument(Context ctx, Document document,
			String pageName, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		Element root = null;

		try {
			if (document != null) {
				root = document.getRootElement();
				if (root != null) {
					Element e = root.getChild("body");
					if (e == null) {
						{ if (logger.isDebugEnabled()) logger.debug(ctx, pageName + " does not contain BODY Element"); }
						return root.getDocument();
					}
					Element form = e.getChild("form");
					Element head = root.getChild("head");
					(new ReportUtils()).parseHeadNode(head, head.getChildren());
					if (form != null) {
						appendHiddenFields(form, ctx, pageName, reportDefImpl);
					}

					if (form == null) {
						{ if (logger.isDebugEnabled()) logger.debug(ctx, pageName
								+ " does not contain FORM Element"); }
					}

					List children = e.getChildren();
					parseHTML(ctx, e, children, root, request, reportDefImpl);
				}
			}
		} catch (Exception arg9) {
			logger.error("Error in ReportUtils.parseReportDocument", arg9);
			throw arg9;
		}

		return root != null ? root.getDocument() : document;
	}

	public static void parseHTML(Context ctx, Element element, List children,
			Element rootElement, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		Element blockelement = null;
		if (children != null) {
			for (int i = 0; i < children.size(); ++i) {
				blockelement = null;
				blockelement = (Element) children.get(i);
				Attribute elementsAttr = blockelement.getAttribute("id");
				List subchildren = blockelement.getChildren();
				if (elementsAttr != null) {
					String type = blockelement.getName();
					String elementID = elementsAttr.getValue();
					if (elementID == null)
						continue;
					String[] e;
					String dynaValues;
					String method;
					String arg22;
					if (elementID != null && elementID.contains("#Include:")) {
						arg22 = elementID.substring(elementID.indexOf(":") + 1,
								elementID.length() - 1);
						if (arg22.indexOf("/") != -1) {
							arg22 = arg22.substring(arg22.lastIndexOf("/") + 1,
									arg22.length() - 5);
						}

						e = null;

						Document arg23;
						try {
							ComponentImpl arg24 = ComponentResources
									.getInstance(ctx).getComponent(arg22);
							arg24.processView(ctx);
							arg23 = (new PageUtils()).parseHtmlDocument(ctx,
									arg22, "", request);
						} catch (Exception arg20) {
							logger.error("Error in ReportUtils.parseHTML", arg20);
							dynaValues = SystemProperties.getInstance()
									.getString(
											"appl." + ctx.getProject()
													+ ".LoadHTMLS");
							method = null;
							Object arg26;
							if (dynaValues != null
									&& "Y".equalsIgnoreCase(dynaValues)) {
								arg26 = CacheManager.get(arg22) == null
										? new HashMap()
										: (HashMap) CacheManager.get(arg22);
							} else {
								arg26 = (new PageUtils()).getHtmlDocMap(ctx,
										arg22);
							}

							Document arg27 = ((Map) arg26).get("XMLDocument") == null
									? null
									: (Document) ((Map) arg26)
											.get("XMLDocument");
							arg23 = (Document) ObjectCloner.deepCopy(arg27);
						}

						Element arg25 = (Element) arg23.getRootElement()
								.getChild("body").getChild("form")
								.getChild("div").detach();
						blockelement.addContent(arg25);
						continue;
					}

					if ("isShowSavedSearchTrue".equalsIgnoreCase(elementID)
							&& !reportDefImpl.getIsshowsavedsearch()) {
						blockelement.removeContent();
						continue;
					}

					if ("isShowFiltersSearchTrue".equalsIgnoreCase(elementID)
							&& (reportDefImpl == null || reportDefImpl != null
									&& !reportDefImpl.getIsshowfiltersearch())) {
						blockelement.removeContent();
						continue;
					}

					if ("isDrillDownReportTrue".equalsIgnoreCase(elementID)) {
						if (ctx.get("isDrillDownReportTrue") == null
								|| !ctx.get("isDrillDownReportTrue")
										.equals("Y")) {
							blockelement.detach();
							continue;
						}

						processDrillDownBackButton(ctx, blockelement);
					}

					if (elementID != null
							&& elementID.equalsIgnoreCase("#REPORTING_NAME#")) {
						new ReportUtils();
						blockelement.setText(getLabelData(reportDefImpl
								.getDescription()));
					}

					if (elementID != null
							&& elementID
									.equalsIgnoreCase("#REPORTING_PAGE_ERROR#")) {
						(new ReportFilterBlock(reportDefImpl))
								.generateErrorField(ctx, blockelement);
						continue;
					}

					if (elementID.contains("#REPORTING_SEARCH_POPUP#")) {
						(new ReportFreeformBlock()).processSearchPopup(ctx,
								subchildren, reportDefImpl);
						continue;
					}

					if (elementID.contains("_freeform_contextdata_")) {
						(new ReportFreeformBlock()).parseFreeform(ctx,
								subchildren);
						continue;
					}

					if ("#REPORTING_FILTER_AREA#".equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.processFilterBlock(ctx, blockelement,
										subchildren, request);
						continue;
					}

					if ("#REPORTING_SAVESEARCH_BLOCK#"
							.equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.generateReportingSavedSearchBlock(ctx,
										blockelement);
					}

					if ("#REPORTING_SAVED_SEARCH_LIST_BLOCK#"
							.equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.generateReportingSavedSearchListBlock(ctx,
										blockelement);
					}

					if ("#REPORTING_SHOW_BACK_LINK#"
							.equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.buildBackSubmitLink(ctx, blockelement);
					}

					if ("isShowResetButtonTrue".equalsIgnoreCase(elementID)) {
						if (reportDefImpl != null
								&& !reportDefImpl.getIsshowresetbutton()) {
							blockelement.removeContent();
							continue;
						}

						(new ReportFilterBlock(reportDefImpl))
								.buildResetSubmitLink(ctx, blockelement);
					}

					if ("#REPORTING_SHOW_REPORT_LINK#"
							.equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.buildShowReportLink(ctx, blockelement, request);
					}

					if ("#REPORTING_DATA_AREA#".equalsIgnoreCase(elementID)) {
						ctx.put("DATA_AREA", "#REPORTING_DATA_AREA#");
						(new ReportDataBlock(reportDefImpl)).processDataBlock(
								ctx, blockelement, subchildren, request,
								reportDefImpl);
						continue;
					}

					if ("#REPORTING_DEFAULT_DATA_AREA#"
							.equalsIgnoreCase(elementID)) {
						ctx.put("DATA_AREA", "#REPORTING_DEFAULT_DATA_AREA#");
						(new ReportDataBlock(reportDefImpl)).processDataBlock(
								ctx, blockelement, subchildren, request,
								reportDefImpl);
						continue;
					}

					if ("#REPORTING_SAVED_FILTER_AREA#"
							.equalsIgnoreCase(elementID)) {
						(new ReportDataBlock(reportDefImpl)).processDataBlock(
								ctx, blockelement, subchildren, request,
								reportDefImpl);
						continue;
					}

					if ("#REPORTING_SEARCHED_FILTERS_BLOCK#"
							.equalsIgnoreCase(elementID)
							&& (ctx.get("selected_filter_data") == null || ""
									.equals(ctx.get("selected_filter_data")
											.toString().trim()))) {
						element.removeContent(blockelement.detach());
						continue;
					}

					if ("#REPORTING_SEARCHED_FILTERS_DATA_AREA#"
							.equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.processSearchedFiltersBlock(ctx, blockelement,
										subchildren, request);
						continue;
					}

					if ("#REPORTING_SAVEDSEARCH_LIST_AREA#"
							.equalsIgnoreCase(elementID)) {
						(new ReportDataBlock(reportDefImpl))
								.processSavedSearchListDataBlock(ctx,
										blockelement, subchildren, request);
						continue;
					}

					if ("#REPORTING_SORT_ORDER_BLOCK#"
							.equalsIgnoreCase(elementID)) {
						if (!reportDefImpl.getIsshowsortordersearch()) {
							blockelement.removeContent();
							continue;
						}

						if (!(new ReportFilterBlock(reportDefImpl))
								.checkSortingBlockDisplay(ctx, blockelement,
										element)) {
							continue;
						}
					}

					if ("#REPORTING_SORT_ORDER#".equalsIgnoreCase(elementID)) {
						(new ReportFilterBlock(reportDefImpl))
								.generateSortOrderFieldList(ctx, blockelement);
					}

					if ("isShowDefaultReportDataListTrue"
							.equalsIgnoreCase(elementID)
							&& (ctx.get("isBatchScheduleForReportExists") != null
									&& !ctx.get(
											"isBatchScheduleForReportExists")
											.toString().equals("Y") || !reportDefImpl
										.getIsshowdefaultreportdatalist())) {
						blockelement.removeContent();
						continue;
					}

					if (elementID.contains("#REPORTING_EXPORT_POPUP#")) {
						(new ReportFreeformBlock()).processExportPopup(ctx,
								subchildren, reportDefImpl);
						continue;
					}

					if (elementID.contains("isShowExportButtonTrue")
							&& (new ReportDataBlock(reportDefImpl))
									.isReportDataListEmpty(ctx)) {
						blockelement.removeAttribute("class");
						blockelement.removeContent();
					}

					if (elementID.contains("isShowExportAllButtonTrue")) {
						int href = 10000;
						if (ctx.get("reportsExportAllRecordsMaxSize") != null
								&& !ctx.get("reportsExportAllRecordsMaxSize")
										.toString().equals("")) {
							href = Integer.parseInt(ctx.get(
									"reportsExportAllRecordsMaxSize")
									.toString());
						}

						if (reportDefImpl.getIsshowexportall()
								&& !(new ReportDataBlock(reportDefImpl))
										.isReportDataListEmpty(ctx)
								&& (ctx.get("TOTAL_RECORD") == null || Integer
										.parseInt(ctx.get("TOTAL_RECORD")
												.toString()) <= href)) {
							(new ReportUtils()).parseExportAllButton(ctx,
									reportDefImpl, blockelement);
						} else {
							blockelement.removeAttribute("class");
							blockelement.removeContent();
						}
					}

					if (elementID.contains("isShowGroupBySearchTrue")) {
						if (!reportDefImpl.getIsshowgroupbysearch()) {
							blockelement.removeContent();
							continue;
						}

						(new ReportFreeformBlock()).processGroupBySearch(ctx,
								blockelement, reportDefImpl);
					}

					if (elementID.contains("isShowOutputColumnsTrue")) {
						if (!reportDefImpl.getIsshowoutputcolumns()) {
							blockelement.removeContent();
							continue;
						}

						(new ReportFreeformBlock()).processOutputColumns(ctx,
								blockelement, reportDefImpl);
					}

					if (elementID.contains("#REPORTING_OUTPUTYPE#")) {
						(new ReportFreeformBlock()).processOutputTypes(ctx,
								blockelement, reportDefImpl);
					}

					if (elementID.equals("#SAVESEARCHBLOCKNUMBER#")) {
						blockelement.addContent(ctx
								.get("savesearchblocknumber") != null ? ctx
								.get("savesearchblocknumber").toString() : "");
					}

					if (elementID.equals("#FILTERBLOCKNUMBER#")) {
						blockelement
								.addContent(ctx.get("filterblocknumber") != null
										? ctx.get("filterblocknumber")
												.toString() : "");
					}

					if (elementID.equals("#OUTPUTCOLUMNSBLOCKNUMBER#")) {
						blockelement.addContent(ctx
								.get("outputcolumnsblocknumber") != null
								? ctx.get("outputcolumnsblocknumber")
										.toString() : "");
					}

					if (elementID.equals("#GROUPBYBLOCKNUMBER#")) {
						blockelement
								.addContent(ctx.get("groupbyblocknumber") != null
										? ctx.get("groupbyblocknumber")
												.toString() : "");
					}

					if (elementID.equals("#OUTPUTTYPEBLOCKNUMBER#")) {
						blockelement.addContent(ctx
								.get("outputtypeblocknumber") != null ? ctx
								.get("outputtypeblocknumber").toString() : "");
					}

					if (elementID.equals("#SAVESEARCHNAMEBLOCKNUMBER#")) {
						blockelement.addContent(ctx
								.get("savesearchnameblocknumber") != null
								? ctx.get("savesearchnameblocknumber")
										.toString() : "");
					}

					if (elementID.contains("isShowGraphButtonTrue")) {
						if ((new ReportDataBlock(reportDefImpl))
								.isReportDataListEmpty(ctx)) {
							blockelement.detach();
						}

						if (reportDefImpl.getIsshowgraph()) {
							(new ReportFreeformBlock()).processGraphButton(ctx,
									blockelement, reportDefImpl);
						} else {
							blockelement.detach();
						}
					}

					if (elementID.equals("#REPORTING_GRAPH_POPUP#")) {
						(new ReportFreeformBlock()).processGraphPopup(ctx,
								blockelement, reportDefImpl);
					}

					if (type != null
							&& type.equals("a")
							&& blockelement.getAttributeValue("href") != null
							&& blockelement.getAttributeValue("href").contains(
									"managereport(")) {
						arg22 = blockelement.getAttributeValue("href");

						try {
							if (arg22.endsWith(");")) {
								arg22 = arg22
										.substring(
												arg22.indexOf("javascript:managereport(") + 24,
												arg22.lastIndexOf(");"));
							} else {
								arg22 = arg22
										.substring(
												arg22.indexOf("javascript:managereport(") + 24,
												arg22.lastIndexOf(")"));
							}

							e = arg22.split(",");
							String dynaKeys = e[0];
							dynaKeys = dynaKeys.substring(
									dynaKeys.indexOf("\'") + 1,
									dynaKeys.lastIndexOf("\'"));
							dynaValues = e[1];
							dynaValues = dynaValues.substring(
									dynaValues.indexOf("\'") + 1,
									dynaValues.lastIndexOf("\'"));
							method = e[2];
							method = method.substring(method.indexOf("\'") + 1,
									method.lastIndexOf("\'"));
							String page = e[3];
							page = page.substring(page.indexOf("\'") + 1,
									page.lastIndexOf("\'"));
							String newDynaValues = null;
							StringTokenizer tokens = new StringTokenizer(
									dynaValues, "|");

							while (true) {
								String newHref;
								if (!tokens.hasMoreTokens()) {
									if (newDynaValues != null) {
										if (!newDynaValues.startsWith("|")) {
											newDynaValues = "|" + newDynaValues;
										}

										if (!newDynaValues.endsWith("|")) {
											newDynaValues = newDynaValues + "|";
										}
									}

									newHref = "javascript:managereport(\'"
											+ dynaKeys + "\',\'"
											+ newDynaValues + "\',\'" + method
											+ "\',\'" + page + "\');";
									blockelement.setAttribute("href", newHref);
									break;
								}

								newHref = tokens.nextToken();
								if (ctx.containsKey(newHref)) {
									newHref = ctx.get(newHref) != null ? ctx
											.get(newHref).toString() : newHref;
								}

								if (newDynaValues == null) {
									newDynaValues = newHref;
								} else {
									newDynaValues = newDynaValues + "|"
											+ newHref;
								}
							}
						} catch (Exception arg21) {
							logger.error(
									ctx,
									"Unable to parse managereport link href to add dynamic values at run time due to error : "
											+ arg21.getMessage(), arg21);
						}
					}

					if (elementID.equals("#REPORTING_GRAPH_REPORTID#")) {
						(new ReportFreeformBlock()).processGraphReportID(ctx,
								blockelement);
					}

					if (elementID.equals("#REPORTING_GRAPH_AREA#")) {
						if (ctx.get("isgraph") != null
								&& ctx.get("isgraph").toString().equals("Y")) {
							(new ReportFreeformBlock()).parseManageGraphArea(
									ctx, blockelement, reportDefImpl);
							continue;
						}

						blockelement.detach();
						continue;
					}

					if (elementID.equals("#REPORTING_SHOW_GRAPH_LINK#")
							&& (ctx.get("reportID") == null || ctx
									.get("reportID").toString().equals(""))) {
						blockelement.detach();
						continue;
					}
				}

				parseHTML(ctx, blockelement, subchildren, rootElement, request,
						reportDefImpl);
			}
		}

	}

	private static void appendHiddenFields(Element form, Context ctx,
			String action, ReportdefImpl reportDefImpl) {
		ParseUtil.addAttribute(form, "action",
				action + ".do;jsessionid=" + ctx.get("jsessionid"));
		ParseUtil.addAttribute(form, "method", "post");
		form.addContent(ParseUtil.createInputElement("inet_method", "hidden",
				"", "inet_method"));
		form.addContent(ParseUtil.createInputElement("inet_page", "hidden", "",
				"inet_page"));
		form.addContent(ParseUtil.createInputElement("dynaKeys", "hidden", "",
				"dynaKeys"));
		form.addContent(ParseUtil.createInputElement("dynaValues", "hidden",
				"", "dynaValues"));
		form.addContent(ParseUtil.createInputElement("inet_context", "hidden",
				"", "inet_context"));
		form.addContent(ParseUtil.createInputElement("ctxmenuparam", "hidden",
				"", "ctxmenuparam"));
		form.addContent(ParseUtil.createInputElement("dateFormat", "hidden",
				ctx.get("dateFormat") != null ? ctx.get("dateFormat")
						.toString() : "", "ajax_field_dateFormat"));
		form.addContent(ParseUtil.createInputElement("dateFormatTS", "hidden",
				ctx.get("dateFormatTS") != null ? ctx.get("dateFormatTS")
						.toString() : "", "ajax_field_dateFormatTS"));
		if (ctx.get("REQUEST_AUTH_TOKEN") != null) {
			form.addContent(ParseUtil.createInputElement("REQUEST_AUTH_TOKEN",
					"hidden", ctx.get("REQUEST_AUTH_TOKEN").toString(),
					"REQUEST_AUTH_TOKEN"));
		}

		if (reportDefImpl != null && reportDefImpl.getDynakeys() != null
				&& !reportDefImpl.getDynakeys().equals("")) {
			String dynaKeys = reportDefImpl.getDynakeys();
			StringTokenizer tokens = new StringTokenizer(dynaKeys, ",");

			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				String value = ctx.get(token) != null ? ctx.get(token)
						.toString() : "";
				form.addContent(ParseUtil.createInputElement(token, "hidden",
						value, token));
			}
		}

	}

	private static LinkedHashMap<String, String> creatingExcelHeadersMap(
			Context ctx, ReportdefImpl reportDefImpl) throws Exception {
		LinkedHashMap map;
		String appendColName;
		if (reportDefImpl.getIsshowmultiplegrid()) {
			map = new LinkedHashMap();
			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				for (int arg13 = 0; arg13 < reportDefImpl
						.getOrderedColumnSetList().size(); ++arg13) {
					ColumnsetImpl arg14 = (ColumnsetImpl) reportDefImpl
							.getOrderedColumnSetList().get(arg13);
					if (arg14 != null
							&& arg14.getOrderedColumnsList().size() > 0) {
						for (int arg15 = 0; arg15 < arg14
								.getOrderedColumnsList().size(); ++arg15) {
							ColumnImpl arg16 = (ColumnImpl) arg14
									.getOrderedColumnsList().get(arg15);
							if (arg16.getIsreportdisplay()
									&& (new ReportUtils())
											.checkForHideShowColumn(ctx, arg16)
									&& (ctx.get("ISCOMINGFORDATAONLY") != null
											&& ctx.get("ISCOMINGFORDATAONLY")
													.toString().equals("Y") || (!reportDefImpl
											.getIsshowgroupbysearch() || ReportDataBlock
											.filterColumnForGroupBySearch(arg14
													.getOrderedColumnsList(),
													arg16, ctx.get("groupby")))
											&& (!reportDefImpl
													.getIsshowoutputcolumns() || ReportDataBlock
													.filterColumnForOutputColumns(
															arg14.getOrderedColumnsList(),
															arg16,
															ctx.get("outputcolumns"))))) {
								appendColName = arg16.getColumnlabel();
								String arg17;
								if (!StringUtils.isBlank(appendColName)) {
									if (appendColName.startsWith("eval:")) {
										try {
											arg17 = appendColName
													.substring(
															appendColName
																	.indexOf("eval:") + 5,
															appendColName
																	.length());
											RuleImpl ruleImpl1 = RulesResources
													.getInstance(ctx).findRule(
															arg17);
											appendColName = ruleImpl1.execute(
													ctx, (String[]) null)
													.toString();
										} catch (Exception arg10) {
											logger.error(ctx,
													"Unable to execute rule for column : "
															+ arg16.getName(), arg10);
										}
									}

									new ReportUtils();
									appendColName = getLabelData(appendColName);
								}

								arg17 = null;

								try {
									if (MetaDataResources.getInstance(ctx)
											.getField(arg16.getName())
											.getType().equals("N")) {
										if (arg17 == null) {
											arg17 = "_textalign=right";
										} else {
											arg17 = arg17 + "_textalign=right";
										}
									} else if (MetaDataResources
											.getInstance(ctx)
											.getField(arg16.getName())
											.getType().equals("D")) {
										if (arg17 == null) {
											arg17 = "_textalign=center";
										} else {
											arg17 = arg17 + "_textalign=center";
										}
									}

									if (!StringUtils.isBlank(arg16.getAlign())) {
										if (arg17 == null) {
											arg17 = "_textalign="
													+ arg16.getAlign();
										} else {
											arg17 = arg17 + "_textalign="
													+ arg16.getAlign();
										}
									}
								} catch (Exception arg9) {
									logger.error("Error in ReportUtils.creatingExcelHeadersMap", arg9);
									;
								}

								if (arg17 != null) {
									map.put(arg16.getName() + arg17 + "_"
											+ arg14.getName(), appendColName);
								} else {
									map.put(arg16.getName() + "_"
											+ arg14.getName(), appendColName);
								}
							}
						}
					}
				}
			}

			return map;
		} else {
			map = new LinkedHashMap();
			if (reportDefImpl != null
					&& reportDefImpl.getColumnsetList() != null
					&& reportDefImpl.getColumnsetList().size() > 0) {
				ColumnsetImpl columnSetImp = (ColumnsetImpl) reportDefImpl
						.getColumnsetList().get(0);
				if (columnSetImp != null
						&& columnSetImp.getOrderedColumnsList().size() > 0) {
					for (int i = 0; i < columnSetImp.getOrderedColumnsList()
							.size(); ++i) {
						ColumnImpl column = (ColumnImpl) columnSetImp
								.getOrderedColumnsList().get(i);
						if (column.getIsreportdisplay()
								&& (new ReportUtils()).checkForHideShowColumn(
										ctx, column)
								&& (ctx.get("ISCOMINGFORDATAONLY") != null
										&& ctx.get("ISCOMINGFORDATAONLY")
												.toString().equals("Y") || (!reportDefImpl
										.getIsshowgroupbysearch() || ReportDataBlock
										.filterColumnForGroupBySearch(
												columnSetImp
														.getOrderedColumnsList(),
												column, ctx.get("groupby")))
										&& (!reportDefImpl
												.getIsshowoutputcolumns() || ReportDataBlock
												.filterColumnForOutputColumns(
														columnSetImp
																.getOrderedColumnsList(),
														column,
														ctx.get("outputcolumns"))))) {
							String colLabel = column.getColumnlabel();
							if (!StringUtils.isBlank(colLabel)) {
								if (colLabel.startsWith("eval:")) {
									try {
										appendColName = colLabel.substring(
												colLabel.indexOf("eval:") + 5,
												colLabel.length());
										RuleImpl ruleImpl = RulesResources
												.getInstance(ctx).findRule(
														appendColName);
										colLabel = ruleImpl.execute(ctx,
												(String[]) null).toString();
									} catch (Exception arg12) {
										logger.error(ctx,
												"Unable to execute rule for column : "
														+ column.getName(), arg12);
									}
								}

								new ReportUtils();
								colLabel = getLabelData(colLabel);
							}

							appendColName = null;

							try {
								if (MetaDataResources.getInstance(ctx)
										.getField(column.getName()).getType()
										.equals("N")) {
									if (appendColName == null) {
										appendColName = "_textalign=right";
									} else {
										appendColName = appendColName
												+ "_textalign=right";
									}
								} else if (MetaDataResources.getInstance(ctx)
										.getField(column.getName()).getType()
										.equals("D")) {
									if (appendColName == null) {
										appendColName = "_textalign=center";
									} else {
										appendColName = appendColName
												+ "_textalign=center";
									}
								}

								if (!StringUtils.isBlank(column.getAlign())) {
									if (appendColName == null) {
										appendColName = "_textalign="
												+ column.getAlign();
									} else {
										appendColName = appendColName
												+ "_textalign="
												+ column.getAlign();
									}
								}
							} catch (Exception arg11) {
								logger.error("Error in ReportUtils.creatingExcelHeadersMap", arg11);
								;
							}

							if (appendColName != null) {
								map.put(column.getName() + appendColName,
										colLabel);
							} else {
								map.put(column.getName(), colLabel);
							}
						}
					}
				}
			}

			return map;
		}
	}

	private static void filterDataXml(Context ctx, Context pdfContext,
			ReportdefImpl reportDefImpl) throws Exception {
		new ReportUtils();
		pdfContext.put("report_description",
				getLabelData(reportDefImpl.getDescription()));
		if (ctx.get("isdynacolumns") != null
				&& ctx.get("isdynacolumns").toString().equals("Y")) {
			ArrayList arg28 = new ArrayList();
			arg28.add(creatingExcelDynamicHeadersMap(ctx, false));
			pdfContext.put("report_data_list_1", arg28);
			filterSelectedFilterDataXml(ctx, pdfContext, reportDefImpl);
		} else {
			if (ctx.get("report_data_list_1") != null
					&& ctx.get("report_data_list_1") instanceof List) {
				List dataList = (List) ctx.get("report_data_list_1");
				List columnList = null;
				if (reportDefImpl.getFirstColumnset() != null
						&& reportDefImpl.getFirstColumnset()
								.getOrderedColumnsList() != null) {
					columnList = reportDefImpl.getFirstColumnset()
							.getOrderedColumnsList();
				}

				ArrayList newDataElementList = new ArrayList();
				String sortOrderHeader = null;
				String sortOrder = null;
				if (ctx.get("SortOrder") != null) {
					sortOrder = ctx.get("SortOrder").toString();
					if (sortOrder.contains(",")) {
						sortOrder = sortOrder.substring(0,
								sortOrder.indexOf(","));
						sortOrderHeader = (new ReportDataBlock(reportDefImpl))
								.getSortOrderHeader(columnList, sortOrder);
					}
				}

				LinkedHashMap headerMap = new LinkedHashMap();
				ColumnImpl itr;
				if (columnList != null) {
					for (int keySet = 0; keySet < columnList.size(); ++keySet) {
						itr = (ColumnImpl) columnList.get(keySet);
						if (itr.getIsreportdisplay()) {
							if (!(new ReportUtils()).checkForHideShowColumn(
									ctx, itr)) {
								headerMap.put(itr.getName(), "HIDDEN");
							} else {
								if (headerMap.size() <= 0
										&& sortOrderHeader != null) {
									headerMap.put("sort1", sortOrderHeader);
								}

								String isGrandTotalRowExists = itr
										.getColumnlabel();
								if (!StringUtils.isBlank(isGrandTotalRowExists)) {
									if (isGrandTotalRowExists
											.startsWith("eval:")) {
										try {
											String i = isGrandTotalRowExists
													.substring(
															isGrandTotalRowExists
																	.indexOf("eval:") + 5,
															isGrandTotalRowExists
																	.length());
											RuleImpl map = RulesResources
													.getInstance(ctx).findRule(
															i);
											isGrandTotalRowExists = map
													.execute(ctx,
															(String[]) null)
													.toString();
										} catch (Exception arg26) {
											logger.error(ctx,
													"Unable to execute rule for column : "
															+ itr.getName(), arg26);
										}
									}

									new ReportUtils();
									isGrandTotalRowExists = getLabelData(isGrandTotalRowExists);
								}

								if (ctx.get("ISCOMINGFORDATAONLY") == null
										|| !ctx.get("ISCOMINGFORDATAONLY")
												.toString().equals("Y")) {
									if (reportDefImpl.getIsshowgroupbysearch()) {
										new ReportDataBlock(reportDefImpl);
										if (!ReportDataBlock
												.filterColumnForGroupBySearch(
														columnList, itr,
														ctx.get("groupby"))) {
											headerMap.put(itr.getName(),
													"HIDDEN");
											continue;
										}
									}

									if (reportDefImpl.getIsshowoutputcolumns()) {
										new ReportDataBlock(reportDefImpl);
										if (!ReportDataBlock
												.filterColumnForOutputColumns(
														columnList,
														itr,
														ctx.get("outputcolumns"))) {
											headerMap.put(itr.getName(),
													"HIDDEN");
											continue;
										}
									}
								}

								headerMap.put(itr.getName(),
										isGrandTotalRowExists);
							}
						}
					}
				}

				newDataElementList.add(headerMap);
				Set arg29 = null;
				itr = null;
				if (dataList != null && dataList.size() > 0) {
					boolean arg32 = false;

					for (int arg31 = 0; arg31 < dataList.size(); ++arg31) {
						HashMap arg33 = (HashMap) dataList.get(arg31);
						HashMap dataMap = new HashMap();
						LinkedHashMap newMap = new LinkedHashMap();
						boolean isFirstHeader = true;
						if ((new ReportDataBlock(reportDefImpl))
								.isGrandTotalRowExists(arg33)) {
							arg32 = true;
						} else {
							arg32 = false;
						}

						arg29 = headerMap.keySet();
						Iterator arg30 = arg29.iterator();

						while (true) {
							while (arg30.hasNext()) {
								String key = (String) arg30.next();
								if (((String) headerMap.get(key))
										.equals("HIDDEN")) {
									dataMap.put(key, "HIDDEN");
								} else {
									if (sortOrderHeader == null) {
										dataMap.put("sort1", "");
									}

									ColumnImpl column = reportDefImpl
											.getFirstColumnset().getColumn(key);
									String property = column.getMetaproperty();
									if (property != null
											&& property.contains(".")) {
										property = property.substring(
												property.lastIndexOf(".") + 1,
												property.length());
									}

									String value = arg33.get(column.getName()
											.toString()) != null ? arg33.get(
											column.getName().toString())
											.toString() : "";
									if (arg33.get(key) != null
											&& arg33.get(key) instanceof ClobImpl) {
										try {
											BufferedReader reader = null;
											StringBuffer strbuf = null;
											ClobImpl clobValue = (ClobImpl) arg33
													.get(key);
											reader = new BufferedReader(
													clobValue
															.getCharacterStream());
											boolean endFlag = true;
											String line = null;
											strbuf = new StringBuffer(300);

											while (endFlag) {
												line = reader.readLine();
												if (line != null) {
													strbuf.append(line).append(
															"\n");
												}

												if (line == null) {
													endFlag = false;
												}
											}

											value = strbuf.toString();
										} catch (Exception arg27) {
											logger.error("Error in ReportUtils.filterDataXml", arg27);
											;
										}
									}

									try {
										if (MetaDataResources.getInstance(ctx)
												.getField(property).getType()
												.equals("N")) {
											value = DataUtils.getAmountFormat(
													value).toString();
											if (!StringUtils.isBlank(value)
													&& value.startsWith("-")) {
												value = "("
														+ value.substring(1,
																value.length())
														+ ")";
											}
										}
									} catch (Exception arg25) {
										logger.error("Error in ReportUtils.filterDataXml", arg25);
										;
									}

									if (!arg32) {
										dataMap.put(column.getName(), value);
									} else if (isFirstHeader) {
										isFirstHeader = false;
										if (sortOrderHeader == null) {
											dataMap.put(column.getName(),
													"Grand Total");
											dataMap.put("isgrandrow", "Y");
											fillEmptyRow(columnList,
													sortOrderHeader,
													newDataElementList);
										} else {
											dataMap.put("sort1", "Grand Total");
											dataMap.put("isgrandrow", "Y");
											dataMap.put(
													column.getName(),
													value == null
															|| !value
																	.contains("Total")
															&& !value
																	.contains("total")
															? ""
															: "");
											fillEmptyRow(columnList,
													sortOrderHeader,
													newDataElementList);
										}
									} else {
										dataMap.put(
												column.getName(),
												value != null
														&& !value
																.contains("Total")
														&& !value
																.contains("total")
														? value
														: "");
									}
								}
							}

							newMap.putAll(dataMap);
							newDataElementList.add(newMap);
							break;
						}
					}
				}

				pdfContext.put("report_data_list_1", newDataElementList);
			}

			filterSelectedFilterDataXml(ctx, pdfContext, reportDefImpl);
		}
	}

	private static void fillEmptyRow(List columnList, String sortOrderHeader,
			List newDataElementList) throws Exception {
		LinkedHashMap headerMap = new LinkedHashMap();

		for (int j = 0; j < columnList.size(); ++j) {
			ColumnImpl column = (ColumnImpl) columnList.get(j);
			if (column.getIsreportdisplay()) {
				if (headerMap.size() <= 0) {
					headerMap.put("sort1", "");
				}

				headerMap.put(column.getName(), "");
			}
		}

		newDataElementList.add(headerMap);
	}

	public static void filterSelectedFilterDataXml(Context ctx,
			Context pdfContext, ReportdefImpl reportDefImpl) throws Exception {
		ArrayList filtersList = new ArrayList();
		if (ctx.get("selected_filter_data") != null) {
			int index = 0;
			int count = 1;
			LinkedHashMap map = null;

			for (StringTokenizer tokens = new StringTokenizer(ctx.get(
					"selected_filter_data").toString(), "|", false); tokens
					.hasMoreElements(); ++count) {
				String token = tokens.nextToken();
				String key = token.substring(0, token.indexOf("="));
				String value = token.substring(token.indexOf("=") + 1,
						token.length());
				String e;
				if (ctx.get("popupsearchcolumns") != null
						&& !ctx.get("popupsearchcolumns").toString().equals("")) {
					StringTokenizer colLabel = new StringTokenizer(ctx.get(
							"popupsearchcolumns").toString(), ",");

					while (colLabel.hasMoreTokens()) {
						e = colLabel.nextToken();
						if (e.equals(key) && value.contains("=")) {
							value = value.substring(0, value.indexOf("="));
						}
					}
				}

				if (key.equalsIgnoreCase("groupby")) {
					key = "Group By";
					value = parseGroupByColumnInSavedSearch(reportDefImpl,
							value);
				} else {
					if (reportDefImpl.getFirstColumnset() != null
							&& reportDefImpl.getFirstColumnset().getColumn(key) != null) {
						key = reportDefImpl.getFirstColumnset().getColumn(key)
								.getColumnlabel();
					}

					if (value != null && value.equals("null")) {
						value = null;
					}
				}

				String arg14 = key;
				if (!StringUtils.isBlank(key)) {
					if (key.startsWith("eval:")) {
						try {
							e = arg14.substring(arg14.indexOf("eval:") + 5,
									arg14.length());
							RuleImpl ruleImpl = RulesResources.getInstance(ctx)
									.findRule(e);
							arg14 = ruleImpl.execute(ctx, (String[]) null)
									.toString();
						} catch (Exception arg13) {
							logger.error(ctx,
									"Unable to execute rule for column : "
											+ key, arg13);
						}
					}

					new ReportUtils();
					arg14 = getLabelData(arg14);
				}

				if (index % 2 == 0) {
					map = new LinkedHashMap();
					filtersList.add(map);
					count = 1;
				}

				map.put("col" + count, arg14);
				map.put("val" + count, value);
				++index;
			}
		}

		pdfContext.put("selected_filters_list_1", filtersList);
	}

	private static void getPoneBranchReportsList(Context ctx,
			ReportdefImpl reportdefImpl) throws Exception {
		if (reportdefImpl.getIsshowdefaultreportdatalist()) {
			ctx.put("reportID", reportdefImpl.getName());
			ctx.put("ispaginationrequired", "Y");
			ctx.put("PAGE_SIZE",
					ctx.get("PAGE_SIZE") != null
							&& !ctx.get("PAGE_SIZE").toString().equals("")
							? ctx.get("PAGE_SIZE").toString()
							: "25");
			ctx.put("PAGE_NUMBER",
					ctx.get("PAGE_NUMBER") != null
							&& !ctx.get("PAGE_NUMBER").toString().equals("")
							? ctx.get("PAGE_NUMBER").toString()
							: "1");
			ctx.remove("report_data_list_1");
			boolean isScheduledBatchVisibleToAll = true;
			if (ctx.get("isScheduledBatchVisibleToAll") != null
					&& "N".equals(ctx.get("isScheduledBatchVisibleToAll")
							.toString())) {
				isScheduledBatchVisibleToAll = false;
			}

			(new SetParametersForStoredProcedures()).setParametersInContext(
					ctx, "PAGE_SIZE,PAGE_NUMBER");
			List poneBatchReportList = SqlResources.getSqlMapProcessor(ctx)
					.select("report_search_columns.getPoneBatchReports_p_java",
							ctx);
			if (poneBatchReportList != null && poneBatchReportList.size() > 0) {
				label167 : for (int i = 0; i < poneBatchReportList.size(); ++i) {
					Map row = (Map) poneBatchReportList.get(i);
					String search_xml_desc;
					if (i == 0) {
						search_xml_desc = row.get("pagination_xml") != null
								? row.get("pagination_xml").toString()
								: null;
						if (!StringUtils.isBlank(search_xml_desc)) {
							populateContextWithPaginationData(ctx,
									search_xml_desc);
						}
					}

					if (!isScheduledBatchVisibleToAll
							&& row.get("user_id") != null
							&& !row.get("user_id")
									.toString()
									.equalsIgnoreCase(
											ctx.get("user_id").toString())) {
						poneBatchReportList.remove(row);
						--i;
					} else {
						if (row.get("archive_date") != null
								&& row.get("archive_date").toString()
										.contains("1900-01-01")) {
							row.put("archive_date", (Object) null);
						}

						if (row.get("search_xml_desc") != null
								&& !row.get("search_xml_desc").toString()
										.equals("")) {
							search_xml_desc = null;
							String row_search_xml_desc = row.get(
									"search_xml_desc").toString();
							StringTokenizer tokens = new StringTokenizer(
									row_search_xml_desc, "|");

							while (true) {
								String key;
								String value;
								String arg18;
								RuleImpl arg19;
								while (true) {
									String token;
									do {
										do {
											if (!tokens.hasMoreTokens()) {
												if (search_xml_desc != null
														&& search_xml_desc
																.endsWith("; ")) {
													search_xml_desc = search_xml_desc
															.substring(
																	0,
																	search_xml_desc
																			.length());
												}

												row.put("search_xml_desc",
														search_xml_desc);
												continue label167;
											}

											token = tokens.nextToken();
										} while (token == null);
									} while (token.indexOf("=") == -1);

									key = token
											.substring(0, token.indexOf("="));
									value = token.substring(
											token.indexOf("=") + 1,
											token.length());
									if (key.equalsIgnoreCase("groupby")) {
										key = "Group By";
										value = parseGroupByColumnInSavedSearch(
												reportdefImpl, value);
										break;
									}

									try {
										ColumnImpl colLabel = null;
										if (reportdefImpl
												.getIsshowmultiplegrid()) {
											if (reportdefImpl
													.getOrderedColumnSetList() != null
													&& reportdefImpl
															.getOrderedColumnSetList()
															.size() > 0) {
												for (int e = 0; e < reportdefImpl
														.getOrderedColumnSetList()
														.size(); ++e) {
													ColumnsetImpl ruleImpl = (ColumnsetImpl) reportdefImpl
															.getOrderedColumnSetList()
															.get(e);
													colLabel = ruleImpl
															.getColumn(key);
													if (colLabel != null) {
														break;
													}
												}
											}
										} else {
											colLabel = ((ColumnsetImpl) reportdefImpl
													.getColumnsetList().get(0))
													.getColumn(key);
										}

										if (colLabel == null)
											continue;

										if (!StringUtils.isBlank(colLabel
												.getEval())) {
											arg18 = colLabel.getEval();
											if (arg18.startsWith("eval:")) {
												try {
													arg18 = arg18
															.substring(
																	arg18.indexOf("eval:") + 5,
																	arg18.length());
													arg19 = RulesResources
															.getInstance(ctx)
															.findRule(arg18);
													if (!arg19.evaluate(ctx,
															(String[]) null)) {
														continue;
													}
												} catch (Exception arg15) {
													logger.error(
															ctx,
															"Unable to execute rule "
																	+ colLabel
																			.getEval()
																	+ " due to error : "
																	+ arg15.getMessage(), arg15);
												}
											} else {
												HashMap arg20 = CacheManager
														.get("Tabs_Conf") == null
														? null
														: (HashMap) CacheManager
																.get("Tabs_Conf");
												if (arg20 != null
														&& arg20.containsKey(arg18)
														&& arg20.get(arg18) != null
														&& "Y".equalsIgnoreCase(arg20
																.get(arg18)
																.toString())) {
													continue;
												}
											}
										}
									} catch (Exception arg16) {
										logger.error(ctx,
												"Error while getting label for default display column due to error : "
														+ arg16.getMessage(), arg16);
									}

									if (value != null && value.equals("Select")) {
										value = "All";
									}
									break;
								}

								String arg17 = key;
								if (!StringUtils.isBlank(key)) {
									if (key.startsWith("eval:")) {
										try {
											arg18 = arg17.substring(
													arg17.indexOf("eval:") + 5,
													arg17.length());
											arg19 = RulesResources.getInstance(
													ctx).findRule(arg18);
											arg17 = arg19.execute(ctx,
													(String[]) null).toString();
										} catch (Exception arg14) {
											logger.error(ctx,
													"Unable to execute rule for column : "
															+ key, arg14);
										}
									}

									new ReportUtils();
									arg17 = getLabelData(arg17);
								}

								if (search_xml_desc == null) {
									search_xml_desc = arg17 + " = " + value;
								} else {
									search_xml_desc = search_xml_desc + "; "
											+ arg17 + " = " + value;
								}
							}
						}
					}
				}
			}

			ctx.put("report_data_list_1", poneBatchReportList);
		}
	}

	public static void submitExportPopup(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		if (ctx.get("exporttype") != null
				&& !ctx.get("exporttype").toString().equals("")) {
			(new ReportUtils()).getDynaKeysValues(ctx, reportDefImpl,
					(ColumnImpl) null);
			if (ctx.get("outputtype") != null) {
				List workflowList;
				int i;
				ReportworkflowImpl reportWorkflowImpl;
				String eval;
				boolean isExecuteWorkflow;
				if (!ctx.get("exporttype").toString().equals("1")
						&& !ctx.get("exporttype").toString().equals("3")) {
					Context arg11 = new Context();
					arg11.putAll(ctx);
					(new RequestProcessor()).populateContext(arg11,
							arg11.get("search_xml").toString());
					(new RequestProcessor()).populateContext(ctx,
							ctx.get("search_xml").toString());
					arg11.put("ispaginationrequired",
							reportDefImpl.getIsreportdatapagination());
					arg11.put("reportID", reportDefImpl.getName());
					arg11.put("last_updated_by", ctx.get("user_id"));
					arg11.put("Type", ctx.get("outputtype"));
					workflowList = reportDefImpl.getReportworkflowList();
					if (workflowList != null) {
						for (i = 0; i < workflowList.size(); ++i) {
							reportWorkflowImpl = (ReportworkflowImpl) workflowList
									.get(i);
							if ((reportWorkflowImpl.getIsexecuteonview() == null || !reportWorkflowImpl
									.getIsexecuteonview().equals("Y"))
									&& reportWorkflowImpl
											.getStoredprocedureoperationList() != null
									&& reportWorkflowImpl
											.getStoredprocedureoperationList()
											.size() > 0) {
								arg11.put(
										"project_name",
										((StoredprocedureoperationImpl) reportWorkflowImpl
												.getStoredprocedureoperationList()
												.get(0)).getApplid());
							}
						}
					}

					(new SetParametersForStoredProcedures())
							.setParametersInContext(arg11,
									"user_id,last_updated_ts,last_updated_by");
					SqlResources
							.getSqlMapProcessor(arg11)
							.insert("report_search_columns.insert_pone_batch_reports_p_java",
									arg11);
					ctx.put("pageMsg", MessageResources.getInstance(ctx)
							.getMessage("reportScheduleSuccessfullyMsg")
							.getMessage());
					ctx.put("ispaginationrequired",
							reportDefImpl.getIsreportdatapagination());
					workflowList = reportDefImpl.getReportworkflowList();
					if (workflowList != null) {
						for (i = 0; i < workflowList.size(); ++i) {
							reportWorkflowImpl = (ReportworkflowImpl) workflowList
									.get(i);
							if (reportWorkflowImpl.getIsexecuteonview() == null
									|| !reportWorkflowImpl.getIsexecuteonview()
											.equals("Y")) {
								eval = reportWorkflowImpl.getEval();
								isExecuteWorkflow = true;
								if (!StringUtils.isBlank(eval)) {
									try {
										isExecuteWorkflow = RulesResources
												.getInstance(ctx)
												.findRule(eval)
												.evaluate(ctx, (String[]) null);
									} catch (Exception arg9) {
										logger.error(
												ctx,
												"Unable to execute rule : "
														+ eval
														+ " for report : "
														+ reportDefImpl
																.getName(), arg9);
									}
								}

								if (isExecuteWorkflow) {
									if (reportWorkflowImpl
											.getCheckfornullparams() != null
											&& !reportWorkflowImpl
													.getCheckfornullparams()
													.equals("")) {
										checkForNullParams(
												ctx,
												reportWorkflowImpl
														.getCheckfornullparams());
									}

									ctx.put("reportoutputtype",
											reportWorkflowImpl.getOutputtype());
									populateContextWithPopupSearchColumn(ctx,
											reportDefImpl);
									reportWorkflowImpl.executeWorkflow(ctx,
											reportWorkflowImpl.getOutputtype(),
											reportDefImpl.getName(),
											reportWorkflowImpl
													.getIsdynacolumns());
								}
							}
						}
					}
				} else {
					HttpServletResponse newCtx = (HttpServletResponse) ctx
							.get("DocumentResponse");
					(new RequestProcessor()).populateContext(ctx,
							ctx.get("search_xml").toString());
					ctx.put("ispaginationrequired",
							reportDefImpl.getIsreportdatapagination());
					workflowList = reportDefImpl.getReportworkflowList();
					if (workflowList != null) {
						for (i = 0; i < workflowList.size(); ++i) {
							reportWorkflowImpl = (ReportworkflowImpl) workflowList
									.get(i);
							if (reportWorkflowImpl.getIsexecuteonview() == null
									|| !reportWorkflowImpl.getIsexecuteonview()
											.equals("Y")) {
								eval = reportWorkflowImpl.getEval();
								isExecuteWorkflow = true;
								if (!StringUtils.isBlank(eval)) {
									try {
										isExecuteWorkflow = RulesResources
												.getInstance(ctx)
												.findRule(eval)
												.evaluate(ctx, (String[]) null);
									} catch (Exception arg10) {
										logger.error(
												ctx,
												"Unable to execute rule : "
														+ eval
														+ " for report : "
														+ reportDefImpl
																.getName(), arg10);
									}
								}

								if (isExecuteWorkflow) {
									if (reportWorkflowImpl
											.getCheckfornullparams() != null
											&& !reportWorkflowImpl
													.getCheckfornullparams()
													.equals("")) {
										checkForNullParams(
												ctx,
												reportWorkflowImpl
														.getCheckfornullparams());
									}

									ctx.put("reportoutputtype",
											reportWorkflowImpl.getOutputtype());
									populateContextWithPopupSearchColumn(ctx,
											reportDefImpl);
									if (ctx.get("exporttype").toString()
											.equals("3")) {
										ctx.put("PAGE_NUMBER", (Object) null);
										ctx.put("PAGE_SIZE", (Object) null);
									}

									reportWorkflowImpl.executeWorkflow(ctx,
											reportWorkflowImpl.getOutputtype(),
											reportDefImpl.getName(),
											reportWorkflowImpl
													.getIsdynacolumns());
									if (reportWorkflowImpl.getIsdynacolumns()) {
										ctx.put("isdynacolumns", "Y");
									}
								}
							}
						}
					}

					String arg12;
					if (ctx.get("outputtype").equals("E")) {
						newCtx.setContentType("application/vnd.ms-excel");
						ctx.put("isDynamicList", "Y");
						LinkedHashMap arg13;
						if (reportDefImpl.getIsshowmultiplegrid()) {
							arg12 = null;
							arg13 = creatingExcelHeadersMapForMultipleGrid(ctx,
									reportDefImpl);
							ctx.put("report_data_list_1_headers", arg13);
						} else {
							arg12 = null;
							if (ctx.get("isdynacolumns") != null
									&& ctx.get("isdynacolumns").toString()
											.equals("Y")) {
								arg13 = creatingExcelDynamicHeadersMap(ctx,
										true);
							} else {
								arg13 = creatingExcelHeadersMap(ctx,
										reportDefImpl);
							}

							ctx.put("report_data_list_1_headers", arg13);
						}

						arg12 = reportDefImpl.getDescription();
						arg12 = arg12.replace("/", "_").replace("\\", "_")
								.replace(":", "_").replace("*", "_")
								.replace("?", "_").replace("\"", "_")
								.replace("<", "_").replace(">", "_")
								.replace("|", "_");
						StringBuilder arg10001 = new StringBuilder(
								"attachment;filename=");
						new ReportUtils();
						newCtx.setHeader(
								"Content-disposition",
								arg10001.append(getLabelData(arg12))
										.append("_")
										.append(System.currentTimeMillis())
										.append(".xlsx").toString());
						(new DocumentImpl()).downloadExcel(ctx, request,
								newCtx, SystemProperties.getInstance()
										.getString("html.basedir")
										+ "//templates//exceltemplate.xls");
						return;
					}

					if (ctx.get("outputtype").equals("P")) {
						ctx.put("isShowFiltersSearchTrue", Boolean
								.valueOf(reportDefImpl.getIsshowfiltersearch()));
						arg12 = SystemProperties.getInstance().getString(
								"html.basedir")
								+ "documenttempxml.xml";
						File arg14 = new File(arg12);
						if (arg14.exists()) {
							arg14.delete();
							arg14.createNewFile();
						}

						Context arg15 = new Context();
						arg15.setProject(ctx.getProject());
						if (ctx.get("isdynacolumns") != null
								&& ctx.get("isdynacolumns").toString()
										.equals("Y")) {
							arg15.put("reportID", "DYNACOLUMNS");
						} else {
							arg15.put("reportID", ctx.get("reportID"));
						}

						arg15.put("isShowFiltersSearchTrue",
								ctx.get("isShowFiltersSearchTrue"));
						filterDataXml(ctx, arg15, reportDefImpl);
						(new DocumentImpl()).populateDataXml(ctx, arg15, arg12);
						String arg16 = reportDefImpl.getDescription();
						arg16 = arg16.replace("/", "_").replace("\\", "_")
								.replace(":", "_").replace("*", "_")
								.replace("?", "_").replace("\"", "_")
								.replace("<", "_").replace(">", "_")
								.replace("|", "_");
						new ReportUtils();
						ctx.put("filename",
								getLabelData(arg16) + "_"
										+ System.currentTimeMillis());
						(new XML2PDF()).convertPOToPDF(SystemProperties
								.getInstance().getString("html.basedir")
								+ "//foxsl//performancereport.xsl", arg12, ctx,
								request, newCtx,
								(ServletContextURIResolver) ctx
										.get("DocumentUriResolver"),
								(ServletContext) ctx
										.get("DocumentServletContext"));
						if (arg14.exists()) {
							arg14.delete();
						}
					}
				}
			}

		}
	}

	public static void submitBatch(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		Context newCtx = new Context();
		newCtx.putAll(ctx);
		newCtx.put("reportID", reportDefImpl.getName());
		newCtx.put("last_updated_by", ctx.get("user_id"));
		newCtx.put("Type", ctx.get("outputtype"));
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if ((reportWorkflowImpl.getIsexecuteonview() == null || !reportWorkflowImpl
						.getIsexecuteonview().equals("Y"))
						&& reportWorkflowImpl.getStoredprocedureoperationList() != null
						&& reportWorkflowImpl.getStoredprocedureoperationList()
								.size() > 0) {
					newCtx.put("project_name",
							((StoredprocedureoperationImpl) reportWorkflowImpl
									.getStoredprocedureoperationList().get(0))
									.getApplid());
				}
			}
		}

		(new SetParametersForStoredProcedures()).setParametersInContext(newCtx,
				"user_id,last_updated_ts,last_updated_by");
		SqlResources.getSqlMapProcessor(newCtx).insert(
				"report_search_columns.insert_pone_batch_reports_p_java",
				newCtx);
		ctx.put("pageMsg",
				MessageResources.getInstance(ctx)
						.getMessage("reportScheduleSuccessfullyMsg")
						.getMessage());
	}

	private static void checkForNullParams(Context ctx,
			String checkForNullParams) throws Exception {
		if (!StringUtils.isBlank(checkForNullParams)) {
			StringTokenizer tokens = new StringTokenizer(checkForNullParams,
					",");

			while (true) {
				String token;
				do {
					if (!tokens.hasMoreTokens()) {
						return;
					}

					token = tokens.nextToken();
				} while (ctx.get(token) != null
						&& !ctx.get(token).toString().equals(""));

				ctx.put(token, (Object) null);
			}
		}
	}

	public static void listPagination(Context ctx, ReportdefImpl reportDefImpl,
			HttpServletRequest request) throws Exception {
		if (ctx.get("TEMPLATE_NAME") != null
				&& ctx.get("TEMPLATE_NAME").toString()
						.equals("managereportdatatemplate")) {
			(new RequestProcessor()).populateContext(ctx, ctx.get("search_xml")
					.toString());
			ctx.put("ispaginationrequired",
					reportDefImpl.getIsreportdatapagination());
			List workflowList = reportDefImpl.getReportworkflowList();
			if (workflowList != null) {
				for (int i = 0; i < workflowList.size(); ++i) {
					ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
							.get(i);
					if (reportWorkflowImpl.getIsexecuteonview() == null
							|| !reportWorkflowImpl.getIsexecuteonview().equals(
									"Y")) {
						String eval = reportWorkflowImpl.getEval();
						boolean isExecuteWorkflow = true;
						if (!StringUtils.isBlank(eval)) {
							try {
								isExecuteWorkflow = RulesResources
										.getInstance(ctx).findRule(eval)
										.evaluate(ctx, (String[]) null);
							} catch (Exception arg8) {
								logger.error(ctx, "Unable to execute rule : "
										+ eval + " for report : "
										+ reportDefImpl.getName(), arg8);
							}
						}

						if (isExecuteWorkflow) {
							if (reportWorkflowImpl.getCheckfornullparams() != null
									&& !reportWorkflowImpl
											.getCheckfornullparams().equals("")) {
								checkForNullParams(ctx,
										reportWorkflowImpl
												.getCheckfornullparams());
							}

							ctx.put("reportoutputtype",
									reportWorkflowImpl.getOutputtype());
							populateContextWithPopupSearchColumn(ctx,
									reportDefImpl);
							reportWorkflowImpl.executeWorkflow(ctx,
									reportWorkflowImpl.getOutputtype(),
									reportDefImpl.getName(),
									reportWorkflowImpl.getIsdynacolumns());
						}
					}
				}
			}
		} else {
			getSavedSearchList(ctx, reportDefImpl);
			getPoneBranchReportsList(ctx, reportDefImpl);
		}

	}

	private static void executeDefaultView(Context ctx,
			ReportdefImpl reportDefImpl) throws Exception {
		getSavedSearchList(ctx, reportDefImpl);
		getPoneBranchReportsList(ctx, reportDefImpl);
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() != null
						&& reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg7) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg7);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

	}

	public static void downloadBatchReport(Context ctx,
			ReportdefImpl reportDefImpl, HttpServletRequest request)
			throws Exception {
		try {
			String resp = "appl." + ctx.getProject()
					+ ".commission.reports.output.path";
			DocumentImpl rb1 = new DocumentImpl();
			rb1.setOutput("excel");
			rb1.setDownloadfilepath(resp);
			rb1.setFilenamefield("file_name");
			rb1.execute(ctx);
		} catch (Exception arg7) {
			logger.error(ctx,
					"Unable to download file from server path due to error : "
							+ arg7.getMessage(), arg7);
			HttpServletResponse rb = (HttpServletResponse) ctx
					.get("DocumentResponse");
			byte[] fileName = new byte[0];
			rb.setContentLength(fileName.length);
			String sout = null;
			if (sout == null && ctx.get("file_name") != null
					&& !ctx.get("file_name").toString().equals("")) {
				sout = ctx.get("file_name").toString();
			}

			if (sout == null) {
				sout = ctx.getProject();
			}

			rb.setContentType(((ServletContext) ctx
					.get("DocumentServletContext")).getMimeType(sout));
			rb.setHeader("content-disposition", "attachment;filename=" + sout);
			ServletOutputStream sout1 = rb.getOutputStream();
			sout1.write(fileName);
			sout1.close();
		}

		if (ctx.get("inet_errors_list") != null) {
			logger.error(ctx,
					"Unable to download file from server path due to error : "
							+ ctx.get("inet_errors_list"));
			HttpServletResponse resp1 = (HttpServletResponse) ctx
					.get("DocumentResponse");
			byte[] rb2 = new byte[0];
			resp1.setContentLength(rb2.length);
			String fileName1 = null;
			if (fileName1 == null && ctx.get("file_name") != null
					&& !ctx.get("file_name").toString().equals("")) {
				fileName1 = ctx.get("file_name").toString();
			}

			if (fileName1 == null) {
				fileName1 = ctx.getProject();
			}

			resp1.setContentType(((ServletContext) ctx
					.get("DocumentServletContext")).getMimeType(fileName1));
			resp1.setHeader("content-disposition", "attachment;filename="
					+ fileName1);
			ServletOutputStream sout2 = resp1.getOutputStream();
			sout2.write(rb2);
			sout2.close();
		}

	}

	public static String parseGroupByColumnInSavedSearch(
			ReportdefImpl reportDefImpl, String value) throws Exception {
		if (StringUtils.isBlank(value)) {
			return "";
		} else {
			value = value.concat(",");
			String newValue = null;
			String arg9999;
			StringBuilder arg9;
			if (reportDefImpl.getIsshowmultiplegrid()) {
				if (reportDefImpl.getOrderedColumnSetList() != null
						&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
					for (int colSet = 0; colSet < reportDefImpl
							.getOrderedColumnSetList().size(); ++colSet) {
						ColumnsetImpl i = (ColumnsetImpl) reportDefImpl
								.getOrderedColumnSetList().get(colSet);

						for (int colImpl = 0; colImpl < i
								.getOrderedColumnsList().size(); ++colImpl) {
							ColumnImpl colImpl1 = (ColumnImpl) i
									.getOrderedColumnsList().get(colImpl);
							if (colImpl1.getIsgroupbydisplay()
									&& value.contains(colImpl1.getName() + ",")) {
								if (newValue == null) {
									new ReportUtils();
									arg9999 = getLabelData(colImpl1
											.getColumnlabel());
								} else {
									arg9 = (new StringBuilder(
											String.valueOf(newValue)))
											.append(", ");
									new ReportUtils();
									arg9999 = arg9.append(
											getLabelData(colImpl1
													.getColumnlabel()))
											.toString();
								}

								newValue = arg9999;
							}
						}
					}
				}
			} else if (reportDefImpl.getColumnsetList() != null) {
				ColumnsetImpl arg6 = (ColumnsetImpl) reportDefImpl
						.getColumnsetList().get(0);

				for (int arg7 = 0; arg7 < arg6.getOrderedColumnsList().size(); ++arg7) {
					ColumnImpl arg8 = (ColumnImpl) arg6.getOrderedColumnsList()
							.get(arg7);
					if (arg8.getIsgroupbydisplay()
							&& value.contains(arg8.getName() + ",")) {
						if (newValue == null) {
							new ReportUtils();
							arg9999 = getLabelData(arg8.getColumnlabel());
						} else {
							arg9 = (new StringBuilder(String.valueOf(newValue)))
									.append(", ");
							new ReportUtils();
							arg9999 = arg9.append(
									getLabelData(arg8.getColumnlabel()))
									.toString();
						}

						newValue = arg9999;
					}
				}
			}

			return newValue;
		}
	}

	private static void populateContextWithPaginationData(Context ctx,
			String pagination_xml) {
		try {
			SAXBuilder e = new SAXBuilder();
			Document doc = e.build(new StringReader(pagination_xml));
			if (doc == null) {
				return;
			}

			Element root = doc.getRootElement();
			if (root.getName().equals("PAGE_DETAIL")) {
				ctx.put("PAGE_NUMBER", root.getChild("PAGE_NUMBER") != null
						? root.getChild("PAGE_NUMBER").getText()
						: "1");
				ctx.put("PAGE_SIZE", root.getChild("PAGE_SIZE") != null ? root
						.getChild("PAGE_SIZE").getText() : "1");
				ctx.put("TOTAL_RECORD", root.getChild("TOTAL_RECORD") != null
						? root.getChild("TOTAL_RECORD").getText()
						: "1");
				ctx.put("START_INDEX", root.getChild("START_INDEX") != null
						? root.getChild("START_INDEX").getText()
						: "1");
				ctx.put("END_INDEX", root.getChild("END_INDEX") != null ? root
						.getChild("END_INDEX").getText() : "1");
				ctx.put("TOTAL_PAGE", root.getChild("TOTAL_PAGE") != null
						? root.getChild("TOTAL_PAGE").getText()
						: "1");
			}
		} catch (Exception arg4) {
			logger.error(ctx,
					"unable to populate context with pagination xml due to error : "
							+ arg4.getMessage(), arg4);
		}

	}

	public static String getGroupByColumns(Context ctx,
			ReportdefImpl reportDefImpl) throws Exception {
		String groupby = null;
		if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			List colList = reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList();
			if (colList != null && colList.size() > 0) {
				for (int i = 0; i < colList.size(); ++i) {
					ColumnImpl colImpl = (ColumnImpl) colList.get(i);
					if (colImpl.getIsgroupbydisplay()
							&& (new ReportUtils()).checkForHideShowColumn(ctx,
									colImpl)) {
						if (StringUtils.isBlank(groupby)) {
							groupby = colImpl.getName();
						} else {
							groupby = groupby + "," + colImpl.getName();
						}
					}
				}
			}
		}

		return groupby;
	}

	public static String getOutputColumns(Context ctx,
			ReportdefImpl reportDefImpl) throws Exception {
		String outputcolumns = null;
		List colList;
		int i;
		if (reportDefImpl.getIsshowmultiplegrid()) {
			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				colList = reportDefImpl.getOrderedColumnSetList();

				for (i = 0; i < colList.size(); ++i) {
					ColumnsetImpl colImpl = (ColumnsetImpl) colList.get(i);
					List colList1 = colImpl.getOrderedColumnsList();
					if (colList1 != null && colList1.size() > 0) {
						for (int j = 0; j < colList1.size(); ++j) {
							ColumnImpl colImpl1 = (ColumnImpl) colList1.get(j);
							if (colImpl1.getIsreportdisplay()
									&& (new ReportUtils())
											.checkForHideShowColumn(ctx,
													colImpl1)
									&& colImpl1.getIsdefaultoutputcolumn()) {
								if (StringUtils.isBlank(outputcolumns)) {
									outputcolumns = colImpl.getName() + "="
											+ colImpl1.getName();
								} else {
									outputcolumns = outputcolumns + ","
											+ colImpl.getName() + "="
											+ colImpl1.getName();
								}
							}
						}
					}
				}
			}
		} else if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			colList = reportDefImpl.getFirstColumnset().getOrderedColumnsList();
			if (colList != null && colList.size() > 0) {
				for (i = 0; i < colList.size(); ++i) {
					ColumnImpl arg8 = (ColumnImpl) colList.get(i);
					if (arg8.getIsreportdisplay()
							&& (new ReportUtils()).checkForHideShowColumn(ctx,
									arg8) && arg8.getIsdefaultoutputcolumn()) {
						if (StringUtils.isBlank(outputcolumns)) {
							outputcolumns = arg8.getName();
						} else {
							outputcolumns = outputcolumns + ","
									+ arg8.getName();
						}
					}
				}
			}
		}

		return outputcolumns;
	}

	private static LinkedHashMap<String, String> creatingExcelDynamicHeadersMap(
			Context ctx, boolean isExcel) throws Exception {
		LinkedHashMap map = new LinkedHashMap();
		if (ctx.get("report_data_list_1") != null
				&& ((List) ctx.get("report_data_list_1")).size() > 0) {
			List dataList = (List) ctx.get("report_data_list_1");
			Map row = (Map) dataList.get(0);
			Set keysSet = row.keySet();

			String key;
			for (Iterator itr = keysSet.iterator(); itr.hasNext(); map.put(key,
					key)) {
				key = (String) itr.next();
				if (isExcel && key.contains("_space")) {
					key = key.replaceAll("_space", " ");
				}

				if (!StringUtils.isBlank(key)) {
					if (key.startsWith("eval:")) {
						try {
							String e = key.substring(key.indexOf("eval:") + 5,
									key.length());
							RuleImpl ruleImpl = RulesResources.getInstance(ctx)
									.findRule(e);
							key = ruleImpl.execute(ctx, (String[]) null)
									.toString();
						} catch (Exception arg9) {
							logger.error(ctx,
									"Unable to execute rule for column : "
											+ key, arg9);
						}
					}

					new ReportUtils();
					key = getLabelData(key);
				}
			}
		}

		return map;
	}

	public static void resetReportFilters(Context ctx,
			ReportdefImpl reportDefImpl, HttpServletRequest request)
			throws Exception {
		try {
			List arg10;
			int arg11;
			if (!reportDefImpl.getIsshowmultiplegrid()) {
				if (reportDefImpl.getFirstColumnset() != null
						&& reportDefImpl.getFirstColumnset()
								.getOrderedColumnsList() != null) {
					arg10 = reportDefImpl.getFirstColumnset()
							.getOrderedColumnsList();

					for (arg11 = 0; arg11 < arg10.size(); ++arg11) {
						ColumnImpl arg12 = (ColumnImpl) arg10.get(arg11);
						if (arg12.getIsreportfilter()) {
							ctx.remove(arg12.getName());
							if (arg12.getIspopupsearch()) {
								request.getSession().removeAttribute(
										"to_filter_data_" + arg12.getName());
							}
						}
					}
				}
			} else if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				for (int e = 0; e < reportDefImpl.getOrderedColumnSetList()
						.size(); ++e) {
					ColumnsetImpl i = (ColumnsetImpl) reportDefImpl
							.getOrderedColumnSetList().get(e);
					List reportWorkflowImpl = i.getOrderedColumnsList();

					for (int eval = 0; eval < reportWorkflowImpl.size(); ++eval) {
						ColumnImpl isExecuteWorkflow = (ColumnImpl) reportWorkflowImpl
								.get(eval);
						if (isExecuteWorkflow.getIsreportfilter()) {
							ctx.remove(isExecuteWorkflow.getName());
							if (isExecuteWorkflow.getIspopupsearch()) {
								request.getSession().removeAttribute(
										"to_filter_data_"
												+ isExecuteWorkflow.getName());
							}
						}
					}
				}
			}

			getSavedSearchList(ctx, reportDefImpl);
			getPoneBranchReportsList(ctx, reportDefImpl);
			ctx.remove("report_id");
			ctx.remove("last_updated_by");
			ctx.remove("last_updated_ts");
			ctx.remove("search_name");
			ctx.remove("search_id");
			ctx.remove("created_by");
			ctx.remove("isglobal");
			ctx.remove("search_xml");
			updateFieldListWithData(ctx, request);
			populateParameter(ctx, reportDefImpl, request);
			arg10 = reportDefImpl.getReportworkflowList();
			if (arg10 != null) {
				for (arg11 = 0; arg11 < arg10.size(); ++arg11) {
					ReportworkflowImpl arg13 = (ReportworkflowImpl) arg10
							.get(arg11);
					if (arg13.getIsexecuteonview() != null
							&& arg13.getIsexecuteonview().equals("Y")) {
						String arg14 = arg13.getEval();
						boolean arg15 = true;
						if (!StringUtils.isBlank(arg14)) {
							try {
								arg15 = RulesResources.getInstance(ctx)
										.findRule(arg14)
										.evaluate(ctx, (String[]) null);
							} catch (Exception arg8) {
								logger.error(ctx, "Unable to execute rule : "
										+ arg14 + " for report : "
										+ reportDefImpl.getName(), arg8);
							}
						}

						if (arg15) {
							if (arg13.getCheckfornullparams() != null
									&& !arg13.getCheckfornullparams()
											.equals("")) {
								checkForNullParams(ctx,
										arg13.getCheckfornullparams());
							}

							ctx.put("reportoutputtype", arg13.getOutputtype());
							populateContextWithPopupSearchColumn(ctx,
									reportDefImpl);
							arg13.executeWorkflow(ctx, arg13.getOutputtype(),
									reportDefImpl.getName(),
									arg13.getIsdynacolumns());
						}
					}
				}
			}

			if (ctx.get("report_page_error") == null
					|| ctx.get("report_page_error").equals("")) {
				ctx.put("groupby", getGroupByColumns(ctx, reportDefImpl));
				ctx.put("outputcolumns", getOutputColumns(ctx, reportDefImpl));
			}
		} catch (Exception arg9) {
			logger.error(ctx, "Unable to reset filter data due to error : "
					+ arg9.getMessage(), arg9);
		}

	}

	public static void showDrillDownReportData(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.put("PAGE_SIZE", Integer.valueOf(100));
		ctx.put("PAGE_NUMBER", Integer.valueOf(1));
		ctx.put("isDrillDownReportTrue", "Y");
		String current_search_xml = ctx.get("search_xml") != null ? ctx.get(
				"search_xml").toString() : null;
		if (!StringUtils.isBlank(current_search_xml)) {
			current_search_xml = current_search_xml.replace("|", "#pipeline#");
			ctx.put("drilldown_search_xml", current_search_xml);
		}

		String current_selected_filter_data = ctx.get("selected_filter_data") != null
				? ctx.get("selected_filter_data").toString()
				: null;
		if (!StringUtils.isBlank(current_selected_filter_data)) {
			current_selected_filter_data = current_selected_filter_data
					.replace("|", "#pipeline#");
			ctx.put("drilldown_search_xml_desc", current_selected_filter_data);
			ctx.put("drilldown_selected_filter_data",
					current_selected_filter_data);
		}

		populateParameter(ctx, reportDefImpl, request);
		if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getColumnList() != null
				&& reportDefImpl.getFirstColumnset().getColumnList().size() > 0) {
			String workflowList = null;
			String i = null;

			for (int reportWorkflowImpl = 0; reportWorkflowImpl < reportDefImpl
					.getFirstColumnset().getOrderedColumnsList().size(); ++reportWorkflowImpl) {
				ColumnImpl eval = (ColumnImpl) reportDefImpl
						.getFirstColumnset().getColumnList()
						.get(reportWorkflowImpl);
				if (eval.getIsreportdisplay()) {
					if (workflowList == null) {
						workflowList = eval.getName();
					} else {
						workflowList = workflowList + "," + eval.getName();
					}
				}

				if (eval.getIsreportfilter()) {
					String isExecuteWorkflow = "";
					if (eval.getFilterdisplaytype() != null
							&& (eval.getFilterdisplaytype().equals("select") || eval
									.getFilterdisplaytype().equals(
											"multiselect"))) {
						isExecuteWorkflow = "All";
					}

					if (ctx.get(eval.getName()) != null
							&& !ctx.get(eval.getName()).toString().equals("")) {
						ctx.get(eval.getName()).toString();
					} else {
						Object arg9999 = null;
					}

					String e = ctx
							.get(eval.getName() + "_drilldowndescription") != null
							&& !ctx.get(
									eval.getName() + "_drilldowndescription")
									.toString().equals("") ? ctx.get(
							eval.getName() + "_drilldowndescription")
							.toString() : "";
					if (i == null) {
						i = (e != null ? eval.getName() + "=" + e : eval
								.getName() + "=" + isExecuteWorkflow)
								+ "|";
					} else {
						i = i
								+ (e != null ? eval.getName() + "=" + e : eval
										.getName() + "=" + isExecuteWorkflow)
								+ "|";
					}
				}
			}

			if (StringUtils.isBlank(i)) {
				i = "|groupby=" + ctx.get("groupby") + "|";
			} else {
				i = i + "|groupby=" + ctx.get("groupby") + "|";
			}

			ctx.put("outputcolumns", workflowList);
			ctx.put("selected_filter_data", i);
			ctx.put("search_xml_desc", i);
		}

		List arg11 = reportDefImpl.getReportworkflowList();
		if (arg11 != null) {
			for (int arg13 = 0; arg13 < arg11.size(); ++arg13) {
				ReportworkflowImpl arg12 = (ReportworkflowImpl) arg11
						.get(arg13);
				if (arg12.getIsexecuteonview() == null
						|| !arg12.getIsexecuteonview().equals("Y")) {
					String arg14 = arg12.getEval();
					boolean arg15 = true;
					if (!StringUtils.isBlank(arg14)) {
						try {
							arg15 = RulesResources.getInstance(ctx)
									.findRule(arg14)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg10) {
							logger.error(
									ctx,
									"Unable to execute rule : " + arg14
											+ " for report : "
											+ reportDefImpl.getName(), arg10);
						}
					}

					if (arg15) {
						if (arg12.getCheckfornullparams() != null
								&& !arg12.getCheckfornullparams().equals("")) {
							checkForNullParams(ctx,
									arg12.getCheckfornullparams());
						}

						ctx.put("reportoutputtype", arg12.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						arg12.executeWorkflow(ctx, arg12.getOutputtype(),
								reportDefImpl.getName(),
								arg12.getIsdynacolumns());
					}
				}
			}
		}

		generateRequestXml(ctx, reportDefImpl, request, true);
	}

	public static void processDrillDownBackButton(Context ctx, Element element)
			throws Exception {
		if (element.getName().equals("a")) {
			String dynaKeys = "|search_xml|search_xml_desc|selected_filter_data|projectID|reportID|";
			String dynaValues = "|" + ctx.get("drilldown_search_xml") + "|"
					+ ctx.get("drilldown_search_xml_desc") + "|"
					+ ctx.get("drilldown_selected_filter_data") + "|"
					+ ctx.getProject() + "|" + ctx.get("drilldown_reportID")
					+ "|";
			String href = "javascript:managereport(\'"
					+ dynaKeys
					+ "\',\'"
					+ dynaValues
					+ "\',\'INET_SHOW_DRILLDOWN_REPORT_BACK\',\'MANAGE_REPORTING\');";
			element.setAttribute("href", href);
		}

	}

	public static void showDrillDownBackReportData(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.remove("isDrillDownReportTrue");
		String current_search_xml = ctx.get("search_xml") != null ? ctx.get(
				"search_xml").toString() : null;
		if (!StringUtils.isBlank(current_search_xml)) {
			current_search_xml = current_search_xml.replace("#pipeline#", "|");
			ctx.put("search_xml", current_search_xml);
			(new RequestProcessor()).populateContext(ctx, current_search_xml);
		}

		String current_selected_filter_data = ctx.get("selected_filter_data") != null
				? ctx.get("selected_filter_data").toString()
				: null;
		if (!StringUtils.isBlank(current_selected_filter_data)) {
			current_selected_filter_data = current_selected_filter_data
					.replace("#pipeline#", "|");
			ctx.put("search_xml_desc", current_selected_filter_data);
			ctx.put("selected_filter_data", current_selected_filter_data);
		}

		ctx.put("outputtype", "S");
		showReportData(ctx, request, reportDefImpl);
	}

	public static void generateSelectedFilterDataForDataOnly(Context ctx,
			ReportdefImpl reportDefImpl) throws Exception {
		if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getColumnList() != null
				&& reportDefImpl.getFirstColumnset().getColumnList().size() > 0) {
			String outputcolumns = null;
			String selected_filter_data = null;

			for (int i = 0; i < reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList().size(); ++i) {
				ColumnImpl colImpl = (ColumnImpl) reportDefImpl
						.getFirstColumnset().getColumnList().get(i);
				if (colImpl.getIsreportdisplay()) {
					if (outputcolumns == null) {
						outputcolumns = colImpl.getName();
					} else {
						outputcolumns = outputcolumns + "," + colImpl.getName();
					}
				}

				if (colImpl.getIsreportfilter()) {
					String defValue = "";
					if (colImpl.getFilterdisplaytype() != null
							&& (colImpl.getFilterdisplaytype().equals("select") || colImpl
									.getFilterdisplaytype().equals(
											"multiselect"))) {
						defValue = "All";
					}

					if (ctx.get(colImpl.getName()) != null
							&& !ctx.get(colImpl.getName()).toString()
									.equals("")) {
						ctx.get(colImpl.getName()).toString();
					} else {
						Object arg9999 = null;
					}

					String value = ctx.get(colImpl.getName()
							+ "_drilldowndescription") != null
							&& !ctx.get(
									colImpl.getName() + "_drilldowndescription")
									.toString().equals("") ? ctx.get(
							colImpl.getName() + "_drilldowndescription")
							.toString() : "";
					if (selected_filter_data == null) {
						selected_filter_data = (value != null ? colImpl
								.getName() + "=" + value : colImpl.getName()
								+ "=" + defValue)
								+ "|";
					} else {
						selected_filter_data = selected_filter_data
								+ (value != null ? colImpl.getName() + "="
										+ value : colImpl.getName() + "="
										+ defValue) + "|";
					}
				}
			}

			if (StringUtils.isBlank(selected_filter_data)) {
				selected_filter_data = "|groupby=" + ctx.get("groupby") + "|";
			} else {
				selected_filter_data = selected_filter_data + "|groupby="
						+ ctx.get("groupby") + "|";
			}

			ctx.put("outputcolumns", outputcolumns);
			ctx.put("selected_filter_data", selected_filter_data);
			ctx.put("search_xml_desc", selected_filter_data);
		}

	}

	public static String getPopupSearchFields(ReportdefImpl reportDefImpl)
			throws Exception {
		String popupSearchColumns = null;
		List colList;
		int i;
		if (reportDefImpl.getIsshowmultiplegrid()) {
			if (reportDefImpl.getOrderedColumnSetList() != null
					&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
				colList = reportDefImpl.getOrderedColumnSetList();

				for (i = 0; i < colList.size(); ++i) {
					ColumnsetImpl colImpl = (ColumnsetImpl) colList.get(i);
					List colsList = colImpl.getOrderedColumnsList();

					for (int j = 0; j < colsList.size(); ++j) {
						ColumnImpl colImpl1 = (ColumnImpl) colsList.get(j);
						if (colImpl1.getIsreportfilter()
								&& colImpl1.getIspopupsearch()) {
							if (StringUtils.isBlank(popupSearchColumns)) {
								popupSearchColumns = colImpl1.getName();
							} else {
								popupSearchColumns = popupSearchColumns + ","
										+ colImpl1.getName();
							}
						}
					}
				}
			}
		} else if (reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			colList = reportDefImpl.getFirstColumnset().getOrderedColumnsList();
			if (colList != null && colList.size() > 0) {
				for (i = 0; i < colList.size(); ++i) {
					ColumnImpl arg7 = (ColumnImpl) colList.get(i);
					if (arg7.getIsreportfilter() && arg7.getIspopupsearch()) {
						if (StringUtils.isBlank(popupSearchColumns)) {
							popupSearchColumns = arg7.getName();
						} else {
							popupSearchColumns = popupSearchColumns + ","
									+ arg7.getName();
						}
					}
				}
			}
		}

		return popupSearchColumns;
	}

	public static boolean isPopupSearchColumnFound(IContext ctx, String column)
			throws Exception {
		if (ctx.get("popupsearchcolumns") != null
				&& !ctx.get("popupsearchcolumns").toString().equals("")) {
			StringTokenizer tokens = new StringTokenizer(ctx.get(
					"popupsearchcolumns").toString(), ",");

			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				if (token.trim().equals(column)) {
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	public static void populateContextWithPopupSearchColumn(IContext ctx,
			ReportdefImpl reportDefImpl) throws Exception {
		if (reportDefImpl != null && reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getColumnList() != null) {
			Object colList = new ArrayList();
			int i;
			if (reportDefImpl.getIsshowmultiplegrid()) {
				if (reportDefImpl.getOrderedColumnSetList() != null
						&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
					for (i = 0; i < reportDefImpl.getOrderedColumnSetList()
							.size(); ++i) {
						ColumnsetImpl colImpl = (ColumnsetImpl) reportDefImpl
								.getOrderedColumnSetList().get(i);
						((List) colList)
								.addAll(colImpl.getOrderedColumnsList());
					}
				}
			} else {
				colList = reportDefImpl.getFirstColumnset().getColumnList();
			}

			for (i = 0; i < ((List) colList).size(); ++i) {
				ColumnImpl arg9 = (ColumnImpl) ((List) colList).get(i);
				if (arg9.getIsreportfilter() && arg9.getIspopupsearch()) {
					String value = ctx.get(arg9.getName()) != null ? ctx.get(
							arg9.getName()).toString() : null;
					if (!StringUtils.isBlank(value) && value.contains("=")) {
						String popupSeachValue = null;
						if (!value.contains("#Hash;#")) {
							ctx.put(arg9.getName(), value.substring(
									value.lastIndexOf("=") + 1, value.length()));
						} else {
							String[] tokens = value.split("#Hash;#");

							for (int j = 0; j < tokens.length; ++j) {
								String token = tokens[j];
								if (!StringUtils.isBlank(token)
										&& token.contains("=")) {
									if (popupSeachValue == null) {
										popupSeachValue = token.substring(
												token.lastIndexOf("=") + 1,
												token.length());
									} else {
										popupSeachValue = popupSeachValue
												+ ","
												+ token.substring(
														token.lastIndexOf("=") + 1,
														token.length());
									}
								}
							}

							ctx.put(arg9.getName(), popupSeachValue);
						}
					}
				}
			}

		}
	}

	public static void refreshManageReport(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl,
			HttpServletResponse response) throws Exception {
		updateFieldListWithData(ctx, request);
		populateParameter(ctx, reportDefImpl, request);
		ctx.put("TEMPLATE_NAME", "managereporttemplate");
		String pageContent = parseReportHTML(ctx, "managereporttemplate",
				"reports/managereporttemplate.html", request, reportDefImpl);
		pageContent = pageContent.substring(pageContent.indexOf("<html>"),
				pageContent.length());
		pageContent = (new PageUtils()).cleanContent(pageContent);
		Document document = null;
		SAXBuilder builder = new SAXBuilder();
		document = builder.build(new StringReader(pageContent));
		Element root = null;
		if (document != null) {
			root = document.getRootElement();
		}

		String replayid = ctx.get("datadisplayid").toString();
		Element element = (new PageUtils()).getExistingElement(root, replayid);
		if (element == null) {
			{ if (logger.isDebugEnabled()) logger.debug(ctx, replayid + " div id is not present in "
					+ ctx.get("inet_page").toString() + ".html"); }
		}

		String htmlDoc = "";
		if (element != null) {
			List eleList = element.getChildren();
			if (eleList != null) {
				for (int i = 0; i < eleList.size(); ++i) {
					htmlDoc = htmlDoc
							+ HTMLUtils.beautifyHTML((Element) eleList.get(i));
				}

				htmlDoc = htmlDoc.replaceAll("#space#", "&nbsp;");
			}
		}

		(new PageUtils()).writeResponse(response, htmlDoc, "text/html");
	}

	private static void validateWorkflow(Context ctx,
			ReportdefImpl reportDefImpl) throws Exception {
		if (ctx.get("inet_form_dirty") == null
				|| !ctx.get("inet_form_dirty").equals("Y")) {
			List workflowList = reportDefImpl.getReportworkflowList();
			if (workflowList != null) {
				for (int i = 0; i < workflowList.size(); ++i) {
					ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
							.get(i);
					if (reportWorkflowImpl.getIsexecuteonview() == null
							|| !reportWorkflowImpl.getIsexecuteonview().equals(
									"Y")) {
						String eval = reportWorkflowImpl.getEval();
						if (!StringUtils.isBlank(eval)) {
							try {
								RulesResources.getInstance(ctx).findRule(eval)
										.evaluate(ctx, (String[]) null);
								if (ctx.get("inet_errors_list") != null) {
									return;
								}
							} catch (Exception arg6) {
								logger.error(ctx, "Unable to execute rule : "
										+ eval + " for report : "
										+ reportDefImpl.getName(), arg6);
							}
						}
					}
				}
			}

		}
	}

	public static String getLabelData(String key) {
		String labelData = null;

		try {
			StringTokenizer e = new StringTokenizer(key, " ");

			while (e.hasMoreTokens()) {
				String token = e.nextToken();
				HashMap map = CacheManager.get("Labels_Conf") == null
						? null
						: (HashMap) CacheManager.get("Labels_Conf");
				if (map != null && map.containsKey(token)) {
					token = (String) map.get(token);
				}

				if (labelData == null) {
					labelData = token;
				} else {
					labelData = labelData + " " + token;
				}
			}
		} catch (Exception arg4) {
			logger.error("Unable to read label for key " + key
					+ " due to error : " + arg4.getMessage(), arg4);
		}

		if (StringUtils.isBlank(labelData)) {
			labelData = key;
		}

		labelData = labelData.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">").replaceAll("&apos;", "\'");
		return labelData;
	}

	public void getDynaKeysValues(Context ctx, ReportdefImpl reportDefImpl,
			ColumnImpl column) {
		if (reportDefImpl != null) {
			try {
				List e = reportDefImpl.getReportworkflowList();
				String token;
				String value;
				if (e != null) {
					label94 : for (int dynaKeysValues = 0; dynaKeysValues < e
							.size(); ++dynaKeysValues) {
						ReportworkflowImpl tokens = (ReportworkflowImpl) e
								.get(dynaKeysValues);
						if ((tokens.getIsexecuteonview() == null || !tokens
								.getIsexecuteonview().equals("Y"))
								&& !StringUtils.isBlank(tokens
										.getDynakeysvalues())) {
							token = tokens.getDynakeysvalues();
							StringTokenizer key = new StringTokenizer(token,
									"|");

							while (true) {
								while (true) {
									do {
										do {
											if (!key.hasMoreTokens()) {
												continue label94;
											}

											value = key.nextToken();
										} while (value == null);
									} while (!value.contains("="));

									String e1 = value.substring(0,
											value.indexOf("="));
									String value1 = value.substring(
											value.indexOf("=") + 1,
											value.length());
									if (value1 != null
											&& value1.startsWith("eval:")) {
										try {
											RuleImpl e2 = RulesResources
													.getInstance(ctx).findRule(
															value1);
											e2.evaluate(ctx, (String[]) null);
										} catch (Exception arg13) {
											logger.error(
													ctx,
													"Unable to execute rule for dynakey "
															+ e1
															+ " due to error : "
															+ arg13.getMessage(), arg13);
										}
									} else {
										ctx.put(e1, value1);
									}
								}
							}
						}
					}
				}

				if (column != null) {
					String arg15 = column.getDynakeysvalues();
					StringTokenizer arg16 = new StringTokenizer(arg15, "|");

					while (true) {
						while (true) {
							do {
								do {
									if (!arg16.hasMoreTokens()) {
										return;
									}

									token = arg16.nextToken();
								} while (token == null);
							} while (!token.contains("="));

							String arg17 = token.substring(0,
									token.indexOf("="));
							value = token.substring(token.indexOf("=") + 1,
									token.length());
							if (value != null && value.startsWith("eval:")) {
								try {
									RuleImpl arg18 = RulesResources
											.getInstance(ctx).findRule(value);
									arg18.evaluate(ctx, (String[]) null);
								} catch (Exception arg12) {
									logger.error(ctx,
											"Unable to execute rule for dynakey "
													+ arg17
													+ " due to error : "
													+ arg12.getMessage(), arg12);
								}
							} else {
								ctx.put(arg17, value);
							}
						}
					}
				}
			} catch (Exception arg14) {
				logger.error(ctx,
						"Unable to get dynakeysvalues for reportId "
								+ reportDefImpl.getName() + " due to error : "
								+ arg14.getMessage(), arg14);
			}

		}
	}

	public static boolean openHrefPage(Context ctx, HttpServletRequest request,
			HttpServletResponse response, Object reportID) throws Exception {
		if (reportID != null && !reportID.toString().equals("")) {
			String next_page = ctx.get("NEXT_PAGE").toString();

			try {
				String e = null;
				List columnList = null;
				Object column = null;
				ReportdefImpl reportDefImpl = ReportResources.getInstance(ctx)
						.getReportDef(reportID.toString());
				if (reportDefImpl.getFirstColumnset() != null
						&& reportDefImpl.getFirstColumnset()
								.getOrderedColumnsList() != null) {
					columnList = reportDefImpl.getFirstColumnset()
							.getOrderedColumnsList();

					for (int session = 0; session < columnList.size(); ++session) {
						ColumnImpl tokens = (ColumnImpl) columnList
								.get(session);
						if (tokens.getIsreportdisplay()
								&& tokens.getName() != null
								&& tokens.getName().equals(
										ctx.get("currentFieldName"))) {
							e = tokens.getSessionparams();
							break;
						}
					}
				}

				(new ProcessAction()).processRoute(ctx, next_page);
				HttpSession arg13 = request.getSession();
				StringTokenizer arg14 = new StringTokenizer(e != null ? e : "", ",");

				String pageContent;
				while (arg14.hasMoreTokens()) {
					pageContent = arg14.nextToken();
					arg13.setAttribute(pageContent, ctx.get(pageContent));
				}

				Context storedBreadCrumbContext = BreadcrumbSessionUtils.snapshot(ctx);
				(new PageUtils()).setPageInStack(storedBreadCrumbContext, request,
						ctx.get("NEXT_PAGE").toString(), "", ctx);
				Object pageListStack = storedBreadCrumbContext.get(PageUtils.PAGE_LIST_STACK);
				BreadcrumbSessionUtils.sanitize(pageListStack);
				ctx.put(PageUtils.PAGE_LIST_STACK, pageListStack);
				ctx.put("NEXT_PAGE_COMPONENT", next_page);
				pageContent = (new PageUtils()).parseHtmlDocument(ctx,
						next_page, request);
				String pageStackContent = (new PageUtils())
						.createPageListStack(ctx);
				pageContent = pageContent.replace("#page_list_stack#",
						pageStackContent != null ? pageStackContent : "");
				(new PageUtils()).writeResponse(response, pageContent,
						"text/html");
				(new PageUtils()).setPageInStackWithContent(ctx, request,
						next_page, pageContent);
				return true;
			} catch (Exception arg12) {
				logger.error(ctx, "Unable to navigate to page " + next_page
						+ " due to error : " + arg12.getMessage(), arg12);
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean checkForHideShowColumn(Context ctx, ColumnImpl column) {
		if (!StringUtils.isBlank(column.getEval())) {
			try {
				String e = column.getEval();
				if (e.startsWith("eval:")) {
					e = e.substring(e.indexOf("eval:") + 5, e.length());
					RuleImpl map = RulesResources.getInstance(ctx).findRule(e);
					if (!map.evaluate(ctx, (String[]) null)) {
						return false;
					}
				}

				HashMap map1 = CacheManager.get("Tabs_Conf") == null
						? null
						: (HashMap) CacheManager.get("Tabs_Conf");
				if (map1 != null && map1.containsKey(e) && map1.get(e) != null
						&& "N".equalsIgnoreCase(map1.get(e).toString())) {
					return false;
				}

				int accessType = SecurityResources.getInstance(ctx)
						.getAccessType(e, ctx);
				if (SecurityParser.isHideResource(accessType)) {
					return false;
				}
			} catch (Exception arg5) {
				logger.error(ctx, "unable to execute rule " + column.getEval()
						+ " due to error : " + arg5.getMessage(), arg5);
			}
		}

		return true;
	}

	private void createPageError(Context ctx) throws Exception {
		if (ctx.get("inet_errors_list") != null
				&& ctx.get("inet_errors_list") instanceof List) {
			List errorsList = (List) ctx.get("inet_errors_list");

			for (int j = 0; j < errorsList.size(); ++j) {
				ValidationException exp = (ValidationException) errorsList
						.get(0);
				ctx.put("report_page_error", exp.getMessage());
			}

			ctx.put("managereport_errorfound", "Y");
		}

	}

	public String getDecryptValue(Context ctx, String encryptKey, String value) {
		if (StringUtils.isBlank(encryptKey)) {
			return value;
		} else {
			value = (new TripleDESEncryptionDecryption()).decrypt(value);

			try {
				int accesstye = SecurityResources.getInstance(ctx)
						.getAccessType(encryptKey, ctx);
				if (accesstye == 0) {
					String arg8 = "";

					for (int arg9 = 0; arg9 < value.length(); ++arg9) {
						arg8 = arg8 + "X";
					}

					return arg8;
				}

				RuleImpl ruleImpl = RulesResources.getInstance(ctx).findRule(
						encryptKey);
				if (ruleImpl != null
						&& !ruleImpl.evaluate(ctx, (String[]) null)) {
					String newValue = "";

					for (int i = 0; i < value.length(); ++i) {
						newValue = newValue + "X";
					}

					return newValue;
				}
			} catch (Exception arg7) {
				logger.error("Error in ReportUtils.getDecryptValue", arg7);
				;
			}

			return value;
		}
	}

	public static void showGraphPopup(Context ctx, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.put("isgraph", "Y");
		ctx.put("graphlink",
				"graph;jsessionid=" + ctx.get("jsessionid") + "?rnum="
						+ (new Random()).nextLong() + "&graphID="
						+ ctx.get("graphID"));
		Context newCtx = new Context();
		newCtx.putAll(ctx);
		(new RequestProcessor()).populateContext(newCtx,
				newCtx.get("search_xml").toString());
		(new RequestProcessor()).populateContext(ctx, ctx.get("search_xml")
				.toString());
		ctx.put("ispaginationrequired",
				reportDefImpl.getIsreportdatapagination());
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() == null
						|| !reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg8) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg8);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

	}

	public static void closeGraphPopup(Context ctx, ReportdefImpl reportDefImpl)
			throws Exception {
		ctx.put("isgraph", (Object) null);
		ctx.put("graphdatatablecolumns", (Object) null);
		ctx.put("graphoptions", (Object) null);
		ctx.put("graphlink", (Object) null);
		Context newCtx = new Context();
		newCtx.putAll(ctx);
		(new RequestProcessor()).populateContext(newCtx,
				newCtx.get("search_xml").toString());
		(new RequestProcessor()).populateContext(ctx, ctx.get("search_xml")
				.toString());
		ctx.put("ispaginationrequired",
				reportDefImpl.getIsreportdatapagination());
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int i = 0; i < workflowList.size(); ++i) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(i);
				if (reportWorkflowImpl.getIsexecuteonview() == null
						|| !reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg8) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg8);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
					}
				}
			}
		}

	}

	public static void showGraph(Context ctx, HttpServletRequest request)
			throws Exception {
		request.getSession().removeAttribute("report_data_list_1");
		request.getSession().removeAttribute("graphdefImpl");
		if (ctx.get("graphID") != null
				&& !ctx.get("graphID").toString().equals("")) {
			String graphID = ctx.get("graphID").toString();

			try {
				GraphdefImpl e = GraphResources.getInstance(ctx).getGraphDef(
						graphID);
				if (e == null || e.getGraphworkflowList() == null
						|| e.getGraphworkflowList().get(0) == null) {
					return;
				}

				GraphworkflowImpl graphWorkflowImpl = (GraphworkflowImpl) e
						.getGraphworkflowList().get(0);
				graphWorkflowImpl.executeWorkflow(ctx, "1xml", (String) null,
						false);
				logger.debug(ctx, ctx.get("report_data_list_1"));
				request.getSession().setAttribute("report_data_list_1",
						ctx.get("report_data_list_1"));
				request.getSession().setAttribute("graphdefImpl", e);
			} catch (Exception arg4) {
				logger.error(
						ctx,
						"Unable to show graph due to error : "
								+ arg4.getMessage(), arg4);
			}

		}
	}

	public static void showAccordianReportData(Context ctx,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (ctx.get("ACCORDIANID") != null
				&& !ctx.get("ACCORDIANID").toString().equals("")
				&& ctx.get("DRILLDOWNREPORTID") != null
				&& !ctx.get("DRILLDOWNREPORTID").toString().equals("")) {
			String accordianID = ctx.get("ACCORDIANID").toString();
			ctx.put("PAGE_SIZE", Integer.valueOf(100));
			ctx.put("PAGE_NUMBER", Integer.valueOf(1));
			String drillDownReportID = ctx.get("DRILLDOWNREPORTID").toString();

			try {
				ReportdefImpl e = ReportResources.getInstance(ctx)
						.getReportDef(drillDownReportID);
				if (e == null) {
					return;
				}

				if (e.getReportworkflowList() == null
						|| e.getReportworkflowList().size() == 0) {
					return;
				}

				String outputcolumns = null;
				Object selected_filter_data = null;

				ColumnImpl pageContent;
				for (int workflowList = 0; workflowList < e.getFirstColumnset()
						.getOrderedColumnsList().size(); ++workflowList) {
					pageContent = (ColumnImpl) e.getFirstColumnset()
							.getColumnList().get(workflowList);
					if (pageContent.getIsreportdisplay()) {
						if (outputcolumns == null) {
							outputcolumns = pageContent.getName();
						} else {
							outputcolumns = outputcolumns + ","
									+ pageContent.getName();
						}
					}
				}

				ctx.put("outputcolumns", outputcolumns);
				List arg18 = e.getReportworkflowList();
				if (arg18 != null) {
					for (int arg19 = 0; arg19 < arg18.size(); ++arg19) {
						ReportworkflowImpl mainDivElement = (ReportworkflowImpl) arg18
								.get(arg19);
						if (mainDivElement.getIsexecuteonview() == null
								|| !mainDivElement.getIsexecuteonview().equals(
										"Y")) {
							String tableElement = mainDivElement.getEval();
							boolean trElement = true;
							if (!StringUtils.isBlank(tableElement)) {
								try {
									trElement = RulesResources.getInstance(ctx)
											.findRule(tableElement)
											.evaluate(ctx, (String[]) null);
								} catch (Exception arg16) {
									logger.error(
											ctx,
											"Unable to execute rule : "
													+ tableElement
													+ " for report : "
													+ e.getName(), arg16);
								}
							}

							if (trElement) {
								if (mainDivElement.getCheckfornullparams() != null
										&& !mainDivElement
												.getCheckfornullparams()
												.equals("")) {
									checkForNullParams(ctx,
											mainDivElement
													.getCheckfornullparams());
								}

								ctx.put("reportoutputtype",
										mainDivElement.getOutputtype());
								populateContextWithPopupSearchColumn(ctx, e);
								mainDivElement.executeWorkflow(ctx,
										mainDivElement.getOutputtype(),
										e.getName(),
										mainDivElement.getIsdynacolumns());
							}
						}
					}
				}

				ctx.put("TEMPLATE_NAME", "managereportdatatemplate");
				pageContent = null;
				Element arg20 = new Element("div");
				Element arg22 = new Element("table");
				arg22.setAttribute("id", "#REPORTING_LIST#");
				arg22.setAttribute("cellspacing", "0");
				arg22.setAttribute("cellpadding", "0");
				arg22.setAttribute("width", "100%");
				Element arg23 = new Element("tr");
				Element tdElement = new Element("td");
				Element divElement = new Element("div");
				divElement.setAttribute("class", "bgAccordionNReports");
				Element divTableElement = new Element("table");
				divTableElement.setAttribute("class", "gridTable");
				divTableElement.setAttribute("cellspacing", "0");
				divTableElement.setAttribute("cellpadding", "0");
				divTableElement.setAttribute("width", "100%");
				Element divTableTrElement = new Element("tr");
				divTableTrElement.setAttribute("id", "#REPORTING_LIST_HEADER#");
				divTableElement.addContent(divTableTrElement);
				divTableTrElement = new Element("tr");
				divTableTrElement.setAttribute("id", "#REPORTING_LIST_DATA#");
				divTableElement.addContent(divTableTrElement);
				divElement.addContent(divTableElement);
				tdElement.addContent(divElement);
				arg23.addContent(tdElement);
				arg22.addContent(arg23);
				arg20.addContent(arg22);
				(new ReportDataBlock(e)).processDataBlock(ctx, arg20,
						arg20.getChildren(), request, e);
				String arg21 = (new XMLOutputter()).outputString(arg20);
				response.getWriter().write(arg21);
			} catch (Exception arg17) {
				logger.error(ctx,
						"Unable to get accordian report due to error : "
								+ arg17.getMessage(), arg17);
			}

		}
	}

	public static void submitAccordianExportPopup(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl)
			throws Exception {
		if (ctx.get("exporttype") != null
				&& !ctx.get("exporttype").toString().equals("")) {
			(new ReportUtils()).getDynaKeysValues(ctx, reportDefImpl,
					(ColumnImpl) null);
			if (ctx.get("outputtype") != null) {
				List workflowList;
				int i;
				ReportworkflowImpl reportWorkflowImpl;
				String eval;
				boolean isExecuteWorkflow;
				if (!ctx.get("exporttype").toString().equals("1")
						&& !ctx.get("exporttype").toString().equals("3")) {
					Context arg13 = new Context();
					arg13.putAll(ctx);
					(new RequestProcessor()).populateContext(arg13,
							arg13.get("search_xml").toString());
					(new RequestProcessor()).populateContext(ctx,
							ctx.get("search_xml").toString());
					arg13.put("ispaginationrequired",
							reportDefImpl.getIsreportdatapagination());
					arg13.put("reportID", reportDefImpl.getName());
					arg13.put("last_updated_by", ctx.get("user_id"));
					arg13.put("Type", ctx.get("outputtype"));
					workflowList = reportDefImpl.getReportworkflowList();
					if (workflowList != null) {
						for (i = 0; i < workflowList.size(); ++i) {
							reportWorkflowImpl = (ReportworkflowImpl) workflowList
									.get(i);
							if ((reportWorkflowImpl.getIsexecuteonview() == null || !reportWorkflowImpl
									.getIsexecuteonview().equals("Y"))
									&& reportWorkflowImpl
											.getStoredprocedureoperationList() != null
									&& reportWorkflowImpl
											.getStoredprocedureoperationList()
											.size() > 0) {
								arg13.put(
										"project_name",
										((StoredprocedureoperationImpl) reportWorkflowImpl
												.getStoredprocedureoperationList()
												.get(0)).getApplid());
							}
						}
					}

					SqlResources
							.getSqlMapProcessor(arg13)
							.insert("report_search_columns.insert_pone_batch_reports_p_java",
									arg13);
					ctx.put("pageMsg", MessageResources.getInstance(ctx)
							.getMessage("reportScheduleSuccessfullyMsg")
							.getMessage());
					ctx.put("ispaginationrequired",
							reportDefImpl.getIsreportdatapagination());
					workflowList = reportDefImpl.getReportworkflowList();
					if (workflowList != null) {
						for (i = 0; i < workflowList.size(); ++i) {
							reportWorkflowImpl = (ReportworkflowImpl) workflowList
									.get(i);
							if (reportWorkflowImpl.getIsexecuteonview() == null
									|| !reportWorkflowImpl.getIsexecuteonview()
											.equals("Y")) {
								eval = reportWorkflowImpl.getEval();
								isExecuteWorkflow = true;
								if (!StringUtils.isBlank(eval)) {
									try {
										isExecuteWorkflow = RulesResources
												.getInstance(ctx)
												.findRule(eval)
												.evaluate(ctx, (String[]) null);
									} catch (Exception arg11) {
										logger.error(
												ctx,
												"Unable to execute rule : "
														+ eval
														+ " for report : "
														+ reportDefImpl
																.getName(), arg11);
									}
								}

								if (isExecuteWorkflow) {
									if (reportWorkflowImpl
											.getCheckfornullparams() != null
											&& !reportWorkflowImpl
													.getCheckfornullparams()
													.equals("")) {
										checkForNullParams(
												ctx,
												reportWorkflowImpl
														.getCheckfornullparams());
									}

									ctx.put("reportoutputtype",
											reportWorkflowImpl.getOutputtype());
									populateContextWithPopupSearchColumn(ctx,
											reportDefImpl);
									reportWorkflowImpl.executeWorkflow(ctx,
											reportWorkflowImpl.getOutputtype(),
											reportDefImpl.getName(),
											reportWorkflowImpl
													.getIsdynacolumns());
								}
							}
						}
					}
				} else {
					HttpServletResponse newCtx = (HttpServletResponse) ctx
							.get("DocumentResponse");
					(new RequestProcessor()).populateContext(ctx,
							ctx.get("search_xml").toString());
					ctx.put("ispaginationrequired",
							reportDefImpl.getIsreportdatapagination());
					workflowList = reportDefImpl.getReportworkflowList();
					if (workflowList != null) {
						for (i = 0; i < workflowList.size(); ++i) {
							reportWorkflowImpl = (ReportworkflowImpl) workflowList
									.get(i);
							if (reportWorkflowImpl.getIsexecuteonview() == null
									|| !reportWorkflowImpl.getIsexecuteonview()
											.equals("Y")) {
								eval = reportWorkflowImpl.getEval();
								isExecuteWorkflow = true;
								if (!StringUtils.isBlank(eval)) {
									try {
										isExecuteWorkflow = RulesResources
												.getInstance(ctx)
												.findRule(eval)
												.evaluate(ctx, (String[]) null);
									} catch (Exception arg12) {
										logger.error(
												ctx,
												"Unable to execute rule : "
														+ eval
														+ " for report : "
														+ reportDefImpl
																.getName(), arg12);
									}
								}

								if (isExecuteWorkflow) {
									if (reportWorkflowImpl
											.getCheckfornullparams() != null
											&& !reportWorkflowImpl
													.getCheckfornullparams()
													.equals("")) {
										checkForNullParams(
												ctx,
												reportWorkflowImpl
														.getCheckfornullparams());
									}

									ctx.put("reportoutputtype",
											reportWorkflowImpl.getOutputtype());
									populateContextWithPopupSearchColumn(ctx,
											reportDefImpl);
									if (ctx.get("exporttype").toString()
											.equals("3")) {
										ctx.put("PAGE_NUMBER", (Object) null);
										ctx.put("PAGE_SIZE", (Object) null);
									}

									reportWorkflowImpl.executeWorkflow(ctx,
											reportWorkflowImpl.getOutputtype(),
											reportDefImpl.getName(),
											reportWorkflowImpl
													.getIsdynacolumns());
									if (reportWorkflowImpl.getIsdynacolumns()) {
										ctx.put("isdynacolumns", "Y");
									}
								}
							}
						}
					}

					if (ctx.get("outputtype").equals("E")) {
						newCtx.setContentType("application/vnd.ms-excel");
						ctx.put("isDynamicList", "Y");
						StringBuilder arg10001 = new StringBuilder(
								"attachment;filename=");
						new ReportUtils();
						newCtx.setHeader(
								"Content-disposition",
								arg10001.append(
										getLabelData(reportDefImpl
												.getDescription())).append("_")
										.append(System.currentTimeMillis())
										.append(".xls").toString());
						(new DocumentImpl())
								.downloadExcel(
										ctx,
										request,
										newCtx,
										SystemProperties.getInstance()
												.getString("html.basedir")
												+ "//templates//accordianexceltemplate.xls");
						return;
					}

					if (ctx.get("outputtype").equals("P")) {
						ctx.put("isShowFiltersSearchTrue", Boolean
								.valueOf(reportDefImpl.getIsshowfiltersearch()));
						Context arg14 = new Context();
						arg14.setProject(ctx.getProject());
						arg14.putAll(ctx);
						String arg15 = SystemProperties.getInstance()
								.getString("html.basedir")
								+ "documenttempxml.xml";
						File arg16 = new File(arg15);
						if (arg16.exists()) {
							arg16.delete();
							arg16.createNewFile();
						}

						if (ctx.get("isdynacolumns") != null
								&& ctx.get("isdynacolumns").toString()
										.equals("Y")) {
							arg14.put("reportID", "DYNACOLUMNS");
						} else {
							arg14.put("reportID", ctx.get("reportID"));
						}

						arg14.put("isShowFiltersSearchTrue",
								ctx.get("isShowFiltersSearchTrue"));
						filterDataXml(arg14, arg14, reportDefImpl);
						(new DocumentImpl()).populateDataXml(ctx, arg14, arg15);
						new ReportUtils();
						arg14.put("filename",
								getLabelData(reportDefImpl.getDescription())
										+ "_" + System.currentTimeMillis());
						LinkedHashMap arg17 = new LinkedHashMap();
						List e = null;
						List columnList = null;
						if (reportDefImpl != null
								&& reportDefImpl.getFirstColumnset() != null
								&& reportDefImpl.getFirstColumnset()
										.getOrderedColumnsList() != null) {
							columnList = reportDefImpl.getFirstColumnset()
									.getOrderedColumnsList();
						}

						if (reportDefImpl.getIsshowaccordianreport() != null
								&& reportDefImpl.getIsshowaccordianreport()
										.equals("Y")) {
							e = (List) arg14
									.get("report_data_pdf_list_1_accordianreport");
							arg14.put("isShowAccordianReport", "Y");
							arg17 = getAccordianReportHeaders(arg14,
									reportDefImpl.getName());
							arg14.put("report_data_list_1", e);
						} else {
							e = (List) arg14.get("report_data_list_1");
							if (e != null) {
								ArrayList newDataList = new ArrayList();
								e = filterDataXMLWithFormat(arg14, e,
										reportDefImpl, columnList);
								if (arg14.get("report_data_list_1_headers") != null) {
									arg17 = (LinkedHashMap) arg14
											.get("report_data_list_1_headers");
									arg17 = filterHeadersMapWithFormat(arg14,
											arg17, reportDefImpl, columnList,
											"pdf");
								}

								newDataList.add(arg17);
								newDataList.addAll(e);
								arg14.put("report_data_list_1", newDataList);
							}
						}

						convertPOToPDF(SystemProperties.getInstance()
								.getString("html.basedir")
								+ "//foxsl//performancereport.xsl", arg15,
								arg14, request, newCtx,
								(ServletContextURIResolver) arg14
										.get("DocumentUriResolver"),
								(ServletContext) arg14
										.get("DocumentServletContext"), arg17);
						if (arg16.exists()) {
							arg16.delete();
						}
					}
				}
			}

		}
	}

	public static void convertPOToPDF(String foFile, String xmlFile,
			Context ctx, HttpServletRequest req, HttpServletResponse res,
			ServletContextURIResolver uriResolver,
			ServletContext servletContext, LinkedHashMap headersMap) {
		ServletOutputStream out = null;

		try {
			File e = new File(xmlFile);
			new File(foFile);
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setURIResolver(uriResolver);
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			foUserAgent
					.setBaseURL("file:///" + servletContext.getRealPath("/"));
			out = res.getOutputStream();

			try {
				if (res != null) {
					res.setHeader("Expires", "0");
					res.setHeader("Content-disposition",
							"attachment; filename=\""
									+ ctx.get("filename").toString() + ".pdf\"");
					res.setHeader("Pragma", "public");
				}

				Fop e1 = fopFactory.newFop("application/pdf", foUserAgent, out);
				TransformerFactory factory = TransformerFactory.newInstance();
				factory.setURIResolver(uriResolver);
				StreamSource xsltSrc = null;
				xsltSrc = new StreamSource(foFile);
				Transformer transformer = factory.newTransformer(xsltSrc);
				transformer.setURIResolver(uriResolver);
				StreamSource src = new StreamSource(e);
				SAXResult result = new SAXResult(e1.getDefaultHandler());
				transformer.transform(src, result);
				out = res.getOutputStream();
				out.flush();
			} catch (Exception arg27) {
				logger.error("Error in ReportUtils.convertPOToPDF", arg27);
			}
		} catch (Exception arg28) {
			logger.error("Error in ReportUtils.convertPOToPDF", arg28);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception arg26) {
					logger.error("Error in ReportUtils.convertPOToPDF", arg26);
				}
			}

		}

	}

	public static String parseReportTemplate(Context ctx,
			LinkedHashMap headersMap, String templateFile) throws Exception {
		String templateContent = null;
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(templateFile));
		Element root = doc.getRootElement();
		parseReportTemplate1(ctx, root, headersMap);
		templateContent = (new XMLOutputter(Format.getPrettyFormat()))
				.outputString(root);
		templateContent = templateContent.replaceAll("COLON", ":");
		return templateContent;
	}

	private static void parseReportTemplate1(Context ctx, Element element,
			LinkedHashMap headersMap) throws Exception {
		if (element.getChildren() != null && element.getChildren().size() > 0) {
			for (int i = 0; i < element.getChildren().size(); ++i) {
				Object obj = element.getChildren().get(i);
				if (obj != null && obj instanceof Element) {
					Element ele = (Element) obj;
					String name = ele.getName();
					String value = ele.getText();
					if (name.equalsIgnoreCase("block") && value != null
							&& value.trim().equalsIgnoreCase("#CONTENT#")) {
						ele.removeContent();
						if (ctx.get("isShowAccordianReport") != null
								&& ctx.get("isShowAccordianReport").toString()
										.equals("Y")) {
							generateAccordianReportDataList(ctx, ele,
									headersMap);
						} else {
							generateReportDataList(ctx, ele, headersMap);
						}

						return;
					}

					parseReportTemplate1(ctx, ele, headersMap);
				}
			}
		}

	}

	private static void generateReportDataList(Context ctx,
			Element parentElement, LinkedHashMap headersMap) throws Exception {
		if (headersMap != null) {
			Element tableElement = new Element("foCOLONtable");
			tableElement.setAttribute("border", "0.2pt solid #6bc2f8");
			tableElement.setAttribute("margin-top", "2mm");
			Element bodyElement = new Element("foCOLONtable-body");
			Element forEachElement = new Element("xslCOLONfor-each");
			forEachElement.setAttribute("select",
					"response/report_data_list_1/data");
			Element tableRowElement = new Element("foCOLONtable-row");
			Element chooseElement = new Element("xslCOLONchoose");
			Element whenElement = new Element("xslCOLONwhen");
			whenElement.setAttribute("test", "position()=1");
			Element otherwiseElement = new Element("xslCOLONotherwise");
			Set keySet = headersMap.keySet();
			Iterator itr = keySet.iterator();

			while (itr.hasNext()) {
				String key = (String) itr.next();
				String value = headersMap.get(key) != null ? headersMap
						.get(key).toString() : null;
				String otherwiseKey = key;
				String align = null;
				if (key.contains("_textalignEQUAL")) {
					align = key.substring(key.indexOf("_textalignEQUAL") + 15,
							key.length());
					otherwiseKey = key.substring(0, key.indexOf("_textalign"));
				}

				if (value != null && !value.equals("HIDDEN")) {
					Element whenTableCellElement = new Element(
							"foCOLONtable-cell");
					whenTableCellElement.setAttribute("border",
							"0.2pt solid #6bc2f8");
					whenTableCellElement.setAttribute("padding-start", "3pt");
					whenTableCellElement.setAttribute("padding-top", "3pt");
					whenTableCellElement.setAttribute("padding-bottom", "3pt");
					whenTableCellElement.setAttribute("padding-end", "3pt");
					whenTableCellElement.setAttribute("background-color",
							"#6bc2f8");
					whenTableCellElement.setAttribute("font-weight", "bold");
					if (StringUtils.isNotBlank(align)) {
						whenTableCellElement.setAttribute("text-align", align);
					}

					Element otherwiseTableCellElement = new Element(
							"foCOLONtable-cell");
					otherwiseTableCellElement.setAttribute("border",
							"0.2pt solid #6bc2f8");
					otherwiseTableCellElement.setAttribute("padding-start",
							"3pt");
					otherwiseTableCellElement
							.setAttribute("padding-top", "3pt");
					otherwiseTableCellElement.setAttribute("padding-bottom",
							"3pt");
					otherwiseTableCellElement
							.setAttribute("padding-end", "3pt");
					if (StringUtils.isNotBlank(align)) {
						otherwiseTableCellElement.setAttribute("text-align",
								align);
					}

					Element whenBlockElement = new Element("foCOLONblock");
					whenBlockElement.setAttribute("font-size", "6px");
					whenBlockElement.setAttribute("font-family", "Arial");
					Element whenValueOfElement = new Element("xslCOLONvalue-of");
					whenValueOfElement.setAttribute("select", key);
					whenBlockElement.addContent(whenValueOfElement.detach());
					whenTableCellElement.addContent(whenBlockElement.detach());
					Element otherwiseBlockElement = new Element("foCOLONblock");
					otherwiseBlockElement.setAttribute("font-size", "6px");
					otherwiseBlockElement.setAttribute("font-family", "Arial");
					Element otherwiseValueOfElement = new Element(
							"xslCOLONvalue-of");
					otherwiseValueOfElement
							.setAttribute("select", otherwiseKey);
					otherwiseBlockElement.addContent(otherwiseValueOfElement
							.detach());
					otherwiseTableCellElement.addContent(otherwiseBlockElement
							.detach());
					whenElement.addContent(whenTableCellElement.detach());
					otherwiseElement.addContent(otherwiseTableCellElement
							.detach());
				}
			}

			chooseElement.addContent(whenElement.detach());
			chooseElement.addContent(otherwiseElement.detach());
			tableRowElement.addContent(chooseElement.detach());
			forEachElement.addContent(tableRowElement.detach());
			bodyElement.addContent(forEachElement.detach());
			tableElement.addContent(bodyElement.detach());
			parentElement.addContent(tableElement.detach());
		}
	}

	private static void generateAccordianReportDataList(Context ctx,
			Element parentElement, LinkedHashMap headersMap) throws Exception {
		if (headersMap != null) {
			Element tableElement = new Element("foCOLONtable");
			tableElement.setAttribute("border", "0.2pt solid #6bc2f8");
			tableElement.setAttribute("margin-top", "2mm");
			Element bodyElement = new Element("foCOLONtable-body");
			Element forEachElement = new Element("xslCOLONfor-each");
			forEachElement.setAttribute("select",
					"response/report_data_pdf_list_1_accordianreport/data");
			boolean isHeaderExists = false;
			Set keySet = headersMap.keySet();
			Iterator itr = keySet.iterator();
			if (itr.hasNext()) {
				String tableRowElement = (String) itr.next();
				Object key = headersMap.get(tableRowElement);
				if (tableRowElement.equals("isHeaderExists") && key != null
						&& key.toString().equals("Y")) {
					isHeaderExists = true;
				}
			}

			Element tableCellElement;
			Element cellBlockElement;
			Object arg9999;
			Element tableRowElement1;
			Element valueOfElement1;
			if (isHeaderExists) {
				tableRowElement1 = new Element("xslCOLONchoose");
				Element key1 = new Element("xslCOLONwhen");
				key1.setAttribute("test", "position()=1");
				Element value = new Element("xslCOLONotherwise");
				tableCellElement = new Element("foCOLONtable-row");
				cellBlockElement = new Element("foCOLONtable-row");
				keySet = headersMap.keySet();
				itr = keySet.iterator();

				String tableCellElement1;
				while (itr.hasNext()) {
					tableCellElement1 = (String) itr.next();
					String blockElement = headersMap.get(tableCellElement1) != null
							? headersMap.get(tableCellElement1).toString()
							: null;
					if (!tableCellElement1.equals("isHeaderExists")
							&& !tableCellElement1.contains("AccordianList")) {
						String valueOfElement = tableCellElement1;
						String tableCellElement2 = tableCellElement1;
						String cellBlockElement1 = null;
						if (tableCellElement1.contains("_textalignEQUAL")) {
							cellBlockElement1 = tableCellElement1.substring(
									tableCellElement1
											.indexOf("_textalignEQUAL") + 15,
									tableCellElement1.length());
							tableCellElement2 = tableCellElement1.substring(0,
									tableCellElement1.indexOf("_textalign"));
							valueOfElement = tableCellElement1.substring(0,
									tableCellElement1.indexOf("_textalign"));
						}

						if (blockElement != null
								&& !blockElement.equals("HIDDEN")) {
							Element whenTableCellElement = new Element(
									"foCOLONtable-cell");
							whenTableCellElement.setAttribute("border",
									"0.2pt solid #6bc2f8");
							whenTableCellElement.setAttribute("padding-start",
									"3pt");
							whenTableCellElement.setAttribute("padding-top",
									"3pt");
							whenTableCellElement.setAttribute("padding-bottom",
									"3pt");
							whenTableCellElement.setAttribute("padding-end",
									"3pt");
							whenTableCellElement.setAttribute(
									"background-color", "#6bc2f8");
							whenTableCellElement.setAttribute("font-weight",
									"bold");
							whenTableCellElement.setAttribute("margin-left",
									"0pt");
							if (StringUtils.isNotBlank(cellBlockElement1)) {
								whenTableCellElement.setAttribute("text-align",
										cellBlockElement1);
							}

							Element otherwiseTableCellElement = new Element(
									"foCOLONtable-cell");
							otherwiseTableCellElement.setAttribute("border",
									"0.2pt solid #6bc2f8");
							otherwiseTableCellElement.setAttribute(
									"padding-start", "3pt");
							otherwiseTableCellElement.setAttribute(
									"padding-top", "3pt");
							otherwiseTableCellElement.setAttribute(
									"padding-bottom", "3pt");
							otherwiseTableCellElement.setAttribute(
									"padding-end", "3pt");
							otherwiseTableCellElement.setAttribute(
									"margin-left", "0pt");
							if (StringUtils.isNotBlank(cellBlockElement1)) {
								otherwiseTableCellElement.setAttribute(
										"text-align", cellBlockElement1);
							}

							Element whenBlockElement = new Element(
									"foCOLONblock");
							whenBlockElement.setAttribute("font-size", "6px");
							whenBlockElement.setAttribute("font-family",
									"Arial");
							Element whenValueOfElement = new Element(
									"xslCOLONvalue-of");
							whenValueOfElement.setAttribute("select",
									valueOfElement + "_header");
							whenBlockElement.addContent(whenValueOfElement
									.detach());
							whenTableCellElement.addContent(whenBlockElement
									.detach());
							Element otherwiseBlockElement = new Element(
									"foCOLONblock");
							otherwiseBlockElement.setAttribute("font-size",
									"6px");
							otherwiseBlockElement.setAttribute("font-family",
									"Arial");
							Element otherwiseValueOfElement = new Element(
									"xslCOLONvalue-of");
							otherwiseValueOfElement.setAttribute("select",
									tableCellElement2);
							otherwiseBlockElement
									.addContent(otherwiseValueOfElement
											.detach());
							otherwiseTableCellElement
									.addContent(otherwiseBlockElement.detach());
							tableCellElement.addContent(whenTableCellElement
									.detach());
							key1.addContent(tableCellElement.detach());
							cellBlockElement
									.addContent(otherwiseTableCellElement
											.detach());
							value.addContent(cellBlockElement.detach());
						}
					}
				}

				tableRowElement1.addContent(key1.detach());
				forEachElement.addContent(tableRowElement1);
				keySet = headersMap.keySet();
				itr = keySet.iterator();

				while (itr.hasNext()) {
					tableCellElement1 = (String) itr.next();
					if (headersMap.get(tableCellElement1) != null) {
						headersMap.get(tableCellElement1).toString();
					} else {
						arg9999 = null;
					}

					if (tableCellElement1.contains("AccordianList")) {
						valueOfElement1 = new Element("foCOLONtable-row");
						Element tableCellElement5 = new Element(
								"foCOLONtable-cell");
						tableCellElement5.setAttribute(
								"number-columns-spanned", "10");
						Element cellBlockElement3 = new Element("foCOLONblock");
						tableCellElement5.addContent(cellBlockElement3);
						valueOfElement1.addContent(tableCellElement5.detach());
						bodyElement.addContent(valueOfElement1);
						valueOfElement1 = new Element("foCOLONtable-row");
						tableCellElement5 = new Element("foCOLONtable-cell");
						tableCellElement5.setAttribute("margin-left", "0pt");
						tableCellElement5.setAttribute(
								"number-columns-spanned", "10");
						generateAccordianReportDataList1(ctx,
								tableCellElement5,
								(LinkedHashMap) headersMap
										.get(tableCellElement1),
								tableCellElement1);
						valueOfElement1.addContent(tableCellElement5);
						value.addContent(valueOfElement1.detach());
					}
				}

				tableRowElement1.addContent(value.detach());
			} else {
				tableRowElement1 = new Element("foCOLONtable-row");
				keySet = headersMap.keySet();
				itr = keySet.iterator();

				String key2;
				while (itr.hasNext()) {
					key2 = (String) itr.next();
					String value1 = headersMap.get(key2) != null ? headersMap
							.get(key2).toString() : null;
					if (!key2.equals("isHeaderExists")
							&& !key2.contains("AccordianList")) {
						String tableCellElement3 = key2;
						String cellBlockElement2 = null;
						if (key2.contains("_textalignEQUAL")) {
							cellBlockElement2 = key2.substring(
									key2.indexOf("_textalignEQUAL") + 15,
									key2.length());
							tableCellElement3 = key2.substring(0,
									key2.indexOf("_textalign"));
						}

						if (value1 != null && !value1.equals("HIDDEN")) {
							Element tableCellElement4 = new Element(
									"foCOLONtable-cell");
							tableCellElement4.setAttribute("border",
									"0.2pt solid #6bc2f8");
							tableCellElement4.setAttribute("padding-start",
									"3pt");
							tableCellElement4
									.setAttribute("padding-top", "3pt");
							tableCellElement4.setAttribute("padding-bottom",
									"3pt");
							tableCellElement4
									.setAttribute("padding-end", "3pt");
							tableCellElement4
									.setAttribute("margin-left", "0pt");
							if (StringUtils.isNotBlank(cellBlockElement2)) {
								tableCellElement4.setAttribute("text-align",
										cellBlockElement2);
							}

							Element blockElement1 = new Element("foCOLONblock");
							blockElement1.setAttribute("font-size", "6px");
							blockElement1.setAttribute("font-family", "Arial");
							valueOfElement1 = new Element("xslCOLONvalue-of");
							valueOfElement1.setAttribute("select",
									tableCellElement3);
							blockElement1.addContent(valueOfElement1.detach());
							tableCellElement4
									.addContent(blockElement1.detach());
							tableRowElement1.addContent(tableCellElement4
									.detach());
							forEachElement
									.addContent(tableRowElement1.detach());
						}
					}
				}

				keySet = headersMap.keySet();
				itr = keySet.iterator();

				while (itr.hasNext()) {
					key2 = (String) itr.next();
					if (headersMap.get(key2) != null) {
						headersMap.get(key2).toString();
					} else {
						arg9999 = null;
					}

					if (key2.contains("AccordianList")) {
						tableRowElement1 = new Element("foCOLONtable-row");
						tableCellElement = new Element("foCOLONtable-cell");
						tableCellElement.setAttribute("number-columns-spanned",
								"10");
						cellBlockElement = new Element("foCOLONblock");
						tableCellElement.addContent(cellBlockElement);
						tableRowElement1.addContent(tableCellElement.detach());
						bodyElement.addContent(tableRowElement1);
						tableRowElement1 = new Element("foCOLONtable-row");
						tableCellElement = new Element("foCOLONtable-cell");
						tableCellElement.setAttribute("number-columns-spanned",
								"10");
						tableCellElement.setAttribute("margin-left", "0pt");
						generateAccordianReportDataList1(ctx, tableCellElement,
								(LinkedHashMap) headersMap.get(key2), key2);
						tableRowElement1.addContent(tableCellElement);
						forEachElement.addContent(tableRowElement1.detach());
					}
				}
			}

			bodyElement.addContent(forEachElement.detach());
			tableElement.addContent(bodyElement.detach());
			parentElement.addContent(tableElement.detach());
		}
	}

	private static void generateAccordianReportDataList1(Context ctx,
			Element parentElement, LinkedHashMap headersMap,
			String accordianListKey) throws Exception {
		if (headersMap != null) {
			Element tableElement = new Element("foCOLONtable");
			tableElement.setAttribute("border", "0.2pt solid #6bc2f8");
			tableElement.setAttribute("margin-top", "2mm");
			tableElement.setAttribute("margin-left", "15pt");
			Element bodyElement = new Element("foCOLONtable-body");
			Element whenListSizeElement = new Element("xslCOLONwhen");
			whenListSizeElement.setAttribute("test", accordianListKey
					+ "/data[2]");
			Element chooseListSizeElement = new Element("xslCOLONchoose");
			Element forEachElement = new Element("xslCOLONfor-each");
			forEachElement.setAttribute("select", accordianListKey + "/data");
			boolean isAccordianListFound = false;
			boolean isHeaderExists = false;
			Set keySet = headersMap.keySet();
			Iterator itr = keySet.iterator();
			if (itr.hasNext()) {
				String tableRowElement = (String) itr.next();
				Object key = headersMap.get(tableRowElement);
				if (tableRowElement.equals("isHeaderExists") && key != null
						&& key.toString().equals("Y")) {
					isHeaderExists = true;
				}
			}

			Element tableCellElement;
			Object arg9999;
			Element cellBlockElement;
			Element tableRowElement1;
			Element valueOfElement1;
			if (isHeaderExists) {
				tableRowElement1 = new Element("xslCOLONchoose");
				Element key1 = new Element("xslCOLONwhen");
				key1.setAttribute("test", "position()=1");
				Element value = new Element("xslCOLONotherwise");
				tableCellElement = new Element("foCOLONtable-row");
				cellBlockElement = new Element("foCOLONtable-row");
				keySet = headersMap.keySet();
				itr = keySet.iterator();

				String tableCellElement1;
				while (itr.hasNext()) {
					tableCellElement1 = (String) itr.next();
					String blockElement = headersMap.get(tableCellElement1) != null
							? headersMap.get(tableCellElement1).toString()
							: null;
					if (!tableCellElement1.equals("isHeaderExists")
							&& !tableCellElement1.contains("AccordianList")) {
						String valueOfElement = tableCellElement1;
						String tableCellElement2 = tableCellElement1;
						String cellBlockElement1 = null;
						if (tableCellElement1.contains("_textalignEQUAL")) {
							cellBlockElement1 = tableCellElement1.substring(
									tableCellElement1
											.indexOf("_textalignEQUAL") + 15,
									tableCellElement1.length());
							tableCellElement2 = tableCellElement1.substring(0,
									tableCellElement1.indexOf("_textalign"));
							valueOfElement = tableCellElement1.substring(0,
									tableCellElement1.indexOf("_textalign"));
						}

						if (blockElement != null
								&& !blockElement.equals("HIDDEN")) {
							Element whenTableCellElement = new Element(
									"foCOLONtable-cell");
							whenTableCellElement.setAttribute("border",
									"0.2pt solid #6bc2f8");
							whenTableCellElement.setAttribute("padding-start",
									"3pt");
							whenTableCellElement.setAttribute("padding-top",
									"3pt");
							whenTableCellElement.setAttribute("padding-bottom",
									"3pt");
							whenTableCellElement.setAttribute("padding-end",
									"3pt");
							whenTableCellElement.setAttribute(
									"background-color", "#6bc2f8");
							whenTableCellElement.setAttribute("font-weight",
									"bold");
							whenTableCellElement.setAttribute("margin-left",
									"0pt");
							if (StringUtils.isNotBlank(cellBlockElement1)) {
								whenTableCellElement.setAttribute("text-align",
										cellBlockElement1);
							}

							Element otherwiseTableCellElement = new Element(
									"foCOLONtable-cell");
							otherwiseTableCellElement.setAttribute("border",
									"0.2pt solid #6bc2f8");
							otherwiseTableCellElement.setAttribute(
									"padding-start", "3pt");
							otherwiseTableCellElement.setAttribute(
									"padding-top", "3pt");
							otherwiseTableCellElement.setAttribute(
									"padding-bottom", "3pt");
							otherwiseTableCellElement.setAttribute(
									"padding-end", "3pt");
							otherwiseTableCellElement.setAttribute(
									"margin-left", "0pt");
							if (StringUtils.isNotBlank(cellBlockElement1)) {
								otherwiseTableCellElement.setAttribute(
										"text-align", cellBlockElement1);
							}

							Element whenBlockElement = new Element(
									"foCOLONblock");
							whenBlockElement.setAttribute("font-size", "6px");
							whenBlockElement.setAttribute("font-family",
									"Arial");
							Element whenValueOfElement = new Element(
									"xslCOLONvalue-of");
							whenValueOfElement.setAttribute("select",
									valueOfElement + "_header");
							whenBlockElement.addContent(whenValueOfElement
									.detach());
							whenTableCellElement.addContent(whenBlockElement
									.detach());
							Element otherwiseBlockElement = new Element(
									"foCOLONblock");
							otherwiseBlockElement.setAttribute("font-size",
									"6px");
							otherwiseBlockElement.setAttribute("font-family",
									"Arial");
							Element otherwiseValueOfElement = new Element(
									"xslCOLONvalue-of");
							otherwiseValueOfElement.setAttribute("select",
									tableCellElement2);
							otherwiseBlockElement
									.addContent(otherwiseValueOfElement
											.detach());
							otherwiseTableCellElement
									.addContent(otherwiseBlockElement.detach());
							tableCellElement.addContent(whenTableCellElement
									.detach());
							key1.addContent(tableCellElement.detach());
							cellBlockElement
									.addContent(otherwiseTableCellElement
											.detach());
							value.addContent(cellBlockElement.detach());
						}
					}
				}

				tableRowElement1.addContent(key1.detach());
				forEachElement.addContent(tableRowElement1.detach());
				keySet = headersMap.keySet();
				itr = keySet.iterator();

				while (itr.hasNext()) {
					tableCellElement1 = (String) itr.next();
					if (headersMap.get(tableCellElement1) != null) {
						headersMap.get(tableCellElement1).toString();
					} else {
						arg9999 = null;
					}

					if (tableCellElement1.contains("AccordianList")) {
						valueOfElement1 = new Element("foCOLONtable-row");
						Element tableCellElement5 = new Element(
								"foCOLONtable-cell");
						tableCellElement5.setAttribute(
								"number-columns-spanned", "10");
						Element cellBlockElement3 = new Element("foCOLONblock");
						tableCellElement5.addContent(cellBlockElement3);
						valueOfElement1.addContent(tableCellElement5.detach());
						bodyElement.addContent(valueOfElement1);
						valueOfElement1 = new Element("foCOLONtable-row");
						tableCellElement5 = new Element("foCOLONtable-cell");
						tableCellElement5.setAttribute("margin-left", "0pt");
						tableCellElement5.setAttribute(
								"number-columns-spanned", "10");
						generateAccordianReportDataList1(ctx,
								tableCellElement5,
								(LinkedHashMap) headersMap
										.get(tableCellElement1),
								tableCellElement1);
						valueOfElement1.addContent(tableCellElement5);
						value.addContent(valueOfElement1.detach());
					}
				}

				tableRowElement1.addContent(value.detach());
			} else {
				tableRowElement1 = new Element("foCOLONtable-row");
				keySet = headersMap.keySet();
				itr = keySet.iterator();

				String key2;
				while (itr.hasNext()) {
					key2 = (String) itr.next();
					String value1 = headersMap.get(key2) != null ? headersMap
							.get(key2).toString() : null;
					if (!key2.equals("isHeaderExists")
							&& !key2.contains("AccordianList")) {
						String tableCellElement3 = key2;
						String cellBlockElement2 = null;
						if (key2.contains("_textalignEQUAL")) {
							cellBlockElement2 = key2.substring(
									key2.indexOf("_textalignEQUAL") + 15,
									key2.length());
							tableCellElement3 = key2.substring(0,
									key2.indexOf("_textalign"));
						}

						if (value1 != null && !value1.equals("HIDDEN")) {
							Element tableCellElement4 = new Element(
									"foCOLONtable-cell");
							tableCellElement4.setAttribute("border",
									"0.2pt solid #6bc2f8");
							tableCellElement4.setAttribute("padding-start",
									"3pt");
							tableCellElement4
									.setAttribute("padding-top", "3pt");
							tableCellElement4.setAttribute("padding-bottom",
									"3pt");
							tableCellElement4
									.setAttribute("padding-end", "3pt");
							tableCellElement4
									.setAttribute("margin-left", "0pt");
							if (StringUtils.isNotBlank(cellBlockElement2)) {
								tableCellElement4.setAttribute("text-align",
										cellBlockElement2);
							}

							Element blockElement1 = new Element("foCOLONblock");
							blockElement1.setAttribute("font-size", "6px");
							blockElement1.setAttribute("font-family", "Arial");
							valueOfElement1 = new Element("xslCOLONvalue-of");
							valueOfElement1.setAttribute("select",
									tableCellElement3);
							blockElement1.addContent(valueOfElement1.detach());
							tableCellElement4
									.addContent(blockElement1.detach());
							tableRowElement1.addContent(tableCellElement4
									.detach());
							forEachElement
									.addContent(tableRowElement1.detach());
						}
					}
				}

				keySet = headersMap.keySet();
				itr = keySet.iterator();

				while (itr.hasNext()) {
					key2 = (String) itr.next();
					if (headersMap.get(key2) != null) {
						headersMap.get(key2).toString();
					} else {
						arg9999 = null;
					}

					if (key2.contains("AccordianList")) {
						tableRowElement1 = new Element("foCOLONtable-row");
						tableCellElement = new Element("foCOLONtable-cell");
						tableCellElement.setAttribute("number-columns-spanned",
								"10");
						cellBlockElement = new Element("foCOLONblock");
						tableCellElement.addContent(cellBlockElement);
						tableRowElement1.addContent(tableCellElement.detach());
						bodyElement.addContent(tableRowElement1);
						tableRowElement1 = new Element("foCOLONtable-row");
						tableCellElement = new Element("foCOLONtable-cell");
						tableCellElement.setAttribute("margin-left", "0pt");
						tableCellElement.setAttribute("number-columns-spanned",
								"10");
						generateAccordianReportDataList1(ctx, tableCellElement,
								(LinkedHashMap) headersMap.get(key2), key2);
						tableRowElement1.addContent(tableCellElement);
						forEachElement.addContent(tableRowElement1.detach());
						isAccordianListFound = true;
					}
				}
			}

			whenListSizeElement.addContent(forEachElement.detach());
			chooseListSizeElement.addContent(whenListSizeElement.detach());
			bodyElement.addContent(chooseListSizeElement.detach());
			tableElement.addContent(bodyElement.detach());
			parentElement.addContent(tableElement.detach());
		}
	}

	public static LinkedHashMap getAccordianReportHeaders(IContext ctx,
			String reportId) throws Exception {
		LinkedHashMap headersMap = new LinkedHashMap();
		int accordianIndex = 1;

		try {
			ReportdefImpl e = ReportResources.getInstance(ctx).getReportDef(
					reportId);
			if (e == null || e.getFirstColumnset() == null
					|| e.getFirstColumnset().getColumnList() == null) {
				return null;
			}

			if (e.getIsshowreportdataheader() == null
					|| !e.getIsshowreportdataheader().equals("N")) {
				headersMap.put("isHeaderExists", "Y");
			}

			List colList = e.getFirstColumnset().getOrderedColumnsList();

			for (int i = 0; i < colList.size(); ++i) {
				ColumnImpl colImpl = (ColumnImpl) colList.get(i);
				if (colImpl.getIsreportdisplay()
						&& (!e.getIsshowgroupbysearch() || filterColumnForGroupBySearch(
								colList, colImpl, ctx.get("groupby")))
						&& (!e.getIsshowoutputcolumns() || filterColumnForOutputColumns(
								colList, colImpl, ctx.get("outputcolumns")))) {
					String colLabel = colImpl.getColumnlabel();
					String appendColName;
					if (!StringUtils.isBlank(colLabel)) {
						if (colLabel.startsWith("eval:")) {
							try {
								appendColName = colLabel.substring(
										colLabel.indexOf("eval:") + 1,
										colLabel.length());
								RuleImpl accordianReportDefImpl = RulesResources
										.getInstance(ctx).findRule(
												appendColName);
								colLabel = accordianReportDefImpl.execute(
										(Context) ctx, (String[]) null)
										.toString();
							} catch (Exception arg12) {
								logger.error((Context) ctx,
										"Unable to execute rule for column : "
												+ colImpl.getName());
							}
						}

						new DataUtils();
						colLabel = DataUtils.getLabelFromLabelConf(colLabel);
					}

					if (colLabel != null) {
						colLabel = colLabel.replaceAll("&amp;", "&")
								.replaceAll("&lt;", "<")
								.replaceAll("&gt;", ">")
								.replaceAll("&apos;", "\'");
					}

					appendColName = null;

					try {
						if (MetaDataResources.getInstance(ctx)
								.getField(colImpl.getName()).getType()
								.equals("N")) {
							if (appendColName == null) {
								appendColName = "_textalignEQUALright";
							} else {
								appendColName = appendColName
										+ "_textalignEQUALright";
							}
						} else if (MetaDataResources.getInstance(ctx)
								.getField(colImpl.getName()).getType()
								.equals("D")) {
							if (appendColName == null) {
								appendColName = "_textalignEQUALcenter";
							} else {
								appendColName = appendColName
										+ "_textalignEQUALcenter";
							}
						}

						if (!StringUtils.isBlank(colImpl.getAlign())) {
							if (appendColName == null) {
								appendColName = "_textalignEQUAL"
										+ colImpl.getAlign();
							} else {
								appendColName = appendColName
										+ "_textalignEQUAL"
										+ colImpl.getAlign();
							}
						}
					} catch (Exception arg11) {
						logger.error("Error in ReportUtils.getAccordianReportHeaders", arg11);
						;
					}

					if (appendColName != null) {
						headersMap.put(colImpl.getName() + appendColName,
								colLabel);
					} else {
						headersMap.put(colImpl.getName(), colLabel);
					}

					if (colImpl.getIsaccordian() != null
							&& colImpl.getIsaccordian().toString().equals("Y")) {
						ReportdefImpl arg14 = ReportResources.getInstance(ctx)
								.getReportDef(colImpl.getDrilldownreport());
						LinkedHashMap map = getAccordianReportHeaders1(ctx,
								arg14.getName(), headersMap, colImpl.getName(),
								accordianIndex);
						headersMap.put("AccordianList" + accordianIndex, map);
						++accordianIndex;
					}
				}
			}
		} catch (Exception arg13) {
			ctx.put("isErrorExists", "Y");
			logger.error((Context) ctx,
					"Unable to get accordian headers for report " + reportId
							+ " due to error : " + arg13.getMessage());
		}

		return headersMap;
	}

	private static LinkedHashMap getAccordianReportHeaders1(IContext ctx,
			String reportId, LinkedHashMap mainHeadersMap, String name,
			int accordianIndex) throws Exception {
		LinkedHashMap headersMap = new LinkedHashMap();

		try {
			ReportdefImpl e = ReportResources.getInstance(ctx).getReportDef(
					reportId);
			if (e == null || e.getFirstColumnset() == null
					|| e.getFirstColumnset().getColumnList() == null) {
				return null;
			}

			if (e.getIsshowreportdataheader() == null
					|| !e.getIsshowreportdataheader().equals("N")) {
				headersMap.put("isHeaderExists", "Y");
			}

			List colList = e.getFirstColumnset().getOrderedColumnsList();

			for (int i = 0; i < colList.size(); ++i) {
				ColumnImpl colImpl = (ColumnImpl) colList.get(i);
				if (colImpl.getIsreportdisplay()) {
					String colLabel = colImpl.getColumnlabel();
					String appendColName;
					if (!StringUtils.isBlank(colLabel)) {
						if (colLabel.startsWith("eval:")) {
							try {
								appendColName = colLabel.substring(
										colLabel.indexOf("eval:") + 1,
										colLabel.length());
								RuleImpl accordianReportDefImpl = RulesResources
										.getInstance(ctx).findRule(
												appendColName);
								colLabel = accordianReportDefImpl.execute(
										(Context) ctx, (String[]) null)
										.toString();
							} catch (Exception arg15) {
								logger.error((Context) ctx,
										"Unable to execute rule for column : "
												+ colImpl.getName());
							}
						}

						new DataUtils();
						colLabel = DataUtils.getLabelFromLabelConf(colLabel);
					}

					if (colLabel != null) {
						colLabel = colLabel.replaceAll("&amp;", "&")
								.replaceAll("&lt;", "<")
								.replaceAll("&gt;", ">")
								.replaceAll("&apos;", "\'");
					}

					appendColName = null;

					try {
						if (MetaDataResources.getInstance(ctx)
								.getField(colImpl.getName()).getType()
								.equals("N")) {
							if (appendColName == null) {
								appendColName = "_textalignEQUALright";
							} else {
								appendColName = appendColName
										+ "_textalignEQUALright";
							}
						} else if (MetaDataResources.getInstance(ctx)
								.getField(colImpl.getName()).getType()
								.equals("D")) {
							if (appendColName == null) {
								appendColName = "_textalignEQUALcenter";
							} else {
								appendColName = appendColName
										+ "_textalignEQUALcenter";
							}
						}

						if (!StringUtils.isBlank(colImpl.getAlign())) {
							if (appendColName == null) {
								appendColName = "_textalignEQUAL"
										+ colImpl.getAlign();
							} else {
								appendColName = appendColName
										+ "_textalignEQUAL"
										+ colImpl.getAlign();
							}
						}
					} catch (Exception arg14) {
						logger.error("Error in ReportUtils.getAccordianReportHeaders1", arg14);
						;
					}

					if (appendColName != null) {
						headersMap.put(colImpl.getName() + appendColName,
								colLabel);
					} else {
						headersMap.put(colImpl.getName(), colLabel);
					}

					if (colImpl.getIsaccordian() != null
							&& colImpl.getIsaccordian().toString().equals("Y")) {
						ReportdefImpl arg17 = ReportResources.getInstance(ctx)
								.getReportDef(colImpl.getDrilldownreport());
						int accordianIndex1 = accordianIndex + 1;
						LinkedHashMap map = getAccordianReportHeaders1(ctx,
								arg17.getName(), headersMap, colImpl.getName(),
								accordianIndex1);
						headersMap.put("AccordianList" + accordianIndex1, map);
						++accordianIndex;
					}
				}
			}
		} catch (Exception arg16) {
			ctx.put("isErrorExists", "Y");
			logger.error((Context) ctx,
					"Unable to get accordian headers1 for report " + reportId
							+ " due to error : " + arg16.getMessage());
		}

		return headersMap;
	}

	private static List filterDataXMLWithFormat(Context ctx, List dataList,
			ReportdefImpl reportDefImpl, List columnList) {
		if (reportDefImpl != null && columnList != null) {
			int records = 10000;
			ArrayList newDataList = new ArrayList();

			try {
				String e = SystemProperties.getInstance().getString(
						"appl." + ctx.getProject()
								+ ".commission.reports.pdf.maxrecords");
				if (e != null && !"".equals(e)) {
					records = Integer.parseInt(e);
				}
			} catch (Exception arg13) {
				logger.error(
						ctx,
						"Property appl."
								+ ctx.getProject()
								+ ".commission.reports.pdf.maxrecords does not exist, Default max records set to "
								+ records);
			}

			try {
				if (dataList != null && dataList.size() > 0) {
					for (int arg16 = 0; arg16 < dataList.size()
							&& arg16 <= records; ++arg16) {
						Map map = (Map) dataList.get(arg16);

						for (int j = 0; j < columnList.size(); ++j) {
							ColumnImpl colImpl = (ColumnImpl) columnList.get(j);
							if (colImpl.getIsreportdisplay()) {
								String property;
								if (!StringUtils.isBlank(colImpl.getEval())) {
									property = colImpl.getEval();
									if (property.startsWith("eval:")) {
										try {
											property = property
													.substring(
															property.indexOf("eval:") + 5,
															property.length());
											RuleImpl value = RulesResources
													.getInstance(ctx).findRule(
															property);
											if (!value.evaluate(ctx,
													(String[]) null)) {
												continue;
											}
										} catch (Exception arg14) {
											logger.error(
													ctx,
													"Unable to execute rule "
															+ colImpl.getEval()
															+ " due to error : "
															+ arg14.getMessage(), arg14);
										}
									} else {
										HashMap arg17 = ctx.get("Tabs_Conf") == null
												? null
												: (HashMap) ctx
														.get("Tabs_Conf");
										if (arg17 != null
												&& arg17.containsKey(property)
												&& arg17.get(property) != null
												&& "Y".equalsIgnoreCase(arg17
														.get(property)
														.toString())) {
											continue;
										}
									}
								}

								property = colImpl.getMetaproperty();
								if (property != null && property.contains(".")) {
									property = property.substring(
											property.lastIndexOf(".") + 1,
											property.length());
								}

								String arg18 = map.get(colImpl.getName()
										.toString()) != null ? map.get(
										colImpl.getName().toString())
										.toString() : "";

								try {
									if (MetaDataResources.getInstance(ctx)
											.getField(property).getType()
											.equals("N")
											&& !StringUtils.isBlank(arg18)
											&& arg18.startsWith("-")) {
										arg18 = "("
												+ arg18.substring(1,
														arg18.length()) + ")";
									}
								} catch (Exception arg12) {
									logger.error("Error in ReportUtils.filterDataXMLWithFormat", arg12);
								}

								if (reportDefImpl.getIsshowgroupbysearch()
										&& !filterColumnForGroupBySearch(
												columnList, colImpl,
												ctx.get("groupby"))) {
									map.put(colImpl.getName(), "HIDDEN");
								} else if (reportDefImpl
										.getIsshowoutputcolumns()
										&& !filterColumnForOutputColumns(
												columnList, colImpl,
												ctx.get("outputcolumns"))) {
									map.put(colImpl.getName(), "HIDDEN");
								} else {
									map.put(colImpl.getName(), arg18);
								}
							}
						}

						newDataList.add(map);
					}
				}
			} catch (Exception arg15) {
				logger.error("Error in ReportUtils.filterDataXMLWithFormat", arg15);
				ctx.put("isErrorExists", "Y");
			}

			return newDataList;
		} else {
			return dataList;
		}
	}

	private static LinkedHashMap filterHeadersMapWithFormat(Context ctx,
			LinkedHashMap headersMap, ReportdefImpl reportDefImpl,
			List columnList, String type) {
		if (reportDefImpl != null && headersMap != null) {
			LinkedHashMap newHeadersMap = new LinkedHashMap();

			try {
				for (int e = 0; e < columnList.size(); ++e) {
					ColumnImpl column = (ColumnImpl) columnList.get(e);
					if (column.getIsreportdisplay()) {
						if (reportDefImpl.getIsshowgroupbysearch()
								&& !filterColumnForGroupBySearch(columnList,
										column, ctx.get("groupby"))) {
							newHeadersMap.put(column.getName(), "HIDDEN");
						} else if (reportDefImpl.getIsshowoutputcolumns()
								&& !filterColumnForOutputColumns(columnList,
										column, ctx.get("outputcolumns"))) {
							newHeadersMap.put(column.getName(), "HIDDEN");
						} else {
							String colLabel = column.getColumnlabel();
							String appendColName;
							if (!StringUtils.isBlank(colLabel)) {
								if (colLabel.startsWith("eval:")) {
									try {
										appendColName = colLabel.substring(
												colLabel.indexOf("eval:") + 1,
												colLabel.length());
										RuleImpl ruleImpl = RulesResources
												.getInstance(ctx).findRule(
														appendColName);
										colLabel = ruleImpl.execute(ctx,
												(String[]) null).toString();
									} catch (Exception arg12) {
										logger.error(ctx,
												"Unable to execute rule for column : "
														+ column.getName(), arg12);
									}
								}

								new DataUtils();
								colLabel = DataUtils
										.getLabelFromLabelConf(colLabel);
							}

							if (type != null && type.equals("excel")) {
								appendColName = null;

								try {
									if (MetaDataResources.getInstance(ctx)
											.getField(column.getName())
											.getType().equals("N")) {
										if (appendColName == null) {
											appendColName = "_textalign=right";
										} else {
											appendColName = appendColName
													+ "_textalign=right";
										}
									} else if (MetaDataResources
											.getInstance(ctx)
											.getField(column.getName())
											.getType().equals("D")) {
										if (appendColName == null) {
											appendColName = "_textalign=center";
										} else {
											appendColName = appendColName
													+ "_textalign=center";
										}
									}

									if (!StringUtils.isBlank(column.getAlign())) {
										if (appendColName == null) {
											appendColName = "_textalign="
													+ column.getAlign();
										} else {
											appendColName = appendColName
													+ "_textalign="
													+ column.getAlign();
										}
									}
								} catch (Exception arg11) {
									logger.error("Error in ReportUtils.filterHeadersMapWithFormat", arg11);
									;
								}

								if (appendColName != null) {
									newHeadersMap.put(column.getName()
											+ appendColName, colLabel);
								} else {
									newHeadersMap.put(column.getName(),
											colLabel);
								}
							} else {
								appendColName = null;

								try {
									if (MetaDataResources.getInstance(ctx)
											.getField(column.getName())
											.getType().equals("N")) {
										if (appendColName == null) {
											appendColName = "_textalignEQUALright";
										} else {
											appendColName = appendColName
													+ "_textalignEQUALright";
										}
									} else if (MetaDataResources
											.getInstance(ctx)
											.getField(column.getName())
											.getType().equals("D")) {
										if (appendColName == null) {
											appendColName = "_textalignEQUALcenter";
										} else {
											appendColName = appendColName
													+ "_textalignEQUALcenter";
										}
									}

									if (!StringUtils.isBlank(column.getAlign())) {
										if (appendColName == null) {
											appendColName = "_textalignEQUAL"
													+ column.getAlign();
										} else {
											appendColName = appendColName
													+ "_textalignEQUAL"
													+ column.getAlign();
										}
									}
								} catch (Exception arg10) {
									logger.error("Error in ReportUtils.filterHeadersMapWithFormat", arg10);
									;
								}

								if (appendColName != null) {
									newHeadersMap.put(column.getName()
											+ appendColName, colLabel);
								} else {
									newHeadersMap.put(column.getName(),
											colLabel);
								}
							}
						}
					}
				}
			} catch (Exception arg13) {
				logger.error("Error in ReportUtils.filterHeadersMapWithFormat", arg13);
				ctx.put("isErrorExists", "Y");
			}

			return newHeadersMap;
		} else {
			return headersMap;
		}
	}

	private static boolean filterColumnForGroupBySearch(List columnList,
			ColumnImpl column, Object groupBy) throws Exception {
		String metaproperty = column.getMetaproperty();
		metaproperty = metaproperty.indexOf(".") != -1
				&& metaproperty.indexOf(".") < metaproperty.length()
				? metaproperty.substring(metaproperty.indexOf(".") + 1,
						metaproperty.length()) : metaproperty;

		for (int i = 0; i < columnList.size(); ++i) {
			ColumnImpl colImpl = (ColumnImpl) columnList.get(i);
			if (colImpl.getIsgroupbydisplay()
					&& colImpl.getName().equals(metaproperty)) {
				boolean isFound = false;
				if (groupBy != null && !groupBy.toString().equals("")) {
					StringTokenizer groupBytokens = new StringTokenizer(
							groupBy.toString(), ",");

					while (groupBytokens.hasMoreTokens()) {
						String groupBytoken = groupBytokens.nextToken();
						if (groupBytoken.equals(metaproperty)) {
							isFound = true;
							break;
						}
					}
				}

				return isFound;
			}
		}

		return true;
	}

	private static boolean filterColumnForOutputColumns(List columnList,
			ColumnImpl column, Object outputcolumns) throws Exception {
		String colName = column.getName();
		if (colName == null) {
			return true;
		} else {
			boolean isFound = false;
			if (outputcolumns != null && !outputcolumns.toString().equals("")) {
				StringTokenizer outputcolumnstokens = new StringTokenizer(
						outputcolumns.toString(), ",");

				while (outputcolumnstokens.hasMoreTokens()) {
					String outputcolumnstoken = outputcolumnstokens.nextToken();
					if (outputcolumnstoken.equals(colName)) {
						isFound = true;
						break;
					}
				}
			}

			return isFound;
		}
	}

	public static void openManageGraph(Context ctx, HttpServletRequest request)
			throws Exception {
		request.getSession().removeAttribute("old_FieldList");
		ctx.put("old_FieldList", (Object) null);
	}

	public static String getGraphPageContent(Context ctx,
			HttpServletRequest request, HttpServletResponse response,
			String inet_method) throws Exception {
		String pageContent = "";
		if (StringUtils.isBlank(inet_method)) {
			return pageContent;
		} else {
			String reportID = ctx.get("reportID") != null ? ctx.get("reportID")
					.toString() : null;
			ReportdefImpl reportDefImpl = null;
			if (StringUtils.isNotBlank(reportID)) {
				reportDefImpl = ReportResources.getInstance(ctx).getReportDef(
						reportID);
			}

			pageContent = parseReportHTML(ctx, "managegraphtemplate",
					"reports/managegraphtemplate.html", request, reportDefImpl);
			return pageContent;
		}
	}

	public static void refreshManageGraphFilter(Context ctx,
			HttpServletRequest request) throws Exception {
		request.getSession().removeAttribute("old_FieldList");
		ctx.put("old_FieldList", (Object) null);
		ctx.put("isgraph", (Object) null);
		String reportID = ctx.get("reportID") != null ? ctx.get("reportID")
				.toString() : null;
		ReportdefImpl reportDefImpl = null;
		if (StringUtils.isNotBlank(reportID)) {
			reportDefImpl = ReportResources.getInstance(ctx).getReportDef(
					reportID);
		}

		addFieldToPage(ctx, reportDefImpl, request);
	}

	public static void resetManageGraph(Context ctx, HttpServletRequest request)
			throws Exception {
		request.getSession().removeAttribute("old_FieldList");
		ctx.put("old_FieldList", (Object) null);
		ctx.put("isgraph", (Object) null);
		String reportID = ctx.get("reportID") != null ? ctx.get("reportID")
				.toString() : null;
		ReportdefImpl reportDefImpl = null;
		if (StringUtils.isNotBlank(reportID)) {
			reportDefImpl = ReportResources.getInstance(ctx).getReportDef(
					reportID);
		}

		if (reportDefImpl != null
				&& reportDefImpl.getFirstColumnset() != null
				&& reportDefImpl.getFirstColumnset().getOrderedColumnsList() != null) {
			List colsList = reportDefImpl.getFirstColumnset()
					.getOrderedColumnsList();

			for (int i = 0; i < colsList.size(); ++i) {
				ColumnImpl column = (ColumnImpl) colsList.get(i);
				if (column.getIsreportfilter()) {
					ctx.remove(column.getName());
					if (column.getIspopupsearch()) {
						request.getSession().removeAttribute(
								"to_filter_data_" + column.getName());
					}
				}
			}
		}

		ctx.put("reportID", (Object) null);
	}

	public static void showManageGraph(Context ctx, HttpServletRequest request)
			throws Exception {
		String reportID = ctx.get("reportID") != null ? ctx.get("reportID")
				.toString() : null;
		ReportdefImpl reportDefImpl = null;
		if (StringUtils.isNotBlank(reportID)) {
			reportDefImpl = ReportResources.getInstance(ctx).getReportDef(
					reportID);
		}

		if (reportDefImpl != null) {
			if (ctx.get("inet_skip_validation") == null
					|| !ctx.get("inet_skip_validation").toString().equals("Y")) {
				validatePage(ctx, reportDefImpl);
				validateWorkflow(ctx, reportDefImpl);
			}

			(new ReportUtils()).createPageError(ctx);
			if (ctx.get("inet_form_dirty") == null
					|| !ctx.get("inet_form_dirty").equals("Y")) {
				GraphdefImpl graphDefImpl = GraphResources.getInstance(ctx)
						.getGraphDef(reportDefImpl.getGraphid());
				if (graphDefImpl != null
						&& graphDefImpl.getFirstGraphworkflow() != null) {
					GraphworkflowImpl graphWrkfImpl = graphDefImpl
							.getFirstGraphworkflow();
					HashMap ctxMap = new HashMap();
					if (graphWrkfImpl.getStoredprocedureoperationList() != null
							&& graphWrkfImpl.getStoredprocedureoperationList()
									.size() > 0) {
						com.manage.managereporting.graphs.StoredprocedureoperationImpl procImpl = (com.manage.managereporting.graphs.StoredprocedureoperationImpl) graphWrkfImpl
								.getStoredprocedureoperationList().get(0);
						if (procImpl.getParameteroperationList() != null
								&& procImpl.getParameteroperationList().size() > 0) {
							for (int i = 0; i < procImpl
									.getParameteroperationList().size(); ++i) {
								ParameteroperationImpl pImpl = (ParameteroperationImpl) procImpl
										.getParameteroperationList().get(i);
								String ctxValue = ctx.get(pImpl.getProperty()) != null
										&& !ctx.get(pImpl.getProperty())
												.toString().equals("")
										? ctx.get(pImpl.getProperty())
												.toString() : null;
								ctxMap.put(pImpl.getProperty(), ctxValue);
							}
						}
					}

					request.getSession().setAttribute("graph_filters", ctxMap);
				}

				ctx.put("isgraph", "Y");
				ctx.put("graphlink",
						"graph;jsessionid=" + ctx.get("jsessionid") + "?rnum="
								+ (new Random()).nextLong() + "&graphID="
								+ reportDefImpl.getGraphid());
			}

		}
	}

	public static void clickHierarchyManageReport(Context ctx,
			HttpServletRequest request, ReportdefImpl reportDefImpl,
			HttpServletResponse response) throws Exception {
		updateFieldListWithData(ctx, request);
		populateParameter(ctx, reportDefImpl, request);
		ctx.put("TEMPLATE_NAME", "managereporttemplate");
		String pageContent = parseReportHTML(ctx, "managereporttemplate",
				"reports/managereporttemplate.html", request, reportDefImpl);
		pageContent = pageContent.substring(pageContent.indexOf("<html>"),
				pageContent.length());
		pageContent = (new PageUtils()).cleanContent(pageContent);
		Document document = null;
		SAXBuilder builder = new SAXBuilder();
		document = builder.build(new StringReader(pageContent));
		Element root = null;
		if (document != null) {
			root = document.getRootElement();
		}

		String replayid = ctx.get("datadisplayid").toString();
		Element element = (new PageUtils()).getExistingElement(root, replayid);
		if (element == null) {
			{ if (logger.isDebugEnabled()) logger.debug(ctx, replayid + " div id is not present in "
					+ ctx.get("inet_page").toString() + ".html"); }
		}

		String htmlDoc = "";
		if (element != null) {
			List eleList = element.getChildren();
			if (eleList != null) {
				for (int i = 0; i < eleList.size(); ++i) {
					htmlDoc = htmlDoc
							+ HTMLUtils.beautifyHTML((Element) eleList.get(i));
				}

				htmlDoc = htmlDoc.replaceAll("#space#", "&nbsp;");
			}
		}

		(new PageUtils()).writeResponse(response, htmlDoc, "text/html");
	}

	private static LinkedHashMap<String, LinkedHashMap<String, String>> creatingExcelHeadersMapForMultipleGrid(
			Context ctx, ReportdefImpl reportDefImpl) throws Exception {
		LinkedHashMap map = new LinkedHashMap();
		if (reportDefImpl.getIsshowmultiplegrid()
				&& reportDefImpl.getOrderedColumnSetList() != null
				&& reportDefImpl.getOrderedColumnSetList().size() > 0) {
			for (int i = 0; i < reportDefImpl.getOrderedColumnSetList().size(); ++i) {
				ColumnsetImpl columnSetImp = (ColumnsetImpl) reportDefImpl
						.getOrderedColumnSetList().get(i);
				if (columnSetImp != null
						&& columnSetImp.getOrderedColumnsList().size() > 0) {
					LinkedHashMap localMap = new LinkedHashMap();

					for (int j = 0; j < columnSetImp.getOrderedColumnsList()
							.size(); ++j) {
						ColumnImpl column = (ColumnImpl) columnSetImp
								.getOrderedColumnsList().get(j);
						if (column.getIsreportdisplay()
								&& (new ReportUtils()).checkForHideShowColumn(
										ctx, column)
								&& (ctx.get("ISCOMINGFORDATAONLY") != null
										&& ctx.get("ISCOMINGFORDATAONLY")
												.toString().equals("Y") || (!reportDefImpl
										.getIsshowgroupbysearch() || ReportDataBlock
										.filterColumnForGroupBySearch(
												columnSetImp
														.getOrderedColumnsList(),
												column, ctx.get("groupby")))
										&& (!reportDefImpl
												.getIsshowoutputcolumns() || ReportDataBlock
												.filterColumnForOutputColumns(
														columnSetImp
																.getOrderedColumnsList(),
														column,
														ctx.get("outputcolumns"))))) {
							String colLabel = column.getColumnlabel();
							String appendColName;
							if (!StringUtils.isBlank(colLabel)) {
								if (colLabel.startsWith("eval:")) {
									try {
										appendColName = colLabel.substring(
												colLabel.indexOf("eval:") + 5,
												colLabel.length());
										RuleImpl ruleImpl = RulesResources
												.getInstance(ctx).findRule(
														appendColName);
										colLabel = ruleImpl.execute(ctx,
												(String[]) null).toString();
									} catch (Exception arg11) {
										logger.error(ctx,
												"Unable to execute rule for column : "
														+ column.getName(), arg11);
									}
								}

								new ReportUtils();
								colLabel = getLabelData(colLabel);
							}

							appendColName = null;

							try {
								if (MetaDataResources.getInstance(ctx)
										.getField(column.getName()).getType()
										.equals("N")) {
									if (appendColName == null) {
										appendColName = "_textalign=right";
									} else {
										appendColName = appendColName
												+ "_textalign=right";
									}
								} else if (MetaDataResources.getInstance(ctx)
										.getField(column.getName()).getType()
										.equals("D")) {
									if (appendColName == null) {
										appendColName = "_textalign=center";
									} else {
										appendColName = appendColName
												+ "_textalign=center";
									}
								}

								if (!StringUtils.isBlank(column.getAlign())) {
									if (appendColName == null) {
										appendColName = "_textalign="
												+ column.getAlign();
									} else {
										appendColName = appendColName
												+ "_textalign="
												+ column.getAlign();
									}
								}
							} catch (Exception arg10) {
								logger.error("Error in ReportUtils.creatingExcelHeadersMapForMultipleGrid", arg10);
								;
							}

							if (appendColName != null) {
								localMap.put(column.getName() + appendColName,
										colLabel);
							} else {
								localMap.put(column.getName(), colLabel);
							}
						}
					}

					map.put(columnSetImp.getName() + "_headers", localMap);
					ctx.put(columnSetImp.getName() + "_headers", localMap);
				}
			}
		}

		return map;
	}

	private void parseExportAllButton(Context ctx, ReportdefImpl reportdefImpl,
			Element blockelement) throws Exception {
		String href = "javascript:managereport(\'|reportID|ISSKIPREQUESTAUTHTOKEN|\',\'|"
				+ reportdefImpl.getName()
				+ "|Y|\',\'INET_SUBMIT_EXPORT_ALL\',\'MANAGE_REPORTING\');";
		blockelement.setAttribute("href", href);
	}

	public static void submitExportAll(Context ctx, HttpServletRequest request,
			ReportdefImpl reportDefImpl) throws Exception {
		(new ReportUtils()).getDynaKeysValues(ctx, reportDefImpl,
				(ColumnImpl) null);
		ctx.put("PAGE_NUMBER", (Object) null);
		ctx.put("PAGE_SIZE", (Object) null);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get("DocumentResponse");
		(new RequestProcessor()).populateContext(ctx, ctx.get("search_xml")
				.toString());
		ctx.put("ispaginationrequired",
				reportDefImpl.getIsreportdatapagination());
		List workflowList = reportDefImpl.getReportworkflowList();
		if (workflowList != null) {
			for (int headerMap = 0; headerMap < workflowList.size(); ++headerMap) {
				ReportworkflowImpl reportWorkflowImpl = (ReportworkflowImpl) workflowList
						.get(headerMap);
				if (reportWorkflowImpl.getIsexecuteonview() == null
						|| !reportWorkflowImpl.getIsexecuteonview().equals("Y")) {
					String eval = reportWorkflowImpl.getEval();
					boolean isExecuteWorkflow = true;
					if (!StringUtils.isBlank(eval)) {
						try {
							isExecuteWorkflow = RulesResources.getInstance(ctx)
									.findRule(eval)
									.evaluate(ctx, (String[]) null);
						} catch (Exception arg9) {
							logger.error(
									ctx,
									"Unable to execute rule : " + eval
											+ " for report : "
											+ reportDefImpl.getName(), arg9);
						}
					}

					if (isExecuteWorkflow) {
						if (reportWorkflowImpl.getCheckfornullparams() != null
								&& !reportWorkflowImpl.getCheckfornullparams()
										.equals("")) {
							checkForNullParams(ctx,
									reportWorkflowImpl.getCheckfornullparams());
						}

						ctx.put("reportoutputtype",
								reportWorkflowImpl.getOutputtype());
						populateContextWithPopupSearchColumn(ctx, reportDefImpl);
						reportWorkflowImpl.executeWorkflow(ctx,
								reportWorkflowImpl.getOutputtype(),
								reportDefImpl.getName(),
								reportWorkflowImpl.getIsdynacolumns());
						if (reportWorkflowImpl.getIsdynacolumns()) {
							ctx.put("isdynacolumns", "Y");
						}
					}
				}
			}
		}

		response.setContentType("application/vnd.ms-excel");
		ctx.put("isDynamicList", "Y");
		LinkedHashMap arg10;
		if (reportDefImpl.getIsshowmultiplegrid()) {
			arg10 = null;
			arg10 = creatingExcelHeadersMapForMultipleGrid(ctx, reportDefImpl);
			ctx.put("report_data_list_1_headers", arg10);
		} else {
			arg10 = null;
			if (ctx.get("isdynacolumns") != null
					&& ctx.get("isdynacolumns").toString().equals("Y")) {
				arg10 = creatingExcelDynamicHeadersMap(ctx, true);
			} else {
				arg10 = creatingExcelHeadersMap(ctx, reportDefImpl);
			}

			ctx.put("report_data_list_1_headers", arg10);
		}

		StringBuilder arg10001 = new StringBuilder("attachment;filename=");
		new ReportUtils();
		response.setHeader(
				"Content-disposition",
				arg10001.append(getLabelData(reportDefImpl.getDescription()))
						.append("_").append(System.currentTimeMillis())
						.append(".xlsx").toString());
		(new DocumentImpl()).downloadExcel(ctx, request, response,
				SystemProperties.getInstance().getString("html.basedir")
						+ "//templates//exceltemplate.xls");
	}

	private void parseHeadNode(Element element, List children) throws Exception {
		Element sibling = null;
		String js_src = null;

		for (int i = 0; i < children.size(); ++i) {
			js_src = null;
			sibling = null;
			sibling = (Element) children.get(i);
			if (sibling.getName() == null
					|| !"script".equalsIgnoreCase(sibling.getName())
					&& !"link".equalsIgnoreCase(sibling.getName())) {
				this.parseHeadNode(sibling, sibling.getChildren());
			} else {
				if ("script".equalsIgnoreCase(sibling.getName())
						&& sibling.getAttributeValue("src") != null) {
					js_src = sibling.getAttributeValue("src");
					js_src = js_src.contains("?") ? js_src + "&v1="
							+ System.currentTimeMillis() : js_src + "?v="
							+ System.currentTimeMillis();
					ParseUtil.addAttribute(sibling, "src", js_src);
				}

				if ("link".equalsIgnoreCase(sibling.getName())
						&& sibling.getAttributeValue("href") != null) {
					js_src = sibling.getAttributeValue("href");
					js_src = js_src.contains("?") ? js_src + "&v1="
							+ System.currentTimeMillis() : js_src + "?v="
							+ System.currentTimeMillis();
					ParseUtil.addAttribute(sibling, "href", js_src);
				}
			}
		}

	}
}
