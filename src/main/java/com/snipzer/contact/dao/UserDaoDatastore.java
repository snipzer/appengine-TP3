package com.snipzer.contact.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.*;
import com.snipzer.contact.entity.User;

public class UserDaoDatastore implements IUserDao {

    private static UserDaoDatastore INSTANCE = new UserDaoDatastore();

    public static UserDaoDatastore getInstance() {
        return INSTANCE;
    }

    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public long save(User contact) {
        Entity entity = new Entity("User");
        if (contact.id != null) {
            Key key = KeyFactory.createKey("User", contact.id);
            try {
                entity = datastore.get(key);
            } catch(EntityNotFoundException enf) {
                System.out.println(enf.getMessage());
            }
        }
        entity.setProperty("firstName", contact.firstName);
        entity.setProperty("lastName", contact.lastName);
        entity.setProperty("email", contact.email);
        entity.setProperty("notes", contact.notes);
        Key key = datastore.put(entity);
        return key.getId();
    }

	public void delete(Long id) {
        Key k = KeyFactory.createKey("User", id);
        datastore.delete(k);
    }

	public User get(Long id) {
        Entity entity = null;
        try {
            entity = datastore.get(KeyFactory.createKey("User", id));
        }
        catch(EntityNotFoundException exc) {
            System.out.println(exc.getMessage());
        }
        return User.create()
            .id(entity.getKey().getId())
            .firstName((String) entity.getProperty("firstName"))
            .lastName((String) entity.getProperty("lastName"))
            .email((String) entity.getProperty("email"))
            .notes((String) entity.getProperty("notes"));
    }
	public List<User> getAll() {
        List<User> contacts = new ArrayList<>();
        Query q = new Query("User")
            .addProjection(new PropertyProjection("firstName", String.class))
            .addProjection(new PropertyProjection("lastName", String.class))
            .addProjection(new PropertyProjection("email", String.class))
            .addProjection(new PropertyProjection("notes", String.class));
        PreparedQuery pq = datastore.prepare(q);
        for (Entity e : pq.asIterable()) {
            contacts.add(User.create()
                .id(e.getKey().getId())
                .firstName((String) e.getProperty("firstName"))
                .lastName((String) e.getProperty("lastName"))
                .email((String) e.getProperty("email"))
                .notes((String) e.getProperty("notes")));
        }
        return contacts;
    }

}