import React, {Component} from 'react';
import './App.css';
import Home from './component/Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import * as Constants from './component/Constants';

import AddressesList from "./component/address/AddressesList";
import AddressEdit from "./component/address/AddressEdit";
import AddressCreate from "./component/address/AddressCreate";

import OfficesList from "./component/office/OfficesList";
import OfficeCreate from "./component/office/OfficeCreate";

import RoomsList from "./component/room/RoomsList";
import RoomCreate from "./component/room/RoomCreate";

import PlacesList from "./component/place/PlacesList";
import PlaceCreate from "./component/place/PlaceCreate";

import EmployeesList from "./component/employee/EmployeesList";
import EmployeeEdit from "./component/employee/EmployeeEdit";
import EmployeePlace from "./component/employee/EmployeePlace";
import EmployeeCreate from "./component/employee/EmployeeCreate";

class App extends Component { //TODO: check what buttons (cancel, back, home) we need
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>

                    <Route path={Constants.allAddressesPath} exact={true} component={AddressesList}/>
                    <Route path={Constants.baseAddressesPath + '/:id'} exact={true} component={AddressEdit}/>
                    <Route path={Constants.baseAddressesPath} exact={true} component={AddressCreate}/>

                    <Route path={Constants.allOfficesPath} exact={true} component={OfficesList}/>
                    <Route path={Constants.baseOfficesPath} exact={true} component={OfficeCreate}/>

                    <Route path={Constants.allRoomsPath} exact={true} component={RoomsList}/>
                    <Route path={Constants.baseRoomsPath} exact={true} component={RoomCreate}/>

                    <Route path={Constants.allPlacesPath} exact={true} component={PlacesList}/>
                    <Route path={Constants.basePlacesPath} exact={true} component={PlaceCreate}/>

                    <Route path={Constants.allEmployeesPath} exact={true} component={EmployeesList}/>
                    <Route path={Constants.baseEmployeesPath + '/:id'} exact={true} component={EmployeeEdit}/>
                    <Route path={Constants.baseEmployeesPath + '/:id/place'} exact={true} component={EmployeePlace}/>
                    <Route path={Constants.baseEmployeesPath} exact={true} component={EmployeeCreate}/>
                </Switch>
            </Router>
        )
    }
}//TODO: handle if parameters are null|empty

export default App; //TODO: add readme and refactor public folder + think about style
//TODO: + should i ignore package-lock.json?
//TODO: handle all exceptions
//TODO: unplace button
//TODO: get all with params
//TODO: empty objects in constants + in general handle constants