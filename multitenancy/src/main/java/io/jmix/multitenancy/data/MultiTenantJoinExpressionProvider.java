/*
 * Copyright 2021 Haulmont.
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

package io.jmix.multitenancy.data;

import io.jmix.eclipselink.impl.mapping.AbstractJoinExpressionProvider;
import io.jmix.multitenancy.core.TenantEntityOperation;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.ManyToManyMapping;
import org.eclipse.persistence.mappings.ManyToOneMapping;
import org.eclipse.persistence.mappings.OneToManyMapping;
import org.eclipse.persistence.mappings.OneToOneMapping;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.lang.reflect.Field;

@Component("mten_MultiTenantJoinExpressionProvider")
public class MultiTenantJoinExpressionProvider extends AbstractJoinExpressionProvider {

    private final TenantEntityOperation tenantEntityOperation;

    public MultiTenantJoinExpressionProvider(TenantEntityOperation tenantEntityOperation) {
        this.tenantEntityOperation = tenantEntityOperation;
    }

    @Override
    protected Expression processOneToManyMapping(OneToManyMapping mapping) {
        return null;
    }

    @Override
    protected Expression processOneToOneMapping(OneToOneMapping mapping) {
        return createToOneJoinExpression(mapping);
    }

    @Override
    protected Expression processManyToOneMapping(ManyToOneMapping mapping) {
        return createToOneJoinExpression(mapping);
    }

    @Override
    protected Expression processManyToManyMapping(ManyToManyMapping mapping) {
        return null;
    }

    private Expression createToOneJoinExpression(OneToOneMapping oneToOneMapping) {
        ClassDescriptor descriptor = oneToOneMapping.getDescriptor();
        Class<?> referenceClass = oneToOneMapping.getReferenceClass();
        if (isMultiTenant(referenceClass) && isMultiTenant(descriptor.getJavaClass())) {
            Field tenantIdField = tenantEntityOperation.getTenantField(referenceClass);
            Field parentTenantId = tenantEntityOperation.getTenantField(descriptor.getJavaClass());
            if (tenantIdField != null && parentTenantId != null) {
                String columnName = tenantIdField.getName();
                ExpressionBuilder builder = new ExpressionBuilder();
                Expression tenantColumExpression = builder.get(columnName);
                return tenantColumExpression.equal(
                        builder.getParameter(
                                parentTenantId.getAnnotation(Column.class).name()));
            }
        }
        return null;
    }

    private boolean isMultiTenant(Class<?> referenceClass) {
        return tenantEntityOperation.getTenantMetaProperty(referenceClass) != null;
    }

}
