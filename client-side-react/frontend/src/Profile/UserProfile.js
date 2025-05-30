import React from 'react';
import axiosInstance from '../helper/axios';
import { Container, Form, Button, Card } from 'react-bootstrap';

class UserProfile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user: null,
      name: '',
      email: '',
      password: ''
    };
  }

  componentDidMount() {
    const userId = localStorage.getItem("USER_ID");
    axiosInstance.get(`/users/${userId}`)
      .then(res => {
        this.setState({
          user: res.data,
          name: res.data.name,
          email: res.data.email
        });
      });
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  handleSave = () => {
    const userId = localStorage.getItem("USER_ID");
    const { name, email, password } = this.state;

    axiosInstance.put(`/users/update`, {
      name,
      email,
      password
    },
      {
        params: {
          id: userId
        }
      }
    ).then(() => {
      localStorage.setItem("USER_NAME", name);
      alert("Profile succesfuly updated!")
    });
  }

  render() {
    return (
      <Container className="mt-5 d-flex justify-content-center">
        <Card style={{ width: '100%', maxWidth: '500px' }}>
          <Card.Body>
            <h3 className="mb-4 text-center">Profil utilizator</h3>
            <Form>
              <Form.Group className="mb-3" controlId="formName">
                <Form.Label>Nume</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={this.state.name}
                  onChange={this.handleChange}
                  placeholder="Introduceți numele"
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="formEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  value={this.state.email}
                  onChange={this.handleChange}
                  placeholder="Introduceți emailul"
                />
              </Form.Group>

              <Form.Group className="mb-4" controlId="formPassword">
                <Form.Label>Parolă nouă</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleChange}
                  placeholder="Lăsați gol dacă nu doriți să schimbați"
                />
              </Form.Group>

              <div className="d-grid">
                <Button variant="primary" onClick={this.handleSave}>
                  Salvează modificările
                </Button>
              </div>
            </Form>
          </Card.Body>
        </Card>
      </Container>
    );
  }
}

export default UserProfile;
