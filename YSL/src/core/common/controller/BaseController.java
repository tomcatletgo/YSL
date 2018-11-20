package core.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import core.common.service.CommonService;
import core.interceptors.DateConvertEditor;
import core.interceptors.TimestampConvertEditor;
import ycw.om.form.common.NSCommonUtils;



/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * @author joe
 * @date 2015年10月4日
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(Timestamp.class,new TimestampConvertEditor());
	}

	/**
	 * 分页公共方法(非easyui)
	 * 
	 * @author Alexander
	 * @date 20131022
	 */
	public List<?> pageBaseMethod(HttpServletRequest request, DetachedCriteria dc, CommonService commonService, int pageRow) {
		// 当前页
		int currentPage = 1;
		// 总条数
		int totalRow = 0;
		// 总页数
		int totalPage = 0;
		// 获取当前页
		String str_currentPage = request.getParameter("str_currentPage");
		currentPage = str_currentPage == null || "".equals(str_currentPage) ? 1 : Integer.parseInt(str_currentPage);
		// 获取每页的条数
		String str_pageRow = request.getParameter("str_pageRow");
		pageRow = str_pageRow == null || "".equals(str_pageRow) ? pageRow : Integer.parseInt(str_pageRow);
		// 统计的总行数
		dc.setProjection(Projections.rowCount());
		totalRow = Integer.parseInt(commonService.findByDetached(dc).get(0).toString());
		totalPage = (totalRow + pageRow - 1) / pageRow;
		currentPage = currentPage < 1 ? 1 : currentPage;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
		// 清空统计函数
		dc.setProjection(null);
		// dc.setResultTransformer(dc.DISTINCT_ROOT_ENTITY);
		List<?> list = commonService.pageList(dc, (currentPage - 1) * pageRow, pageRow);

		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageRow", pageRow);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPage", totalPage);
		return list;
	}
	
	public String getParamaterMapValue(String key,HttpServletRequest request) {
		Map<String,String[]> pMap = request.getParameterMap();
		String[] valueArray = pMap.get("txt_businessesName");
		if(NSCommonUtils.isArrayEmpty(valueArray)) {
			return null;
		}
		String rValue = valueArray[0];
		 
		return rValue;
	}
	
}
