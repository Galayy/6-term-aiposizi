import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import * as Constants from '../Constants';

class OfficesList extends Component {

    constructor(props) {
        super(props);
        this.state = {offices: [], isLoading: true, errorMessage: ''};
        this.remove = this.remove.bind(this);
    }

    checkStatus = response => {
        if (response.status === 404) {
            this.setState({errorMessage: 'Office isn\'t found.'});
        } else if (response.status >= 500) {
            console.log("here");
            this.setState({errorMessage: 'Internal server error.'});
        }
        return response;
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch(Constants.allOfficesPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({offices: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(Constants.baseOfficesPath + '/' + id, {
            method: Constants.deleteMethod,
            headers: Constants.jsonRequestHeaders
        }).then(this.checkStatus).then(() => {
            let updatedOffices = [...this.state.offices].filter(i => i.id !== id);
            this.setState({offices: updatedOffices});
        });
    }

    render() {
        const {offices, isLoading, errorMessage} = this.state;

        if (errorMessage) {
            return <h2> {errorMessage} </h2>;
        } else if (isLoading) {
            return <p>Loading...</p>;
        }

        const officesList = offices.map(office => {
            return <tr key={office.id}>
                <td style={{whiteSpace: 'nowrap'}}>{office.companyName}</td>
                <td>{office.roomsNumber}</td>
                <td>{office.address}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="danger" onClick={() => this.remove(office.id)}>Delete</Button>
                        <Button size="sm" color="primary" tag={Link}
                                to={Constants.roomsByOfficesPath + office.companyName + '&officeId=' + office.id}>
                            Rooms</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={Constants.allOfficesPath}>Back</Button>
                    </div>
                    <h3>Offices</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Company name</th>
                            <th width="20%">Rooms number</th>
                            <th>Address</th>
                        </tr>
                        </thead>
                        <tbody>
                        {officesList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default OfficesList;