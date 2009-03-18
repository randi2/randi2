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
			if(object.getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
				object.beforeCreate();
				object.beforeUpdate();
			}else{
				object.beforeUpdate();
			}
		}
		persist.onPersist(event);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onPersist(PersistEvent event, Map createdAlready)
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
		persist.onPersist(event, createdAlready);
	}
	
	


}
