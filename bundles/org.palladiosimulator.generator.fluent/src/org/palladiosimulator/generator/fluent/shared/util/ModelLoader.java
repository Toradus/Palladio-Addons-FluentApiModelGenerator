package org.palladiosimulator.generator.fluent.shared.util;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.system.System;

/**
 * This utility class contains functions to load Repositories, ResourceTypeReopsitories, Systems,
 * ResourceEnvironemnts and Allocations.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.Repository
 * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository
 * @see org.palladiosimulator.pcm.system.System
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 * @see org.palladiosimulator.pcm.allocation.Allocation
 */
public class ModelLoader {

    public static final String PRIMITIVE_TYPES_PATH = "pathmap://PCM_MODELS/PrimitiveTypes.repository";
    public static final String RESOURCE_TYPE_PATH = "pathmap://PCM_MODELS/Palladio.resourcetype";
    public static final String FAILURE_TYPES_PATH = "pathmap://PCM_MODELS/FailureTypes.repository";

    /**
     * Utility class constructor
     *
     * @throws IllegalAccessException
     *             on call
     */
    private ModelLoader() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Loads a repository from the specified path.
     *
     * @param uri
     * @return the repository
     * @see org.palladiosimulator.pcm.repository.Repository
     */
    public static Repository loadRepository(final String uri) {
        return (Repository) ModelLoader.load(uri, "repository");
    }

    /**
     * Loads a ResourceRepository from the specified path.
     *
     * @param uri
     * @return the resource repository
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository
     */
    public static ResourceRepository loadResourceTypeRepository(final String uri) {
        return (ResourceRepository) ModelLoader.load(uri, "resourcetype");
    }

    /**
     * Loads a System from the specified path.
     *
     * @param uri
     * @return the org.palladiosimulator.generator.fluent.system
     * @see org.palladiosimulator.pcm.system.System
     */
    public static System loadSystem(final String uri) {
        return (System) ModelLoader.load(uri, "system");
    }

    /**
     * Loads a ResourceEnvironment from the specified path.
     *
     * @param uri
     * @return the resource environment
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     */
    public static ResourceEnvironment loadResourceEnvironment(final String uri) {
        return (ResourceEnvironment) ModelLoader.load(uri,
                "org.palladiosimulator.generator.fluent.resourceenvironment");
    }

    /**
     * Loads an Allocation form the specified path.
     *
     * @param uri
     * @return the org.palladiosimulator.generator.fluent.allocation
     * @see org.palladiosimulator.pcm.allocation.Allocation
     */
    public static Allocation loadAllocation(final String uri) {
        return (Allocation) ModelLoader.load(uri, "org.palladiosimulator.generator.fluent.allocation");
    }

    private static EObject load(final String uri, final String type) {
        ResourcetypePackage.eINSTANCE.eClass();
        // Register the XMI resource
        // org.palladiosimulator.generator.fluent.component.factory for the
        // .resourcetype extension
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(type, new XMIResourceFactoryImpl());
        // Get the resource
        final ResourceSet resSet = new ResourceSetImpl();
        final Resource resource = resSet.getResource(URI.createURI(uri), true);
        // Get the first model element
        return resource.getContents()
            .get(0);
    }
}
