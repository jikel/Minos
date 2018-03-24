package minos.model.dao;

import java.sql.Connection;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO(Connection conn) {
		this.connect = conn;
	}

	public abstract T create(T obj);

	public abstract void delete(T obj);

	public abstract T update(T obj);

	public abstract T find(long id);

}