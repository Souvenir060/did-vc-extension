/*
 *  Copyright (c) 2025 Selenade. All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Selenade - initial API and implementation
 *
 */

package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class to bootstrap the Spring Boot application.
 */
@SpringBootApplication
public class Application {
    /**
     * Entry point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Get port from environment variable, default to 8080 if not set
        // This is necessary for cloud deployment platforms like Render.com
        String port = System.getenv("PORT");
        if (port != null) {
            System.setProperty("server.port", port);
        }
        SpringApplication.run(Application.class, args);
    }
}