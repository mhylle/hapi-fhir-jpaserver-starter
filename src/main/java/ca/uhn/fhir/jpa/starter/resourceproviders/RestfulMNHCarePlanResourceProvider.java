package ca.uhn.fhir.jpa.starter.resourceproviders;

import ca.uhn.fhir.jpa.starter.data.Repository;
import ca.uhn.fhir.jpa.starter.resources.MNHCarePlan;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r4.model.IdType;

import java.util.List;
import java.util.UUID;

public class RestfulMNHCarePlanResourceProvider implements IResourceProvider {

  /**
   * The getResourceType method comes from IResourceProvider, and must
   * be overridden to indicate what type of resource this provider
   * supplies.
   */
  @Override
  public Class<MNHCarePlan> getResourceType() {
    return MNHCarePlan.class;
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
  public MNHCarePlan getResourceById(@IdParam IdType theId) {
    return Repository.getInstance().getById(theId);
  }

  @Create()
  public MethodOutcome createCarePlan(@ResourceParam MNHCarePlan mnhCarePlan) {
//    if (mnhCarePlan.getReason() == null) {
//      throw new UnprocessableEntityException("No reason supplied");
//    }
    String assignedId = UUID.randomUUID().toString();
    mnhCarePlan.setId(assignedId);
    Repository.getInstance().add(mnhCarePlan);
    MethodOutcome result = new MethodOutcome();
    result.setId(new IdType("MNHCarePlan", assignedId, "1"));
    return result;
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
   *    This method returns a list of MNHCarePLans. This list may contain multiple
   *    matching resources, or it may also be empty.
   */
  @Search()
  public List<MNHCarePlan> getCarePlan(@RequiredParam(name = MNHCarePlan.SP_IDENTIFIER) StringParam theFamilyName) {
   return Repository.getInstance().get();
  }

}
