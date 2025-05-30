// src/components/transactions/AddTransaction.jsx
import React from "react";
import { Modal, Button, Form } from "react-bootstrap";

class AddTransactionModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            amount: "",
            description: "",
            date: "",
            categoryId: ""
        };
    }

    handleChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    handleSubmit = e => {
        e.preventDefault();
        const { amount, description, date, categoryId } = this.state;
        const { onSubmit } = this.props;          //  make sure to grab onSubmit

        if (!amount || !description || !date || !categoryId) {
            return alert("Please fill in all fields.");
        }

        console.log("Submitting payload:", { amount, description, date, categoryId });

        onSubmit({
            amount: parseFloat(amount),
            description,
            date,
            categoryId: parseInt(categoryId, 10),
         
        });

        this.setState({                    // reset the form
            amount: "",
            description: "",
            date: "",
            categoryId: ""
        });
    };

    render() {
        const { show, onClose, type } = this.props;
        // give categories a default empty array
        const categories = this.props.categories || [];
        console.log("CATEGORIES from props--->", categories);
        const title = type === "income" ? "Add Income" : "Add Expense";

        return (
            <Modal show={show} onHide={onClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{title}</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>

                        <Form.Group className="mb-3">
                            <Form.Label>Amount</Form.Label>
                            <Form.Control
                                type="number"
                                name="amount"
                                value={this.state.amount}
                                onChange={this.handleChange}
                                required
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                type="text"
                                name="description"
                                value={this.state.description}
                                onChange={this.handleChange}
                                required
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Date</Form.Label>
                            <Form.Control
                                type="date"
                                name="date"
                                value={this.state.date}
                                onChange={this.handleChange}
                                required
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Category</Form.Label>
                            <Form.Select
                                name="categoryId"
                                value={this.state.categoryId}
                                onChange={this.handleChange}
                                required
                            >
                                <option value="">
                                    -- Choose a {type === "income" ? "income" : "expense"} category --
                                </option>
                                {categories.map(cat => (
                                    <option key={cat.id} value={cat.id}>
                                        {cat.name}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>

                        <div className="text-end">
                            <Button
                                variant="secondary"
                                onClick={onClose}
                                className="me-2"
                            >
                                Cancel
                            </Button>
                            <Button variant="primary" type="submit">
                                Save
                            </Button>
                        </div>

                    </Form>
                </Modal.Body>
            </Modal>
        );
    }
}

export default AddTransactionModal;
