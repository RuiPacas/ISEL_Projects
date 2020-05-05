package pt.isel.ls.transactionmanager;

import java.sql.Connection;
import pt.isel.ls.utils.SqlFunction;

public interface TransactionManager<R> {

    R run(SqlFunction<Connection, R> function);

}
