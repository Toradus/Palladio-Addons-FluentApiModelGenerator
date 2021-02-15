package system.structure.connector.infrastructureDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

public interface IContextProvidedRoleCombinator {
    public ProvidedInfrastructureDelegationConnectorCreator combineContextAndProvidedRole(AssemblyContext context,
            InfrastructureProvidedRole role);
}
