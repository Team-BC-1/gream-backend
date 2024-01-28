package bc1.gream.global.config.datasource;

import jakarta.persistence.EntityManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TransactionRoutingDataSourceConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Read-Write Transaction에 대해 Primary DB 커넥션을 사용합니다.")
    @Transactional
    void primary_데이터소스_LazyConnectionDataSourceProxy() throws SQLException {
        // Perform database operations that require read-write transaction
        // This method will use the primary data source
        System.out.println("dataSource.getConnection().getMetaData().getURL() = " + dataSource.getConnection().getMetaData().getURL());
    }

    @Test
    @DisplayName("Read-Only Transaction에 대해 Secondary DB 커넥션을 사용합니다.")
    @Transactional(readOnly = true)
    void secondary_데이터소스_LazyConnectionDataSourceProxy() throws SQLException {
        // Perform database operations that only require read-only access
        // This method will use the secondary data source
        System.out.println("dataSource.getConnection().getMetaData().getURL() = " + dataSource.getConnection().getMetaData().getURL());
    }
}