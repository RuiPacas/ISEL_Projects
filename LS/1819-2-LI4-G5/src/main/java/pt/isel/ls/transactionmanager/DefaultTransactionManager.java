package pt.isel.ls.transactionmanager;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.utils.SqlFunction;

public class DefaultTransactionManager<R> implements TransactionManager<R> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTransactionManager.class);
    private DataSource ds;

    public DefaultTransactionManager(DataSource ds) {
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
            logger.error(e.getMessage());
            if (con != null) {
                handlerSqlException = e;
                try {
                    con.rollback();
                } catch (SQLException x) {
                    //do Nothing
                }
            }
        } finally {
            try {
                if (con != null) {
                    con.commit();
                    con.close();
                }
                if (handlerSqlException != null) {
                    throw new RuntimeException(handlerSqlException.getMessage());
                }
                if (toReturn == null) {
                    throw new RuntimeException("Result is Null");
                }
            } catch (SQLException e) {
                //do Nothing
            }
        }
        return toReturn;
    }


}
