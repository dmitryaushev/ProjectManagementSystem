package com.jdbc.config;

import java.util.List;

public abstract class DataAccessObject<T> {

    public abstract void create(T object);
    public abstract T getByID(int id);
    public abstract List<T> getAll();
    public abstract void update(T object);
    public abstract void delete(int id);
}
