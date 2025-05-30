import React from "react";
import axiosInstance from "../helper/axios";
import { Button, Container, Form, Navbar } from "react-bootstrap";

class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: ""
    };
  }

  handleInputChange = (e) => {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const { username, password } = this.state;

    if (!username || !password) {
      alert("Please enter both username and password");
      return;
    }

    axiosInstance.post("/login", {
      username: username,
      password: password
    })
      .then(res => {
        localStorage.setItem("USER_ID", res.data.id);
        localStorage.setItem("USER_NAME", res.data.username);
        window.location.href = "/dashboard";
      })
      .catch(err => {
        console.error("Login failed:", err);
        alert("Invalid username or password.");
      });
  }

  render() {
    return (
      <>
        <Navbar expand="lg" className="bg-body-tertiary">
          <Container fluid className="justify-content-center">
            <Navbar.Brand>Login</Navbar.Brand>
          </Container>
        </Navbar>

        <div className="d-flex justify-content-center align-items-center vh-100 py-5">
          <Container className="p-4 border rounded bg-light">
            <Form onSubmit={this.handleSubmit}>
              <Form.Group className="mb-3">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  name="username"
                  value={this.state.username}
                  onChange={this.handleInputChange}
                  placeholder="Enter username"
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleInputChange}
                  placeholder="Enter password"
                  required
                />
              </Form.Group>

              <div className="text-center">
                <Button variant="outline-dark" type="submit">Login</Button>
              </div>
            </Form>
          </Container>
        </div>
      </>
    );
  }
}

export default Login;
