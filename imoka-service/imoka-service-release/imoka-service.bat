:: IMOKA-SERVICE
:: 
:: Starter of service. File should be in same directory as jar file.
:: This batch file only start the jar file
echo "JAVA_HOME should be define with version 17"
java -version
echo "Now start application"

java -jar imoka-service-jar-with-dependencies.jar

