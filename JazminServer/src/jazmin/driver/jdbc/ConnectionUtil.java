package jazmin.driver.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import jazmin.driver.jdbc.smartjdbc.Types;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.util.DumpUtil;
/**
 * 
 * @author yama
 * 8 Jan, 2015
 */
public class ConnectionUtil {
	private static Logger logger=LoggerFactory.get(ConnectionUtil.class);
	private ConnectionUtil(){}
	/**
	 * Close the given JDBC Connection and ignore any thrown exception. This is
	 * useful for typical finally blocks in manual JDBC code.
	 * 
	 * @param con
	 *            the JDBC Connection to close (may be <code>null</code>)
	 */
	protected static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				logger.error("Could not close JDBC Connection", ex);
			} catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw
				// RuntimeException or Error.
				logger.error("Unexpected exception on closing JDBC Connection", ex);
			}
		}
	}

	/**
	 * Close the given JDBC Statement and ignore any thrown exception. This is
	 * useful for typical finally blocks in manual JDBC code.
	 * 
	 * @param stmt
	 *            the JDBC Statement to close (may be <code>null</code>)
	 */
	protected static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				logger.error("Could not close JDBC Statement", ex);
			} catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw
				// RuntimeException or Error.
				logger.error("Unexpected exception on closing JDBC Statement", ex);
			}
		}
	}

	/**
	 * Close the given JDBC ResultSet and ignore any thrown exception. This is
	 * useful for typical finally blocks in manual JDBC code.
	 * 
	 * @param rs
	 *            the JDBC ResultSet to close (may be <code>null</code>)
	 */
	protected static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				logger.error("Could not close JDBC ResultSet", ex);
			} catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw
				// RuntimeException or Error.
				logger.error("Unexpected exception on closing JDBC ResultSet", ex);
			}
		}
	}
	//
	
	/**
	 *
	 */
	public static  void set(
			PreparedStatement ps, 
			Object... objs)
			throws SQLException {
		if (objs == null || objs.length == 0) {
			return;
		}
		int i = 1;
		for (Object o : objs) {
			if (o == null) {
				ps.setString(i++, null);
				continue;
			}
			if (o instanceof String) {
				ps.setString(i++, ((String) o));
				continue;
			}
			if (o instanceof Date) {
				Date date = (Date) o;
				ps.setTimestamp(i++, new Timestamp(date.getTime()));
				continue;
			}
			// Integer
			if (o instanceof Integer) {
				ps.setInt(i++, ((Integer) o));
				continue;
			}
			if (o instanceof Double) {
				ps.setDouble(i++, ((Double) o));
				continue;
			}
			if (o instanceof Float) {
				ps.setFloat(i++, ((Float) o));
				continue;
			}
			if (o instanceof BigDecimal) {
				ps.setBigDecimal(i++, ((BigDecimal) o));
				continue;
			}
			if (o instanceof Long) {
				ps.setLong(i++, ((Long) o));
				continue;
			}
			if (o instanceof Short) {
				ps.setShort(i++, ((Short) o));
				continue;
			}
			if (o instanceof Byte) {
				ps.setByte(i++, ((Byte) o));
				continue;
			}
			if (o instanceof byte[]) {
				ps.setBytes(i++, ((byte[]) o));
				continue;
			}
			if (o instanceof Boolean) {
				ps.setBoolean(i++, ((Boolean) o));
				continue;
			}
			throw new IllegalArgumentException("unsupport type:" + o.getClass()+","+DumpUtil.dump(o));
		}
	}


	public static void set(Connection conn,
					   DatabaseType databaseType,
					   PreparedStatement ps,
					   Object... objs)
			throws SQLException {
		if (objs == null || objs.length == 0) {
			return;
		}
		int i = 1;
		for (Object o : objs) {
			if (o == null) {
				ps.setString(i++, null);
				continue;
			}
			Class<?> type=o.getClass();
			if (o instanceof String) {
				ps.setString(i++, ((String) o));
				continue;
			}
			if (o instanceof Date) {
				Date date = (Date) o;
				ps.setTimestamp(i++, new Timestamp(date.getTime()));
				continue;
			}
			// Integer
			if (o instanceof Integer) {
				ps.setInt(i++, ((Integer) o));
				continue;
			}
			if (o instanceof Double) {
				ps.setDouble(i++, ((Double) o));
				continue;
			}
			if (o instanceof Float) {
				ps.setFloat(i++, ((Float) o));
				continue;
			}
			if (o instanceof BigDecimal) {
				ps.setBigDecimal(i++, ((BigDecimal) o));
				continue;
			}
			if (o instanceof Long) {
				ps.setLong(i++, ((Long) o));
				continue;
			}
			if (o instanceof Short) {
				ps.setShort(i++, ((Short) o));
				continue;
			}
			if (o instanceof Byte) {
				ps.setByte(i++, ((Byte) o));
				continue;
			}
			if (o instanceof byte[]) {
				ps.setBytes(i++, ((byte[]) o));
				continue;
			}
			if (o instanceof Boolean) {
				ps.setBoolean(i++, ((Boolean) o));
				continue;
			}
			if (Types.ARRAY_TYPES.contains(type)) {
				if(DatabaseType.NDS.equals(databaseType)) {
					StringBuilder v=new StringBuilder();
					v.append("{");
					Object[] list=((Object[]) o);
					if(list.length>0) {
						for (Object item : list) {
							v.append(item.toString()).append(",");
						}
						v.deleteCharAt(v.length()-1);
					}
					v.append("}");
					ps.setString(i++, v.toString());
				}else {
					if(type.equals(String[].class)) {
						ps.setArray(i++, conn.createArrayOf("TEXT",(Object[]) o));
					}
					if(type.equals(Short[].class)) {
						ps.setArray(i++, conn.createArrayOf("INTEGER",(Object[]) o));
					}
					if(type.equals(Integer[].class)) {
						ps.setArray(i++, conn.createArrayOf("INTEGER",(Object[]) o));
					}
					if(type.equals(Long[].class)) {
						ps.setArray(i++, conn.createArrayOf("BIGINT",(Object[]) o));
					}
					if(type.equals(Float[].class)) {
						ps.setArray(i++, conn.createArrayOf("FLOAT",(Object[]) o));
					}
					if(type.equals(Double[].class)) {
						ps.setArray(i++, conn.createArrayOf("DOUBLE",(Object[]) o));
					}
				}
				continue;
			}
			throw new IllegalArgumentException("unsupport type:" + o.getClass());
		}
	}
	
}
