import React from "react";
import axiosInstance from "../helper/axios";
import BudgetCard from "./BudgetCard";
import { Container, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";
import AddTransactionModal from "../transactions/AddTransaction";
import BudgetTable from "./BudgetTable";
import ExpenseChart from "./ExpenseChart";
//websockets improts

import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { type } from "@testing-library/user-event/dist/type";





class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      budgets: [],
      userName: localStorage.getItem("USER_NAME") || "",
      userId: localStorage.getItem("USER_ID"),
      incomeCategories: [],
      expenseCategories: [],

      selectedMonth: new Date().getMonth() + 1,
      selectedYear: new Date().getFullYear(),
      monthlyExpenses: [],
      monthlyIncomes: [],
      showChart: false,

    };
    this.stompClient = null;
  }

  componentDidMount() {
    const userId = localStorage.getItem("USER_ID");
    if (!userId) return;

    // 1) Budgets
    axiosInstance.get(`/budgets/user/${userId}`)
      .then(res => this.setState({ budgets: res.data }))
      .catch(err => console.error("Error loading budgets:", err));

    // 2) Categories
    axiosInstance.get("/categories")
      .then(res => {
        console.log("Received categories:", res.data);
        const all = res.data;
        const incomeCategories = all.filter(c => c.type.toLowerCase() === "income");
        const expenseCategories = all.filter(c => c.type.toLowerCase() === "expense");
        this.setState({ incomeCategories, expenseCategories }, () => {
          console.log("Income categories:", this.state.incomeCategories);
          console.log("Expense categories:", this.state.expenseCategories);
        });
      })
      .catch(err => console.error("Error loading categories:", err));


    // websocket implementation
    const socket = new SockJS("http://localhost:8080/socket");

    this.stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("WebSocket connected");
        this.stompClient.subscribe(`/topic/income/${userId}`, (msg) => {
          alert(msg.body);
        });
        this.stompClient.subscribe(`/topic/expense/${userId}`, (msg) => {
          console.log("RAW message content", msg.body)
          alert(msg.body);
        });
      },
      onStompError: (frame) => {
        console.error("STOMP error:", frame);
      }
    });

    this.stompClient.activate();
  }
  componentDidUpdate(prevProps, prevState) {

    //console.log("componentDidUpdate — previous:", prevState, " current:", this.state);
  }


  handleAddTransaction = (data, type) => {
    const budget = this.state.budgets[0];
    const url = type === "income" ? "/incomes" : "/expenses";

    const payload = {
      amount: data.amount,
      description: data.description,
      date: data.date,
      budgetId: budget.id,
      categoryId: data.categoryId
    };

    console.log("Sending payload:", payload);
    axiosInstance.post(url, payload)
      .then(res => {
        console.log("Saved OK", res);
        window.location.reload();
      })
      .catch(err => {
        console.error("Error adding transaction:", err);
        alert("Something went wrong.");
      });
  };

  handleLogout = () => {
    localStorage.removeItem("USER_ID");
    localStorage.removeItem("USER_NAME");

    window.location.href = "/login";
  }


  fetchMonthlyExpenses = () => {
    const { selectedMonth, selectedYear, userId } = this.state;

    axiosInstance.get(`/expenses/user/${userId}/month`, {
      params: {
        month: selectedMonth,
        year: selectedYear
      }
    })
      .then(res => {
        this.setState({ monthlyExpenses: res.data });
      })
      .catch(err => {
        console.error("Error loading monthly expenses:", err);
        alert("error loading expenses");
      });
  };

  fetchMonthlyIncomes = () => {
    const { selectedMonth, selectedYear, userId } = this.state;

    axiosInstance.get(`/incomes/user/${userId}/month`, {
      params: {
        month: selectedMonth,
        year: selectedYear
      }
    })
      .then(res => {
        this.setState({ monthlyIncomes: res.data });
      })
      .catch(err => {
        console.error("Error loading monthly incomes:", err);
        alert("error loading incomes.");
      });
  };



  fetchFile = async (type) => {
    const { userId } = this.state;
    const format = type.toLowerCase();

    try {
      const response = await axiosInstance.get(`/expenses/report`, {
        params: { userId, format },
        responseType: "blob",
      });

      const mimeType = format === "xml" ? "text/xml" : "text/csv";
      const fileName = `expenses_report.${format}`;

      const url = window.URL.createObjectURL(new Blob([response.data], { type: mimeType }));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", fileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error("Error fetching report", error);
      alert("Failed to download report.");
    }
  };

  render() {
    return (
      <>
        <Container className="mt-3">
          {/* Top-right profile button */}
          <Row className="justify-content-end">
            <Col xs="auto">
              <Link to="/profile" className="btn btn-outline-primary">
                View Profile
              </Link>
              <button className="btn btn-outline-danger" onClick={this.handleLogout}>
                Logout
              </button>
            </Col>
          </Row>

          {/* Title */}
          <Row className="mb-4">
            <Col>
              <h2 className="text-center">Your budget, {this.state.userName}</h2>
            </Col>
          </Row>

          {/* Budget + Table Section */}
          <Container className="mt-4">
            <Row className="g-4">
              {/* Left: Budget card + buttons */}
              <Col xs={12} md={4}>
                {this.state.budgets.map(budget => (
                  <BudgetCard key={budget.id} budget={budget} />
                ))}

                <div className="d-grid gap-2 mt-3">
                  <button
                    className="btn btn-success"
                    onClick={() => this.setState({ showIncomeModal: true })}
                  >
                    + Add Income
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => this.setState({ showExpenseModal: true })}
                  >
                    - Add Expense
                  </button>
                  <button className="btn btn-outline-success btn-sm" onClick={() => this.fetchFile("xml")}>
                    Export Expenses XML
                  </button>
                  <button className="btn btn-outline-success btn-sm" onClick={() => this.fetchFile("csv")}>
                    Export Expenses CSV
                  </button>
                </div>
              </Col>

              {/* Right: Transactions Table */}
              <Col xs={12} md={8}>
                <BudgetTable userId={this.state.userId}
                  incomeCategories={this.state.incomeCategories}
                  expenseCategories={this.state.expenseCategories}
                />
              </Col>
              <Col xs={12}>
                <div className="mt-5">
                  <h5>Check monthly transactions</h5>

                  {/* Select Month and Year */}
                  <div className="d-flex gap-3 align-items-center mb-3">
                    <select
                      value={this.state.selectedMonth}
                      onChange={(e) => this.setState({ selectedMonth: e.target.value })}
                      className="form-select w-auto"
                    >
                      {[...Array(12).keys()].map(m => (
                        <option key={m + 1} value={m + 1}>Month {m + 1}</option>
                      ))}
                    </select>

                    <select
                      value={this.state.selectedYear}
                      onChange={(e) => this.setState({ selectedYear: e.target.value })}
                      className="form-select w-auto"
                    >
                      {[2023, 2024, 2025].map(y => (
                        <option key={y} value={y}>{y}</option>
                      ))}
                    </select>

                    <button className="btn btn-outline-info btn-sm" onClick={() => this.fetchMonthlyExpenses()}>
                      Show expenses
                    </button>
                    <button
                      className="btn btn-outline-dark btn-sm"
                      onClick={() => this.setState({ showChart: true })}
                    >
                      Visualize Expenses
                    </button>

                    <button className="btn btn-outline-success btn-sm" onClick={() => this.fetchMonthlyIncomes()}>
                      Show incomes
                    </button>

                  </div>

                  {/* Display Results */}
                  {this.state.monthlyExpenses.length > 0 && (
                    <>
                      <h6>Expenses:</h6>
                      <ul>
                        {this.state.monthlyExpenses.map((e, i) => (
                          <li key={i}>{e.date} — {e.description} — {e.amount} RON</li>
                        ))}
                      </ul>
                    </>
                  )}

                  {this.state.monthlyIncomes.length > 0 && (
                    <>
                      <h6>Incomes:</h6>
                      <ul>
                        {this.state.monthlyIncomes.map((e, i) => (
                          <li key={i}>{e.date} — {e.description} — {e.amount} RON</li>
                        ))}
                      </ul>
                    </>
                  )}
                  {this.state.showChart && (
                    <ExpenseChart
                      userId={this.state.userId}
                      month={this.state.selectedMonth}
                      year={this.state.selectedYear}
                    />
                  )}

                </div>
              </Col>
            </Row>



          </Container>

          {/* Modals */}
          <AddTransactionModal
            show={this.state.showIncomeModal}
            type="income"
            categories={this.state.incomeCategories}      // pass incomes
            onClose={() => this.setState({ showIncomeModal: false })}
            onSubmit={(data) => this.handleAddTransaction(data, "income")}
          />
          <AddTransactionModal
            show={this.state.showExpenseModal}
            type="expense"
            categories={this.state.expenseCategories}     // pass expenses
            onClose={() => this.setState({ showExpenseModal: false })}
            onSubmit={(data) => this.handleAddTransaction(data, "expense")}
          />
        </Container>
      </>
    );
  }
}

export default Dashboard;
