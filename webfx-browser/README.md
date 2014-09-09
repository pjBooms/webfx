WebFX
=====
The purpose of this project is to investigate the capabilities of using JavaFX (FXML + JS + CSS) to build rich web pages, instead of using HTML.
With the new Javascript engine, *Nashorn*, the performance of a JavaFX page in FXML and the controllers in JS will be much higher than it is today.
Idea is to build an FX browser, a security layer, a navigation scheme where one FXML can tell the browser to go to another FXML and a protocol for server-side communication.

Running the WebFX browser
=====
1. Get the code
2. Open on NetBeans
3. Click 'Run'
4. Click 'Home' button

This URL is a pure, static HTML rendered by WebView, with links to sample applications that are pure FXML+CSS+Javascript.
You may also want to go to a HTML5 website, such as this one from Mozilla HTML5 Showcase:

- http://html5demos.com/canvas

Resource Bundles
=====
Supports loading resource bundles from the Web Server hosting the FXML pages. Convetion is having the .properties with the same name as the FXML page.
*Example*
- http://www.mysite.com/login.fxml
- http://www.mysite.com/login.properties

Developer can also offer language/country specifics, i.e. login_pt_BR.properties

Navigation Scheme
=====
The developer can setup his application to navigate between FXML pages using Javascript. The WebFX injects a NavigationContext to the script context where user can do things such as: 
```javascript
webfx.navigation.goTo("../otherPath/anotherScreen.fxml");
```

Security Layer (planned)
=====
The security layer must provide a sandbox on each tab, to run JavaFX pages. The sandbox must ensure that:
- unsecure code will be run (i.e. local access to files, System.exit, network, etc)
- dialogs/windows can't be created, unless the user gives permition
- access to parent objects (the Tab object, for example)
- provide management and control for long running process, memory consumption, etc.

FX Protocol (optional)
=====
There should be an specific protocol to allow server-side communication. It is already possible though, to use HTTP.

Java ReStart integration
=====
It is forked version and it depends on Java ReStart project -- https://github.com/pjBooms/Java-ReStart.
The integration allows you to reference remote classes from FXML pages
(originally only JS could be referenced from FXML) and launch Java ReStart applications from the WebFX browser.
You may play with this with two demos from the home page:
  1. "WebFX Demo" link -- opens FXML page that reference other FXML pages
     (all but "Login Demo" reference remote java classes). One of the examples is WebFX browser itself.
  2. "Java ReStart Demo" link opens FXML page that runs several Java applications located remotely.

This Java ReStart <-> WebFX integration allows you to write applications the same way as web applications but use FXML instead of HTML
and any programming language that is available on top of Java platform from Java, Scala to JavaScript, JRuby, etc., and without compromising performance or
to-JS-translated-that-I-do-not-know-how-to-work-and-why-it-does-not-work-when-it-does-not-work issues.



