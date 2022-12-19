import mode from '../../public/areas.json'

const AdcodeToCity = new Map();
for(let i = 0; i < mode.length; i ++) {
    const city = mode[i].city;
    for(let j of city) {
        AdcodeToCity.set(j.city_adcode, j.city_name);
        AdcodeToCity.set(j.city_name, j.city_adcode);
    }

}

export default AdcodeToCity;