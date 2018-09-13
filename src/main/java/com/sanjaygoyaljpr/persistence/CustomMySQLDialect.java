package com.sanjaygoyaljpr.persistence;

import org.hibernate.dialect.MySQL5Dialect;

public class CustomMySQLDialect extends MySQL5Dialect {

	@Override
	public boolean dropConstraints() {
		return false;
	}
}
