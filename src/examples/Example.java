package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import factory.FluentRepositoryFactory;
import repositoryStructure.internals.Primitive;

class Example {

	public static void main(String[] args) {

		mediaStoreExample();
		exampleWithoutMeaning();

	}

	public static void mediaStoreExample() {
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		// TODO: mediastore Beispiel vervollständigen: seffs + failureType + Namen der
		// Provided Roles
		Repository mediaStore = create.newRepository().withName("MediaStoreRepository")
				.addToRepository(create.newCompositeDataType().withName("FileContent"))
				.addToRepository(create.newCompositeDataType().withName("AudioCollectionRequest")
						.withInnerDeclaration("Count", Primitive.INTEGER)
						.withInnerDeclaration("Size", Primitive.INTEGER))
				.addToRepository(create.newOperationInterface().withName("IFileStorage").withOperationSignature()
						.withName("getFiles")
						.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"),
								ParameterModifier.NONE)
						.withReturnType(create.fetchOfDataType("FileContent")).now().withOperationSignature()
						.withName("storeFile").withParameter("file", create.fetchOfDataType("FileContent"), null).now())
				.addToRepository(create.newOperationInterface().withName("IDownload").withOperationSignature()
						.withName("download")
						.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"),
								ParameterModifier.NONE)
						.withReturnType(create.fetchOfDataType("AudioCollectionRequest")).now())
				.addToRepository(create.newOperationInterface().withName("IMediaAccess").withOperationSignature()
						.withName("upload").withParameter("file", create.fetchOfDataType("FileContent"), null).now()
						.withOperationSignature().withName("getFileList").now())
				.addToRepository(create.newOperationInterface().withName("IPackaging").withOperationSignature()
						.withName("zip").withParameter("audios", create.fetchOfDataType("AudioCollectionRequest"), null)
						.withReturnType(create.fetchOfDataType("FileContent")).now())
				.addToRepository(create.newOperationInterface().withName("IMediaManagement").withOperationSignature()
						.withName("upload").withParameter("file", create.fetchOfDataType("FileContent"), null).now()
						.withOperationSignature().withName("download")
						.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"),
								ParameterModifier.NONE)
						.withReturnType(create.fetchOfDataType("FileContent")).now().withOperationSignature()
						.withName("getFileList").now())
				.addToRepository(create.newBasicComponent().withName("EnqueueDownloadCache")
						.provides(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IDownload"))
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("InstantDownloadCache")
						.provides(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IDownload"))
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("FileStorage")
						.provides(create.fetchOfOperationInterface("IFileStorage"), "IDataStorage")
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("MediaManagement")
						.provides(create.fetchOfOperationInterface("IMediaManagement"))
						.requires(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IPackaging"))
						.requires(create.fetchOfOperationInterface("IMediaAccess"))
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("Packaging")
						.provides(create.fetchOfOperationInterface("IPackaging"))
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("MediaAccess")
						.provides(create.fetchOfOperationInterface("IMediaAccess"))
						.provides(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IFileStorage"), "IDataStorage")
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff()))
				.createRepositoryNow();

		saveRepository(mediaStore, "./", "myMediaStore.repository", true);
	}

	public static void exampleWithoutMeaning() {
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		// TODO: Ausführliches Beispiel zum Testen der API
		Repository repo = create.newRepository().withName("defaultRepository").withDescription("This is my PCM model.")

				// DATATYPES
				.addToRepository(create.newCollectionDataType("StringList", Primitive.STRING))
				.addToRepository(create.newResourceTimeoutFailureType("blub"))

				.addToRepository(create.newCompositeDataType().withName("Person")
						.withInnerDeclaration("first names", create.fetchOfDataType("StringList"))
						.withInnerDeclaration("age", Primitive.INTEGER))
				.addToRepository(create.newExceptionType().withName("myException").withExceptionMessage("FEHLER!"))

				// INTERFACES
				.addToRepository(create.newOperationInterface().withName("IDatabase").withOperationSignature()
						.withName("saveDatabaseEntry")
						.withParameter("first names", create.fetchOfDataType("StringList"), null)
						.withParameter("age", Primitive.INTEGER, ParameterModifier.INOUT)
						.withReturnType(create.fetchOfDataType("Person")).now().withRequiredCharacterisation(
								create.fetchOfParameter("age"), VariableCharacterisationType.VALUE))
				.addToRepository(create.newEventGroup().withName("haha"))

				// BASIC COMPONENTS
				.addToRepository(create.newBasicComponent().withName("Database")
						.handles(create.newEventGroup().withName("hallo").withEventType().withName("type")
								.withParameter("foo", Primitive.BOOLEAN, null).now().withRequiredCharacterisation(
										create.fetchOfParameter("foo"), VariableCharacterisationType.STRUCTURE))
						.withServiceEffectSpecification(create.newSeff().withSeffBehaviour().withStartAction()
								.followedBy().externalCallAction().followedBy().stopAction().createBehaviourNow())
						.withPassiveResource("3", create.fetchOfResourceTimeoutFailureType("blub"), "passivo")
						.provides(create.fetchOfOperationInterface("IDatabase"), "provDB")
						.requires(create.newOperationInterface().withName("someInterface"), "reqSomeI")
						.withPassiveResource("2*3", create.fetchOfResourceTimeoutFailureType("blub"), "passResource")
						.withVariableUsage(create.newVariableUsage()))

				.addToRepository(create.newBasicComponent().withName("Web2")
						.provides(create.newOperationInterface().withName("IDatabase2")))

				// COMPOSITE COMPONENTS
				.addToRepository(
						create.newCompositeComponent().withName("Web").withVariableUsage(create.newVariableUsage())
								.requires(create.newOperationInterface().withName("HelloWorld"))
								.withAssemblyContext(create.fetchOfComponent("Database"), "DBContext")
								.withAssemblyConnection(create.fetchOfOperationProvidedRole("provDB"),
										create.fetchOfAssemblyContext("DBContext"),
										create.fetchOfOperationRequiredRole("reqSomeI"),
										create.fetchOfAssemblyContext("DBContext"))
								.withEventChannel(create.fetchOfEventGroup("haha")))
				.createRepositoryNow();

		saveRepository(repo, "/Users/louisalambrecht/Documents/eclipse-workspace/FluentAPICreation/",
				"myrepo.repository", false);
	}

	public static void readmeExampleBackend() {
		// Factory
		RepositoryFactory repoFact = RepositoryFactory.eINSTANCE;

		// Repository
		Repository repository = repoFact.createRepository();

		// Database component
		BasicComponent databaseComponent = repoFact.createBasicComponent();
		databaseComponent.setEntityName("Database");

		// IDatabase interface
		OperationInterface databaseInterface = repoFact.createOperationInterface();
		databaseInterface.setEntityName("IDatabase");

		// Signature store
		OperationSignature store = repoFact.createOperationSignature();
		store.setEntityName("store");
		// with parameters forename, name
		Parameter forename = repoFact.createParameter();
		forename.setParameterName("forename");
		forename.setDataType__Parameter(null); // referencing the imported data types poses another problem
		Parameter name = repoFact.createParameter();
		name.setParameterName("forename");
		name.setDataType__Parameter(null);

		// Providing connection from Database component to IDatabase interface
		OperationProvidedRole dbProvIDb = repoFact.createOperationProvidedRole();
		dbProvIDb.setProvidedInterface__OperationProvidedRole(databaseInterface);
		dbProvIDb.setProvidingEntity_ProvidedRole(databaseComponent);

		// Seff for Database component on service store
		ResourceDemandingSEFF storeSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
		storeSeff.setDescribedService__SEFF(store);
		databaseComponent.getServiceEffectSpecifications__BasicComponent().add(storeSeff);

		// Adding component + interfaces to the repository
		repository.getComponents__Repository().add(databaseComponent);
		repository.getInterfaces__Repository().add(databaseInterface);

		// Web component
		BasicComponent webComponent = repoFact.createBasicComponent();
		databaseComponent.setEntityName("Web");

		OperationInterface webInterface = repoFact.createOperationInterface();
		databaseInterface.setEntityName("IWeb");

		OperationSignature submit = repoFact.createOperationSignature();
		submit.setEntityName("submit");
		// with parameters forename, name
		Parameter forename2 = repoFact.createParameter();
		forename2.setParameterName("forename");
		forename2.setDataType__Parameter(null);
		Parameter name2 = repoFact.createParameter();
		name2.setParameterName("forename");
		name2.setDataType__Parameter(null);

		OperationProvidedRole webProvIweb = repoFact.createOperationProvidedRole();
		webProvIweb.setProvidedInterface__OperationProvidedRole(webInterface);
		webProvIweb.setProvidingEntity_ProvidedRole(webComponent);

		ResourceDemandingSEFF submitSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
		submitSeff.setDescribedService__SEFF(submit);
		webComponent.getServiceEffectSpecifications__BasicComponent().add(submitSeff);

		OperationRequiredRole webRequIDb = repoFact.createOperationRequiredRole();
		webRequIDb.setRequiredInterface__OperationRequiredRole(databaseInterface);
		webRequIDb.setRequiringEntity_RequiredRole(webComponent);

		// Adding component + interfaces to the repository
		repository.getComponents__Repository().add(webComponent);
		repository.getInterfaces__Repository().add(webInterface);
	}

	public static void readmeExampleFluentAPI() {
		// Factory
		FluentRepositoryFactory create = new FluentRepositoryFactory();
		
		create.newRepository()
			.addToRepository(create.newOperationInterface()
					.withName("IDatabase")
					.withOperationSignature()
						.withName("store")
						.withParameter("forename", Primitive.STRING, ParamterModifier))
			.addToRepository(create.newBasicComponent().withName("Database")).createRepositoryNow();
	}

	public static void saveRepository(Repository repo, String path, String name, boolean printToConsole) {
		String outputFile = path + name;
		String[] fileExtensions = new String[] { "repository", "xml" };

		// Create File
		ResourceSet rs = new ResourceSetImpl();
		for (String fileext : fileExtensions)
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());

		URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

		// Put content to file resource
		resource.getContents().add(repo);

		// Save file
		((XMLResource) resource).setEncoding("UTF-8");
		Map<Object, Object> saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());

		try {
			resource.save(saveOptions);
			if (printToConsole)
				((XMLResource) resource).save(System.out, ((XMLResource) resource).getDefaultSaveOptions());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
