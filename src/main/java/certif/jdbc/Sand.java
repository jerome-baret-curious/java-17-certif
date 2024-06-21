package certif.jdbc;

import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class Sand {
    public static void main(String[] args) throws SQLException {
        Sand sand = new Sand();
        try (Connection connection = sand.getConnection()) {

            sand.statement(connection, false);

            sand.prepStatement(connection);
            sand.statement(connection, true);

            sand.updateFromResult(connection);
            sand.statement(connection, true);

            sand.insert(connection);
            sand.statement(connection, true);

            sand.call(connection);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
        }
    }

    private void call(Connection connection) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall("{call updateThing(?, ?)}");

        callableStatement.setString(1, "theFirst");
        callableStatement.setInt(2, 987);
        callableStatement.executeUpdate();

        callableStatement = connection.prepareCall("{call calculateIt(?)}");

        callableStatement.setDouble(1, 45.0);
        callableStatement.registerOutParameter(1, Types.DOUBLE);

        ResultSet result = callableStatement.executeQuery();
        //while(result.next()) {  }

        Double out = callableStatement.getDouble(1);

        callableStatement = connection.prepareCall("{? = call hmm(?)}");
        callableStatement.registerOutParameter(1, Types.VARCHAR);
        callableStatement.setInt(2, 58);
        boolean isResultSet = callableStatement.execute();
        String hmmResult = callableStatement.getString(1);
    }

    private void insert(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet uprs = stmt.executeQuery("SELECT * FROM USERS");
            uprs.moveToInsertRow();
            uprs.updateString("NAME", "moi");
            uprs.insertRow();
            uprs.beforeFirst();
        }
    }

    private void updateFromResult(Connection connection) throws SQLException {
        String query = "select ID, NAME, STATUS from USERS";
        try (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String status = rs.getString("STATUS");
                rs.updateString("STATUS", "Jogging");
                rs.updateRow(); // real update
            }
        }
        System.out.println("Updated");
    }

    private void prepStatement(Connection connection) throws SQLException {
        String updateString = "update USERS set NAME = ? where ID = ?";
        try (PreparedStatement updateSales = connection.prepareStatement(updateString)) {
            connection.setAutoCommit(false);
            updateSales.setInt(2, 1);
            updateSales.setString(1, "GEGE");
            updateSales.executeUpdate();
            connection.commit();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void statement(Connection connection, boolean withStatus) throws SQLException {
        String query = withStatus ? "select ID, NAME, STATUS from USERS" : "select ID, NAME from USERS";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String status = "";
                if (withStatus) {
                    status = rs.getString(3);
                    if (rs.wasNull()) {
                        status = "wasNULL"; //otherwise status==null
                    }
                }
                System.out.println(id + ", " + name + ", " + status);
            }
            printWarnings(stmt.getWarnings());
        }
    }

    // SQLWarning from Connection, Statement or ResultSet
    private void printWarnings(SQLWarning warnings) throws SQLException {
        while (warnings != null) {
            System.out.println(warnings.getMessage());
            System.out.println(warnings.getSQLState());
            System.out.println(warnings.getErrorCode());
            warnings = warnings.getNextWarning();
        }
    }

    public Connection getConnection() throws SQLException {
        ClassLoader loader = Sand.class.getClassLoader();
        URL create = loader.getResource("jdbc/create.sql");
        URL init = loader.getResource("jdbc/init.sql");
        Properties connectionProps = new Properties();
        connectionProps.put("user", "h2");
        connectionProps.put("password", "sa");

        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;INIT=runscript from '" + create.getPath() + "'\\;runscript from '" + init.getPath() + "'", connectionProps);
        System.out.println("Connected to database");
        return conn;
    }
}
