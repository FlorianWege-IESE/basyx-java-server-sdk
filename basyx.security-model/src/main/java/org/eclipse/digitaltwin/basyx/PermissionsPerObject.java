package org.eclipse.digitaltwin.basyx;

import org.eclipse.digitaltwin.aas4j.v3.model.Reference;

import java.util.List;

public interface PermissionsPerObject {
    Reference getObject();
    ObjectAttributes getTargetObjectAttributes();
    List<Permission> getPermission();

}