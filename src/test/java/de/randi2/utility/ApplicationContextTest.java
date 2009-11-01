package de.randi2.utility;

import de.randi2.services.ChartsService;
import de.randi2.services.TrialService;
import de.randi2.services.TrialSiteService;
import de.randi2.services.UserService;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

/**
 *
 * @author jthoenes
 */
public class ApplicationContextTest {

   private ApplicationContext context;

   @Before
   public void loadApplicationContext() {
      context = new ClassPathXmlApplicationContext("META-INF/spring-test.xml");
   }

   @Test
   public void testTrialServiceWiring() {
      
      Map<String, Object> beans = context.getBeansOfType(TrialService.class);

      assertNotNull(beans.get("trialService"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testTrialSiteServiceWiring() {
      
      Map<String, Object> beans = context.getBeansOfType(TrialSiteService.class);

      assertNotNull(beans.get("trialSiteService"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testUserServiceWiring() {
      
      Map<String, Object> beans = context.getBeansOfType(UserService.class);

      assertNotNull(beans.get("userService"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testChartServiceWiring() {
      
      Map<String, Object> beans = context.getBeansOfType(ChartsService.class);

      assertNotNull(beans.get("chartsService"));
      assertEquals(1, beans.size());
   }


}
