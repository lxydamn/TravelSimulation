import $ from 'jquery'

let AdcodeToLng = new Map;

$.get('../../geoinfo-all.json', (data) => {
    for(let province of data) {
        for(let city of province.districts) {
            AdcodeToLng.set(city.center, city.adcode);
            AdcodeToLng.set(city.adcode, city.center);
        }
    }
})



export default AdcodeToLng;