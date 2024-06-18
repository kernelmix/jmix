/*
 * Copyright 2024 Haulmont.
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
 */

package io.jmix.flowui.xml.layout.loader.impl;

import io.jmix.core.JmixOrder;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.xml.layout.loader.PropertyParser;
import io.jmix.flowui.xml.layout.loader.PropertyParsingContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(JmixOrder.LOWEST_PRECEDENCE - 10)
@Component("flowui_IconPropertyParser")
public class IconPropertyParser implements PropertyParser {

    public static final String TYPE = "ICON";

    @Override
    public boolean supports(PropertyParsingContext context) {
        return TYPE.equals(context.type());
    }

    @Override
    public Object parse(PropertyParsingContext context) {
        return ComponentUtils.parseIcon(context.value());
    }
}
