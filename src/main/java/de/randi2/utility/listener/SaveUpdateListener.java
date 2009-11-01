/* 
 * (c) 2008- RANDI2 Core Development Team
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
package de.randi2.utility.listener;

import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.event.MergeEvent;
import org.hibernate.event.MergeEventListener;
import org.hibernate.event.PersistEvent;
import org.hibernate.event.PersistEventListener;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.hibernate.event.def.DefaultPersistEventListener;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import org.springframework.orm.hibernate3.support.IdTransferringMergeEventListener;

import de.randi2.model.AbstractDomainObject;


public class SaveUpdateListener implements SaveOrUpdateEventListener, MergeEventListener, PersistEventListener{

	private static final long serialVersionUID = -8131583568540670427L;
	
	private static SaveOrUpdateEventListener saveUpdate = new DefaultSaveOrUpdateEventListener();
	private static MergeEventListener merge = new IdTransferringMergeEventListener();
	private static PersistEventListener persist = new DefaultPersistEventListener();
	
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		if(event.getObject() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getObject();
			if(object.getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
				object.beforeCreate();
				object.beforeUpdate();
			}else{
				object.beforeUpdate();
			}
		}
		saveUpdate.onSaveOrUpdate(event);
	}

	public void onMerge(MergeEvent event) throws HibernateException {
		if(event.getOriginal() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getOriginal();
			if(object.getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
				object.beforeCreate();
				object.beforeUpdate();
			}else{
				object.beforeUpdate();
			}
		}
		merge.onMerge(event);
		
	}

	@SuppressWarnings("unchecked")
	public void onMerge(MergeEvent event, Map copiedAlready)
			throws HibernateException {
		if(event.getOriginal() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getOriginal();
			if(object.getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
				object.beforeCreate();
				object.beforeUpdate();
			}else{
				object.beforeUpdate();
			}
		}
		merge.onMerge(event, copiedAlready);
		
	}

	@Override
	public void onPersist(PersistEvent event) throws HibernateException {
		if(event.getObject() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getObject();
				object.beforeCreate();
				object.beforeUpdate();
		}
		persist.onPersist(event);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onPersist(PersistEvent event, Map createdAlready)
			throws HibernateException {
		if(event.getObject() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getObject();
				object.beforeCreate();
				object.beforeUpdate();
		}
		persist.onPersist(event, createdAlready);
	}
	
	


}
