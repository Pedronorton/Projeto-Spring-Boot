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
    let token = localStorage.getItem('token')
    const config = {
        headers: {
            Authorization: "Bearer "+token
        }
    }
    return Api().post('/pedidos', data, config)
}

export default {
    post,
    getAll,
}