package translation.repository;

import org.junit.Ignore;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

public class AuditRepositoryIT {

    @Test
    public void postgressConnectionSpike() throws Exception {
        Connection c = DriverManager.getConnection("jdbc:postgresql://192.168.206.128:5432/postgres","oghaffar", "notASecret");

        assertFalse(c.isClosed());
    }

    @Test @Ignore
    public void hsqlDbConnectionSpikeForInMemoryDB() throws Exception {
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:practiceDB", "SA", "");

        assertFalse(c.isClosed());
    }

}