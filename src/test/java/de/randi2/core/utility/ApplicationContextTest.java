package de.randi2.core.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.dao.RoleDao;
import de.randi2.dao.TrialDao;
import de.randi2.dao.TrialSiteDao;
import de.randi2.services.ChartsService;
import de.randi2.services.TrialService;
import de.randi2.services.TrialSiteService;
import de.randi2.services.UserService;

/**
 *
 * @author jthoenes
 */
public class ApplicationContextTest {

   private static ApplicationContext context;

   @BeforeClass
   public static void loadApplicationContext() {
      context = new ClassPathXmlApplicationContext("META-INF/spring-test.xml");
   }

   @Test
   public void testTrialServiceWiring() {
      
      Map<String, TrialService> beans = context.getBeansOfType(TrialService.class);

      assertNotNull(beans.get("trialService"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testTrialSiteServiceWiring() {
      
      Map<String, TrialSiteService> beans = context.getBeansOfType(TrialSiteService.class);

      assertNotNull(beans.get("trialSiteService"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testUserServiceWiring() {
      
      Map<String, UserService> beans = context.getBeansOfType(UserService.class);

      assertNotNull(beans.get("userService"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testChartServiceWiring() {
      
      Map<String, ChartsService> beans = context.getBeansOfType(ChartsService.class);

      assertNotNull(beans.get("chartsService"));
      assertEquals(1, beans.size());
   }

   
   @Test
   public void testTrialDaoWiring() {
      
      Map<String, TrialDao> beans = context.getBeansOfType(TrialDao.class);

      assertNotNull(beans.get("trialDao"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testPersonDaoWiring() {
      
      Map<String, PersonDao> beans = context.getBeansOfType(PersonDao.class);

      assertNotNull(beans.get("personDao"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testLoginDaoWiring() {
      
      Map<String, LoginDao> beans = context.getBeansOfType(LoginDao.class);

      assertNotNull(beans.get("loginDao"));
      assertEquals(1, beans.size());
   }

   @Test
   public void testTrialSiteDaoWiring() {
      
      Map<String, TrialSiteDao> beans = context.getBeansOfType(TrialSiteDao.class);

      assertNotNull(beans.get("trialSiteDao"));
      assertEquals(1, beans.size());
   }
   
   @Test
   public void testRoleDaoWiring() {
      
      Map<String, RoleDao> beans = context.getBeansOfType(RoleDao.class);

      assertNotNull(beans.get("roleDao"));
      assertEquals(1, beans.size());
   }
   
   
}
