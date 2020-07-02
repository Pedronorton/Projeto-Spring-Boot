import Api from './config'

    const post = (data) => {
        const config = {
            headers: {
                "content-Type": "multipart/form-data"
            }
        }
        return Api().post('/login', data, config);
    }
    // const post = (data) => {
    //     const config = {
    //         headers: {
    //             "content-Type": "multipart/form-data"
    //         }
    //     }
    //     return Api().post('/login', data, config);
    // }
    export default {
        post,
    }