import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import CustomerListComponent from "./component/CustomerListComponent";
import AppBarComponent from "./component/AppBarComponent";
import SearchComponent from "./component/SearchComponent";
import NewAccountComponent from "./component/NewAccountComponent";
import NewCustomerComponent from "./component/NewCustomerComponent";

ReactDOM.render(
  <BrowserRouter>
      <AppBarComponent/>
      <Routes>
          <Route exact path="/" element={<App/>} />
          <Route exact path="/list" element={<CustomerListComponent/>}/>
          <Route exact path="/search" element={<SearchComponent/>}/>
          <Route exact path="/newAccount" element={<NewAccountComponent/>}/>
          <Route exact path="/newCustomer" element={<NewCustomerComponent/>}/>
          <Route path="*" element={<App/>}/>
      </Routes>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
