/*
 * Copyright 2019 Haulmont.
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

package io.jmix.core.impl.keyvalue;

import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.metamodel.model.*;
import io.jmix.core.metamodel.model.impl.MetadataObjectImpl;

import javax.annotation.Nullable;
import java.util.*;

/**
 * MetaClass for {@link KeyValueEntity}.
 */
public class KeyValueMetaClass extends MetadataObjectImpl implements MetaClass {

    private Map<String, MetaProperty> properties = new LinkedHashMap<>();

    private Store store;

    public void addProperty(MetaProperty property) {
        properties.put(property.getName(), property);
    }

    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    public KeyValueMetaClass() {
        name = "sys_KeyValueEntity";
    }

    @Nullable
    @Override
    public MetaClass getAncestor() {
        return null;
    }

    @Override
    public List<MetaClass> getAncestors() {
        return Collections.emptyList();
    }

    @Override
    public Collection<MetaClass> getDescendants() {
        return Collections.emptyList();
    }

    @Override
    public Session getSession() {
        return null; // temporary metaclass
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> getJavaClass() {
        return (Class<T>) KeyValueEntity.class;
    }

    @Override
    public MetaProperty findProperty(String name) {
        return properties.get(name);
    }

    @Override
    public MetaProperty getProperty(String name) {
        MetaProperty property = findProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Property '" + name + "' not found in " + getName());
        return property;
    }

    @Override
    public MetaPropertyPath getPropertyPath(String propertyPath) {
        String[] properties = propertyPath.split("\\."); // split should not create java.util.regex.Pattern

        // do not use ArrayList, leads to excessive memory allocation
        MetaProperty[] metaProperties = new MetaProperty[properties.length];

        MetaProperty currentProperty;
        MetaClass currentClass = this;

        for (int i = 0; i < properties.length; i++) {
            if (currentClass == null) {
                return null;
            }
            currentProperty = currentClass.getProperty(properties[i]);
            if (currentProperty == null) {
                return null;
            }

            Range range = currentProperty.getRange();
            currentClass = range.isClass() ? range.asClass() : null;

            metaProperties[i] = currentProperty;
        }

        return new MetaPropertyPath(this, metaProperties);
    }

    @Override
    public Collection<MetaProperty> getOwnProperties() {
        return properties.values();
    }

    @Override
    public Collection<MetaProperty> getProperties() {
        return properties.values();
    }

    @Override
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "KeyValueMetaClass{" +
                "properties=" + properties.keySet() +
                '}';
    }
}
