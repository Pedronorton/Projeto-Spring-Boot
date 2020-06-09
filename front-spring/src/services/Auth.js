import Api from './config'



export default {

    async getUser () {

        let token = localStorage.getItem('token')
        
    
        const config = {
            headers: {
                Authorization: "Bearer "+token
            }
        }
    
        const res = await  Api().get("auth/user", config)
        return res.data
    },

    async isValidToken() {
        var tokenLocal = localStorage.getItem('token');
        
        
        let formData = new FormData();
        formData.append('token', tokenLocal)
    
        const res = await Api().post('/auth/validateToken', formData);
        
        return res.data
    },
    
}