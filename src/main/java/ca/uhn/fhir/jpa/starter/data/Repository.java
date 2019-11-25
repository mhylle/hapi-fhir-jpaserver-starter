package ca.uhn.fhir.jpa.starter.data;

import ca.uhn.fhir.jpa.starter.resources.CustomResource;
import ca.uhn.fhir.jpa.starter.resources.MNHCarePlan;
import org.hl7.fhir.r4.model.IdType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
  Map<IdType, MNHCarePlan> elements;
  Map<IdType, CustomResource> customResourceMap;

  private Repository() {
    elements = new HashMap<>();
    customResourceMap = new HashMap<>();
  }

  private static Repository _instance;

  public static Repository getInstance() {
    if (_instance == null) {
      _instance = new Repository();
    }
    return _instance;
  }


  public void add(MNHCarePlan element) {
    elements.put(element.getIdElement(), element);
  }

  public MNHCarePlan getById(IdType theId) {
    return elements.get(theId);
  }

  public List<MNHCarePlan> get() {
    return new ArrayList<>(elements.values());
  }

  public void addCR(CustomResource element) {
    customResourceMap.put(element.getIdElement(), element);
  }

  public CustomResource getCRById(IdType theId) {
    return customResourceMap.get(theId);
  }

  public List<CustomResource> getCR() {
    return new ArrayList<>(customResourceMap.values());
  }
}
