/*
 * Copyright 2017 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file incorporates changes by @continuumsecurity
 */

package com.continuumsecurity.elasticagent.ec2.utils;

import com.continuumsecurity.elasticagent.ec2.executors.GetClusterProfileViewRequestExecutor;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class Util {

    public static String readResource(String resourceFile) {
        try (InputStreamReader reader = new InputStreamReader(Util.class.getResourceAsStream(resourceFile), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new RuntimeException("Could not find resource " + resourceFile, e);
        }
    }

    public static byte[] readResourceBytes(String resourceFile) {
        try (InputStream in = GetClusterProfileViewRequestExecutor.class.getResourceAsStream(resourceFile)) {
            return ByteStreams.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not find resource " + resourceFile, e);
        }
    }

    public static String pluginId() {
        String s = readResource("/plugin.properties");
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(s));
            return (String) properties.get("pluginId");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<String> splitIntoLinesAndTrimSpaces(String lines) {
        if (isBlank(lines)) {
            return Collections.emptyList();
        }

        return Collections2.transform(Arrays.asList(lines.split("[\r\n]+")), new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.trim();
            }
        });
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
