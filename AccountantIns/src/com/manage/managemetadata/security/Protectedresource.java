package com.manage.managemetadata.security;
import java.util.List;

public abstract class Protectedresource extends com.xmlobjectbuilder.ResourceKeysNode
{
	protected String description;
	protected boolean override;
	protected String type;
	protected String uniquename;
	protected String defaultaccesstype;
	protected String accesstype;
	protected String operator;

	protected BusinessroleMap businessroleMap;
	protected AppuserMap appuserMap;
	protected UserdefinedMap userdefinedMap;
	protected ValidaccesslevelsMap validaccesslevelsMap;

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setOverride(String override)
	{
		this.override = super.convertBoolean(override);
	}

	public boolean getOverride()
	{
		return override;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setUniquename(String uniquename)
	{
		this.uniquename = uniquename;
	}

	public String getUniquename()
	{
		return uniquename;
	}

	public void setDefaultaccesstype(String defaultaccesstype)
	{
		this.defaultaccesstype = defaultaccesstype;
	}

	public String getDefaultaccesstype()
	{
		return defaultaccesstype;
	}

	public void setAccesstype(String accesstype)
	{
		this.accesstype = accesstype;
	}

	public String getAccesstype()
	{
		return accesstype;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setBusinessroleMap(BusinessroleMap businessroleMap)
	{
		this.businessroleMap = businessroleMap;
	}

	public BusinessroleImpl getBusinessrole(String roleid)
	{
		return businessroleMap==null ? null : businessroleMap.get(roleid);
	}

	public List getBusinessroleList()
	{
		return (List) (businessroleMap==null ? null : businessroleMap.values());
	}

	public BusinessroleImpl getFirstBusinessrole()
	{
		return businessroleMap==null ? null : businessroleMap.getFirstBusinessrole();
	}

	public void setAppuserMap(AppuserMap appuserMap)
	{
		this.appuserMap = appuserMap;
	}

	public AppuserImpl getAppuser(String userid)
	{
		return appuserMap==null ? null : appuserMap.get(userid);
	}

	public List getAppuserList()
	{
		return (List) (appuserMap==null ? null : appuserMap.values());
	}

	public AppuserImpl getFirstAppuser()
	{
		return appuserMap==null ? null : appuserMap.getFirstAppuser();
	}

	public void setUserdefinedMap(UserdefinedMap userdefinedMap)
	{
		this.userdefinedMap = userdefinedMap;
	}

	public UserdefinedImpl getUserdefined(String ruleid)
	{
		return userdefinedMap==null ? null : userdefinedMap.get(ruleid);
	}

	public List getUserdefinedList()
	{
		return (List) (userdefinedMap==null ? null : userdefinedMap.values());
	}

	public UserdefinedImpl getFirstUserdefined()
	{
		return userdefinedMap==null ? null : userdefinedMap.getFirstUserdefined();
	}

	public void setValidaccesslevelsMap(ValidaccesslevelsMap validaccesslevelsMap)
	{
		this.validaccesslevelsMap = validaccesslevelsMap;
	}

	public List getValidaccesslevelsList()
	{
		return (List) (validaccesslevelsMap==null ? null : validaccesslevelsMap.values());
	}

	public ValidaccesslevelsImpl getFirstValidaccesslevels()
	{
		return validaccesslevelsMap==null ? null : validaccesslevelsMap.getFirstValidaccesslevels();
	}

	public synchronized void refresh()
	{
		if(businessroleMap!=null)
			businessroleMap.sort();
		if(appuserMap!=null)
			appuserMap.sort();
		if(userdefinedMap!=null)
			userdefinedMap.sort();
		if(validaccesslevelsMap!=null)
			validaccesslevelsMap.sort();
	}

	public String toString()
	{
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("\n<protectedresource");

		strBuf.append(" override=\"");
		if(getOverride())
			strBuf.append("Y\"");
		else
			strBuf.append("N\"");

		if(description!=null)
		{
			strBuf.append(" description=\"");
			strBuf.append(this.description);
			strBuf.append("\"");
		}

		if(true)
		{
			strBuf.append(" seq=\"");
			strBuf.append(getSeq());
			strBuf.append("\"");
		}

		if(uniquename!=null)
		{
			strBuf.append(" uniquename=\"");
			strBuf.append(this.uniquename);
			strBuf.append("\"");
		}

		if(type!=null)
		{
			strBuf.append(" type=\"");
			strBuf.append(this.type);
			strBuf.append("\"");
		}

		if(accesstype!=null)
		{
			strBuf.append(" accesstype=\"");
			strBuf.append(this.accesstype);
			strBuf.append("\"");
		}

		if(defaultaccesstype!=null)
		{
			strBuf.append(" defaultaccesstype=\"");
			strBuf.append(this.defaultaccesstype);
			strBuf.append("\"");
		}

		if(operator!=null)
		{
			strBuf.append(" operator=\"");
			strBuf.append(this.operator);
			strBuf.append("\"");
		}

		strBuf.append(">");

		if (getNodeText()!= null){
			strBuf.append(getNodeText());
		}
		if(businessroleMap!=null)
			strBuf.append(businessroleMap);

		if(appuserMap!=null)
			strBuf.append(appuserMap);

		if(userdefinedMap!=null)
			strBuf.append(userdefinedMap);

		if(validaccesslevelsMap!=null)
			strBuf.append(validaccesslevelsMap);

		strBuf.append("\n</protectedresource>");
		return strBuf.toString();
	}

	public String getUniqueIdValue()
	{
		return uniquename;
	}
}