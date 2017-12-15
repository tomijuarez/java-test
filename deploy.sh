#!/bin/bash

mvn -DskipTests=true package #genero el war obviando los tests.
cd #root.
cd "c:/glassfish5/bin" #voy a la carpeta de glassfish. Cambiar según la instalación local.
./asadmin start-domain domain1 #Start server domain.
./asadmin deploy --contextroot "/webapp" "c:/Users/Gandalf/IdeaProjects/webapp/target/webapp-1.0-SNAPSHOT.war" #deploy