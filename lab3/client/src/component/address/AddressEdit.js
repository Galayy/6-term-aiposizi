import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import * as Constants from '../Constants';

class AddressEdit extends Component {

    checkStatus = response => {
        if (response.status === 500) {
            this.setState({
                errorMessage: 'All fields should be filled. In case they are the problem is in server connection :('
            });
        } else {
            this.props.history.push(Constants.allAddressesPath);
        }
        return response.json();
    }

    constructor(props) {
        super(props);
        this.state = {
            item: Constants.emptyAddress, errorMessage: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const address = await (await fetch(Constants.baseAddressesPath + '/' + this.props.match.params.id))
                .json();
            this.setState({item: address});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch(Constants.baseAddressesPath + '/' + item.id, {
            method: Constants.putMethod,
            headers: Constants.jsonRequestHeaders,
            body: JSON.stringify(item),
        }).then(this.checkStatus);
    }

    render() {
        const {item} = this.state;
        const title = <h2>Edit Address</h2>;

        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="country">Country</Label>
                        <Input type="text" name="country" id="country" value={item.country}
                               onChange={this.handleChange} autoComplete="country"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="city">City</Label>
                        <Input type="text" name="city" id="city" value={item.city}
                               onChange={this.handleChange} autoComplete="city"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="street">Street</Label>
                        <Input type="text" name="street" id="street" value={item.street}
                               onChange={this.handleChange} autoComplete="street"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="number">Number</Label>
                        <Input type="number" name="number" id="number" value={item.number}
                               onChange={this.handleChange} autoComplete="number"/>
                    </FormGroup>
                    {this.state.errorMessage &&
                    <Label style={Constants.errorLabelStyle}> {this.state.errorMessage} </Label>}
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={Constants.allAddressesPath}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(AddressEdit);