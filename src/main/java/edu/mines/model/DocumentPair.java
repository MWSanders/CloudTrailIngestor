/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mines.model;

import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;

/**
 * <p>A pair consisting of two elements.</p>
 *
 * @version $Id: DocumentPair.java 1557584 2014-01-12 18:26:49Z britter $
 */
@Embedded
public class DocumentPair implements Serializable {

    /**
     * Serialization version
     */
    private static final long serialVersionUID = 4954918890077093841L;

    /**
     * Left object
     */
    public DocumentProxy request;
    /**
     * Right object
     */
    public DocumentProxy response;

    public DocumentPair() {
    }

    public DocumentPair(final DocumentProxy request, final DocumentProxy response) {
        super();
        this.request = request;
        this.response = response;
    }

    public DocumentProxy getRequest() {
        return request;
    }


    public DocumentProxy getResponse() {
        return response;
    }


    /**
     * <p>Returns a String representation of this pair using the format {@code ($request,$response)}.</p>
     *
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        if (request != null && response != null)
            return new StringBuilder().append('(').append(getRequest()).append(',').append(getResponse()).append(')').toString();
        if (request != null)
            return request.toString();
        return response.toString();

    }

    /**
     * <p>Formats the receiver using the given format.</p>
     * <p>
     * <p>This uses {@link java.util.Formattable} to perform the formatting. Two variables may
     * be used to embed the request and response elements. Use {@code %1$s} for the request
     * element (key) and {@code %2$s} for the response element (value).
     * The default format used by {@code toString()} is {@code (%1$s,%2$s)}.</p>
     *
     * @param format the format string, optionally containing {@code %1$s} and {@code %2$s}, not null
     * @return the formatted string, not null
     */
    public String toString(final String format) {
        return String.format(format, getRequest(), getResponse());
    }

}
