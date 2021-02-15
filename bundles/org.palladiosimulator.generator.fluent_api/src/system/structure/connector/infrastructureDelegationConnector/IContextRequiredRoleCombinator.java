package system.structure.connector.infrastructureDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

public interface IContextRequiredRoleCombinator {
    public RequiredInfrastructureDelegationConnectorCreator combineContextAndRequiredRole(AssemblyContext context,
            InfrastructureRequiredRole role);
}
