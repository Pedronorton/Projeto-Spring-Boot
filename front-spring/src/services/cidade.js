import Api from './config'

const getAll = () =>{
    return Api().get('/cidades')
}
const getId = (data) =>  {
    return Api().get(`/cidades/${data}`)
}

export default {
    getAll,
    getId,
}