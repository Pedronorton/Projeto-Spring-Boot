import Api from "./config"


const getAll = () => {
    return Api().get('/pedidos')
}

const post = (data) => {
    return Api().post('/pedidos', data)
}

export default {
    post,
    getAll,
}