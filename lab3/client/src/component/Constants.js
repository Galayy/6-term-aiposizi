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
export const placesByRoomsPath = basePlacesPath + '?rooms=';

//Employees
export const employeesPath = basePath + '/employees';

//Methods
export const putMethod = 'PUT';
export const postMethod = 'POST';
export const deleteMethod = 'DELETE';
export const getMethod = 'GET';

//Headers
export const jsonRequestHeaders = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
export const textRequestHeaders = {
    'Accept': 'text/html',
    'Content-Type': 'text/html'
};

export const errorLabelStyle = {color: 'red'};