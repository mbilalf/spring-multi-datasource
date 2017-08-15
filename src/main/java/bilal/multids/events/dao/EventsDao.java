package bilal.multids.events.dao;

import bilal.multids.events.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EventsDao {

    @Autowired
    @Qualifier("eventsEntityManager")
    private EntityManager eventsEntityManager;

    @Autowired
    @Qualifier("jdbcEvents")
    private JdbcTemplate jdbcEvents;

    public Event getEvent(long id){

        return jdbcEvents.queryForObject("SELECT * FROM events WHERE id=?", eventMapper, id);
    }

    private static final RowMapper<Event> eventMapper = new RowMapper<Event>() {
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event e = new Event();
            e.setId(rs.getLong("id"));
            e.setName(rs.getString("name"));
            e.setCreatedOn(rs.getTimestamp("created_on"));
            e.setUpdatedOn(rs.getTimestamp("updated_on"));
            return e;
        }
    };
}
