package bilal.multids.config;

import bilal.multids.core.repositories.CustomerRepository;
import bilal.multids.events.repositories.EventRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CoreConfig.class, EventsConfig.class })
@TransactionConfiguration
//@Transactional(transactionManager = "")
public class MultipleDSTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Transactional("coreTransactionManager")
    public void testCoreDataSource() {
        Assert.assertNotNull(customerRepository.findOne(1l));
    }

    @Test
    @Transactional("eventsTransactionManager")
    public void testEventsDataSource() {
        Assert.assertNotNull(eventRepository.findOne(1l));
    }
}