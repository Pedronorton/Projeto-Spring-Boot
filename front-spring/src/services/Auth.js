import Api from './config'

export default {

    async isValidToken() {
        var tokenLocal = localStorage.getItem('token');
        
        
        let formData = new FormData();
        formData.append('token', tokenLocal)
    
        const res = await Api().post('/auth/validateToken', formData);
        
        return res.data
    },
}