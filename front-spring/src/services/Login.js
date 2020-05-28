import Api from './config'

    const post = (data) => {
    
        return Api().post('/login', data);
    }

    export default {
        post,
    }