WebFX
=====
The purpose of this project is to investigate the capabilities of using JavaFX (FXML + JS + CSS) to build rich web pages, instead of using HTML. With the new Javascript engine, *Nashorn*, the performance of a JavaFX page in FXML and the controllers in JS will be much higher than it is today. Idea is to build an FX browser, a security layer, a navigation scheme where one FXML can tell the browser to go to another FXML and a protocol for server-side communication.

"WebFX allows me to run the JVM in Raspberry Pi forever and evolve my JavaFX application by modifying FXML and Javascript on remote webserver" - [Bruno Borges](https://twitter.com/brunoborges/status/463187071872823296)

Note
=====
It is forked version and it depends on Java ReStart project -- https://github.com/pjBooms/Java-ReStart.
So please clone it first, build with Maven (launch "mvn install" from the root of that project), and after that you should be able to build WebFX.
