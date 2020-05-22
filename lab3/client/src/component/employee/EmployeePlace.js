import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import Select from "react-dropdown-select";
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import * as Constants from '../Constants';

class EmployeePlace extends Component {

    emptyItem = {
        companyName: '',
        roomNumber: '',
        placeNumber: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem, errorMessage: '', places: [], rooms: [], offices: []
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

    componentDidMount() {
        this.setState({isLoading: true});

        fetch(Constants.allPlacesPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({places: data}));

        fetch(Constants.allRoomsPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({rooms: data}));

        fetch(Constants.allOfficesPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({offices: data}));
    }

    componentWillUnmount() {
        this.isLoading = false;
    }

    checkStatus = response => {
        if (response.status === 400) {
            this.setState({
                errorMessage: 'Make sure the given place and room numbers exist in pointed company :)'
            });
        } else if (response.status >= 500) {
            this.setState({errorMessage: 'Internal server error.'});
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
        this.props.history.push(Constants.allEmployeesPath);
    }

    render() {
        const {item, places, rooms, offices} = this.state;

        const companyNames = offices.map(office => {
            return {value: office.companyName, label: office.companyName};
        });
        const roomNumbers = Array.from(new Set(rooms.map(room => room.number))).map(number => {
            return {value: number, label: number}
        });
        const placeNumbers = Array.from(new Set(places.map(place => place.number))).map(number => {
            return {value: number, label: number}
        });

        const title = <h2>Place Employee</h2>;
        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="companyName">Company name</Label>
                        <Select onInput={this.handleChange} className="custom-select" value={item.companyName}
                                id="companyName" options={companyNames}>
                        </Select>
                    </FormGroup>
                    <FormGroup>
                        <Label for="roomNumber">Room number</Label>
                        <Select onInput={this.handleChange} className="custom-select" value={item.roomNumber}
                                id="roomNumber" options={roomNumbers}>
                        </Select>
                    </FormGroup>
                    <FormGroup>
                        <Label for="placeNumber">Place number</Label>
                        <Select onInput={this.handleChange} className="custom-select" value={item.placeNumber}
                                id="placeNumber" options={placeNumbers}>
                        </Select>
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