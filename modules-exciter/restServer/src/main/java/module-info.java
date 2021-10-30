module restServer {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires transitive exciter.core;
    requires transitive exciter.json;
    requires transitive exciter.user;
    exports restServer;
}
