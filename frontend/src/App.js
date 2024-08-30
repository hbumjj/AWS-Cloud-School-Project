import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AppLayoutPreview from './components/AppLayoutPreview';
import ConfirmSignUpPage from './components/ConfirmSignUpPage';
import EntryPage from './components/EntryPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<EntryPage />} />
        <Route path="/login" element={<EntryPage />} />
        <Route path="/confirm-sign-up" element={<ConfirmSignUpPage />} />
        <Route path="/home" element={<AppLayoutPreview />} />
      </Routes>
    </Router>
  );
}

export default App;
