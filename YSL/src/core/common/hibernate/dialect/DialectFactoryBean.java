package core.common.hibernate.dialect;

import org.springframework.beans.factory.FactoryBean;

public class DialectFactoryBean implements FactoryBean<Dialect> {
	public static final String ORACLE = "oracle";
	public static final String MYSQL = "mysql";
	private Dialect dialect;
	private String dbType = "mysql";

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Dialect getObject() throws Exception {
		if (this.dbType.equals("oracle")) {
			this.dialect = new OracleDialect();
		} else if (this.dbType.equals("mysql")) {
			this.dialect = new MySQLDialect();
		} else {
			throw new Exception("没有设置合适的数据库类型");
		}
		return this.dialect;
	}

	public Class<?> getObjectType() {
		return Dialect.class;
	}

	public boolean isSingleton() {
		return true;
	}
}