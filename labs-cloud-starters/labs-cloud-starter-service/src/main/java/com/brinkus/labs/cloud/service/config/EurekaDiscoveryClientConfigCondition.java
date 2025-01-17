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

package com.brinkus.labs.cloud.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * Condition to control the euraka client configuration loading.
 */
@Component
public class EurekaDiscoveryClientConfigCondition implements Condition {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaDiscoveryClientConfigCondition.class);

    private static final String CONDITION_ENABLED = "labs.eureka.enabled";

    @Override
    public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        String property = context.getEnvironment().getProperty(CONDITION_ENABLED);
        boolean isEnabled = Boolean.parseBoolean(property);
        LOGGER.info("Eureka discovery client configuration is enabled: {}", isEnabled);
        return isEnabled;
    }
}
