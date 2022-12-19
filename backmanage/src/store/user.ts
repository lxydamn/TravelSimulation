import { defineStore } from 'pinia';
import axios from "axios";

export let useUserStore = defineStore('user', {
    persist:true,
    state: () => {
        return {
            id:"",
            username:"",
            token:"",
            photo:"",
            is_login:false,
        }
    },

    getters: {
        getToken: state => {
            return state.token;
        },
        getId: state => {
            return state.id;
        },
        getUsername: state => {
            return state.username;
        },
        getPhoto: state => {
            return state.photo;
        },
    },
    actions: {
       logout() {
           this.id = "";
           this.username = "";
           this.photo = "";
           this.token = "";
           this.is_login = false;
           localStorage.removeItem("is_login");
           localStorage.removeItem("jwt_token");
       },
   
    }
})