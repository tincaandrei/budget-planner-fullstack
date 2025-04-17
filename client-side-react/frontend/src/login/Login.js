import React from "react";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";


const state = {
    username: '',
    password: '',
    loginSucces:{
        id : 0,
    }
};



// handle input on change function 
const handleSubmit = (event) => {
  
}
// on submit function


function Login() {
  return (
    <>
      <Navbar expand="lg" className="bg-body-tertiary">
        <Container fluid className="justify-content-center">
          <Navbar.Brand>Welcome, plese sign in</Navbar.Brand>
        </Container>
      </Navbar>
      <div className="d-flex justify-content-center align-items-center vh-100 py-5  ">
        <Container className="p-4 border rounded bg-light">
          <Form>
            <Form.Group className="mb-3" controlId="formUsername">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter username"
              ></Form.Control>
            </Form.Group>
            <Form.Group className="mb-3" controlId="formPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter password"
              ></Form.Control>
            </Form.Group>
          </Form>
          <div className="text-center">
            <Button as="input" variant="outline-dark" className="w-50" type="submit" value="Sign in" onClick={handleSubmit()}/>
          </div>
        </Container>
      </div>
    </>
  );
}

export default Login;
