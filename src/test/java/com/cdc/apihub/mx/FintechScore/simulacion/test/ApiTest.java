package com.cdc.apihub.mx.FintechScore.simulacion.test;

import com.cdc.apihub.mx.FintechScore.simulacion.client.ApiClient;
import com.cdc.apihub.mx.FintechScore.simulacion.client.ApiException;
import com.cdc.apihub.mx.FintechScore.simulacion.client.ApiResponse;
import com.cdc.apihub.mx.FintechScore.simulacion.client.api.FintechScoreSimulacinApi;
import com.cdc.apihub.mx.FintechScore.simulacion.client.model.CatalogoEstados;
import com.cdc.apihub.mx.FintechScore.simulacion.client.model.CatalogoPais;
import com.cdc.apihub.mx.FintechScore.simulacion.client.model.Domicilio;
import com.cdc.apihub.mx.FintechScore.simulacion.client.model.Persona;
import com.cdc.apihub.mx.FintechScore.simulacion.client.model.Peticion;
import com.cdc.apihub.mx.FintechScore.simulacion.client.model.Respuesta;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {
    
	private Logger logger = LoggerFactory.getLogger(ApiTest.class.getName());
	private ApiClient apiClient = null;
	private final FintechScoreSimulacinApi api = new FintechScoreSimulacinApi();
    private String xApiKey = null;
    
    @Before()
    public void setUp() {
        this.xApiKey = "your_api_key";
        this.apiClient = api.getApiClient();
        this.apiClient.setBasePath("the_url");
    }
    
    @Test
    public void getReporteTest() throws ApiException {
        
        Peticion body = new Peticion();
        Persona persona = new Persona();
        Domicilio domicilio = new Domicilio();
        
        Integer estatusOK = 200;
        Integer estatusNoContent = 204;
        
        try {
        	
        	domicilio.setDireccion("AV 535 84");
            domicilio.setCiudad( "CIUDAD DE MEXICO");
            domicilio.setColoniaPoblacion("SAN JUAN DE ARAGON 1RA SECC");
            domicilio.setDelegacionMunicipio("GUSTAVO A MADERO");
            domicilio.setCP("07969");
            domicilio.setEstado(CatalogoEstados.CDMX);
            domicilio.setPais(CatalogoPais.MX);
            
            persona.setPrimerNombre("PABLO");
            persona.setSegundoNombre("ANTONIO");
            persona.setApellidoPaterno("PRUEBA");
            persona.setApellidoMaterno("ALVAREZ");
            persona.setFechaNacimiento("1985-03-16");
            persona.setRFC("PUAP850316MI1");
            persona.setDomicilio(domicilio);
            
            body.setFolioOtorgante("20210307");
            body.setPersona(persona);
            
            ApiResponse<?>  response = api.getGenericReporte(this.xApiKey, body);
            
        	Assert.assertTrue(estatusOK.equals(response.getStatusCode()));
            
            if(estatusOK.equals(response.getStatusCode())) {
                Respuesta responseOK = (Respuesta) response.getData();
                logger.info(responseOK.toString());
            }

        } catch (ApiException e) {
            if(!estatusNoContent.equals(e.getCode())) {
                logger.info(e.getResponseBody());
            }
            Assert.assertTrue(estatusOK.equals(e.getCode()));           
        }
    }
    
}
