/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * SPDX-License-Identifier: MIT
 ******************************************************************************/
package org.eclipse.digitaltwin.basyx.authorization.rbac;


import org.eclipse.digitaltwin.basyx.authorization.CommonAuthorizationConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(CommonAuthorizationConfig.ENABLED_PROPERTY_KEY)
@ConditionalOnExpression(value = "'${" + CommonAuthorizationConfig.TYPE_PROPERTY_KEY + "}' == 'rbac'")
public class RbacServiceImpl<RbacRuleFilterType> implements IRbacService {
    private final RbacPermissionResolver<RbacRuleFilterType> rbacPermissionResolver;
    private final IRbacStorage<RbacRuleFilterType> storage;

    public RbacServiceImpl(RbacPermissionResolver<RbacRuleFilterType> rbacPermissionResolver, IRbacStorage<RbacRuleFilterType> storage) {
        this.rbacPermissionResolver = rbacPermissionResolver;
        this.storage = storage;
    }

    @Override
    public RbacRuleSet getRbacRuleSet() {
        return storage.getRbacRuleSet(rbacPermissionResolver.getGetRbacRuleSetFilterInfo());
    }

    @Override
    public void addRule(RbacRule rbacRule) {
        rbacPermissionResolver.addRule(rbacRule);
        storage.addRule(rbacRule);
    }

    @Override
    public void removeRule(RbacRule rbacRule) {
        rbacPermissionResolver.addRule(rbacRule);
        storage.removeRule(rbacRule);
    }
}