package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Models the YAML configuration file.
 */
public class ConfigurationFileReader {
    public static final String MINIMUM_KEY = "minimum";
    public static final String MAXIMUM_KEY = "maximum";
    public static final String ATTEMPTS_KEY = "attempts";
    private final String path;
    private int minimum;
    private int maximum;
    private int attempts;

    /**
     * Creates a ConfigurationFileReader instance with the given path.
     * 
     * @param path a path to the resource to load
     */
    public ConfigurationFileReader(final String path) {
        this.path = path;
    }

    /**
     * Parses the file into this class fields.
     * It should be called before calling any getters on this class.
     * 
     * @return {@code true} in case of success and {@code false} otherwise
     */
    public boolean parse() {
        try (var fs = ClassLoader.getSystemResourceAsStream(path)) {
            try (var reader = new BufferedReader(new InputStreamReader(fs, StandardCharsets.UTF_8))) {
                reader.readAllLines().forEach(l -> {
                    final String[] parts = l.split(":");
                    final String key = parts[0];
                    final int value = Integer.parseInt(parts[1].strip());
                    switch (key) {
                        case MINIMUM_KEY:
                            minimum = value;
                            break;
                        case MAXIMUM_KEY:
                            maximum = value;
                            break;
                        case ATTEMPTS_KEY:
                            attempts = value;
                            break;
                        default:
                            break;
                    }
                });
            } catch (final IOException e) {
                return false;
            }
        } catch (final IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the configuration value representing the minimum.
     * 
     * @return the minimum
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * Returns the configuration value representing the maximum.
     * 
     * @return the maximum
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * Returns the configuration value representing the attempts.
     * 
     * @return the attempts
     */
    public int getAttempts() {
        return attempts;
    }
}
