package com.mukul.plugin;

import com.google.gson.Gson;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mojo(name = "generate-json", defaultPhase = LifecyclePhase.COMPILE)
public class ProvenanceMojo extends AbstractMojo {

    @Parameter
    private String[] envVariables;

    @Parameter(defaultValue = "${project.build.directory}/classes")
    private File outputDirectory;

    private static final String META_INF_DIR = "META-INF";
    private static final String JSON_FILENAME = "provenance.json";

    @Override
    public void execute() throws MojoExecutionException {
        if (envVariables == null || envVariables.length == 0) {
            getLog().warn("No environment variables specified. Skipping JSON generation.");
            return;
        }

        // Create a map of environment variables
        Map<String, String> envMap = new HashMap<>();
        for (String var : envVariables) {
            String value = System.getenv(var);
            if (value != null) {
                envMap.put(var, value);
            } else {
                getLog().warn("Environment variable " + var + " is not set.");
            }
        }

        // Check if any environment variables were found
        if (envMap.isEmpty()) {
            getLog().warn("No environment variables found to add to JSON.");
            return;
        }

        // Convert to JSON
        Gson gson = new Gson();
        String jsonContent = gson.toJson(envMap);

        // Define the META-INF output directory within target
        File metaInfDir = new File(outputDirectory, META_INF_DIR);
        if (!metaInfDir.exists() && !metaInfDir.mkdirs()) {
            throw new MojoExecutionException("Failed to create META-INF directory: " + metaInfDir);
        }

        // Define the output file path in META-INF
        File jsonFile = new File(metaInfDir, JSON_FILENAME);

        // Write JSON content to the file
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(jsonContent);
            getLog().info("Generated JSON file at " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            throw new MojoExecutionException("Error writing JSON file", e);
        }
    }
}