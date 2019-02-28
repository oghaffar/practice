package translation.repository;

import org.junit.Ignore;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AuditRepositoryIT {

    @Test
    public void postgressConnectionSpike() throws Exception {
        Connection c = DriverManager.getConnection("jdbc:postgresql://192.168.206.128:5432/postgres","oghaffar", "notASecret");

        assertFalse(c.isClosed());

        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM shoppingCart;" );
        while ( rs.next() ) {
            String itemName = rs.getString("itemName");
            System.out.println("itemName = " + itemName);
        }
    }

    @Test @Ignore
    public void hsqlDbConnectionSpikeForInMemoryDB() throws Exception {
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:practiceDB", "SA", "");

        assertFalse(c.isClosed());
    }

}