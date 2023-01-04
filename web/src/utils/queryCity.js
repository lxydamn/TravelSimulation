import $ from 'jquery'

let AdcodeToCity = new Map;

$.get('../../areas.json', (data) => {
    for(let province of data) {
        for(let city of province.city) {
            AdcodeToCity.set(city.city_name, city.city_adcode);
            AdcodeToCity.set(city.city_adcode, city.city_name);
        }
    }
})

export default AdcodeToCity;
