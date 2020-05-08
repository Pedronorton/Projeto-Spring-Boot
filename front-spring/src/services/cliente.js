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
export default {
    getAll,
    post,
    del
}