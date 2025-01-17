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

package com.brinkus.labs.cloud.service.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Deserialize ISO date (YYYY-MM-DD) formatted text to {@link LocalDate}.
 */
public class LocalISODateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        try {
            return LocalDate.parse(jp.getText());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Allowed format is: yyyy-mm-dd!", e);
        }
    }

}