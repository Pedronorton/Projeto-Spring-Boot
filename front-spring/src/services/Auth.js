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
        var flag = false;
        if(Object.keys(res.data) != 0){
            res.data.forEach(element => {
                if(element.authority == "ROLE_ADMIN"){
                    flag = true;
                }
            })
        }
        if(flag == true){
            return "ROLE_ADMIN"
        }
    },

    async isValidToken() {
        var tokenLocal = localStorage.getItem('token');
        
        
        let formData = new FormData();
        formData.append('token', tokenLocal)
    
        const res = await Api().post('/auth/validateToken', formData);
        
        return res.data
    },
    
}