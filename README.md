Application is made in a library form. 

Example usage: 
```
var directory = "/tmp/com/nvt/";
var reportGenerator = new WeatherReportGeneratorFactory().getReportGenerator();
var reportResponse = reportGenerator.generateReport(new WeatherReportRequest(5, 25, directory, directory));
```

To run application you need to have installed Java 14 and Maven 

To run tests:
```
mvn test
```

You can see simple usage example in ApplicationStarter
