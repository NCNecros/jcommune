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
package org.jtalks.jcommune.web.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.jtalks.jcommune.model.entity.*;
import org.jtalks.jcommune.plugin.api.exceptions.NotFoundException;
import org.jtalks.jcommune.plugin.api.service.transactional.TransactionalTypeAwarePluginTopicService;
import org.jtalks.jcommune.plugin.api.web.dto.PostDto;
import org.jtalks.jcommune.plugin.api.web.dto.TopicDto;
import org.jtalks.jcommune.plugin.api.web.dto.json.JsonResponse;
import org.jtalks.jcommune.plugin.api.web.dto.json.JsonResponseStatus;
import org.jtalks.jcommune.plugin.api.web.util.BreadcrumbBuilder;
import org.jtalks.jcommune.service.*;
import org.jtalks.jcommune.service.dto.EntityToDtoConverter;
import org.jtalks.jcommune.service.nontransactional.LocationService;
import org.jtalks.jcommune.web.validation.editors.DateTimeEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Serves topic management web requests
 *
 * @author Kravchenko Vitaliy
 * @author Kirill Afonin
 * @author Teterin Alexandre
 * @author Max Malakhov
 * @author Evgeniy Naumenko
 * @author Eugeny Batov
 * @author Dmitry S. Dolzhenko
 * @see Topic
 */
@Controller
@Order(value=102)
public class TopicController2 {

    public static final String TOPIC_ID = "topicId";

    private TransactionalTypeAwarePluginTopicService pluginTopicService;

        /**
     *
     */
    @Autowired
    public TopicController2(TransactionalTypeAwarePluginTopicService pluginTopicService) {
        this.pluginTopicService = pluginTopicService;
    }


    /**
     * Displays to user a list of messages from the topic with pagination
     *
     * @param topicId the id of selected Topic
     * @param page    page
     * @return {@code ModelAndView}
     * @throws NotFoundException when topic or branch not found
     */
    @RequestMapping(value = "/topics/{topicType:[A-Za-z]+}/{topicId}", method = RequestMethod.GET)
    public String showTopicPage(WebRequest request,@PathVariable() String topicType, @PathVariable(TOPIC_ID) Long topicId,
                                      @RequestParam(value = "page", defaultValue = "1", required = false) String page)
            throws NotFoundException {
        Topic topic = pluginTopicService.get(topicId, topicType);

        return "redirect:/topics/"+topicId;
    }
}
