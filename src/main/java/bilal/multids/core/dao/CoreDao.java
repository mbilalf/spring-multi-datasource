package bilal.multids.core.dao;

import bilal.multids.core.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CoreDao {

    @Autowired
    @Qualifier("jdbcCore")
    private JdbcTemplate jdbcCore;

    public Customer getCustomer(long id){

        return jdbcCore.queryForObject("SELECT * FROM customer WHERE id=?", customerMapper, id);
    }

    private static final RowMapper<Customer> customerMapper = new RowMapper<Customer>() {
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer c = new Customer();
            c.setId(rs.getLong("id"));
            c.setName(rs.getString("name"));
            c.setStatus(rs.getString("status"));
            c.setCreatedOn(rs.getTimestamp("created_on"));
            return c;
        }
    };

}
