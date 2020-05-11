import Api from './config'

const getAll = () =>{
    return Api().get('clientes')
}

const post = (data) => {
    return Api().post('/clientes', data)
}

const del = (id) => {
    return Api().delete(`/clientes/${id}`)
}

const put = (id,data) => {
    return Api().put(`/clientes/${id}`,data)
}
export default {
    getAll,
    post,
    del,
    put
}