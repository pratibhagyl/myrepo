import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import ca.uhn.fhir.rest.param.StringParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.codesystems.NameUse;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.json.*; 
public class SampleClient {
	
    public static void main(String[] theArgs) {

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));
        Patient p=new Patient();
        IParser parser = fhirContext.newJsonParser();
        // Search for Patient resources
        Bundle response = client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value("SMITH"))
                .returnBundle(Bundle.class)
                .execute();
        String url="http://example.com/fhir/Patient";
        Bundle response1 = client
                .search()
                .forResource("Patient")
                .accept(Patient.SP_BIRTHDATE).accept(Patient.SP_FAMILY).accept(Patient.SP_GIVEN)
                .sort().ascending(Patient.GIVEN)
                .returnBundle(Bundle.class)
                .execute();
        
//        Patient patient = client.read()
//        		   .resource(Patient.class)
//        		   .withUrl(url)
//        		   .execute();
//        System.out.println(patient.getName().get(10).getFamily());
        String string = fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(response1);
       // System.out.println(string);
       // Object obj=JSONValue.parse(s 
        JSONObject jsonObj = new JSONObject(response1);
        System.out.println(jsonObj.getString("name"));
        String serialized = parser.encodeResourceToString(response1);
       // System.out.println(serialized);
//        Patient patient= new Patient();
//        ObjectMapper mapper = new ObjectMapper();
//        MethodOutcome outcome = client.create()
//        		   .resource(patient)
//        		   .prettyPrint()
//        		   .encodedJson()
//        		   .execute();
//        System.out.println(outcome.getResponseHeaders().size());
//        
//        
//        /**
//         * Read JSON from a file into a Map
//         */
//        try {
//            Map<String, Object> map = mapper.readValue(outcome.toString(), new TypeReference<Map<String, Object>>() {
//            });
// 
//           System.out.println(map);
//           Set s1=map.entrySet();
//   		Iterator i1=s1.iterator();
//   		while(i1.hasNext())
//   		{
//   			Map.Entry m=(Map.Entry)i1.next();
//   			System.out.println("key value"+m.getKey()+" "+"actual value"+m.getValue()+"\n\n");
//   		}
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
// 
    }
}
 
       
       
 
    
//    HashMap<String, Object> map = new Gson().fromJson(string.toString(), HashMap.class);
//    Set s1=map.entrySet();
//	Iterator i1=s1.iterator();
//	while(i1.hasNext())
//	{
//		Map.Entry m=(Map.Entry)i1.next();
//		System.out.println(m.getKey()+" "+m.getValue()+"\n");
//	}
