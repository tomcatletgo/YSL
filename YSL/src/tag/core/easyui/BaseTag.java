package tag.core.easyui;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import core.util.oConvertUtils;


/**
 * 
 * @author  张代浩
 *
 */
public class BaseTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type = "default";// 加载类型

	public void setType(String type) {
		this.type = type;
	}

	
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();

			//换肤功能：获取cookie信息---joe---20160127
			 HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			 String indexStyle = "blue";
			 Cookie[] cookies = request.getCookies();
			 if(cookies!=null){
				 for (Cookie cookie : cookies) {
					if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
						continue;
					}
					if (cookie.getName().equalsIgnoreCase("INDEXSTYLE")) {
						indexStyle = cookie.getValue();
					}
				 }
			 }
			//换肤功能：获取cookie信息---joe---20160127 
			
			String types[] = type.split(",");
			if (oConvertUtils.isIn("jquery", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-1.8.3.js\"></script>");
			}
			if (oConvertUtils.isIn("ckeditor", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/ckeditor/ckeditor.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/ckeditorTool.js\"></script>");
			}
			if (oConvertUtils.isIn("ckfinder", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/ckfinder/ckfinder.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/ckfinderTool.js\"></script>");
			}
			if (oConvertUtils.isIn("easyui", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/dataformat.js\"></script>");
//				sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/easyui/themes/default/easyui.css\" type=\"text/css\"></link>");
				
				if (StringUtils.isNotEmpty(indexStyle)&& indexStyle.equalsIgnoreCase("blue")) {
					sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/themes/easyui/default/easyui.css\" type=\"text/css\"></link>");
					//sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/themes/css/accordion.css\">");
					//sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"resource/themes/blue/css/easyui.css\" type=\"text/css\"></link>");
				}
/*				if (StringUtils.isNotEmpty(indexStyle)&& indexStyle.equalsIgnoreCase("green")) {
					sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"resource/themes/green/css/easyui.css\" type=\"text/css\"></link>");
					//sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
				}*/
				
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/easyui-lang-zh_CN.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/extends/datagrid-scrollview.js\"></script>");
			}
			if (oConvertUtils.isIn("DatePicker", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/My97DatePicker/WdatePicker.js\"></script>");
				
				if (StringUtils.isNotEmpty(indexStyle)&& indexStyle.equalsIgnoreCase("blue")) {
					sb.append("<link id=\"WdateTheme\" rel=\"stylesheet\" href=\"resource/themes/blue/css/WdatePicker.css\" type=\"text/css\"></link>");
				}
/*				if (StringUtils.isNotEmpty(indexStyle)&& indexStyle.equalsIgnoreCase("green")) {
					sb.append("<link id=\"WdateTheme\" rel=\"stylesheet\" href=\"resource/themes/green/css/WdatePicker.css\" type=\"text/css\"></link>");
				}*/
			}
			if (oConvertUtils.isIn("jqueryui", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/jquery-ui-1.9.2.custom.min.js\"></script>");
			}
			if (oConvertUtils.isIn("jqueryui-sortable", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.core.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.widget.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.mouse.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.sortable.js\"></script>");
			}
			if (oConvertUtils.isIn("prohibit", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/prohibitutil.js\"></script>");		}
			if (oConvertUtils.isIn("designer", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/designer/easyui/jquery-1.7.2.min.js\"></script>");
				sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/designer/easyui/easyui.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/designer/easyui/icon.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/designer/easyui/jquery.easyui.min.1.3.0.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/designer/easyui/locale/easyui-lang-zh_CN.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
				
				sb.append("<script type=\'text/javascript\' src=\'plug-in/jquery/jquery-autocomplete/lib/jquery.bgiframe.min.js\'></script>");
				sb.append("<script type=\'text/javascript\' src=\'plug-in/jquery/jquery-autocomplete/lib/jquery.ajaxQueue.js\'></script>");
				sb.append("<script type=\'text/javascript\' src=\'plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js\'></script>");
				sb.append("<link href=\"plug-in/designer/designer.css\" type=\"text/css\" rel=\"stylesheet\" />");
				sb.append("<script src=\"plug-in/designer/draw2d/wz_jsgraphics.js\"></script>");
				sb.append("<script src=\'plug-in/designer/draw2d/mootools.js\'></script>");
				sb.append("<script src=\'plug-in/designer/draw2d/moocanvas.js\'></script>");
				sb.append("<script src=\'plug-in/designer/draw2d/draw2d.js\'></script>");
				sb.append("<script src=\"plug-in/designer/MyCanvas.js\"></script>");
				sb.append("<script src=\"plug-in/designer/ResizeImage.js\"></script>");
				sb.append("<script src=\"plug-in/designer/event/Start.js\"></script>");
				sb.append("<script src=\"plug-in/designer/event/End.js\"></script>");
				sb.append("<script src=\"plug-in/designer/connection/MyInputPort.js\"></script>");
				sb.append("<script src=\"plug-in/designer/connection/MyOutputPort.js\"></script>");
				sb.append("<script src=\"plug-in/designer/connection/DecoratedConnection.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/Task.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/UserTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/ManualTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/ServiceTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/gateway/ExclusiveGateway.js\"></script>");
				sb.append("<script src=\"plug-in/designer/gateway/ParallelGateway.js\"></script>");
				sb.append("<script src=\"plug-in/designer/boundaryevent/TimerBoundary.js\"></script>");
				sb.append("<script src=\"plug-in/designer/boundaryevent/ErrorBoundary.js\"></script>");
				sb.append("<script src=\"plug-in/designer/subprocess/CallActivity.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/ScriptTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/MailTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/ReceiveTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/task/BusinessRuleTask.js\"></script>");
				sb.append("<script src=\"plug-in/designer/designer.js\"></script>");
				sb.append("<script src=\"plug-in/designer/mydesigner.js\"></script>");

			}
			if (oConvertUtils.isIn("tools", types)) {
//				sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common.css\" type=\"text/css\"></link>");
				
				if (StringUtils.isNotEmpty(indexStyle)&& indexStyle.equalsIgnoreCase("blue")) {
					sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common_blue.css\" type=\"text/css\"></link>");
					sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js\"></script>");
					//<script type="text/javascript" src="lhgdialog.min.js?self=true&skin=chrome"></script>
				}
/*				if (StringUtils.isNotEmpty(indexStyle)&& indexStyle.equalsIgnoreCase("green")) {
					sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common_green.css\" type=\"text/css\"></link>");
					sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js?skin=igreen\"></script>");
				}*/
				
//				sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js?skin=igreen\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/curdtools.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/easyuiextend.js\"></script>");
			}
			if (oConvertUtils.isIn("toptip", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/toptip/css/css.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/toptip/manhua_msgTips.js\"></script>");
			}
			if (oConvertUtils.isIn("autocomplete", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js\"></script>");
			}
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
