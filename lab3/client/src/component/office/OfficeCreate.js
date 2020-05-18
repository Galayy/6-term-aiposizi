import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import * as Constants from '../Constants';

class OfficeCreate extends Component {

    emptyItem = {
        addressId: '',
        companyName: '',
        errorMessage: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
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
                item: {
                    errorMessage: 'There is already an office on this address or company name '
                        + 'isn\'t unique in our universe :)'
                }
            });
        } else if (response.status === 404) {
            this.setState({item: {errorMessage: 'Address isn\'t found.'}});
        } else if (response.status >= 500) {
            this.setState({item: {errorMessage: 'Internal server error.'}});
        } else {
            this.props.history.push(Constants.allOfficesPath);
        }
        return response.json();
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch(Constants.baseOfficesPath + this.props.location.search + '&companyName=' + item.companyName,
            {
                method: Constants.postMethod,
                headers: Constants.jsonRequestHeaders
            }).then(this.checkStatus);
    }

    render() {
        const {item} = this.state;
        const title = <h2>Create Company</h2>;
        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="companyName">Company name</Label>
                        <Input type="text" name="companyName" id="companyName" value={item.companyName}
                               onChange={this.handleChange} autoComplete="companyName"/>
                    </FormGroup>
                    {this.state.item.errorMessage &&
                    <Label style={Constants.errorLabelStyle}> {this.state.item.errorMessage} </Label>}
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={Constants.allOfficesPath}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}

export default withRouter(OfficeCreate);