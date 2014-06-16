package fr.miage.dao;

public abstract class AbstractDAO<T> {
	public abstract int delete(long id);
	public abstract int update(T object);
	public abstract long create(T object);
	public abstract T find(long id);
}
