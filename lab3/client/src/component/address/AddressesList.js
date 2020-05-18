import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import * as Constants from '../Constants';

class AddressesList extends Component {

    constructor(props) {
        super(props);
        this.state = {addresses: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    checkStatus = response => {
        if (response.status === 404) {
            this.setState({errorMessage: 'Address isn\'t found.'});
        } else if (response.status >= 500) {
            console.log("here");
            this.setState({errorMessage: 'Internal server error.'});
        }
        return response;
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch(Constants.allAddressesPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({addresses: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(Constants.baseAddressesPath + '/' + id, {
            method: Constants.deleteMethod,
            headers: Constants.jsonRequestHeaders
        }).then(this.checkStatus).then(() => {
            let updatedAddresses = [...this.state.addresses].filter(i => i.id !== id);
            this.setState({addresses: updatedAddresses});
        });
    }

    render() {
        const {addresses, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const addressesList = addresses.map(address => {
            return <tr key={address.id}>
                <td style={{whiteSpace: 'nowrap'}}>{address.country}</td>
                <td>{address.city}</td>
                <td>{address.street}</td>
                <td>{address.number}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={Constants.baseAddressesPath + '/' + address.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(address.id)}>Delete</Button>
                        <Button size="sm" color="primary" tag={Link} to={Constants.createOfficePath + address.id}>
                            Create office</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="secondary" tag={Link} to={'/'}>Back</Button>
                    </div>
                    <h3>Addresses</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Country</th>
                            <th width="20%">City</th>
                            <th>Street</th>
                            <th width="10%">Number</th>
                        </tr>
                        </thead>
                        <tbody>
                        {addressesList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={Constants.baseAddressesPath}>Create address</Button>
                    </div>
                </Container>
            </div>
        );
    }
}

export default AddressesList;