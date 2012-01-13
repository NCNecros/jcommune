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
package org.jtalks.jcommune.model.entity;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TopicTest {
    private Topic topic;
    Post post1 = new Post();
    Post post2 = new Post();

    @BeforeMethod
    public void setUp() {
        topic = new Topic(new JCUser(), "title");
        topic.addPost(post1);
        topic.addPost(post2);
    }

    @Test
    public void getFirstPost() {
        Post firstPost = topic.getFirstPost();

        assertEquals(firstPost, post1);
    }

    @Test
    public void getLastPost() {
        Post lastPost = topic.getLastPost();

        assertEquals(lastPost, post2);
    }

    @Test
    public void addPost() throws InterruptedException {
        DateTime prevDate = topic.getModificationDate();
        Thread.sleep(25); // milisecond precise is a kind of fiction
        topic.addPost(new Post());

        assertTrue(topic.getModificationDate().isAfter(prevDate));
    }

    @Test
    public void updatePost() throws InterruptedException {
        DateTime prevDate = topic.getModificationDate();
        Thread.sleep(25); // milisecond precise is a kind of fiction
        post1.updateModificationDate();

        assertTrue(topic.getModificationDate().isAfter(prevDate));
    }

    @Test
    public void updateModificationDate() {
        DateTime prevDate = topic.getModificationDate();

        DateTime modDate = topic.updateModificationDate();

        assertNotSame(modDate, prevDate);
    }

    @Test
    public void testSetStickedResetWeight() {
        topic.setTopicWeight(10);

        topic.setSticked(false);

        assertEquals(topic.getTopicWeight(), 0);
    }

    @Test
    public void testSetStickedNotResetWeight() {
        topic.setTopicWeight(10);

        topic.setSticked(true);

        assertEquals(topic.getTopicWeight(), 10);
    }
}
