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

import java.util.HashMap;
import java.util.Map;

/**
 * DTO for {@link RbacRule} for deserialization.
 *
 * @author wege
 */
public class RbacRuleDTO {
	private String role;
	private String action;
	private Map<String, String> targetInfo;

	public String getRole() {
		return role;
	}

	public String getAction() {
		return action;
	}

	public Map<String, String> getTargetInfo() {
		return targetInfo;
	}

	public RbacRuleDTO() {
		role = "";
		action = "";
		targetInfo = new HashMap<>();
	}

	public RbacRuleDTO(final String role, final String action, final Map<String, String> targetInfo) {
		this.role = role;
		this.action = action;
		this.targetInfo = targetInfo;
	}
}