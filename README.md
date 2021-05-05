# fintechscore-simulacion-client-java [![GitHub Packages](https://img.shields.io/badge/Maven&nbsp;package-Last&nbsp;version-lemon)](https://github.com/orgs/APIHub-CdC/packages?repo_name=fintechscore-simulacion-client-java) 

Esta API simula la evaluación de riesgo cubriendo las necesidades de las FinTechs como los son: plazos más cortos que van de 1 día hasta 3 meses en promedio, montos de $3.5K promedio, renovaciones con mayor frecuencia, mayores tasas de interés y disponibilidad inmediata.

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
```

### Paso 3. Ejecutar la prueba unitaria

Teniendo los pasos anteriores ya solo falta ejecutar la prueba unitaria, con el siguiente comando:

```shell
mvn test -Dmaven.install.skip=true
```

---
[TERMS AND CONDITIONS](https://github.com/APIHub-CdC/licencias-cdc)
