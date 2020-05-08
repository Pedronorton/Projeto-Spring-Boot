import Api from './config'

const getAll = () =>{
    return Api().get('clientes')
}

const post = (data) => {
    return Api().post('/clientes', data)
}

const deletar = (id) => {
    return Api().delete(`/clientes/${id}`)
}
export default {
    getAll,
    post,
    deletar
}