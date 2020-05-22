import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import * as Constants from '../Constants';

class EmployeePlace extends Component {

    emptyItem = {
        companyName: null,
        roomNumber: null,
        placeNumber: null
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem, errorMessage: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    checkStatus = response => {
        if (response.status === 400) {
            this.setState({
                errorMessage: 'Make sure the given place and room numbers exist in pointed company :)'
            });
        } else if (response.status >= 500) {
            this.setState({errorMessage: 'Internal server error.'});
        } else {
            this.props.history.push(Constants.allEmployeesPath);
        }
        return response;
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        console.log(item);
        await fetch(Constants.baseEmployeesPath + '/' + this.props.match.params.id + '/place', {
            method: Constants.putMethod,
            headers: Constants.jsonRequestHeaders,
            body: JSON.stringify(item),
        }).then(this.checkStatus);
    }

    render() {
        const {item} = this.state;
        const title = <h2>Place Employee</h2>;
        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="companyName">Company name</Label>
                        <Input type="text" name="companyName" id="companyName" value={item.companyName}
                               onChange={this.handleChange} autoComplete="companyName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="roomNumber">Room number</Label>
                        <Input type="number" name="roomNumber" id="roomNumber" value={item.roomNumber}
                               onChange={this.handleChange} autoComplete="roomNumber"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="placeNumber">Place number</Label>
                        <Input type="number" name="placeNumber" id="placeNumber" value={item.placeNumber}
                               onChange={this.handleChange} autoComplete="placeNumber"/>
                    </FormGroup>
                    {this.state.errorMessage &&
                    <Label style={Constants.errorLabelStyle}> {this.state.errorMessage} </Label>}
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={Constants.allEmployeesPath}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}

export default withRouter(EmployeePlace);