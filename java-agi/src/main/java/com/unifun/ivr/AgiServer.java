package com.unifun.ivr;


import org.asteriskjava.fastagi.ClassNameMappingStrategy;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.fastagi.MappingStrategy;
import org.asteriskjava.fastagi.StaticMappingStrategy;

import java.io.IOException;

public class AgiServer {
    public static void main(String[] args) throws IOException {
        DefaultAgiServer server = new DefaultAgiServer();

        StaticMappingStrategy mappingStrategy = new StaticMappingStrategy();

        mappingStrategy.setAgiScript(new IvrScript());

        server.setMappingStrategy(mappingStrategy);

        System.out.println("AGI Server started and listening for connections.");

        server.startup();
    }
}
