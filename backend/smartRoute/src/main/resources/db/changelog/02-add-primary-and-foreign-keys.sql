--changeset app:011-add-territory-to-address
ALTER TABLE address
ADD COLUMN territorio_id UUID NOT NULL;

--changeset app:012-add-address-territory-fk
ALTER TABLE address
ADD CONSTRAINT fk_address_territory
FOREIGN KEY (territorio_id)
REFERENCES territorio(id_territorio);


--changeset app:013-add-agent-to-ambulance
ALTER TABLE ambulance
ADD COLUMN agent_id UUID;

--changeset app:014-add-ambulance-agent-fk
ALTER TABLE ambulance
ADD CONSTRAINT fk_ambulance_agent
FOREIGN KEY (agent_id)
REFERENCES health_agent(id_agent);


--changeset app:015-add-user-to-health-agent
ALTER TABLE health_agent
ADD COLUMN user_id UUID UNIQUE;

--changeset app:016-add-health-agent-user-fk
ALTER TABLE health_agent
ADD CONSTRAINT fk_agent_user
FOREIGN KEY (user_id)
REFERENCES app_user(user_id);


--changeset app:017-add-person-to-field-visit
ALTER TABLE field_visit
ADD COLUMN person_id UUID;

--changeset app:018-add-field-visit-person-fk
ALTER TABLE field_visit
ADD CONSTRAINT fk_visit_person
FOREIGN KEY (person_id)
REFERENCES assisted_person(id_aperson);


--changeset app:019-add-route-to-field-visit
ALTER TABLE field_visit
ADD COLUMN route_id UUID;

--changeset app:020-add-field-visit-route-fk
ALTER TABLE field_visit
ADD CONSTRAINT fk_visit_route
FOREIGN KEY (route_id)
REFERENCES route_plan(id_route);


--changeset app:021-add-agent-to-route-plan
ALTER TABLE route_plan
ADD COLUMN agent_id UUID;

--changeset app:022-add-route-plan-agent-fk
ALTER TABLE route_plan
ADD CONSTRAINT fk_route_agent
FOREIGN KEY (agent_id)
REFERENCES health_agent(id_agent);


--changeset app:023-add-address-to-assisted-person
ALTER TABLE assisted_person
ADD COLUMN address_id UUID;

--changeset app:024-add-assisted-person-address-fk
ALTER TABLE assisted_person
ADD CONSTRAINT fk_person_address
FOREIGN KEY (address_id)
REFERENCES address(id_address);
