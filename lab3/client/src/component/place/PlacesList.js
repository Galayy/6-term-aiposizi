import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import * as Constants from '../Constants';

class PlacesList extends Component {

    constructor(props) {
        super(props);
        this.state = {places: [], isLoading: true, errorMessage: '', roomId: ''};
        this.remove = this.remove.bind(this);
    }

    checkStatus = response => {
        if (response.status < 200 || response.status >= 400) {
            this.setState({errorMessage: 'Something went wrong :('});
        }
        return response;
    }

    componentDidMount() {
        const query = this.props.location.search;
        this.setState({isLoading: true, roomId: query.substr(query.lastIndexOf('=') + 1,)});

        fetch(Constants.allPlacesPath + this.props.location.search)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({
                places: data, isLoading: false,
                roomId: query.substr(query.lastIndexOf('=') + 1,)
            }));
    }

    async remove(id) {
        await fetch(Constants.basePlacesPath + '/' + id, {
            method: Constants.deleteMethod,
            headers: Constants.jsonRequestHeaders
        }).then(this.checkStatus)
            .then(() => {
                let updatedPlaces = [...this.state.places].filter(i => i.id !== id);
                this.setState({places: updatedPlaces});
            });
    }

    render() {
        const {places, isLoading, errorMessage, roomId} = this.state;

        if (errorMessage) {
            return <h2> {errorMessage} </h2>;
        } else if (isLoading) {
            return <p>Loading...</p>;
        }

        const placesList = places.map(place => {
            let employee = place.employee ? place.employee.firstName + ' ' + place.employee.lastName + ', '
                + place.employee.speciality : 'Place is empty';
            return <tr key={place.id}>
                <td style={{whiteSpace: 'nowrap'}}>{place.room.office.companyName}</td>
                <td>{place.room.number}</td>
                <td>{place.number}</td>
                <td>{employee}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="danger" onClick={() => this.remove(place.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={Constants.baseOfficesPath}>Back</Button>
                    </div>
                    <h3>Places</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Office name</th>
                            <th width="20%">Room number</th>
                            <th>Place number</th>
                            <th>Employee</th>
                        </tr>
                        </thead>
                        <tbody>
                        {placesList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={Constants.createPlacesPath + roomId}>Create
                            place</Button>
                    </div>
                </Container>
            </div>
        );
    }
}

export default PlacesList;