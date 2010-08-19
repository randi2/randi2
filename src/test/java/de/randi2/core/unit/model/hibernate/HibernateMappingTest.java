/*
 * (c) 2008-2010 RANDI2 Core Development Team
 *
 * This file is part of RANDI2.
 *
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.core.unit.model.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import de.randi2.testUtility.utility.InitializeDatabaseUtil;

/**
 * Testing the hibernate mapping.
 *
 * @author Johannes Th&ouml;nes
 */
@ContextConfiguration(locations = {"classpath:META-INF/sessionFactory-test.xml"})
public class HibernateMappingTest extends AbstractJUnit4SpringContextTests{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private InitializeDatabaseUtil databaseUtil;
    
    /**
     * Walking over all mapped entities and getting a list of them to see,
     * if loading works correct.
     * 
     * @throws Exception
     */
    @Test
    public void testEverything() throws Exception {
    	databaseUtil.setUpDatabaseEmpty();
        Map metadata = sessionFactory.getAllClassMetadata();
        for (Object persisterObject : metadata.values()) {
            Session session = sessionFactory.openSession();
            try {
                EntityPersister persister = (EntityPersister) persisterObject;
                String className = persister.getClassMetadata().getEntityName();
                String queryString = "from " + className + " c";
                List result = session.createQuery(queryString).list();
                logger.debug("QUERY: " + queryString);
            } finally {
                session.close();
            }
        }
    }
}
