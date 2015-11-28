WebFX
=====
The purpose of this project is to investigate the capabilities of using JavaFX (FXML + JS + CSS) to build rich web pages, instead of using HTML. With the new Javascript engine, *Nashorn*, the performance of a JavaFX page in FXML and the controllers in JS will be much higher than it is today. Idea is to build an FX browser, a security layer, a navigation scheme where one FXML can tell the browser to go to another FXML and a protocol for server-side communication. 

### YouTube Demo Videos
 - [WebFX Overview and Demo on RaspberryPi](https://www.youtube.com/watch?v=bzmdkjnbFkI)
 - [WebFX browser inside WebFX demo](https://www.youtube.com/watch?v=2LUF7lgpKLg#t=11)

"WebFX allows me to run the JVM in Raspberry Pi forever and evolve my JavaFX application by modifying FXML and Javascript on remote webserver" - [Bruno Borges](https://twitter.com/brunoborges/status/463187071872823296)

Java ReStart integration
=====
It is forked version that introduces URL handlers concept
where two URL Handlers for ``java://`` and ``wfx://`` protocols are defined in
https://github.com/pjBooms/Java-ReStart project under javarestart-webfx module.
To enable the implementation you need to clone Java ReStart project, build it
(it depends on this fork, so you have to call
``mvn -install`` before building Java ReStart)
and launch the WebFX browser from javarestart-webfx module with the following VM properties:

``-Djava.protocol.handler.pkgs=javarestart.protocols -Dwebfx.url.handlers=javarestart.webfx.JavaURLHandler,javarestart.webfx.WFXURLHandler``

The integration allows you to reference remote classes from FXML pages
(originally only JS could be referenced from FXML) and launch Java ReStart applications from the WebFX browser
(https://github.com/pjBooms/webfx/tree/master/webfx-browser).
