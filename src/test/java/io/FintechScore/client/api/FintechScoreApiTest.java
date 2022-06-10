package io.FintechScore.client.api;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.FintechScore.api.FintechScoreApi;
import io.FintechScore.client.ApiClient;
import io.FintechScore.model.Domicilio;
import io.FintechScore.model.Persona;
import io.FintechScore.model.Peticion;
import io.FintechScore.model.PeticionFolio;
import io.FintechScore.model.Respuesta;
import io.FintechScore.model.CatalogoEstados;
import io.FintechScore.model.CatalogoPais;
import okhttp3.OkHttpClient;

import org.junit.Assert;
import org.junit.Before;

import java.util.concurrent.TimeUnit;

public class FintechScoreApiTest {
	
	private Logger logger = LoggerFactory.getLogger(FintechScoreApiTest.class.getName());
    private final FintechScoreApi api = new FintechScoreApi();
    private ApiClient apiClient;
    private String xApiKey = "your-apikey";
    private String url = "url";
    
    @Before()
    public void setUp() {
    	 
		this.apiClient = api.getApiClient();
        this.apiClient.setBasePath(url);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
               .readTimeout(30, TimeUnit.SECONDS)
               .build();
        apiClient.setHttpClient(okHttpClient);
    }
    
    @Test
    public void getReporteTest() throws Exception {
    	Peticion request = new Peticion();
    	request.setFolioOtorgante("20210304");
    	
        Persona persona = new Persona();
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion("NGENIEROS MILITARES NO. 65");
        domicilio.setColoniaPoblacion("LOMAS DE SOTELO");
        domicilio.setDelegacionMunicipio("MIGUEL HIDALGO");
        domicilio.setCiudad("CIUDAD DE MÉXICO");
        domicilio.setEstado(CatalogoEstados.CDMX);
        domicilio.setCP("11200");
        domicilio.setPais(CatalogoPais.MX);
        persona.setApellidoPaterno("PRUEBA");
        persona.setApellidoMaterno("RUIZ");
        persona.setPrimerNombre("JUAN");
        persona.setSegundoNombre("PABLO");
        persona.setFechaNacimiento("1974-10-28");
        persona.setRFC("PURJ741028PL2");
        persona.setDomicilio(domicilio);
    	request.setPersona(persona);

        Respuesta response = api.getReporte(this.xApiKey, request);
        logger.info("Report: "+response.toString());
        
        Assert.assertTrue(response.getFolioConsulta() != null);
    }
    
    @Test
    public void getReporteFolio() throws Exception {
    	PeticionFolio request = new PeticionFolio();
 
    	request.setFolioOtorgante("20210301");
    	request.setFolioConsulta("12345678");
        
        Respuesta response = api.getReporteFolio(this.xApiKey, request);
        logger.info("Report Folio: "+response.toString());
        
        Assert.assertTrue(response.getFolioConsulta() != null);
    }
    
}
