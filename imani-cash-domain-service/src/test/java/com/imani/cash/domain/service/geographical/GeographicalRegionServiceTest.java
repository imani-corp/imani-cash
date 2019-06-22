package com.imani.cash.domain.service.geographical;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * @author manyce400
 */
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@SpringBootTest(classes={Application.class})
public class GeographicalRegionServiceTest {



    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    //@Qualifier(GeographicalRegionService.SPRING_BEAN)
    private IGeographicalRegionService geographicalRegionService;
    
    @Test
    public void testInjection() {
        System.out.println("entityManager = " + entityManager);
    }

}
