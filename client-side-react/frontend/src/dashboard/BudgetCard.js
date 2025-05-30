import React from "react";
import { Card, ProgressBar } from "react-bootstrap";

class BudgetCard extends React.Component {
  render() {
    const { budget } = this.props;

    const incomeTotal = (budget.incomes || []).reduce((sum, i) => sum + i.amount, 0);
    const expenseTotal = (budget.expenses || []).reduce((sum, e) => sum + e.amount, 0);
    const balance = incomeTotal - expenseTotal;
    const percentUsed = incomeTotal > 0 ? Math.min((expenseTotal / incomeTotal) * 100, 100) : 0;

    // bar color
    const barVariant = balance < 0 ? "danger" : "primary";
    // bar label
    const labelText = balance < 0 ? "Over budget" : `${percentUsed.toFixed(1)}% used`
    return (
      <Card className="mb-3">
        <Card.Body>
          <Card.Title>Your budget</Card.Title>
          <Card.Text><strong>Income:</strong> {incomeTotal}</Card.Text>
          <Card.Text><strong>Expenses:</strong> {expenseTotal}</Card.Text>
          <Card.Text><strong>Balance:</strong> {balance}</Card.Text>
          <ProgressBar now={balance < 0 ? 100 : percentUsed} label={labelText} variant={barVariant} />
        </Card.Body>
      </Card>
    );
  }
}

export default BudgetCard;
