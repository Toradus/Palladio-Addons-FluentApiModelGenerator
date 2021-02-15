package system.structure.connector.assemblyEventConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SinkRole;

public class SinkRoleSelector {
    private final IContextSinkRoleCombinator combinator;
    private final AssemblyContext context;

    public SinkRoleSelector(final IContextSinkRoleCombinator combinator, final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public AssemblyEventConnectorCreator withSinkRole(final SinkRole role) {
        return this.combinator.combineContextAndSinkRole(this.context, role);
    }

    public AssemblyEventConnectorCreator withSinkRole(final String name) {
        final SinkRole role = (SinkRole) this.context.getEncapsulatedComponent__AssemblyContext()
            .getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withSinkRole(role);
    }
}
