import {defineStore} from "pinia";

export const useDataStore = defineStore('data', {
    persist:true,
    state:() => {
       return{
           user:"",
           cities:"",
           car:"",
           plane:"",
           train:"",
       }
    }
})