package bilal.multids.controller;

import bilal.multids.core.dao.CoreDao;
import bilal.multids.core.model.Customer;
import bilal.multids.core.repositories.CustomerRepository;
import bilal.multids.events.dao.EventsDao;
import bilal.multids.events.model.Event;
import bilal.multids.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wow")
public class WowReportController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EventRepository eventRepository;


    @Autowired
    private CoreDao coreDao;

    @Autowired
    private EventsDao eventsDao;

    public static class ReportResponse {
        enum Status{
            SUCCESS,
            ERROR
        }
        public ReportResponse(){
            this.status = Status.SUCCESS.name();
            this.data = new ArrayList();
        }
        String status;
        List data;


        public String getStatus() {
            return status;
        }

        public List getData() {
            return data;
        }
    }

    @RequestMapping("test")
    public ReportResponse getTestReport() {
        System.out.print("----- IN Controller");
        ReportResponse resp = new ReportResponse();
        List all = new ArrayList();
        all.add(customerRepository.findAll());
        all.add(eventRepository.findAll());
        resp.data.add(all);
        return resp;
    }

    @RequestMapping("test2")
    public ReportResponse getTestJdbcTemplate() {
        System.out.println("----- Test Jdbc Template");

        Customer c = coreDao.getCustomer(1);
        Event e = eventsDao.getEvent(2);

        ReportResponse resp = new ReportResponse();
        resp.status = ReportResponse.Status.SUCCESS.name();
        resp.data.add(c);
        resp.data.add(e);

        return resp;
    }
}
