import Api from "./config"


const getAll = () => {

    let token = localStorage.getItem('token')
    

    const config = {
        headers: {
            Authorization: "Bearer "+token
        }
    }

    return Api().get('/pedidos', config)
}

const post = (data) => {
    return Api().post('/pedidos', data)
}

export default {
    post,
    getAll,
}