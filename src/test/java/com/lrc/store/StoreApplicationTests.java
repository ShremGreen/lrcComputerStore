package com.lrc.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    private DataSource dataSource;//注入数据元信息

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
    //HikariProxyConnection@1290795133 wrapping com.mysql.cj.jdbc.ConnectionImpl@3238e2aa
    //Hikari:数据库连接池，管理数据库的连接对象

    @Test
    void contextLoads() {

    }

}
