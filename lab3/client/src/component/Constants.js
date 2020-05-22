const basePath = '/api/v1'

//Addresses
export const baseAddressesPath = basePath + '/addresses';
export const allAddressesPath = baseAddressesPath + '/all';
export const emptyAddress = {
    country: null,
    city: null,
    street: null,
    number: null
};

//Offices
export const baseOfficesPath = basePath + '/offices';
export const createOfficePath = baseOfficesPath + '?addressId=';
export const allOfficesPath = baseOfficesPath + '/all';

//Rooms
export const baseRoomsPath = basePath + '/rooms';
export const createRoomsPath = baseRoomsPath + '?officeId=';
export const allRoomsPath = baseRoomsPath + '/all';
export const roomsByOfficesPath = allRoomsPath + '?offices=';

//Places
export const basePlacesPath = basePath + '/places';
export const createPlacesPath = basePlacesPath + '?roomId=';
export const allPlacesPath = basePlacesPath + '/all';
export const placesByRoomsPath = allPlacesPath + '?offices=';

//Employees
export const baseEmployeesPath = basePath + '/employees';
export const allEmployeesPath = baseEmployeesPath + '/all';

//Methods
export const putMethod = 'PUT';
export const postMethod = 'POST';
export const deleteMethod = 'DELETE';

//Headers
export const jsonRequestHeaders = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export const errorLabelStyle = {color: 'red'};