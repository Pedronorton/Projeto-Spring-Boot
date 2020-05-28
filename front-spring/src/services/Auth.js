import Api from './config'

export default {

    async isValidToken() {
        var tokenLocal = localStorage.getItem('token');
        console.log("token local " + tokenLocal);
        
        let formData = new FormData();
        formData.append('token', tokenLocal)
    
        const res = await Api().post('/auth/validateToken', formData);
        console.log(typeof res.data);
        return res.data
    },
}