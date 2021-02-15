package component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

import component.apiControlFlowInterfaces.seff.RecoverySeff;
import component.repositoryStructure.RepositoryCreator;
import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.RecoveryAction RecoveryAction}. It
 * is used to create the '<em><b>RecoveryAction</b></em>' object step-by-step, i.e.
 * '<em><b>RecoveryActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.RecoveryAction
 */
public class RecoveryActionCreator extends GeneralAction {

    private RecoveryActionBehaviour primary;
    private final List<RecoveryActionBehaviour> otherBehaviours;

    protected RecoveryActionCreator(final SeffCreator seff, final RepositoryCreator repo) {
        this.repository = repo;
        this.seff = seff;
        this.otherBehaviours = new ArrayList<>();
    }

    @Override
    public RecoveryActionCreator withName(final String name) {
        return (RecoveryActionCreator) super.withName(name);
    }

    /**
     * Specifies the primary recovery behaviour of this recovery action.
     *
     * @param recoveryActionBehaviour
     * @return this recovery action in the making
     * @see component.factory.FluentRepositoryFactory#newRecoveryBehaviour()
     */
    public RecoveryActionCreator withPrimaryBehaviour(final RecoverySeff recoveryActionBehaviour) {
        Objects.requireNonNull(recoveryActionBehaviour, "recoveryActionBehaviour must not be null");
        final RecoveryActionBehaviour build = recoveryActionBehaviour.buildRecoveryBehaviour();
        this.primary = build;
        this.repository.addRecoveryActionBehaviour(build);
        return this;
    }

    /**
     * Adds the <code>recoveryActionBehaviour</code> to this action's list of alternate recovery
     * behaviours.
     *
     * @param recoveryActionBehaviour
     * @return this recovery action in the making
     * @see component.factory.FluentRepositoryFactory#newRecoveryBehaviour()
     */
    public RecoveryActionCreator withAlternativeBehaviour(final RecoverySeff recoveryActionBehaviour) {
        Objects.requireNonNull(recoveryActionBehaviour, "recoveryActionBehaviour must not be null");
        final RecoveryActionBehaviour buildRecoveryBehaviour = recoveryActionBehaviour.buildRecoveryBehaviour();
        this.otherBehaviours.add(buildRecoveryBehaviour);
        this.repository.addRecoveryActionBehaviour(buildRecoveryBehaviour);
        return this;
    }

    @Override
    public RecoveryActionCreator withResourceDemand(final String specification_stochasticExpression,
            final ProcessingResource processingResource) {
        return (RecoveryActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
    }

    @Override
    public RecoveryActionCreator withInfrastructureCall(final String numberOfCalls_stochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (RecoveryActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public RecoveryActionCreator withResourceCall(final String numberOfCalls_stochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (RecoveryActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected RecoveryAction build() {
        final RecoveryAction action = SeffReliabilityFactory.eINSTANCE.createRecoveryAction();
        if (this.name != null) {
            action.setEntityName(this.name);
        }
        if (this.primary != null) {
            action.setPrimaryBehaviour__RecoveryAction(this.primary);
        }

        action.getRecoveryActionBehaviours__RecoveryAction()
            .addAll(this.otherBehaviours);

        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);
        action.getResourceDemand_Action()
            .addAll(this.demands);

        return action;
    }

}
