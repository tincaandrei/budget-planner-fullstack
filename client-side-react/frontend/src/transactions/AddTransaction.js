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
            categoryId: "",
            formErrors: {}
        };
    }

    handleChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    validateForm = () => {
        const errors = {};
        const { amount, description, date, categoryId } = this.state;

        if (!amount || parseFloat(amount) <= 0) {
            errors.amount = "Amount must be greater than 0";
        }

        if (!description || description.trim() === "") {
            errors.description = "Description is required";
        }

        if (!date) {
            errors.date = "Date is required";
        }

        if (!categoryId) {
            errors.categoryId = "Category is required";
        }

        return errors;
    };

    handleSubmit = e => {
        e.preventDefault();
        const errors = this.validateForm();

        if (Object.keys(errors).length > 0) {
            this.setState({ formErrors: errors });
            return;
        }

        const { amount, description, date, categoryId } = this.state;
        const { onSubmit } = this.props;

        onSubmit({
            amount: parseFloat(amount),
            description,
            date,
            categoryId: parseInt(categoryId, 10),
        });

        this.setState({
            amount: "",
            description: "",
            date: "",
            categoryId: "",
            formErrors: {}
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
                            />
                            {this.state.formErrors.amount && (
                                <div className="text-danger">{this.state.formErrors.amount}</div>
                            )}
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                type="text"
                                name="description"
                                value={this.state.description}
                                onChange={this.handleChange}
                            />
                            {this.state.formErrors.amount && (
                                <div className="text-danger">{this.state.formErrors.description}</div>
                            )}
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Date</Form.Label>
                            <Form.Control
                                type="date"
                                name="date"
                                value={this.state.date}
                                onChange={this.handleChange}
                            />
                            {this.state.formErrors.amount && (
                                <div className="text-danger">{this.state.formErrors.date}</div>
                            )}
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Category</Form.Label>
                            <Form.Select
                                name="categoryId"
                                value={this.state.categoryId}
                                onChange={this.handleChange}
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
                            {this.state.formErrors.categoryId && (
                                <div className="text-danger">{this.state.formErrors.categoryId}</div>
                            )}

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
