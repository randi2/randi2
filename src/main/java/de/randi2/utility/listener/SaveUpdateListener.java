package de.randi2.utility.listener;

import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.event.MergeEvent;
import org.hibernate.event.MergeEventListener;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import org.springframework.orm.hibernate3.support.IdTransferringMergeEventListener;

import de.randi2.model.AbstractDomainObject;

@SuppressWarnings("serial")
public class SaveUpdateListener implements SaveOrUpdateEventListener, MergeEventListener{

	private static DefaultSaveOrUpdateEventListener saveUpdate = new DefaultSaveOrUpdateEventListener();
	private static IdTransferringMergeEventListener merge = new IdTransferringMergeEventListener();
	
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
		if(event.getEntity() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getEntity();
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
		if(event.getEntity() instanceof AbstractDomainObject){
			AbstractDomainObject object = (AbstractDomainObject) event.getEntity();
			if(object.getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
				object.beforeCreate();
				object.beforeUpdate();
			}else{
				object.beforeUpdate();
			}
		}
		merge.onMerge(event, copiedAlready);
		
	}


}
