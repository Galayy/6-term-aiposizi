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
                </Switch>
            </Router>
        )
    }
}

export default App;