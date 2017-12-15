# Test técnico de Java

El presente da cuenta de la solución al test técnico de Java. En el mismo se utiliza Jax-RS para la implementación de la arquitectura REST, JPA + hsqldb In-Memory para la capa de persistencia, Maven para el control de dependencias y configuración de building y JUnit para los tests. El mismo se implementó utilizando la versión de 1.8 de Java sobre el IDE Intellij Idea 2017.3 Ultimate Edition. El deployment se realiza sobre el servidor Glassfish 5 y todas las dependencias necesarias están actualizadas en sus últimas versiones a la fecha 15/12/2017.

## Configuración
Se asume que el usuario ejecuta la aplicación desde Windows. En caso contrario la configuración es análoga pero con distintos directorios.
### Base de Datos hsqldb
En principio, se debe descargar hsqldb (http://hsqldb.org/) y descomprimirlo en la máquina, preferentemente en la raíz del disco en una carpeta llamada hsqldb. Posteriormente debe crearse un archivo de properties del servidor de la base de datos, el cual debe ser guardado dentro de la carpeta previamente descomprimida. El archivo de propiedades será llamado server.properties y tendrá el siguiente contenido:
```sh
server.database.0 = file:hsqldb/personasdb
server.dbname.0 = personasdb
```

Posteriormente se deben ejecutar los siguientes comandos:
```sh
> java -classpath lib/hsqldb.jar org.hsqldb.server.Server
> java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/personasdb --dbname.0 personasdb
```
nótese que el nombre del archivo contenedor y de la base de datos debe coincidir con el archivo de propiedades del servidor. Finalmente, se debe iniciar una instancia del servidor. Esto puede realizarse mediante interfaz gráfica mediante los siguientes comandos (sobre la raíz de hsqldb):

```sh
> cd bin
> runManagerSwing.bat
```

desde allí se debe configurar la base de datos como sigue:
 - type: HSQL Database Engine In-Memory
 - driver: org.hsqldb.jdbc.JDBCDriver
 - jdbc:hsqldb:hsql://localhost/personasdb

La configuración de persistencia de Java deberá contemplar estos valores (META-INF/persistence.xml).

### deployment
El servidor elegido es Glassfish 5. En principio es necesario crear el archivo war correspondiente a la aplicación web. En este contexto, debe agregarse en el archivo pom.xml el siguiente bloque:
```xml
<packaging>war</packaging>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <configuration>
                    <webXml>WEB-INF\web.xml</webXml>
                </configuration>
            </plugin>
        </plugins>
    </build> 
```

Para generar el empaquetado con Maven y el deployment efectivo se debe correr el script deploy.sh desde la terminal utilizando Cygwin. Para solucionar ciertos problemas recurrentes se listan a continuación una serie de comandos.
 - Si se está ejecutando el puerto 4848 (Glassfish Admin) por una salida abrupta previa o por alguna otra aplicación, usar el comando ```sh netstat -a -n -o | find "4848" ``` para saber qué pid está usando el puerto. Luego se debe matar el proceso según el pid con el flag /f para forzar al sistema a eliminarlo: ```taskkill /f /pid id_del_proceso ```.
 - Si se desplegó previamente un war y se desea quitar del server, se debe ejecutar el comando undeploy desde el contexto de los archivos binarios de glassfish (c:/glassfish5/bin) ejecutando el comando ```./asadmin.bat undeploy nombre_del_war ```. 

## Rutas

Las siguientes rutas son las únicas reconocidas en la aplicación.

| Método | Path | Acción
| ------ | ------ | ------ |
| POST | http://localhost:8080/webapp/personas/{userId} | Inserta un usuario nuevo con id {userId} si no existe. Si existe en la base de datos arroja una excepción. El resto de la información se adjunta en el body del request en formato JSON. Por ejemplo: ```{"nombre": "Ragnar", "apellido": "Lothbrock", "dni": "38670882", "edad":42}``` |
| GET | http://localhost:8080/webapp/personas |  Devuelve el listado de personas en formato JSON. |

## Contacto
Por cualquier queja o consulta, por favor contactar al siguiente e-mail: tomasjuarez.exa@gmail.com
