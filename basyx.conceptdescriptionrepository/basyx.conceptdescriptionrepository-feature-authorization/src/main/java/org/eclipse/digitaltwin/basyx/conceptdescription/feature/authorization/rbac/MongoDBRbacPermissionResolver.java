package org.eclipse.digitaltwin.basyx.conceptdescription.feature.authorization.rbac;

import org.eclipse.digitaltwin.aas4j.v3.model.ConceptDescription;
import org.eclipse.digitaltwin.basyx.authorization.Action;
import org.eclipse.digitaltwin.basyx.authorization.IRoleAuthenticator;
import org.eclipse.digitaltwin.basyx.authorization.ISubjectInfo;
import org.eclipse.digitaltwin.basyx.authorization.ISubjectInfoProvider;
import org.eclipse.digitaltwin.basyx.authorization.rbac.*;
import org.eclipse.digitaltwin.basyx.conceptdescription.feature.authorization.ConceptDescriptionTargetInfo;
import org.eclipse.digitaltwin.basyx.conceptdescription.feature.authorization.PermissionResolver;
import org.eclipse.digitaltwin.basyx.core.exceptions.NotAuthorizedException;
import org.eclipse.digitaltwin.basyx.core.filtering.FilterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ConditionalOnExpression(value = "'${basyx.conceptdescriptionrepository.feature.authorization.type}' == 'rbac' and '${basyx.backend}'.equals('MongoDB')")
@Service
public class MongoDBRbacPermissionResolver implements PermissionResolver<Criteria>, RbacPermissionResolver<Criteria> {
    @Autowired
    private final IRbacStorage<Criteria> storage;

    @Autowired
    private final ISubjectInfoProvider subjectInfoProvider;

    @Autowired
    private final IRoleAuthenticator roleAuthenticator;

    public MongoDBRbacPermissionResolver(IRbacStorage<Criteria> storage, ISubjectInfoProvider subjectInfoProvider, IRoleAuthenticator roleAuthenticator) {
        this.storage = storage;
        this.subjectInfoProvider = subjectInfoProvider;
        this.roleAuthenticator = roleAuthenticator;
    }
    private boolean hasPermission(ITargetInfo targetInfo, Action action, ISubjectInfo<?> subjectInfo) {
        final IRbacRuleChecker rbacRuleChecker = new PredefinedSetRbacRuleChecker(storage.getRbacRuleSet(null));
        final List<String> roles = roleAuthenticator.getRoles();
        return rbacRuleChecker.checkRbacRuleIsSatisfied(roles, action.toString(), targetInfo);
    }

    @Override
    public FilterInfo<Criteria> getGetAllConceptDescriptionsFilterInfo() {
        final RbacRuleSet rbacRuleSet = storage.getRbacRuleSet(null);
        final Set<RbacRule> rbacRules = rbacRuleSet.getRules();
        final List<String> roles = roleAuthenticator.getRoles();

        final Set<String> relevantSubmodelIds = rbacRules.stream()
                .filter(rbacRule -> rbacRule.getTargetInformation() instanceof ConceptDescriptionTargetInfo)
                .filter(rbacRule -> rbacRule.getAction().equals(Action.READ.toString()))
                .filter(rbacRule -> roles.contains(rbacRule.getRole()))
                .map(rbacRule -> (ConceptDescriptionTargetInfo) rbacRule.getTargetInformation())
                .map(ConceptDescriptionTargetInfo::getConceptDescriptionId)
                .collect(Collectors.toSet());
        return new FilterInfo<>(Criteria.where("_id").in(relevantSubmodelIds));
    }

    @Override
    public FilterInfo<Criteria> getGetAllConceptDescriptionsByIdShortFilterInfo() {
        final RbacRuleSet rbacRuleSet = storage.getRbacRuleSet(null);
        final Set<RbacRule> rbacRules = rbacRuleSet.getRules();
        final List<String> roles = roleAuthenticator.getRoles();

        final Set<String> relevantSubmodelIds = rbacRules.stream()
                .filter(rbacRule -> rbacRule.getTargetInformation() instanceof ConceptDescriptionTargetInfo)
                .filter(rbacRule -> rbacRule.getAction().equals(Action.READ.toString()))
                .filter(rbacRule -> roles.contains(rbacRule.getRole()))
                .map(rbacRule -> (ConceptDescriptionTargetInfo) rbacRule.getTargetInformation())
                .map(ConceptDescriptionTargetInfo::getConceptDescriptionId)
                .collect(Collectors.toSet());
        return new FilterInfo<>(Criteria.where("_id").in(relevantSubmodelIds));
    }

    @Override
    public FilterInfo<Criteria> getGetAllConceptDescriptionsByIsCaseOfFilterInfo() {
        final RbacRuleSet rbacRuleSet = storage.getRbacRuleSet(null);
        final Set<RbacRule> rbacRules = rbacRuleSet.getRules();
        final List<String> roles = roleAuthenticator.getRoles();

        final Set<String> relevantSubmodelIds = rbacRules.stream()
                .filter(rbacRule -> rbacRule.getTargetInformation() instanceof ConceptDescriptionTargetInfo)
                .filter(rbacRule -> rbacRule.getAction().equals(Action.READ.toString()))
                .filter(rbacRule -> roles.contains(rbacRule.getRole()))
                .map(rbacRule -> (ConceptDescriptionTargetInfo) rbacRule.getTargetInformation())
                .map(ConceptDescriptionTargetInfo::getConceptDescriptionId)
                .collect(Collectors.toSet());
        return new FilterInfo<>(Criteria.where("_id").in(relevantSubmodelIds));
    }

    @Override
    public FilterInfo<Criteria> getGetAllConceptDescriptionsByDataSpecificationReferenceFilterInfo() {
        final RbacRuleSet rbacRuleSet = storage.getRbacRuleSet(null);
        final Set<RbacRule> rbacRules = rbacRuleSet.getRules();
        final List<String> roles = roleAuthenticator.getRoles();

        final Set<String> relevantSubmodelIds = rbacRules.stream()
                .filter(rbacRule -> rbacRule.getTargetInformation() instanceof ConceptDescriptionTargetInfo)
                .filter(rbacRule -> rbacRule.getAction().equals(Action.READ.toString()))
                .filter(rbacRule -> roles.contains(rbacRule.getRole()))
                .map(rbacRule -> (ConceptDescriptionTargetInfo) rbacRule.getTargetInformation())
                .map(ConceptDescriptionTargetInfo::getConceptDescriptionId)
                .collect(Collectors.toSet());
        return new FilterInfo<>(Criteria.where("_id").in(relevantSubmodelIds));
    }

    @Override
    public void getConceptDescription(String conceptDescriptionId) {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final ConceptDescriptionTargetInfo targetInfo = new ConceptDescriptionTargetInfo(conceptDescriptionId);
        if (!hasPermission(targetInfo, Action.READ, subjectInfo)) {
            throw new NotAuthorizedException();
        }
    }

    @Override
    public void updateConceptDescription(String conceptDescriptionId, ConceptDescription conceptDescription) {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final ConceptDescriptionTargetInfo targetInfo = new ConceptDescriptionTargetInfo(conceptDescriptionId);
        if (!hasPermission(targetInfo, Action.WRITE, subjectInfo)) {
            throw new NotAuthorizedException();
        }
    }

    @Override
    public void createConceptDescription(ConceptDescription conceptDescription) {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final ConceptDescriptionTargetInfo targetInfo = new ConceptDescriptionTargetInfo(conceptDescription.getId());
        if (!hasPermission(targetInfo, Action.WRITE, subjectInfo)) {
            throw new NotAuthorizedException();
        }
    }

    @Override
    public void deleteConceptDescription(String conceptDescriptionId) {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final ConceptDescriptionTargetInfo targetInfo = new ConceptDescriptionTargetInfo(conceptDescriptionId);
        if (!hasPermission(targetInfo, Action.WRITE, subjectInfo)) {
            throw new NotAuthorizedException();
        }
    }

    @Override
    public FilterInfo<Criteria> getGetRbacRuleSetFilterInfo() {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final RbacRuleTargetInfo targetInfo = new RbacRuleTargetInfo();
        if (!hasPermission(targetInfo, Action.READ, subjectInfo)) {
            return new FilterInfo<>(Criteria.where("true").is("false"));
        }
        return null;
    }

    @Override
    public void addRule(RbacRule rbacRule) {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final RbacRuleTargetInfo targetInfo = new RbacRuleTargetInfo();
        if (!hasPermission(targetInfo, Action.WRITE, subjectInfo)) {
            throw new NotAuthorizedException();
        }
    }

    @Override
    public void removeRule(RbacRule rbacRule) {
        final ISubjectInfo<?> subjectInfo = subjectInfoProvider.get();
        final RbacRuleTargetInfo targetInfo = new RbacRuleTargetInfo();
        if (!hasPermission(targetInfo, Action.WRITE, subjectInfo)) {
            throw new NotAuthorizedException();
        }
    }
}
