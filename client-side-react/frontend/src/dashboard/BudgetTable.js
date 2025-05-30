import React from 'react';
import axiosInstance from '../helper/axios';

class BudgetTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      transactions: [],
      sortKey: 'date',
      showIncomes: true,
      showExpenses: true
    };
  }

  componentDidMount() {
    const userId = this.props.userId;

    axiosInstance.get(`budgets/user/${userId}`)
      .then(response => {
        const data = response.data;

        let combined = [];

        // Combine incomes and expenses from each budget
        const { incomeCategories, expenseCategories } = this.props;

        data.forEach(budget => {
          budget.incomes.forEach(income => {
            income.type = "INCOME";
            income.category = incomeCategories.find(c => c.id === income.categoryId); 
            combined.push(income);
          });

          budget.expenses.forEach(expense => {
            expense.type = "EXPENSE";
            expense.category = expenseCategories.find(c => c.id === expense.categoryId); 
            combined.push(expense);
          });
        });


        this.setState({ transactions: combined });
      })
      .catch(error => {
        console.log("Error fetching budgets:", error);
      });
  }

  handleSortChange = (event) => {
    this.setState({ sortKey: event.target.value });
  }

  sortTransactions(transactions) {
    const key = this.state.sortKey;

    return transactions.sort((a, b) => {
      if (key === 'amount') {
        return b.amount - a.amount;
      } else if (key === 'date') {
        return new Date(b.date) - new Date(a.date);
      }
      return 0;
    });
  }

  handleToggleIncome = () => {
    this.setState(prevState => ({ showIncomes: !prevState.showIncomes }));
  }

  handleToggleExpense = () => {
    this.setState(prevState => ({ showExpenses: !prevState.showExpenses }));
  }

  render() {
    const sortedTransactions = this.sortTransactions(this.state.transactions.slice());
    const filteredTransactions = sortedTransactions.filter(item => {
      if (item.type === 'INCOME' && this.state.showIncomes) return true;
      if (item.type === 'EXPENSE' && this.state.showExpenses) return true;
      return false;
    });
    return (
      <div>
        <h2>All Transactions</h2>

        <label>Sort by: </label>
        <select onChange={this.handleSortChange}>
          <option value="date">Most recent</option>
          <option value="amount">Amount</option>
        </select>

        <div style={{ marginTop: '15px' }}>
          <label>
            <input
              type="checkbox"
              checked={this.state.showIncomes}
              onChange={this.handleToggleIncome}
            />
            Show incomes
          </label>

          <label style={{ marginLeft: '20px' }}>
            <input
              type="checkbox"
              checked={this.state.showExpenses}
              onChange={this.handleToggleExpense}
            />
            Show expenses
          </label>
        </div>

        <table className="table table-hover table-bordered shadow-sm mt-3">
          <thead className="table-light">
            <tr>
              <th>Date</th>
              <th>Description</th>
              <th>Category</th>
              <th>Type</th>
              <th>Amount</th>
            </tr>
          </thead>
          <tbody>
            {filteredTransactions.map((item, index) => (
              <tr key={index}>
                <td>{item.date}</td>
                <td>{item.description}</td>
                <td>{item.category?.name || "N/A"}</td>
                <td>{item.type}</td>
                <td>{item.amount}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default BudgetTable;