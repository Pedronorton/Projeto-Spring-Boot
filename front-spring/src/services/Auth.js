import Api from './config'

const getUser = () => {

    let token = localStorage.getItem('token')
    

    const config = {
        headers: {
            Authorization: "Bearer "+token
        }
    }

    return Api().get("auth/user", config)
}

export default {

    async isValidToken() {
        var tokenLocal = localStorage.getItem('token');
        
        
        let formData = new FormData();
        formData.append('token', tokenLocal)
    
        const res = await Api().post('/auth/validateToken', formData);
        
        return res.data
    },
    getUser,
}