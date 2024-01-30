package bc1.gream.global.config.datasource;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled("application.yml의 DB_URL과 DB_READ_ONLY_URL 를 매핑하여 테스트해야합니다.")
class TransactionRoutingDataSourceConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Read-Write Transaction에 대해 Primary DB 커넥션을 사용합니다.")
    @Transactional
    void primary_데이터소스_LazyConnectionDataSourceProxy() throws SQLException {
        // THEN
        assertTrue(dataSource.getConnection().getMetaData().getURL().contains("bc1-gream-database-01"));
    }

    @Test
    @DisplayName("Read-Only Transaction에 대해 Secondary DB 커넥션을 사용합니다.")
    @Transactional(readOnly = true)
    void secondary_데이터소스_LazyConnectionDataSourceProxy() throws SQLException {
        // THEN
        assertTrue(dataSource.getConnection().getMetaData().getURL().contains("bc1-gream-read-only"));
    }
}