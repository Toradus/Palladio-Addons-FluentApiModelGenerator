package org.palladiosimulator.generator.fluent.repository.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.pcm.repository.CompositeDataType;

/**
 * TODO
 *
 * @author dr6817
 */
class FluentRepositoryFactoryTest {

    private FluentRepositoryFactory factory;

    @BeforeEach
    public void init() {
        factory = new FluentRepositoryFactory();
    }

    @Test
    public void testCreateRepositoryWithName() {
        final String name = "test";
        assertEquals(name, factory.newRepository().withName(name).createRepositoryNow().getEntityName());
    }

    @Test
    public void testFetchOfCompositeDataType() {
        final String repositoryName = "example";
        final String dataTypeName = "Person";

        // create
        final Repository repositoryUnderTest = factory.newRepository().withName(repositoryName)
                .addToRepository(factory.newCompositeDataType().withName(dataTypeName)
                        .withInnerDeclaration("name", Primitive.STRING).withInnerDeclaration("age", Primitive.INTEGER))
                .createRepositoryNow();

        // fetch
        final FluentRepositoryFactory factoryUnderTest = new FluentRepositoryFactory();
        factoryUnderTest.newRepository().withImportedResource(repositoryUnderTest);
        final CompositeDataType person = factoryUnderTest.fetchOfCompositeDataType(repositoryName + "." + dataTypeName);

        assertSame(repositoryUnderTest.getDataTypes__Repository().get(0), person);
    }

}
