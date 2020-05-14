import Api from "./config"


const post = (data) => {
    return Api().post('/pedidos', data)
}

export default {
    post,
}