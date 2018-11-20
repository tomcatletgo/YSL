package core.interceptors;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

import core.util.DataUtils;

/**
 * 自定义编辑器.
 * 装配Bean时,转换日期属性.
 * @author joe
 * @date 2015年10月4日
 */
public class TimestampConvertEditor extends PropertyEditorSupport {
	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					setValue(DataUtils.getTimestamp(this.dateFormat.parse(text)));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					setValue(DataUtils.getTimestamp(this.datetimeFormat.parse(text)));
				} else if (text.indexOf(":") > 0 && text.length() == 21) {
					text = text.replace(".0", "");
					setValue(DataUtils.getTimestamp(this.datetimeFormat.parse(text)));
				} else {
					throw new IllegalArgumentException("Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}
}
