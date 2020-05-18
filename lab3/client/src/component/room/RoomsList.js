import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import * as Constants from '../Constants';

class RoomsList extends Component {

    constructor(props) {
        super(props);
        this.state = {rooms: [], isLoading: true, errorMessage: '', officeId: ''};
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
        this.setState({isLoading: true, officeId: query.substr(query.indexOf('=') + 1, )});

        console.log(this.props);
        console.log(query.substr(query.indexOf('=') + 1, ));
        fetch(Constants.allRoomsPath + this.props.location.search)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({rooms: data, isLoading: false, officeId: query.substr(query.indexOf('=') + 1, )}));
        console.log(this.state.officeId);
    }

    async remove(id) {
        await fetch(Constants.baseRoomsPath + '/' + id, {
            method: Constants.deleteMethod,
            headers: Constants.jsonRequestHeaders
        }).then(this.checkStatus)
            .then(() => {
            let updatedRooms = [...this.state.rooms].filter(i => i.id !== id);
            this.setState({rooms: updatedRooms});
        });
    }

    render() { //TODO: get all with params
        const {rooms, isLoading, errorMessage, officeId} = this.state;

        if (errorMessage) {
            return <h2> {errorMessage} </h2>;
        } else if (isLoading) {
            return <p>Loading...</p>;
        }

        const roomsList = rooms.map(room => {
            return <tr key={room.id}>
                <td style={{whiteSpace: 'nowrap'}}>{room.office.companyName}</td>
                <td>{room.number}</td>
                <td>{room.totalPlaces}</td>
                <td>{room.freePlaces}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="danger" onClick={() => this.remove(room.id)}>Delete</Button>
                        <Button size="sm" color="primary" tag={Link}
                                to={Constants.placesByRoomsPath + room.id}>Places</Button>
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
                    <h3>Rooms</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Office name</th>
                            <th width="20%">Number</th>
                            <th>Total places</th>
                            <th>Free places</th>
                        </tr>
                        </thead>
                        <tbody>
                        {roomsList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={Constants.createRoomsPath + officeId}>Create room</Button>
                    </div>
                </Container>
            </div>
        );
    }
}

export default RoomsList;