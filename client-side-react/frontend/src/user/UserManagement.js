import React from "react";
import axiosInstance from "../helper/axios";
import { Button, Container, Form, Table } from "react-bootstrap";

class UserManagement extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      email: "",
      password: "",
      users: []
    };
  }

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers = () => {
    axiosInstance.get("/users")
      .then(res => {
        this.setState({ users: res.data });
      })
      .catch(err => {
        console.error("Error fetching users:", err);
      });
  }

  handleInput = (e) => {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const { username, email, password } = this.state;

    if (!username.trim() || !email.trim() || !password.trim()) {
      alert("Please fill in all fields.");
      return;
    }

    axiosInstance.post("/users", {
      username,
      email,
      password
    })
    .then(() => {
      this.setState({ username: "", email: "", password: "" });
      this.fetchUsers();
    })
    .catch(err => {
      console.error("Error adding user:", err);
    });
  }

  handleDelete = (userId) => {
    if (!userId) {
      console.warn("No user ID provided for deletion.");
      return;
    }

    if (!window.confirm("Are you sure you want to delete this user?")) return;

    axiosInstance.delete(`/users/${userId}`)
      .then(() => this.fetchUsers())
      .catch(err => {
        console.error("Error deleting user:", err);
      });
  }

  render() {
    return (
      <Container className="mt-4">
        <h2 className="mb-4">User Management</h2>
        <Form onSubmit={this.handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Username</Form.Label>
            <Form.Control
              name="username"
              value={this.state.username}
              onChange={this.handleInput}
              placeholder="Enter username"
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              name="email"
              value={this.state.email}
              onChange={this.handleInput}
              placeholder="Enter email"
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="text"
              name="password"
              value={this.state.password}
              onChange={this.handleInput}
              placeholder="Enter password"
              required
            />
          </Form.Group>

          <Button variant="success" type="submit">Add User</Button>
        </Form>

        <hr className="my-4" />

        <h4>All Users</h4>
        <Table bordered hover responsive>
          <thead className="table-light">
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Password</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {this.state.users
              .filter(user => user && user.id != null)
              .map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.username || <i className="text-muted">[No username]</i>}</td>
                  <td>{user.email || <i className="text-muted">[No email]</i>}</td>
                  <td>{user.password || <i className="text-muted">[No password]</i>}</td>
                  <td>
                    <Button
                      variant="danger"
                      size="sm"
                      onClick={() => this.handleDelete(user.id)}
                    >
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Container>
    );
  }
}

export default UserManagement;
