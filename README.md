# DID and VC Extension Testbed

A demonstration system for Decentralized Identifiers (DIDs), Verifiable Credentials (VCs), and Role-Based Access Control (RBAC).

## Live Demo

The system is available for demonstration at:
- [Live Demo on GitHub Pages](https://souvenir060.github.io/did-vc-extension/)

## Features

1. **DID Creation**: Create Decentralized Identifiers with different roles (professor/student)
2. **VC Issuance**: Issue Verifiable Credentials linked to DIDs
3. **VC Verification**: Verify the validity of credentials
4. **RBAC**: Role-Based Access Control (professors can access VCs, students cannot)
5. **Multiple Operating Modes**:
   - Production mode: Connects to real backend on Render.com
   - Local mode: Connects to locally running backend
   - Mock mode: Runs without a backend, using simulated data (default on GitHub Pages)

## Using the Demo

When accessing the demo on GitHub Pages, the system automatically uses Mock Mode for demonstration purposes. This allows anyone to experience the full functionality without the need for a backend connection.

To test with a real backend:
1. Clone the repository
2. Start the backend server
3. Run the frontend app in development mode
4. Switch to "Local Development" in the API Mode selector

## Implementation Details

The system consists of:

- **Frontend**: React.js application with TypeScript
- **Backend**: Java Spring Boot API handling DID and VC operations
- **Mock Mode**: Client-side simulation of backend functionality

## Repository

The source code is available at:
[GitHub Repository](https://github.com/Souvenir060/did-vc-extension)

## Development

To run the project locally:

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Deploy to GitHub Pages
npm run deploy
``` 