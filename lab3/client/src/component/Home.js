import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import * as Constants from './Constants';

class Home extends Component {
    render() {
        return (
            <div>
                <Container fluid>
                    <Button color="link"><Link to={Constants.allAddressesPath}>Addresses</Link></Button>
                    <Button color="link"><Link to={Constants.allOfficesPath}>Offices</Link></Button>
                    <Button color="link"><Link to={Constants.allEmployeesPath}>Employees</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;