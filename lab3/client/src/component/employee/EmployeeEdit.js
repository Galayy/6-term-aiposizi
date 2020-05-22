import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import * as Constants from '../Constants';

class EmployeeEdit extends Component {

    emptyItem = {
        firstName: null,
        lastName: null,
        speciality: null
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
        if (response.status === 500) {
            this.setState({
                errorMessage: 'All fields should be filled. In case they are the problem is in server connection :('
            });
        } else {
            this.props.history.push(Constants.allEmployeesPath);
        }
        return response.json();
    }

    async componentDidMount() {
        console.log(this.props);
        const employee = await (await fetch(Constants.baseEmployeesPath + '/' + this.props.match.params.id))
            .json();
        this.setState({item: employee});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch(Constants.baseEmployeesPath + '/' + item.id, {
            method: Constants.putMethod,
            headers: Constants.jsonRequestHeaders,
            body: JSON.stringify(item),
        }).then(this.checkStatus);
    }

    render() {
        const {item} = this.state;
        const title = <h2>Edit Employee</h2>;
        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="firstName">First name</Label>
                        <Input type="text" name="firstName" id="firstName" value={item.firstName}
                               onChange={this.handleChange} autoComplete="firstName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="lastName">Last name</Label>
                        <Input type="text" name="lastName" id="lastName" value={item.lastName}
                               onChange={this.handleChange} autoComplete="lastName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="speciality">Speciality</Label>
                        <Input type="text" name="speciality" id="speciality" value={item.speciality}
                               onChange={this.handleChange} autoComplete="speciality"/>
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

export default withRouter(EmployeeEdit);