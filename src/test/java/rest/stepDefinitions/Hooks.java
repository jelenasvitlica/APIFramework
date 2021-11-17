package rest.stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        stepDefinitions m = new stepDefinitions();
        if(m.place_id == null) {

            m.add_place_payload_with("Jelena", "SRB", "Dunavska");
            m.user_calls_with_http_request("AddPlaceAPI", "POST");
            m.verify_place_id_created_maps_to_using("Jelena", "GetPLaceAPI");
        }

    }

}
