package ca.uhn.fhir.jpa.starter.resourceproviders;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jpa.starter.data.Repository;
import ca.uhn.fhir.model.api.Tag;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r4.model.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RestfulPatientResourceProvider implements IResourceProvider {

  /**
   * The getResourceType method comes from IResourceProvider, and must
   * be overridden to indicate what type of resource this provider
   * supplies.
   */
  @Override
  public Class<Patient> getResourceType() {
    return Patient.class;
  }

  /**
   * The "@Read" annotation indicates that this method supports the
   * read operation. Read operations should return a single resource
   * instance.
   *
   * @param theId
   *    The read operation takes one parameter, which must be of type
   *    IdDt and must be annotated with the "@Read.IdParam" annotation.
   * @return
   *    Returns a resource matching this identifier, or null if none exists.
   */
  @Read()
  public Patient getResourceById(@IdParam IdType theId) {
    Repository.getInstance().getById(theId);
    Patient patient = new Patient();
    patient.addIdentifier();
    patient.getIdentifier().get(0).setSystem("urn:hapitest:mrns");
    patient.getIdentifier().get(0).setValue("00002");
    patient.addName().setFamily("Test");
    patient.getName().get(0).addGiven("PatientOne");
    patient.setGender(Enumerations.AdministrativeGender.MALE);

    FhirContext ctx = FhirContext.forR4();
    ctx.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());
    patient.getText().setStatus(Narrative.NarrativeStatus.GENERATED);
    patient.getText().setDivAsString(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));

    // Add tags
    patient.getMeta().addTag()
      .setSystem(Tag.HL7_ORG_FHIR_TAG)
      .setCode("mhylle")
      .setDisplay("mhylle");

    // Set the last updated date
    patient.getMeta().setLastUpdatedElement(new InstantType("2011-02-22T11:22:00.0122Z"));
    return patient;
  }

  /**
   * The "@Search" annotation indicates that this method supports the
   * search operation. You may have many different methods annotated with
   * this annotation, to support many different search criteria. This
   * example searches by family name.
   *
   * @param theFamilyName
   *    This operation takes one parameter which is the search criteria. It is
   *    annotated with the "@Required" annotation. This annotation takes one argument,
   *    a string containing the name of the search criteria. The datatype here
   *    is StringParam, but there are other possible parameter types depending on the
   *    specific search criteria.
   * @return
   *    This method returns a list of Patients. This list may contain multiple
   *    matching resources, or it may also be empty.
   */
  @Search()
  public List<Patient> getPatient(@RequiredParam(name = Patient.SP_FAMILY) StringParam theFamilyName) {
    Patient patient = new Patient();
    patient.setId(UUID.randomUUID().toString());
    patient.addIdentifier();
    patient.getIdentifier().get(0).setUse(Identifier.IdentifierUse.OFFICIAL);
    patient.getIdentifier().get(0).setSystem("urn:hapitest:mrns");
    patient.getIdentifier().get(0).setValue("00001");
    patient.addName();
    patient.getName().get(0).setFamily(theFamilyName.getValue());
    patient.getName().get(0).addGiven("PatientOne");
    patient.setGender(Enumerations.AdministrativeGender.MALE);
    return Collections.singletonList(patient);
  }

}
