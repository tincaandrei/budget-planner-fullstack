import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import Login from './login/Login';
import AddUser from './user/UserManagement';
import EditUserWrapper from './user/EditUserWrapper';
import Dashboard from './dashboard/Dashboard';
import UserProfile from './Profile/UserProfile';
import UserManagement from './user/UserManagement';
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/users/new" element={<AddUser />} />
        <Route path="/users" element={< UserManagement/>} />
        <Route path="/users/edit/:id" element={<EditUserWrapper />} />
        <Route path='/dashboard' element={<Dashboard/>}/>
        <Route path="/profile" element={<UserProfile />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;