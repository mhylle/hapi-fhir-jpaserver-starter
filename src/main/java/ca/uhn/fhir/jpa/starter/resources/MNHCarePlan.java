package ca.uhn.fhir.jpa.starter.resources;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.Reference;

@ResourceDef(name = "MNHCarePlan", profile = "http://example.com/StructureDefinition/MNHCarePlan")
public class MNHCarePlan extends CarePlan {

  @Child(name = "reason")
  @Extension(url = "http://example.com/dontuse#reason", definedLocally = true, isModifier = false)
  @Description(shortDefinition = "The medical reason for the careplan")
  private Reference reason;

  public Reference getReason() {
    return reason;
  }

  public void setReason(Reference reason) {
    this.reason = reason;
  }

  @Override
  public String fhirType() {
    return super.fhirType();
  }
}
