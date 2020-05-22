import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import * as Constants from '../Constants';

class PlaceCreate extends Component {

    emptyItem = {
        roomId: null,
        number: null,
        errorMessage: null
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
                    errorMessage: 'There is already a place with this number in this room :)'
                }
            });
        } else if (response.status === 404) {
            this.setState({item: {errorMessage: 'Room isn\'t found.'}});
        } else if (response.status >= 500) {
            this.setState({item: {errorMessage: 'Internal server error.'}});
        } else {
            this.props.history.push(Constants.allPlacesPath);
        }
        return response;
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        const room = item.roomId === null ? this.props.location.search.replace('?', '&')
            : '&roomId=' + item.roomId;
        await fetch(Constants.basePlacesPath + '?number=' + item.number + room,
            {
                method: Constants.postMethod,
                headers: Constants.jsonRequestHeaders
            }).then(this.checkStatus);
    }

    render() {
        const {item} = this.state;
        const title = <h2>Create Place</h2>;

        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="number">Number</Label>
                        <Input type="number" name="number" id="number" value={item.number}
                               onChange={this.handleChange} autoComplete="number"/>
                    </FormGroup>
                    {this.state.item.errorMessage &&
                    <Label style={Constants.errorLabelStyle}> {this.state.item.errorMessage} </Label>}
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={Constants.allRoomsPath}>Cancel</Button>
                        <Button color="secondary" tag={Link} to={'/'}>Home</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}

export default withRouter(PlaceCreate);