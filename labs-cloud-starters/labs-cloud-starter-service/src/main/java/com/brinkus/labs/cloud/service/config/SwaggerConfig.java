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

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Configuration for Swagger API documentation.
 */
@Configuration
@EnableSwagger2
@Conditional(SwaggerConfigCondition.class)
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

    /**
     * The swagger api info.
     */
    @Autowired
    ApiInfo apiInfo;

    /**
     * Creates a new instance of {@link SwaggerConfig}
     */
    public SwaggerConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getName());
    }

    /**
     * Add the webjars to the resource handling.
     *
     * @param registry
     *         the resource handler registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * Configure the Swagger framework.
     *
     * @return the configuration
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(excludeErrorController())
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo);
    }

    /**
     * Get the Swagger {@link ApiInfo} from the external configuration information.
     *
     * @param config
     *         the swagger config bean
     *
     * @return the {@link ApiInfo} instance
     */
    @Bean
    public ApiInfo getApiInfo(final SwaggerConfigBean config) {
        Contact contact = config.hasContact()
                ? new Contact(config.getContact().getName(),
                              config.getContact().getUrl(),
                              config.getContact().getEmail())
                : null;
        String termsOfServiceUrl = config.hasTermsOfService() ? config.getTermsOfService().getUrl() : null;
        String licenseName = config.hasLicense() ? config.getLicense().getName() : null;
        String licenseUrl = config.hasLicense() ? config.getLicense().getUrl() : null;

        return new ApiInfo(config.getTitle(),
                           config.getDescription(),
                           config.getVersion(),
                           termsOfServiceUrl,
                           contact,
                           licenseName,
                           licenseUrl,
                           new ArrayList<>());
    }

    /**
     * Exclude error controllers from the swagger list.
     * @return a new predicate for control exclusion
     */
    private Predicate<RequestHandler> excludeErrorController() {
        return Predicates.not(RequestHandlerSelectors.basePackage("org.springframework"));
    }
}