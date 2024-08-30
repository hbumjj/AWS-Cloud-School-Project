// src/pages/EntryPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/login.css';
import { signUp, signIn, signOut, resetPassword, fetchAuthSession } from 'aws-amplify/auth';


function EntryPage() {
  const [currentView, setCurrentView] = useState("logIn");
  const [formData, setFormData] = useState({ nickname: '', email: '', password: '' }); // Changed 
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const changeView = (view) => {
    setCurrentView(view);
    setError('');
  };

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (currentView === "signUp") {
        await signUp({
          username: formData.email,
          password: formData.password,
          options: {
            userAttributes: {
              email: formData.email,
              nickname: formData.nickname,
            },
          }
        });
        alert('Sign up successful! Please confirm your email.');
        // On successful sign-up, redirect to confirmation page
        navigate('/confirm-sign-up');
      } else if (currentView === "logIn") {
        await signOut();
        const user = await signIn({
          username: formData.username,
          password: formData.password,
        });
        console.log(user);
        
        // console.log("username", username);
        // console.log("user id", userId);
        // console.log("sign-in details", signInDetails);
        const session = await fetchAuthSession();
        //console.log("id token", session.tokens.idToken)
        //console.log("access token", session.tokens.accessToken)
        const token = session.tokens.idToken.sub;
        
        const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
        // Send token to backend
        const response = await fetch(API_BASE_URL+'/api/check-authentication', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`, // Send token in Authorization header
            },
            body: JSON.stringify({
                email: formData.username,
            }),
        });

        if (response.ok) {
            console.log('Token stored successfully');
            navigate('/home');
        } else {
            throw new Error('Failed to store token');
        }
    } else if (currentView === "PWReset") {
        await resetPassword(formData.email);
        alert('Password reset link sent.');
        changeView("logIn");
      }
    } catch (error) {
      setError(`Operation failed: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  const renderView = () => {
    switch (currentView) {
      case "signUp":
        return (
          <form onSubmit={handleSubmit}>
            <h2>Sign Up!</h2>
            <fieldset>
              <legend>Create Account</legend>
              <ul>
                <li>
                  <label htmlFor="nickname">nickname:</label>
                  <input type="text" id="nickname" value={formData.nickname} onChange={handleInputChange} required />
                </li>
                <li>
                  <label htmlFor="email">Email:</label>
                  <input type="email" id="email" value={formData.email} onChange={handleInputChange} required />
                </li>
                <li>
                  <label htmlFor="password">Password:</label>
                  <input type="password" id="password" value={formData.password} onChange={handleInputChange} required />
                </li>
              </ul>
            </fieldset>
            <button type="submit" disabled={loading}>{loading ? 'Submitting...' : 'Submit'}</button>
            <button type="button" onClick={() => changeView("logIn")} disabled={loading}>
              Have an Account?
            </button>
            {error && <p className="error">{error}</p>}
          </form>
        );
      case "logIn":
        return (
          <form onSubmit={handleSubmit}>
            <h2>Welcome Back!</h2>
            <fieldset>
              <legend>Log In</legend>
              <ul>
                <li>
                  <label htmlFor="username">email:</label> 
                  <input type="text" id="username" value={formData.username} onChange={handleInputChange} required />
                </li>
                <li>
                  <label htmlFor="password">Password:</label>
                  <input type="password" id="password" value={formData.password} onChange={handleInputChange} required />
                </li>
                <li>
                  <a onClick={() => changeView("PWReset")} href="#">
                    Forgot Password?
                  </a>
                </li>
              </ul>
            </fieldset>
            <button type="submit" disabled={loading}>{loading ? 'Logging in...' : 'Login'}</button>
            <button type="button" onClick={() => changeView("signUp")} disabled={loading}>
              Create an Account
            </button>
            {error && <p className="error">{error}</p>}
          </form>
        );
      case "PWReset":
        return (
          <form onSubmit={handleSubmit}>
            <h2>Reset Password</h2>
            <fieldset>
              <legend>Password Reset</legend>
              <ul>
                <li>
                  <em>A reset link will be sent to your inbox!</em>
                </li>
                <li>
                  <label htmlFor="email">Email:</label>
                  <input type="email" id="email" value={formData.email} onChange={handleInputChange} required />
                </li>
              </ul>
            </fieldset>
            <button type="submit" disabled={loading}>{loading ? 'Sending...' : 'Send Reset Link'}</button>
            <button type="button" onClick={() => changeView("logIn")} disabled={loading}>
              Go Back
            </button>
            {error && <p className="error">{error}</p>}
          </form>
        );
      default:
        return null;
    }
  };

  return (
    <section id="entry-page">
      {renderView()}
    </section>
  );
}

export default EntryPage;
