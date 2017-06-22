LOWCOST AIRLINE
===============================

Stack:
Maven, Spring (IoC, Security, MVC), JPA(Hibernate)
Jackson, JUnit, Mockito, Tomcat, logback
Bootstrap, webjars, JSP
jQuery plugins (dataTables, autocomplete, datetimepicker, sweetalert, noty)

Web project based on roles:
- anonymous can search flights and register
- user can log in, book and purchase tickets, load his active and archived tickets
- admin has base functionality to manage flights, tickets and users

While developing I practiced working with Spring(Security,IOC,MVC),Hibernate,
jUnit, Mockito, REST approach, multitier architecture.
In application I got use of spring localization features.
Also was used org.springframework.scheduling.concurrent.ConcurrentTaskScheduler for
creating scheduled tasks to delete, but not purchased in particular time tickets.
I made my best to follow code convention and to apply clean code.