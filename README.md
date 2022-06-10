# fintechscore-simulacion-client-java [![GitHub Packages](https://img.shields.io/badge/Maven&nbsp;package-Last&nbsp;version-lemon)](https://github.com/orgs/APIHub-CdC/packages?repo_name=fintechscore-simulacion-client-java) 

Esta API simula la evaluación de riesgo cubriendo las necesidades de las FinTechs como los son: plazos más cortos que van de 1 día hasta 3 meses en promedio, montos de $3.5K promedio, renovaciones con mayor frecuencia, mayores tasas de interés y disponibilidad inmediata. <br/><img src='https://github.com/APIHub-CdC/imagenes-cdc/blob/master/circulo_de_credito-apihub.png' height='37' width='160'/><br/>

## Requisitos

1. Java >= 1.7
2. Maven >= 3.3

## Instalación

Para la instalación de las dependencias se deberá ejecutar el siguiente comando:

```shell
mvn install -Dmaven.test.skip=true
```

> **NOTA:** Este fragmento del comando *-Dmaven.test.skip=true* evitará que se lance la prueba unitaria.


## Guía de inicio

### Paso 1. Agregar el producto a la aplicación

Al iniciar sesión seguir los siguientes pasos:

 1. Dar clic en la sección "**Mis aplicaciones**".
 2. Seleccionar la aplicación.
 3. Ir a la pestaña de "**Editar '@tuApp**' ".
    <p align="center">
      <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/edit_applications.jpg" width="900">
    </p>
 4. Al abrirse la ventana emergente, seleccionar el producto.
 5. Dar clic en el botón "**Guardar App**":
    <p align="center">
      <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/selected_product.jpg" width="400">
    </p>

### Paso 2. Capturar los datos de la petición

Los siguientes datos a modificar se encuentran en ***src/test/java/com/cdc/apihub/mx/FintechScore/test/ApiTest.java***

Es importante contar con el setUp() que se encargará de inicializar la petición. Por tanto, se debe modificar la URL (**the_url**); y la API KEY (**your_api_key**), como se muestra en el siguiente fragmento de código:

```java
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
```

La petición se deberá modificar el siguiente fragmento de código con los datos correspondientes:

> **NOTA:** Para más ejemplos de simulación, consulte la colección de Postman.
### Método con datos del solicitante

```java
 @Test
    public void getReporteTest() throws Exception {
    	Peticion request = new Peticion();
    	request.setFolioOtorgante("20210304");
    	
        Persona persona = new Persona();
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion("INGENIEROS MILITARES NO. 65");
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
```
### Método con folios del solicitante

```java
 @Test
    public void getReporteFolio() throws Exception {
    	PeticionFolio request = new PeticionFolio();
 
    	request.setFolioOtorgante("20210301");
    	request.setFolioConsulta("12345678");
        
        Respuesta response = api.getReporteFolio(this.xApiKey, request);
        logger.info("Report Folio: "+response.toString());
        
        Assert.assertTrue(response.getFolioConsulta() != null);
    }
```
### Paso 3. Ejecutar la prueba unitaria

Teniendo los pasos anteriores ya solo falta ejecutar la prueba unitaria, con el siguiente comando:

```shell
mvn test -Dmaven.install.skip=true
```

---
[CONDICIONES DE USO, REPRODUCCIÓN Y DISTRIBUCIÓN](https://github.com/APIHub-CdC/licencias-cdc)

[1]: https://getcomposer.org/doc/00-intro.md#installation-linux-unix-macos
