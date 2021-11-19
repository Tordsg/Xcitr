open module restserver {
    requires okhttp3;
    requires spring.core;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires transitive exciter.core;
    requires transitive exciter.json;
    requires exciter.user;
    exports restserver;
}
