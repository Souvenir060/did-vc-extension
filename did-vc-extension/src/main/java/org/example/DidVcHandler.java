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

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * A handler for managing Decentralized Identifiers (DIDs) and Verifiable Credentials (VCs).
 */
public class DidVcHandler {
    private Map<String, String> didStore = new HashMap<>();
    private Map<String, String> vcStore = new HashMap<>();
    private Map<String, String> roleStore = new HashMap<>();
    private Random random = new Random();

    /**
     * Creates a new Decentralized Identifier (DID) with an optional role.
     *
     * @param role the role to assign (e.g., "professor" or "student"). If null, assigns a random role.
     * @return the generated DID as a string
     */
    public String createDid(String role) {
        String did = "did:example:" + UUID.randomUUID().toString();
        didStore.put(did, "active");

        String assignedRole = (role != null) ? role : (random.nextBoolean() ? "professor" : "student");
        roleStore.put(did, assignedRole);
        System.out.println("Assigned role to DID " + did + ": " + assignedRole);

        return did;
    }

    /**
     * Creates a new Decentralized Identifier (DID) with a random role.
     *
     * @return the generated DID as a string
     */
    public String createDid() {
        return createDid(null);
    }

    /**
     * Issues a Verifiable Credential (VC) for a given DID and subject.
     *
     * @param did the Decentralized Identifier
     * @param subject the subject of the credential
     * @return the VC ID
     * @throws Exception if an error occurs during issuance
     */
    public String issueVc(String did, String subject) throws Exception {
        if (!didStore.containsKey(did)) {
            throw new IllegalArgumentException("DID " + did + " does not exist.");
        }

        RSAKey rsaKey = new RSAKeyGenerator(2048)
                .keyID(UUID.randomUUID().toString())
                .generate();
        JWSSigner signer = new RSASSASigner(rsaKey);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", subject);
        claims.put("iss", "did:example:issuer");
        claims.put("vc", Map.of("credentialSubject", Map.of("id", did)));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("sub", subject)
                .issuer("did:example:issuer")
                .claim("vc", Map.of("credentialSubject", Map.of("id", did)))
                .build();

        SignedJWT signedJwt = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256),
                claimsSet
        );

        signedJwt.sign(signer);

        String vc = signedJwt.serialize();
        String vcId = "vc:" + UUID.randomUUID().toString();
        vcStore.put(vcId, vc);
        return vcId;
    }

    /**
     * Verifies if a Verifiable Credential (VC) exists.
     *
     * @param vcId the VC ID to verify
     * @return true if the VC exists, false otherwise
     */
    public boolean verifyVc(String vcId) {
        return vcStore.containsKey(vcId);
    }

    /**
     * Retrieves a Verifiable Credential (VC) by its ID, with role-based access control.
     *
     * @param callerDid the DID of the user requesting access
     * @param vcId the VC ID to retrieve
     * @return the VC as a string, or an error message if access is denied or VC not found
     */
    public String getVc(String callerDid, String vcId) {
        String role = roleStore.get(callerDid);
        if (role == null) {
            return "Access denied: Unknown DID " + callerDid;
        }

        if (!"professor".equals(role)) {
            return "Access denied: Role " + role + " is not authorized to access data.";
        }

        return vcStore.getOrDefault(vcId, "VC not found");
    }

    /**
     * Gets the role of a DID.
     *
     * @param did the DID to query
     * @return the role of the DID, or null if not found
     */
    public String getRole(String did) {
        return roleStore.get(did);
    }
}