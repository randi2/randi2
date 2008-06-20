package de.randi2.dao;

import java.util.List;

import de.randi2.model.Center;

public interface CenterDao extends AbstractDao<Center> {
	

	public List<Center> getAll();

}
