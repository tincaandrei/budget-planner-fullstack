// src/user/EditUserClass.js
import React from "react";
import axiosInstance from "../helper/axios";
import { Container, Form, Button } from "react-bootstrap";

class EditUser extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      email: ""
    };
  }

  componentDidMount() {
    const userId = this.props.userId;
    axiosInstance.get(`/users/${userId}`).then(res => {
      const { name, email } = res.data;
      this.setState({ name, email });
    });
  }

  handleInput = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const userId = this.props.userId;
    axiosInstance.put(`/users/${userId}`, this.state)
      .then(() => window.location.href = "/users")
      .catch(err => console.error("Update failed", err));
  }

  render() {
    return (
      <Container className="mt-4">
        <h2>Edit User</h2>
        <Form onSubmit={this.handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Name</Form.Label>
            <Form.Control
              name="name"
              value={this.state.name}
              onChange={this.handleInput}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              name="email"
              value={this.state.email}
              onChange={this.handleInput}
              required
            />
          </Form.Group>

          <Button variant="primary" type="submit">Update</Button>
        </Form>
      </Container>
    );
  }
}

export default EditUser;
