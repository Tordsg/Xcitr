module restServer {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires exciter.core;
    requires exciter.json;
    exports restServer;
}
