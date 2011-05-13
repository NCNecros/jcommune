/**
 * Copyright (C) 2011  jtalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * Also add information on how to contact you by electronic and paper mail.
 * Creation date: Apr 12, 2011 / 8:05:19 PM
 * The jtalks.org Project
 */
package org.jtalks.jcommune.model.dao.hibernate;

import org.hibernate.Query;
import org.jtalks.jcommune.model.dao.UserDao;
import org.jtalks.jcommune.model.entity.User;

import java.util.List;

/**
 * Hibernate implementation of UserDao.
 *
 * @author Pavel Vervenko
 */
public class UserHibernateDao extends AbstractHibernateDao<User> implements UserDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveOrUpdate(User user) {
        getSession().save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long userId) {
        Query query = getSession().createQuery("delete User where id= :authorId");
        query.setLong("authorId", userId);
        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User get(Long id) {
        return (User) getSession().load(User.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        return getSession().createQuery("from User").list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByUsername(String username) {
        return (User) getSession()
                .createQuery("from User u where u.username = ?")
                .setString(0, username)
                .uniqueResult();
    }
}
