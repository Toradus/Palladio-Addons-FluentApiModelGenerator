package resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import resourceenvironment.apiControlFlowInterfaces.IResourceEnvironment;
import resourceenvironment.apiControlFlowInterfaces.IResourceEnvironmentAddition;
import shared.structure.CommunicationLinkResource;
import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;
import shared.validate.IModelValidator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 * ResourceEnvironment}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 */
public class ResourceEnvironmentCreator extends ResourceEntity implements IResourceEnvironment {
    private final IModelValidator validator;
    private final ResourceRepository resources;

    private final List<ResourceContainer> resourceContainers = new ArrayList<>();
    private final List<LinkingResource> linkingResources = new ArrayList<>();

    public ResourceEnvironmentCreator(final ResourceRepository resources, final IModelValidator validator) {
        this.resources = resources;
        this.validator = validator;
    }

    @Override
    public ResourceEnvironmentCreator withName(final String name) {
        return (ResourceEnvironmentCreator) super.withName(name);

    }

    @Override
    public ResourceEnvironment createResourceEnvironmentNow() {
        final ResourceEnvironment environment = build();
        validator.validate(environment, name);
        return environment;
    }

    @Override
    protected ResourceEnvironment build() {
        final ResourceEnvironment environment = ResourceenvironmentFactory.eINSTANCE.createResourceEnvironment();
        if (name != null) {
            environment.setEntityName(name);
        }
        environment.getResourceContainer_ResourceEnvironment().addAll(resourceContainers);
        environment.getLinkingResources__ResourceEnvironment().addAll(linkingResources);
        return environment;
    }

    @Override
    public IResourceEnvironmentAddition addToResourceEnvironment(final ResourceContainerCreator resourceContainer) {
        Objects.requireNonNull(resourceContainer, "The given ResourceContainer must not be null");
        resourceContainers.add(resourceContainer.build());
        return this;
    }

    @Override
    public IResourceEnvironmentAddition addToResourceEnvironment(final LinkingResourceCreator linkingResource) {
        Objects.requireNonNull(linkingResource, "The given LinkingResource must not be null");
        linkingResources.add(linkingResource.build());
        return this;
    }

    /**
     * Fetches the {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy
     * SchedulingPolicy} matching the given policy.
     *
     * @param policy
     * @return the matching <code>SchedulingPolicy</code>
     */
    public SchedulingPolicy getSchedulingPolicy(final SchedulingPolicies policy) {
        Objects.requireNonNull(policy, "The given SchedulingPolicy must not be null");
        return resources.getSchedulingPolicies__ResourceRepository().stream()
                .filter(x -> x.getEntityName().equals(policy.getPolicyName())).findFirst().get();
    }

    /**
     * Fetches the
     * {@link org.palladiosimulator.pcm.resourcetype.ProcessingResourceType
     * ProcessingResourceType} matching the given resource.
     *
     * @param resource
     * @return the matching <code>ProcessingResourceType</code>
     */
    public ProcessingResourceType getProcessingResource(final ProcessingResource resource) {
        Objects.requireNonNull(resource, "The given ProcessignResource must not be null");
        return (ProcessingResourceType) resources.getAvailableResourceTypes_ResourceRepository().stream()
                .filter(x -> x.getEntityName().equals(resource.getResourceName())).findFirst().get();
    }

    /**
     * Fetches the
     * {@link org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType
     * CommunicationLinkResourceType} matching the given resource.
     *
     * @param resource
     * @return the matching <code>CommunicationLinkResourceType</code>
     */
    public CommunicationLinkResourceType getCommunicationLinkResource(final CommunicationLinkResource resource) {
        Objects.requireNonNull(resource, "The given CommunicationLinkResource must not be null");
        return (CommunicationLinkResourceType) resources.getAvailableResourceTypes_ResourceRepository().stream()
                .filter(x -> x.getEntityName().equals(resource.getResourceName())).findFirst().get();
    }

    /**
     * Fetches the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContaienr} with the given name.
     *
     * @param name
     * @return the <code>ResourceContainer</code>
     * @throws IllegalArgumentException Thrown if no <code>ResourceContainer</code>
     *                                  has the given name.
     */
    public ResourceContainer getResourceContainerByName(String name) throws IllegalArgumentException {
        return resourceContainers.stream().filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ResourceContainer with name " + name + " found"));
    }
}
