# Client Packager

This module exists because the shaded class can not be depended upon by another module. Since the integration module needs to see both ui and restserver, the AppStarter to be shaded has to exist in its own module.
