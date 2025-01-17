package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.api.seff.InternalSeff;
import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.InternalCallAction
 * InternalCallAction}. It is used to create the '<em><b>InternalCallAction</b></em>' object
 * step-by-step, i.e. '<em><b>InternalCallActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.InternalCallAction
 */
public class InternalCallActionCreator extends GeneralAction {

    private final List<VariableUsage> inputVariableUsages;
    private InternalSeff internalSeff;

    protected InternalCallActionCreator(final SeffCreator seff) {
        this.seff = seff;
        this.demands = new ArrayList<>();
        this.infrastructureCalls = new ArrayList<>();
        this.resourceCalls = new ArrayList<>();
        this.inputVariableUsages = new ArrayList<>();
    }

    @Override
    public InternalCallActionCreator withName(final String name) {
        return (InternalCallActionCreator) super.withName(name);
    }

    /**
     * Adds the <code>variableUsage</code> to this action's list of input variable usages.
     *
     * @param variableUsage
     * @return this internal call action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newVariableUsage()
     */
    public InternalCallActionCreator withInputVaribleUsage(final VariableUsageCreator variableUsage) {
        IllegalArgumentException.throwIfNull(variableUsage, "variableUsage must not be null");
        this.inputVariableUsages.add(variableUsage.build());
        return this;
    }

    /**
     * Specifies the internal behaviour that is called on by this action.
     *
     * @param internalBehaviour
     * @return this internal call action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInternalBehaviour()
     */
    public InternalCallActionCreator withInternalBehaviour(final InternalSeff internalBehaviour) {
        IllegalArgumentException.throwIfNull(internalBehaviour, "internalBehaviour must not be null");
        this.internalSeff = internalBehaviour;
        return this;
    }

    @Override
    public InternalCallActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (InternalCallActionCreator) super.withResourceDemand(specificationStochasticExpression,
                processingResource);
    }

    @Override
    public InternalCallActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (InternalCallActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public InternalCallActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (InternalCallActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected InternalCallAction build() {
        final InternalCallAction action = SeffFactory.eINSTANCE.createInternalCallAction();

        if (name != null) {
            action.setEntityName(name);
        }

        if (this.internalSeff != null) {
            final ResourceDemandingInternalBehaviour internal = this.internalSeff.buildInternalBehaviour();
            action.setCalledResourceDemandingInternalBehaviour(internal);
        }

        action.getInputVariableUsages__CallAction()
            .addAll(this.inputVariableUsages);

        action.getResourceDemand_Action()
            .addAll(this.demands);
        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);

        return action;
    }

}
