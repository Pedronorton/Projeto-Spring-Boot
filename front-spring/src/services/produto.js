import Api from './config'


const getPage = () => {
    return Api().get('/produtos')
}

const post = (data) => {
    return Api().post('/produtos', data);
}

const del = (id) => {
    return Api().delete(`/produtos/${id}`)
}


export default {
    getPage,
    post,
    del,

}