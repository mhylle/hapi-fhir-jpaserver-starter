package ca.uhn.fhir.jpa.starter.resourceproviders;

import ca.uhn.fhir.jpa.starter.data.Repository;
import ca.uhn.fhir.jpa.starter.resources.CustomResource;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r4.model.IdType;

import java.util.UUID;

public class RestfulCustomResourceResourceProvider implements IResourceProvider {

  /**
   * The getResourceType method comes from IResourceProvider, and must
   * be overridden to indicate what type of resource this provider
   * supplies.
   */
  @Override
  public Class<CustomResource> getResourceType() {
    return CustomResource.class;
  }

  /**
   * The "@Read" annotation indicates that this method supports the
   * read operation. Read operations should return a single resource
   * instance.
   *
   * @param theId The read operation takes one parameter, which must be of type
   *              IdDt and must be annotated with the "@Read.IdParam" annotation.
   * @return Returns a resource matching this identifier, or null if none exists.
   */
  @Read()
  public CustomResource getResourceById(@IdParam IdType theId) {
    return Repository.getInstance().getCRById(theId);
  }

  @Create()
  public MethodOutcome createCustomResource(@ResourceParam CustomResource customResource) {
//    if (customResource.getReason() == null) {
//      throw new UnprocessableEntityException("No reason supplied");
//    }
    String assignedId = UUID.randomUUID().toString();
    customResource.setId(assignedId);
    Repository.getInstance().addCR(customResource);
    MethodOutcome result = new MethodOutcome();
    result.setId(new IdType("CustomResource", assignedId, "1"));
    return result;
  }
}
