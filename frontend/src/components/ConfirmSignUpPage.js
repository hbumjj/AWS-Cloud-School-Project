import React, { useState } from 'react';
import { confirmSignUp } from 'aws-amplify/auth';
import { useNavigate } from 'react-router-dom';

function ConfirmSignUpPage() {
  const [confirmationCode, setConfirmationCode] = useState('');
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    setConfirmationCode(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
        await confirmSignUp({
            username: email,
            confirmationCode: confirmationCode
        });
      alert('Account confirmed! You can now log in.');
      navigate('/logIn'); // Redirect to log in page or another page
    } catch (error) {
      setError(`Error confirming account: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Confirm Your Account</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <label htmlFor="confirmationCode">Confirmation Code:</label>
        <input
          type="text"
          id="confirmationCode"
          value={confirmationCode}
          onChange={handleInputChange}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Confirming...' : 'Confirm'}
        </button>
        {error && <p className="error">{error}</p>}
      </form>
    </div>
  );
}

export default ConfirmSignUpPage;
