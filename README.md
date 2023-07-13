# SmartBear interview project
### Author _Mateusz Podolak_

Main project goal was to create a functionality that will convert numerical time into spoken British representation. 
For example if an input would be: "10:00" the outcome should be: "ten o'clock".

This time converter functionality is available as a REST GET endpoint: _{base_url}/api/v1/time-converter/to-spoken_. 
It expects one query parameter as an input - __numericalTime__ in HH:MM format (24-hour clock).

I have created class with unit tests of TimeConverterService available in test package. 

To make that project more __production ready__ I have decided to add Basic Auth to main endpoints _(user/pass admin/admin)_.

I have also added some extra features:
- everytime time converter will be triggered an audit log will be saved to in memory DB, it is possible to check all created audit logs using additional endpoint _{base_url}/api/v1/audit-log/all_.
- swagger UI available at endpoint: _{base_url}/swagger-ui/index.html_
- h2-console available at endpoint: _{base_url}/h2-console_ _(user/pass admin/admin)_ datasource url: _jdbc:h2:mem:time_converter_db_
- spring actuator health check: _{base_url}/actuator/health_
- added github action to repository that after every push it will trigger build of an app and run all unit tests

This application is deployed using AWS Elastic Beanstalk under this address:

_http://smartbear-podolak.eu-north-1.elasticbeanstalk.com_

I also included swagger ui, actuator and h2-console there. 