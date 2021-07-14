module testSqlite02 {
	requires java.sql;
	requires ormlite.core;
	requires ormlite.jdbc;
	exports testSqlite02.orm to ormlite.core;
}