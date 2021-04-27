package system.structure.systemRole;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
 * OperationRequiredRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
 */
public class OperationRequiredRoleCreator extends SystemEntity {

    private OperationInterface requiredInterface;

    public OperationRequiredRoleCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationInterface
     * OperationInterface} this role requires.
     *
     * @param operationInterface
     * @return this role creator
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationRequiredRoleCreator withRequiredInterface(final OperationInterface operationInterface) {
        Objects.requireNonNull(operationInterface, "The given Interface must not be null.");
        requiredInterface = operationInterface;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationInterface
     * OperationInterface} this role requires. Searches the repositories added to
     * the system for an interface that matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationRequiredRoleCreator withRequiredInterface(final String name) throws NoSuchElementException {
        OperationInterface operationInterface;
        try {
            operationInterface = (OperationInterface) system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an OperationInterface. "
                            + "Please make sure all names are unique.", name));
        }
        return this.withRequiredInterface(operationInterface);
    }

    @Override
    public OperationRequiredRole build() {
        final OperationRequiredRole role = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        if (name != null) {
            role.setEntityName(name);
        }
        role.setRequiredInterface__OperationRequiredRole(requiredInterface);
        return role;
    }

    @Override
    public OperationRequiredRoleCreator withName(final String name) {
        return (OperationRequiredRoleCreator) super.withName(name);
    }

}
