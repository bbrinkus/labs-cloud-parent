/*
 * Labs Cloud Starter Service
 * Copyright (C) 2016  Balazs Brinkus
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.brinkus.labs.cloud.neo4j.factory;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.metadata.MetaData;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.Session;

public class BoltSessionFactory implements Neo4jSessionFactory {

    private final Configuration configuration;

    private final MetaData metaData;

    /**
     * Create a new instance of {@link BoltSessionFactory}.
     *
     * @param configuration
     *         the Neo4j configuration instance
     * @param packages
     *         the meta packages that are containing the Neo4j entity representations
     */
    public BoltSessionFactory(Configuration configuration, String... packages) {
        this.configuration = configuration;
        this.metaData = new MetaData(packages);
    }

    @Override
    public MetaData metaData() {
        return metaData;
    }

    @Override
    public Session openSession() {
        BoltDriver driver = new BoltDriver();
        driver.configure(configuration);
        return new Neo4jSession(metaData, driver);
    }
}
