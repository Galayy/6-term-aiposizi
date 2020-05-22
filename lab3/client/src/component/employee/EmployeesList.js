import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import * as Constants from '../Constants';

class EmployeesList extends Component {

    constructor(props) {
        super(props);
        this.state = {employees: [], isLoading: true, errorMessage: '', places: []};
        this.remove = this.remove.bind(this);
    }

    checkStatus = response => {
        if (response.status < 200 || response.status >= 400) {
            this.setState({errorMessage: 'Something went wrong :('});
        }
        return response;
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch(Constants.allPlacesPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({
                places: data, isLoading: false
            }));

        fetch(Constants.allEmployeesPath)
            .then(this.checkStatus)
            .then(response => response.json())
            .then(data => this.setState({employees: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(Constants.baseEmployeesPath + '/' + id, {
            method: Constants.deleteMethod,
            headers: Constants.jsonRequestHeaders
        }).then(this.checkStatus).then(() => {
            let updatedEmployees = [...this.state.employees].filter(i => i.id !== id);
            this.setState({employees: updatedEmployees});
        });
    }

    render() {
        const {employees, isLoading, errorMessage, places} = this.state;

        if (errorMessage) {
            return <h2> {errorMessage} </h2>;
        } else if (isLoading) {
            return <p>Loading...</p>;
        }

        const employeesList = employees.map(employee => {
            let filteredPlaces = places.map(place => {
                return place.employee && place.employee.id === employee.id ? place : null;
            }).filter(place => place != null);

            let place = filteredPlaces.length !== 0 ? filteredPlaces[0].number + ', room ' + filteredPlaces[0].room.number
                + ', ' + filteredPlaces[0].room.office.companyName : 'Employee has no place';

            return <tr key={employee.id}>
                <td style={{whiteSpace: 'nowrap'}}>{employee.firstName}</td>
                <td>{employee.lastName}</td>
                <td>{employee.speciality}</td>
                <td>{place}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Delete</Button>
                        <Button size="sm" color="primary" tag={Link}
                                to={Constants.baseEmployeesPath + '/' + employee.id}>Edit</Button>
                        {place === 'Employee has no place' && <Button size="sm" color="primary" tag={Link}
                                to={Constants.baseEmployeesPath + '/' + employee.id + '/place'}>Place</Button>}
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={'/'}>Back</Button>
                    </div>
                    <h3>Employees</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">First name</th>
                            <th width="20%">Last name</th>
                            <th width="20%">Speciality</th>
                            <th>Place</th>
                        </tr>
                        </thead>
                        <tbody>
                        {employeesList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={Constants.baseEmployeesPath}>Create employee</Button>
                    </div>
                </Container>
            </div>
        );
    }
}

export default EmployeesList;