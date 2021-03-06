/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.fhir.resources;

import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Encounter;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.openmrs.api.context.Context;
import org.openmrs.module.fhir.api.EncounterService;

import java.util.ArrayList;
import java.util.List;

public class FHIREncounterResource extends Resource {

	public Encounter getByUniqueId(IdDt id) {
		EncounterService encounterService = Context.getService(EncounterService.class);
		Encounter fhirEncounter = encounterService.getEncounter(id.getIdPart());
		if (fhirEncounter == null) {
			throw new ResourceNotFoundException("Encounter is not found for the given Id " + id.getIdPart());
		}
		return fhirEncounter;
	}

	public List<Encounter> searchEncountersById(TokenParam id) {
		return Context.getService(EncounterService.class).searchEncounterById(id.getValue());
	}

	public Bundle getEncounterOperationsById(IdDt id) {
		return Context.getService(EncounterService.class).getEncounterOperationsById(id.getIdPart());
	}

	public List<Encounter> searchEncountersByPatientIdentifier(ReferenceParam identifier) {
		List<Encounter> fhirEncounters = new ArrayList<Encounter>();

		String chain = identifier.getChain();
		if (Patient.SP_IDENTIFIER.equals(chain)) {
			fhirEncounters = Context.getService(EncounterService.class).searchEncountersByPatientIdentifier(identifier.getValue());
		}
		return fhirEncounters;
	}

}
