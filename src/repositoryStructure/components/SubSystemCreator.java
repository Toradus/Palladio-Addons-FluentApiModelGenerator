package repositoryStructure.components;

import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.SubsystemFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class SubSystemCreator extends ComplexComponent {

	public SubSystemCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public SubSystemCreator withName(String name) {
		return (SubSystemCreator) super.withName(name);
	}

	@Override
	public SubSystemCreator withId(String id) {
		return (SubSystemCreator) super.withId(id);
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public SubSystemCreator provides(OperationInterfaceCreator interfce) {
		return (SubSystemCreator) super.provides(interfce);
	}
	
	@Override
	public SubSystemCreator provides(OperationInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.provides(interfce, name);
	}

	// provides infrastructure interface
	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (SubSystemCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.providesInfrastructure(interfce, name);
	}

	// sink role: handles an event group
	@Override
	public SubSystemCreator handles(EventGroupCreator eventGroup) {
		return (SubSystemCreator) super.handles(eventGroup);
	}
	
	@Override
	public SubSystemCreator handles(EventGroupCreator eventGroup, String name) {
		return (SubSystemCreator) super.handles(eventGroup, name);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public SubSystemCreator requires(OperationInterfaceCreator interfce) {
		return (SubSystemCreator) super.requires(interfce);
	}
	
	@Override
	public SubSystemCreator requires(OperationInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.requires(interfce, name);
	}

	// require infrastructure interface
	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce);
	}
	
	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce, name);
	}

	// emits event group (source role)
	@Override
	public SubSystemCreator emits(EventGroupCreator eventGroup) {
		return (SubSystemCreator) super.emits(eventGroup);
	}
	
	@Override
	public SubSystemCreator emits(EventGroupCreator eventGroup, String name) {
		return (SubSystemCreator) super.emits(eventGroup, name);
	}

	// resource required role
	@Override
	public SubSystemCreator requiresResource(ResourceInterface resourceInterface) {
		return (SubSystemCreator) super.requiresResource(resourceInterface);
	}

	@Override
	public SubSystem build() {
		SubSystem subSystem = SubsystemFactory.eINSTANCE.createSubSystem();
		if (this.name != null)
			subSystem.setEntityName(this.name);
		if (this.id != null)
			subSystem.setId(this.id);

		subSystem.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		subSystem.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		subSystem.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

		subSystem.getAssemblyContexts__ComposedStructure().addAll(assemblyContexts);
		subSystem.getConnectors__ComposedStructure().addAll(connectors);
		subSystem.getEventChannel__ComposedStructure().addAll(eventChannels);
		subSystem.getResourceRequiredDelegationConnectors_ComposedStructure().addAll(resourceRequiredDelegationConnectors);

		return subSystem;
	}

}
