package systemModel.structure;

import java.util.ArrayList;
import java.util.List;


import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.apiControlFlowInterfaces.ISystemAddition;

public class SystemCreator extends SystemEntity implements ISystem {
	private List<AssemblyContext> assemblyContexts = new ArrayList<>();
	private List<Repository> repositories = new ArrayList<>();
	private List<Connector> connectors = new ArrayList<>();
	private List<OperationRequiredRole> systemRequiredRoles = new ArrayList<>();
	private List<OperationProvidedRole> systemProvidedRoles = new ArrayList<>();
	private List<SinkRole> systemSinkRoles = new ArrayList<>();
	private List<SourceRole> systemSourceRoles = new ArrayList<>();
	private List<EventChannel> eventChannels = new ArrayList<>();

	@Override
	public SystemCreator withName(String name) {
		return (SystemCreator) super.withName(name);
	}

	@Override
	protected System build() {
		System system = SystemFactory.eINSTANCE.createSystem();
		if (name != null) {
			system.setEntityName(name);
		}
		system.getAssemblyContexts__ComposedStructure().addAll(getAssemblyContexts());
		system.getConnectors__ComposedStructure().addAll(connectors);
		system.getRequiredRoles_InterfaceRequiringEntity().addAll(systemRequiredRoles);
		system.getProvidedRoles_InterfaceProvidingEntity().addAll(systemProvidedRoles);
		system.getRequiredRoles_InterfaceRequiringEntity().addAll(systemSourceRoles);
		system.getProvidedRoles_InterfaceProvidingEntity().addAll(systemSinkRoles);
		system.getEventChannel__ComposedStructure().addAll(eventChannels);
		//TODO: validate
		return system;
	}

	@Override
	public System createSystemNow() {
		return build();
	}

	@Override
	public ISystem withAssembyContext(AssemblyContext context) {
		this.assemblyContexts.add(context);
		return this;
	}

	@Override
	public ISystem withRepository(Repository repository) {
		this.repositories.add(repository);
		return this;
	}

	@Override
	public ISystemAddition withAssemblyConnector(AssemblyConnector connector) {
		this.connectors.add(connector);
		return this;
	}

	@Override
	public ISystemAddition withOperationRequiredRole(OperationRequiredRole role) {
		this.systemRequiredRoles.add(role);
		return this;
	}

	@Override
	public ISystemAddition withRequiredDelegationConnector(RequiredDelegationConnector connector) {
		this.connectors.add(connector);
		return this;
	}
	
	@Override
	public ISystemAddition withOperationProvidedRole(OperationProvidedRole role) {
		this.systemProvidedRoles.add(role);
		return this;
	}
	
	@Override
	public ISystemAddition withProvidedDelegationConnector(ProvidedDelegationConnector connector) {
		this.connectors.add(connector);
		return this;
	}
	
	@Override
	public ISystemAddition withEventChannel(EventChannel eventChannel) {
		this.eventChannels.add(eventChannel);
		return this;
	}
	
	@Override
	public ISystemAddition withEventChannelSinkRoleConnector(EventChannelSinkConnector connector) {
		this.connectors.add(connector);
		return this;
	}
	
	@Override
	public ISystemAddition withEventChannelSourceRoleConnector(EventChannelSourceConnector connector) {
		this.connectors.add(connector);
		return this;
	}

	@Override
	public ISystemAddition withSinkRole(SinkRole role) {
		this.systemSinkRoles.add(role);
		return this;
	}

	@Override
	public ISystemAddition withSinkDelegationConnector(SinkDelegationConnector connector) {
		this.connectors.add(connector);
		return this;
	}

	@Override
	public ISystemAddition withSourceRole(SourceRole role) {
		this.systemSourceRoles.add(role);
		return this;
	}

	@Override
	public ISystemAddition withSourceDelegationConnector(SourceDelegationConnector connector) {
		this.connectors.add(connector);
		return this;
	}

	public List<Repository> getRepositories() {
		return repositories;
	}

	public List<AssemblyContext> getAssemblyContexts() {
		return assemblyContexts;
	}
	
	public List<OperationRequiredRole> getSystemRequiredRoles() {
		return systemRequiredRoles;
	}

	public List<OperationProvidedRole> getSystemProvidedRoles() {
		return systemProvidedRoles;
	}
	
	public List<SinkRole> getSystemSinkRoles() {
		return systemSinkRoles;
	}
	
	public List<SourceRole> getSystemSourceRoles() {
		return systemSourceRoles;
	}
	
	public List<EventChannel> getEventChannels() {
		return eventChannels;
	}
}
