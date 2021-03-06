package edu.gatech.VRDR.model;

import java.util.Date;

import org.hl7.fhir.dstu3.model.Composition;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;

import ca.uhn.fhir.model.api.annotation.ResourceDef;
import edu.gatech.VRDR.model.util.CommonUtil;
import edu.gatech.VRDR.model.util.DeathCertificateUtil;

@ResourceDef(name = "Composition", profile = "http://hl7.org/fhir/us/vrdr/StructureDefinition/VRDR-Death-Certificate")
public class DeathCertificate extends Composition {
	public DeathCertificate() {
		super();
		CommonUtil.initResource(this);
		setType(DeathCertificateUtil.typeFixedValue);
		setStatus(DeathCertificateUtil.status);
		setDate(new Date());
	}
	
	public DeathCertificate(DeathCertification resource) {
		this();
		addEvent(resource);
	}

	private CompositionAttesterComponent addAttesterCommon() {
		CompositionAttesterComponent component = new CompositionAttesterComponent();
		component.addMode(DeathCertificateUtil.attesterMode);
		return component;
	}

	public void addAttester(Patient resource, Date time) {
		addAttesterBase(resource,time);
	}

	public void addAttester(Practitioner resource, Date time) {
		addAttesterBase(resource,time);
	}

	public void addAttester(Organization resource, Date time) {
		addAttesterBase(resource,time);
	}
	
	private void addAttesterBase(Resource resource,Date time) {
		CompositionAttesterComponent component = addAttesterCommon();
		component.setTime(time);
		component.setParty(new Reference(resource.getId()));
		addAttester(component);
	}

	public void addEvent(DeathCertification resource) {
		CompositionEventComponent component = new CompositionEventComponent();
		component.addCode(DeathCertificateUtil.eventCodeFixedValue);
		component.addDetail(new Reference(resource));
		addEvent(component);
	}
}