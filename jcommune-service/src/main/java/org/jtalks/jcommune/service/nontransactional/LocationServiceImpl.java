/**
 * Copyright (C) 2011  JTalks.org Team
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
 */

package org.jtalks.jcommune.service.nontransactional;

import org.aspectj.lang.annotation.Before;
import org.jtalks.common.model.entity.Entity;
import org.jtalks.jcommune.model.entity.User;
import org.jtalks.jcommune.service.LocationService;
import org.jtalks.jcommune.service.SecurityService;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Class for storing and tracking of users on the forum.
 *
 * @author Andrey Kluev
 */
@Component
public class LocationServiceImpl implements LocationService {
    private SecurityService securityService;
    private SessionRegistry sessionRegistry;
    private Map<User, String> registerUserMap = Collections.synchronizedMap(new HashMap<User, String>());

    public LocationServiceImpl(SecurityService securityService, SessionRegistry sessionRegistry) {
        this.securityService = securityService;
        this.sessionRegistry = sessionRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Map<User, String> getRegisterUserMap() {
        return registerUserMap;
    }

    /**
     * Modification map to active user, and create list of user name users on the current page
     * {@inheritDoc}
     */
    @Override
    public synchronized List<String> getUsersViewing(Entity entity) {
        registerUserMap.put(securityService.getCurrentUser(), entity.getUuid());

        List<String> viewList = new ArrayList<String>();
        for (Object o : sessionRegistry.getAllPrincipals()) {
            User user = (User) o;
            if (registerUserMap.containsKey(user) && registerUserMap.get(user).equals(entity.getUuid())) {
                viewList.add(user.getEncodedUsername());
            }
        }
        return viewList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearUserLocation() {
        getRegisterUserMap().remove(securityService.getCurrentUser());
    }
}
