import { useState } from 'react'
import './App.css'

function App() {
  // State management
  const [did, setDid] = useState('')
  const [role, setRole] = useState('professor')
  const [subject, setSubject] = useState('Data Access Authorization')
  const [vcId, setVcId] = useState('')
  const [verificationResult, setVerificationResult] = useState('')
  const [retrievedVc, setRetrievedVc] = useState('')
  const [didRole, setDidRole] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  // Backend API base URL - dynamically choose between local and cloud deployment
  const API_BASE_URL = process.env.NODE_ENV === 'production' 
    ? 'https://did-vc-demo-api.onrender.com/api/vc'  // Render.com部署的URL
    : 'http://localhost:8080/api/vc'                 // 本地开发URL

  // Create DID
  const createDid = async () => {
    setLoading(true)
    setError('')
    try {
      const response = await fetch(`${API_BASE_URL}/create/${role}`)
      const data = await response.text()
      setDid(data)
    } catch (err: any) {
      setError('Failed to create DID: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  // Issue VC
  const issueVc = async () => {
    if (!did) {
      setError('Please create a DID first')
      return
    }
    
    setLoading(true)
    setError('')
    try {
      const encodedDid = encodeURIComponent(did)
      const encodedSubject = encodeURIComponent(subject)
      const response = await fetch(`${API_BASE_URL}/issue/${encodedDid}/${encodedSubject}`)
      const data = await response.text()
      setVcId(data)
    } catch (err: any) {
      setError('Failed to issue VC: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  // Verify VC
  const verifyVc = async () => {
    if (!vcId) {
      setError('Please issue a VC first')
      return
    }
    
    setLoading(true)
    setError('')
    try {
      const encodedVcId = encodeURIComponent(vcId)
      const response = await fetch(`${API_BASE_URL}/verify/${encodedVcId}`)
      const data = await response.text()
      setVerificationResult(data === 'true' ? 'Valid' : 'Invalid')
    } catch (err: any) {
      setError('Failed to verify VC: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  // Get VC
  const getVc = async () => {
    if (!did || !vcId) {
      setError('Please create a DID and issue a VC first')
      return
    }
    
    setLoading(true)
    setError('')
    try {
      const encodedDid = encodeURIComponent(did)
      const encodedVcId = encodeURIComponent(vcId)
      const response = await fetch(`${API_BASE_URL}/get/${encodedDid}/${encodedVcId}`)
      const data = await response.text()
      setRetrievedVc(data)
    } catch (err: any) {
      setError('Failed to retrieve VC: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  // Get DID role
  const getDidRole = async () => {
    if (!did) {
      setError('Please create a DID first')
      return
    }
    
    setLoading(true)
    setError('')
    try {
      const encodedDid = encodeURIComponent(did)
      const response = await fetch(`${API_BASE_URL}/role/${encodedDid}`)
      const data = await response.text()
      setDidRole(data || 'Role not found')
    } catch (err: any) {
      setError('Failed to get role: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container">
      <h1>Data Space DID and VC Management System</h1>
      
      <div className="card">
        <h2>DID Creation and Role Assignment</h2>
        <div className="form-group">
          <label>Role:</label>
          <select value={role} onChange={(e) => setRole(e.target.value)}>
            <option value="professor">Professor</option>
            <option value="student">Student</option>
          </select>
        </div>
        <button onClick={createDid} disabled={loading}>Create DID</button>
        {did && (
          <div className="result">
            <h3>Created DID:</h3>
            <p className="id">{did}</p>
            <button onClick={getDidRole} disabled={loading}>Get Role</button>
            {didRole && <p>Role: <strong>{didRole}</strong></p>}
          </div>
        )}
      </div>

      <div className="card">
        <h2>Verifiable Credential (VC) Issuance</h2>
        <div className="form-group">
          <label>Subject:</label>
          <input 
            type="text" 
            value={subject} 
            onChange={(e) => setSubject(e.target.value)} 
          />
        </div>
        <button onClick={issueVc} disabled={!did || loading}>Issue VC</button>
        {vcId && (
          <div className="result">
            <h3>VC ID:</h3>
            <p className="id">{vcId}</p>
          </div>
        )}
      </div>

      <div className="card">
        <h2>VC Verification and Retrieval</h2>
        <div className="form-controls">
          <button onClick={verifyVc} disabled={!vcId || loading}>Verify VC</button>
          <button onClick={getVc} disabled={!did || !vcId || loading}>Get VC Content</button>
        </div>
        
        {verificationResult && (
          <div className="result">
            <h3>Verification Result:</h3>
            <p>VC Status: <strong>{verificationResult}</strong></p>
          </div>
        )}
        
        {retrievedVc && (
          <div className="result">
            <h3>VC Content:</h3>
            <pre className="vc-content">{retrievedVc}</pre>
          </div>
        )}
      </div>
      
      {error && <div className="error">{error}</div>}
      {loading && <div className="loading">Processing...</div>}
    </div>
  )
}

export default App
