package de.randi2.core.integration.transactions;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.AbstractDao;
import de.randi2.model.AbstractDomainObject;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.InitializeDatabaseUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml"})
@TransactionConfiguration
@Transactional
public abstract class AbstractTransactionalTest<E extends AbstractDao<F>, F extends AbstractDomainObject> extends
		AbstractTransactionalJUnit4SpringContextTests {

	protected E dao;
	@Autowired protected DomainObjectFactory factory;
	protected int numberOfRows = -1;
	protected F object;
	private boolean withRollback = false;
	
	@Autowired protected SessionFactory sessionFactory;
	@Autowired protected InitializeDatabaseUtil databaseUtil;

	
	@BeforeTransaction
	    public void beforeTransaction() {
		init();
		numberOfRows = countRowsInTable(object.getClass().getSimpleName());
	        test(true, object);
	  }
	
	 /**
     * Tests that the size and first record match what is expected 
     * after the transaction.
     */
    @AfterTransaction
    public void afterTransaction() {
        test(false, object);
    }

	
	 /**
     * Tests table.
     */
    protected void test(boolean beforeTransaction, F object) {
    	
    	if(beforeTransaction){
    		assertEquals(numberOfRows,countRowsInTable(object.getClass().getSimpleName()));
    	}else{
    		if(withRollback){
    			assertEquals(numberOfRows,countRowsInTable(object.getClass().getSimpleName()));
    		}else{
    			assertEquals(numberOfRows,countRowsInTable(object.getClass().getSimpleName())-1);
        		assertTrue(object.getId()>0);
        	 List<Map<String, Object>> list = simpleJdbcTemplate.queryForList("Select * FROM "+ object.getClass().getSimpleName() +" where id = " + object.getId());
        	 assertEquals(1, list.size());
        	 if(list.size() ==1){
        		Long id = (Long) list.get(0).get("ID");
        		assertTrue(object.getId() == id.longValue());
    		}
    		
    	 }
    	}
        

    }
    
    protected abstract void init();
    
    @Test
    @Rollback(false)
    public void testTransaction(){
    	withRollback =false;
    	dao.create(object);
    }
    
    @Test
    @Rollback(true)
    public void testTransactionRollback(){
    	withRollback = true;
    	dao.create(object);
    }

	
}
