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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * REST controller for handling DID and VC operations.
 */
@RestController
@RequestMapping("/api/vc")
@CrossOrigin(origins = "*") // Enable CORS for frontend development
public class DidVcController {
    private final DidVcHandler handler = new DidVcHandler();

    /**
     * Creates a new DID based on the provided role.
     *
     * @param role the role for the DID (e.g., "professor" or "student")
     * @return the generated DID
     */
    @GetMapping("/create/{role}")
    public String createDid(@PathVariable String role) {
        return handler.createDid(role);
    }

    /**
     * Issues a Verifiable Credential (VC) for a given DID and subject.
     *
     * @param did the Decentralized Identifier
     * @param subject the subject of the credential
     * @return the VC ID or an error message if issuance fails
     */
    @GetMapping("/issue/{did}/{subject}")
    public String issueVc(@PathVariable String did, @PathVariable String subject) {
        try {
            return handler.issueVc(did, subject);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * Verifies if a Verifiable Credential (VC) exists.
     *
     * @param vcId the VC ID to verify
     * @return true if the VC exists, false otherwise
     */
    @GetMapping("/verify/{vcId}")
    public boolean verifyVc(@PathVariable String vcId) {
        return handler.verifyVc(vcId);
    }
    
    /**
     * Retrieves a Verifiable Credential (VC) by its ID, with role-based access control.
     *
     * @param callerDid the DID of the user requesting access
     * @param vcId the VC ID to retrieve
     * @return the VC as a string, or an error message if access is denied or VC not found
     */
    @GetMapping("/get/{callerDid}/{vcId}")
    public String getVc(@PathVariable String callerDid, @PathVariable String vcId) {
        return handler.getVc(callerDid, vcId);
    }
    
    /**
     * Gets the role of a DID.
     *
     * @param did the DID to query
     * @return the role of the DID, or null if not found
     */
    @GetMapping("/role/{did}")
    public String getRole(@PathVariable String did) {
        return handler.getRole(did);
    }
}