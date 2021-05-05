# rcc-pm-simulacion-client-java [![GitHub Packages](https://img.shields.io/badge/Maven&nbsp;package-Last&nbsp;version-lemon)](https://github.com/orgs/APIHub-CdC/packages?repo_name=rcc-pm-simulacion-client-java) 

Esta API simula el Reporte de Crédito Consolidado de Persona Moral.

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

Los siguientes datos a modificar se encuentran en ***src/test/java/com/cdc/apihub/mx/RCCPM/test/ApiTest.java***

Es importante contar con el setUp() que se encargará de inicializar la petición. Por tanto, se debe modificar la URL (**urlApi**); y la API KEY (**xApiKey**), como se muestra en el siguiente fragmento de código:

```java
@Before()
public void setUp() {
    this.xApiKey = "your_api_key";
    this.apiClient = api.getApiClient();
    this.apiClient.setBasePath("the_url");
}
```

La petición se deberá modificar el siguiente fragmento de código con los datos correspondientes:

> **NOTA:** Para más ejemplos de simulación, consulte la colección de Postman.

```java
@Test
public void getReporteCreditoPMTest() throws ApiException {

    PersonaPeticion request = new PersonaPeticion();
    Persona persona = new Persona();
    PersonaDomicilio domicilio = new PersonaDomicilio();
    
    Integer estatusOK = 200;
    Integer estatusNoContent = 204;
    
    try {
        
        domicilio.setDireccion("AV. PASEO DE LA REFORMA 01");
        domicilio.setColoniaPoblacion("GUERRERO");
        domicilio.setDelegacionMunicipio("CUAUHTEMOC");
        domicilio.setCiudad("CIUDAD DE MÉXICO");
        domicilio.setEstado(CatalogoEstados.DF);
        domicilio.setCP("68370");
        domicilio.setPais(CatalogoPais.MX);

        persona.setRFC("EDC930121E01");
        persona.setNombre("RESTAURANTE SA DE CV");
        persona.setDomicilio(domicilio);

        request.setFolioOtorgante("1000001");
        request.setPersona(persona);
        
        ApiResponse<?> response = api.getgenericReporteCreditoPM(xApiKey, request);

        Assert.assertTrue(estatusOK.equals(response.getStatusCode()));
        
        if(estatusOK.equals(response.getStatusCode())) {
            ReporteRespuesta responseOK = (ReporteRespuesta) response.getData();
            logger.info("RCC-PM Test: "+responseOK.toString());
        }
        
    }catch (ApiException e) {
        if(!estatusNoContent.equals(e.getCode())) {
            logger.info("Error getReporteCreditoPMTest: "+e.getResponseBody());
        }
        Assert.assertTrue(estatusOK.equals(e.getCode()));
    }        
}
```

### Paso 3. Ejecutar la prueba unitaria

Teniendo los pasos anteriores ya solo falta ejecutar la prueba unitaria, con el siguiente comando:

```shell
mvn test -Dmaven.install.skip=true
```
