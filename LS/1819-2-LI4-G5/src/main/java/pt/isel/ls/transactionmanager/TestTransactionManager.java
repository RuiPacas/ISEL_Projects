package pt.isel.ls.transactionmanager;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import pt.isel.ls.utils.SqlFunction;

public class TestTransactionManager<R> implements TransactionManager<R> {

    private DataSource ds;

    public TestTransactionManager(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public R run(SqlFunction<Connection, R> function) {
        Connection con = null;
        R toReturn = null;
        SQLException handlerSqlException = null;
        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            toReturn = function.apply(con);
        } catch (SQLException e) {
            if (con != null) {
                handlerSqlException = e;
            }
        } finally {
            try {
                if (con != null) {
                    con.rollback();
                    con.close();
                }
                if (handlerSqlException != null) {
                    throw new RuntimeException(handlerSqlException.getMessage());
                }
                if (toReturn == null) {
                    throw new RuntimeException("Result is Null");
                }
            } catch (SQLException e) {
                //Do nothing
            }
        }
        return toReturn;
    }
}
