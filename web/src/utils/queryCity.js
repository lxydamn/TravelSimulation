import $ from 'jquery'

let AdcodeToCity = new Map;

$.get('../../geoinfo-all.json', (data) => {
    for(let province of data) {
        for(let city of province.districts) {
            AdcodeToCity.set(city.name, city.adcode);
            AdcodeToCity.set(city.adcode, city.name);
        }
    }
})



export default AdcodeToCity;
