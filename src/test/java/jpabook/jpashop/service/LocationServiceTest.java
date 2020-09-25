package jpabook.jpashop.service;

import jpabook.jpashop.domain.Location;
import jpabook.jpashop.repository.LocationRepository;
import jpabook.jpashop.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LocationServiceTest {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void REGISTER_LOCATION() throws Exception {
        Location location = new Location();
        location.setName("MVB.L1.410");

        Long saveId = locationService.saveLocation(location);

        em.flush();
        assertEquals(location, locationRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void DUPLICATED_REGISTER_LOCATION() throws Exception{

        Location location1 = new Location();
        location1.setName("MVB.L0.410");

        Location location2 = new Location();
        location2.setName("MVB.L0.410");

        locationService.saveLocation(location1);
        locationService.saveLocation(location2);

        fail("exception should be occurred");
    }
}
