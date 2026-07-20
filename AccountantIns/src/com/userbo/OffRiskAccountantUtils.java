package com.userbo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.fop.servlet.ServletContextURIResolver;

import com.fop.DownloadFOP;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class OffRiskAccountantUtils {

	private static InetLogger logger = InetLogger
			.getInetLogger(OffRiskAccountantUtils.class);

	public void OffRiskUpload(IContext ctx) throws Exception {
		logger.debug("Going To initiate the Upload Process Of OffRiskLetters");
		Context offRiskContext = new Context();
		List<HashMap> downloadList = new ArrayList<HashMap>();
		String[] policyKeyArray = null;
		try {
			OffRiskAccountantUtils utils = new OffRiskAccountantUtils();

			offRiskContext.putAll(ctx);

			String strPolicyKey = ctx.get("strPolicyKey") != null ? ctx.get(
					"strPolicyKey").toString() : "";
			policyKeyArray = strPolicyKey.split(",");
			logger.debug("Length of Array " + policyKeyArray.length);
			if (policyKeyArray.length == 0)
				return;
			for (String policykey : policyKeyArray) {
				logger.debug("Going to fetch data for PolicyKey " + policykey);
				offRiskContext.put("PolicyKey", Integer.parseInt(policykey));

				List list = SqlResources.getSqlMapProcessor(offRiskContext).select("Policy.FetchAllPolicyDataFromPolicyKey",offRiskContext);
				if (list != null && list.size() == 1) {
					Map map = (Map) list.get(0);
					offRiskContext.put("VersionSequence",map.get("VersionSequence"));
					offRiskContext.put("QuoteNumber", map.get("QuoteNumber"));
					offRiskContext.put("Address1", map.get("Address1"));
					offRiskContext.put("AccountNameOffRisk",map.get("AccountNameOffRisk"));
					String isLetterAlreadyUploaded = map.get("IsOffRiskLetterUploaded") != null ? map.get("IsOffRiskLetterUploaded").toString() : "";
					logger.debug("Is Off Risk Letter Already Uploaded "
							+ isLetterAlreadyUploaded);
					if (!isLetterAlreadyUploaded.equals("Yes"))
						utils.generateOffRiskLetters(offRiskContext);
				}
				offRiskContext.remove("VersionSequence");
				offRiskContext.remove("PolicyKey");
				offRiskContext.remove("QuoteNumber");
				offRiskContext.remove("Address1");
				offRiskContext.remove("AccountNameOffRisk");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Problem in Uploading the Off Risk Letters "
					+ e.getMessage());
			offRiskContext.remove("VersionSequence");
			offRiskContext.remove("PolicyKey");
			offRiskContext.remove("QuoteNumber");
			offRiskContext.remove("Address1");
			offRiskContext.remove("AccountNameOffRisk");
		}
	}

	private void generateOffRiskLetters(IContext ctx) throws Exception {

		logger.debug("Going To Create Off Risk Letters "
				+ ctx.get("QuoteNumber"));
		OutputStream out = null;
		try {
			String htmlDir = SystemProperties.getInstance().getString(
					"html.basedir");
			String outFile = htmlDir + "data//OffRiskLetters_"
					+ ctx.get("AccountNameOffRisk") + ".pdf";

			out = new java.io.FileOutputStream(outFile);
			out = new java.io.BufferedOutputStream(out);

			String baseUrl = null;
			if (ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();

			ServletContextURIResolver uriResolver = null;
			if (ctx.get("uriResolver") != null)
				uriResolver = (ServletContextURIResolver) ctx
						.get("uriResolver");
			logger.debug("Going to create Off Risk PDFs"
					+ ctx.get("QuoteNumber"));
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new DownloadFOP().makeOffRiskPDF((Context) ctx, out, baseUrl,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));
			logger.debug("Off Risk PDF created succesfully for "
					+ ctx.get("QuoteNumber"));
			if (out != null)
				out.close();

			logger.debug("Going to upload OffRisk PDF "
					+ ctx.get("QuoteNumber"));
			boolean documentUploaded = false;
			try {
				String skipUpload = SystemProperties.getInstance().getString(
						"appl." + ctx.getProject() + ".skipupload");
				if (!"Y".equals(skipUpload)) {
					new DocumentManagment().uploadOffRiskLetter(ctx);
					documentUploaded = true;
				}
				
				logger.debug("Document is uploaded successfully "
						+ documentUploaded);
				logger.debug("Off Risk Letter uploaded for "
						+ ctx.get("QuoteNumber"));
			} catch (Exception e) {
				logger.debug("Exception in upload OffRiskLetters . . " + e);
				logger.debug("Exception Occured " + ctx.get("QuoteNumber"));
			}
			if (documentUploaded) {
				ctx.put("IsOffRiskLetterUploaded", "Yes");
				SqlResources.getSqlMapProcessor(ctx).insert(
						"DocumentArchive.insert", ctx);
				SqlResources
						.getSqlMapProcessor(ctx)
						.insert("SqlStmts.UserStatementManualBoQueriesIsOffRiskLetterUploaded",
								ctx);
			}
			ctx.remove("IsOffRiskLetterUploaded");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void downloadOffRiskLetter(IContext ctx) throws Exception {
		List<HashMap> downloadList = new ArrayList<HashMap>();
		logger.debug("Going to start download");
		Context downloadCtx = new Context();
		downloadCtx.putAll(ctx);
		try {
			String strPolicyKey = ctx.get("strPolicyKey") != null ? ctx.get(
					"strPolicyKey").toString() : "";
			String[] policyKeyArray = strPolicyKey.split(",");
			if (policyKeyArray.length == 0)
				return;

			for (String policykey : policyKeyArray) {
				downloadCtx.put("PolicyKey", policykey);
				Map map = (Map) SqlResources
						.getSqlMapProcessor(downloadCtx)
						.findByKey(
								"SqlStmts.UserStatementManualBoQueriesdocArchiveData",
								downloadCtx);
				downloadList.add((HashMap) map);

			}

			downloadOffRiskLetters(downloadCtx, downloadList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Problem in Download Off Risk Letters "
					+ e.getMessage());
		}

	}

	public void downloadOffRiskLetters(IContext ctx, List<HashMap> list) {
		logger.debug("Initiating Download . . ");

		try {
			createDownloadDirectory(ctx);
			DocumentManagment docMgt = new DocumentManagment();
			for (HashMap downloadMap : list) {
				logger.debug("DownloadDocument Start for Document : "
						+ downloadMap.get("DocFileName"));
				String newFileName = (String) downloadMap.get("DocFileName");
				ctx.put("DocUrl", downloadMap.get("DocUrl"));
				ctx.put("DocFileName", downloadMap.get("DocFileName"));
				new DocumentManagment().downloadDocumentOffRisk(ctx);
				logger.debug("DownloadDocument End for Document : "
						+ downloadMap.get("DocFileName"));
			}
			addFilesToZipAndDownload(ctx);
			deleteDownloadDirectory(ctx);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Download Failed " + e.getMessage());
		}
	}

	public void downloadZip(IContext ctx) throws Exception {
		HttpServletResponse resp = (HttpServletResponse) ctx
				.get("DocumentResponse");
		try {
			String uploaddirectorypath = SystemProperties.getInstance()
					.getString("offriskupload.uploaddirectory");
			String zipFile = uploaddirectorypath + "OffRiskLetters.zip";
			File f = new File(zipFile);
			resp.setContentType("application/zip");
			resp.setHeader("Content-Disposition",
					"attachment; filename=OffRiskLettersAccountant.zip;");
			resp.setHeader("Cache-Control", "no-cache");
			byte[] buf = new byte[resp.getBufferSize()];
			resp.setContentLength((int) f.length());
			int length;
			FileInputStream fis = null;
			BufferedInputStream fileInBuf = null;

			fileInBuf = new BufferedInputStream(new FileInputStream(f));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			while ((length = fileInBuf.read(buf)) > 0) {
				baos.write(buf, 0, length);
			}

			resp.getOutputStream().write(baos.toByteArray());
			fileInBuf.close();
			resp.getOutputStream().flush();
			resp.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addFilesToZipAndDownload(IContext ctx) throws Exception {
		try {
			String uploaddirectorypath = SystemProperties.getInstance()
					.getString("offriskupload.uploaddirectory");
			String zipFile = uploaddirectorypath + "OffRiskLetters.zip";
			File directory = new File(uploaddirectorypath);
			File[] fList = directory.listFiles();
			byte[] buffer = new byte[1024];
			FileOutputStream fout = new FileOutputStream(zipFile);
			ZipOutputStream zout = new ZipOutputStream(fout);
			for (int i = 0; i < fList.length; i++) {

				System.out.println("Adding " + fList[i].getAbsolutePath());

				FileInputStream fin = new FileInputStream(
						fList[i].getAbsolutePath());
				zout.putNextEntry(new ZipEntry(fList[i].getName()));
				int length;
				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}
				zout.closeEntry();
				fin.close();
			}
			zout.close();
			System.out.println("Zip file has been created!");
			downloadZip(ctx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createDownloadDirectory(IContext ctx)  {
		try {
			logger.debug("Going To Create Directory");
			String uploaddirectorypath = SystemProperties.getInstance()
					.getString("offriskupload.uploaddirectory");
			File file = new File(uploaddirectorypath);
			if (!file.exists())
				file.mkdir();

			return uploaddirectorypath;
		} catch (Exception e) {
			logger.debug("Not able to create directory " + e.getMessage());
		}
		return "";
	}

	private void deleteDownloadDirectory(IContext ctx) {
		try {
			logger.debug("Going To Delete Directory");
			String uploaddirectorypath = SystemProperties.getInstance()
					.getString("offriskupload.uploaddirectory");
			File directory = new File(uploaddirectorypath);

			// make sure directory exists
			if (!directory.exists()) {

				logger.debug("Directory does not exist.");
				return;

			} else {

				try {

					delete(directory);

				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
			
			logger.debug("Directory Deleted");

		} catch (Exception e) {
			logger.debug("Not able to delete directory " + e.getMessage());
		}

	}

	public static void delete(File file)  {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				logger.debug("Directory is deleted : "
						+ file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					logger.debug("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			logger.debug("File is deleted : " + file.getAbsolutePath());
		}
	}

}
